package maitrik.smarttimber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.concurrent.thread

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        thread(true) {
            Thread.sleep(3000)
            val intent = Intent(this, STHome::class.java)
            startActivity(intent)
            finish()
        }
    }
}
