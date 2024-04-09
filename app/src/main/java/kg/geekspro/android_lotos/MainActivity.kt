package kg.geekspro.android_lotos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()

        println("Это MainActivity")
        // добавил println()
    }
}
