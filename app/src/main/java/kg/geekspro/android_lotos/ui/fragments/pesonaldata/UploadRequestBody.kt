package kg.geekspro.android_lotos.ui.fragments.pesonaldata

import android.os.Looper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream

class UploadRequestBody(
    private val file:File,
    private val contentType:String
):RequestBody(){

    inner class ProgressUpdate(
        private val uploaded:Long,
        private val total:Long
    ):Runnable{
        override fun run() {
            //callback.onProgressUpdate((100*uploaded/total).toInt())
        }

    }

    override fun contentType() = "$contentType/*".toMediaTypeOrNull()

    override fun writeTo(sink: BufferedSink) {
        val length = file.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val fileInputStream = FileInputStream(file)
        var uploaded = 0L

        fileInputStream.use {
            var read:Int
            val handler = android.os.Handler(Looper.getMainLooper())
            while (it.read(buffer).also { read=it }!=1){
                handler.post(ProgressUpdate(uploaded, length))
                uploaded += read
                sink.write(buffer, 0, read)
            }
        }
    }

    companion object{
        private const val DEFAULT_BUFFER_SIZE = 100
    }
}