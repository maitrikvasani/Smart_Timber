package maitrik.smarttimber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import maitrik.smarttimber.Cut_Size.CutSizeHome
import kotlin.concurrent.thread

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        thread(true) {
            Thread.sleep(500)
            val intent = Intent(this, CutSizeHome::class.java)
            startActivity(intent)
            finish()
        }
    }
}
