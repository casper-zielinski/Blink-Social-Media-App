package com.CU.blink.Upload

import android.content.Context
import android.net.Uri
import com.CU.blink.BuildConfig
import org.json.JSONObject
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest

/**
 * Minimal, dependency-free Cloudinary image upload.
 *
 * Reads the picked image from its content [Uri] and uploads it to Cloudinary using a
 * signed upload (a SHA-1 signature built from the api secret), so no upload preset has to
 * be configured in the dashboard. Returns the hosted `secure_url` of the image, or null on
 * failure (the caller keeps the post queued and retries later).
 *
 * The credentials come from BuildConfig (filled from local.properties, which is gitignored)
 * so the api secret never ends up in version control. In a real app the signature would be
 * created by a small backend so the secret never ships in the app at all.
 */
object CloudinaryUploader {

    private val CLOUD_NAME = BuildConfig.CLOUDINARY_CLOUD_NAME
    private val API_KEY = BuildConfig.CLOUDINARY_API_KEY
    private val API_SECRET = BuildConfig.CLOUDINARY_API_SECRET

    /** Uploads the image and returns its public https URL, or null if the upload failed. */
    fun upload(context: Context, imageUri: Uri): String? {
        return try {
            val bytes = context.contentResolver.openInputStream(imageUri)?.use { it.readBytes() }
                ?: return null

            val timestamp = (System.currentTimeMillis() / 1000).toString()
            val signature = sign("timestamp=$timestamp" + API_SECRET)

            postMultipart(
                urlString = "https://api.cloudinary.com/v1_1/$CLOUD_NAME/image/upload",
                fields = mapOf(
                    "api_key" to API_KEY,
                    "timestamp" to timestamp,
                    "signature" to signature
                ),
                fileBytes = bytes
            )
        } catch (e: Exception) {
            null
        }
    }

    /** SHA-1 hex digest, as required by Cloudinary's signed upload. */
    private fun sign(data: String): String {
        val digest = MessageDigest.getInstance("SHA-1").digest(data.toByteArray(Charsets.UTF_8))
        return digest.joinToString("") { "%02x".format(it) }
    }

    /** Sends a multipart/form-data POST and returns the response's `secure_url`, or null. */
    private fun postMultipart(
        urlString: String,
        fields: Map<String, String>,
        fileBytes: ByteArray
    ): String? {
        val boundary = "----BlinkBoundary" + System.currentTimeMillis()
        val lineEnd = "\r\n"
        val connection = (URL(urlString).openConnection() as HttpURLConnection).apply {
            doInput = true
            doOutput = true
            useCaches = false
            requestMethod = "POST"
            connectTimeout = 30_000
            readTimeout = 30_000
            setRequestProperty("Connection", "Keep-Alive")
            setRequestProperty("Content-Type", "multipart/form-data; boundary=$boundary")
        }

        DataOutputStream(connection.outputStream).use { out ->
            // Text fields (api_key, timestamp, signature)
            fields.forEach { (key, value) ->
                out.writeBytes("--$boundary$lineEnd")
                out.writeBytes("Content-Disposition: form-data; name=\"$key\"$lineEnd$lineEnd")
                out.writeBytes(value + lineEnd)
            }
            // The image itself
            out.writeBytes("--$boundary$lineEnd")
            out.writeBytes(
                "Content-Disposition: form-data; name=\"file\"; filename=\"upload.jpg\"$lineEnd"
            )
            out.writeBytes("Content-Type: image/*$lineEnd$lineEnd")
            out.write(fileBytes)
            out.writeBytes(lineEnd)
            out.writeBytes("--$boundary--$lineEnd")
            out.flush()
        }

        if (connection.responseCode !in 200..299) {
            connection.disconnect()
            return null
        }

        val body = connection.inputStream.bufferedReader().use { it.readText() }
        connection.disconnect()
        return JSONObject(body).optString("secure_url").ifEmpty { null }
    }
}
