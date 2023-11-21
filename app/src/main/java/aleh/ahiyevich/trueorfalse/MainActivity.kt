package aleh.ahiyevich.trueorfalse

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callFullScreen()
        supportFragmentManager.beginTransaction().add(R.id.container,GameFragment()).commit()
    }


    private fun callFullScreen() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // для ранних версий API
            window.decorView.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            // для более поздних версий API
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }


}