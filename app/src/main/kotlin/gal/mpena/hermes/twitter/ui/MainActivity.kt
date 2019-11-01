package gal.mpena.hermes.twitter

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import java.time.Duration

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)

                    val isTweetUrl = sharedText?.matches(Regex(".*twitter\\.com\\/.*\\/status\\/.*"))

                    if (isTweetUrl!!){
                        handleTweetUrl(sharedText)
                    }else{
                        Toast.makeText(this,"The shared text is not a Tweet Url", Toast.LENGTH_LONG).show()

                    }
                }
            }
            else -> {
                Log.d("MainActivity", "Ignoring Intent")
            }
        }
    }

    private fun handleTweetUrl(sharedText: String?) {
        val mainText = this.findViewById<TextView>(R.id.main_text)
        mainText.text = sharedText
        Toast.makeText(this,"Awesome link $sharedText", Toast.LENGTH_LONG).show()


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
}
