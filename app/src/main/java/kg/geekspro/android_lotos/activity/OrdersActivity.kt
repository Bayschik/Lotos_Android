package kg.geekspro.android_lotos.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.ActivityOrdersBinding

@AndroidEntryPoint
class OrdersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrdersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent?.data != null) {
            if (intent.extras != null) {
                val navController = findNavController(R.id.nav_fragment)
                navController.navigate(R.id.orderHistoryFragment)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.extras?.containsKey(MyFirebaseMessagingService.OPEN) == true){
            Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.orderHistoryFragment, intent.extras)
        }
    }
}