package com.bluecheese.domain.interactor

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.bluecheese.domain.Done
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream
import java.util.UUID
import javax.inject.Inject

@ActivityRetainedScoped
class SavePhotoToGalleryUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun perform(photo: Bitmap): Either<Exception, Done> = withContext(Dispatchers.IO) {

        val resolver: ContentResolver = context.applicationContext.contentResolver

        val imageCollection: Uri = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )

            else -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        // Publish a new image.
        val nowTimestamp: Long = System.currentTimeMillis()
        val imageContentValues: ContentValues = ContentValues().apply {

            put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_$nowTimestamp.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.DATE_TAKEN, nowTimestamp)
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DCIM + "/bluecheese"
                )
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                put(MediaStore.Images.Media.DATE_TAKEN, nowTimestamp)
                put(MediaStore.Images.Media.DATE_ADDED, nowTimestamp)
                put(MediaStore.Images.Media.DATE_MODIFIED, nowTimestamp)
            }
        }

        val imageMediaStoreUri: Uri? = resolver.insert(imageCollection, imageContentValues)

        // Write the image data to the new Uri.
        val result = imageMediaStoreUri?.let { uri ->
            Either.runCatching {
                resolver.openOutputStream(uri).use { outputStream: OutputStream? ->
                    checkNotNull(outputStream) { "Couldn't create file for gallery, MediaStore output stream is null" }
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    imageContentValues.clear()
                    imageContentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(uri, imageContentValues, null, null)
                }

                Done.right()
            }.getOrElse { exception: Throwable ->
                exception.message?.let(::println)
                resolver.delete(uri, null, null)
                Exception().left()
            }
        } ?: Exception("Couldn't create file for gallery").left()

        return@withContext result
    }
}