package kg.geekspro.android_lotos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.OrderHistoryFragment
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.ActivityOrdersBinding

@AndroidEntryPoint
class OrdersActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOrdersBinding
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
}