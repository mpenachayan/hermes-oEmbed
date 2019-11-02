package gal.mpena.hermes.oembed.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import gal.mpena.hermes.oembed.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            //            val sendIntent: Intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
//                type = "text/plain"
//            }
//
//            val shareIntent = Intent.createChooser(sendIntent, null)
//            startActivity(shareIntent)

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    0
                )

            }

            val bitmap = Bitmap.createBitmap(
                embedded_webview.width,
                embedded_webview.height,
                Bitmap.Config.ARGB_8888
            )
            embedded_webview.draw(Canvas(bitmap))

            val file = File(Environment.getExternalStorageDirectory(), "${System.currentTimeMillis()}.jpg")
//            file.createNewFile()
            Log.w("DIRECTORY_DOWNLOADS", file.absolutePath)
            file.createNewFile()
            file.writeBitmap(
                bitmap,
                Bitmap.CompressFormat.JPEG,
                100
            )



        }

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)

                    val isTweetUrl =
                        sharedText?.matches(Regex(".*twitter\\.com\\/.*\\/status\\/.*"))

                    if (isTweetUrl!!) {
                        GetEmbeddedTweetTask().execute(sharedText)
                    } else {
                        Toast.makeText(
                            this,
                            "The shared text is not a Tweet Url",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
            }
            else -> {
                Log.d("MainActivity", "Ignoring Intent")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }

    inner class GetEmbeddedTweetTask : AsyncTask<String, Int, String>() {

        override fun doInBackground(vararg params: String?): String {
            val url = params[0]
            val urlConnection = URL("https://publish.twitter.com/oembed?url=$url")
                .openConnection() as HttpURLConnection
            return JSONObject(urlConnection.inputStream.bufferedReader().readText()).get("html") as String

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            embedded_webview.settings.javaScriptEnabled = true

            embedded_webview.loadData(result, "text/html", "UTF-8")

        }
    }
}