package com.bluecheese.domain.interactor

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

private const val TAG = "retrieve-photo"

@ActivityRetainedScoped
class RetrievePhotosFromGalleryUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun perform(dateInSeconds: Long): Flow<Either<Exception, Map<Uri, Bitmap>>> {
        val result = mutableMapOf<Uri, Bitmap>()
        val isVersionNewerThanQ = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        return flow {
            withContext(Dispatchers.IO) {
                Either.runCatching {
                    val resolver = context.contentResolver
                    val imageCollection: Uri =
                        if (isVersionNewerThanQ) MediaStore.Images.Media.getContentUri(
                            MediaStore.VOLUME_EXTERNAL_PRIMARY
                        ) else MediaStore.Images.Media.EXTERNAL_CONTENT_URI

                    val epochDay = dateInSeconds.toDuration(DurationUnit.SECONDS).inWholeDays
                    val currentDate = LocalDate.ofEpochDay(epochDay).atStartOfDay()
                    val nextDate = currentDate.plusDays(1)
                    val selection =
                        MediaStore.Images.Media.DATE_ADDED + ">=? and " + MediaStore.Images.Media.DATE_ADDED + "<=?"
                    val selectionArgs = arrayOf(
                        "${currentDate.toEpochSecond(ZoneOffset.UTC)}",
                        "${nextDate.toEpochSecond(ZoneOffset.UTC)}"
                    )
                    val projection = arrayOf(
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.DATE_ADDED
                    )
                    val sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC"

                    resolver.query(
                        /* uri = */ imageCollection,
                        /* projection = */ projection,
                        /* selection = */ selection,
                        /* selectionArgs = */ selectionArgs,
                        /* sortOrder = */ sortOrder,
                    )?.use { cursor ->
                        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

                        Log.i(TAG, "Found ${cursor.count} images")
                        while (cursor.moveToNext()) {
                            val photoID = cursor.getLong(idColumn)
                            Log.d(TAG, "PhotoID: $photoID")

                            val contentUri = ContentUris.withAppendedId(
                                /* contentUri = */ MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                /* id = */ photoID
                            )
                            Log.d(TAG, "ContentUri: $contentUri")

                            val image = if (isVersionNewerThanQ) resolver.loadThumbnail(
                                /* uri = */ contentUri,
                                /* size = */ Size(640, 480),
                                /* signal = */ null
                            ) else MediaStore.Images.Thumbnails.getThumbnail(
                                /* cr = */ resolver,
                                /* imageId = */ photoID,
                                /* kind = */ MediaStore.Images.Thumbnails.MINI_KIND,
                                /* options = */ null
                            )

                            result += contentUri to image
                        }
                    }
                    result.right()
                }.getOrElse { exception: Throwable ->
                    exception.message?.let(::println)
                    Exception().left()
                }
            }.let { emit(it) }
        }
    }
}
