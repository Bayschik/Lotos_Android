package kg.geekspro.android_lotos.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.messaging.FirebaseMessaging
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.ActivityMainBinding
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val pref by lazy {
        Pref(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token->
            Log.d("shamal", token)
        } // Don't touch!!!

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainFragment,
                R.id.aboutUsFragment,
                R.id.profileFragment,
                R.id.onBoardingFragment
            )
        )

        if (!pref.isShow()) {
            navController.navigate(R.id.onBoardingFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.mainFragment ||
                destination.id == R.id.aboutUsFragment ||
                destination.id == R.id.profileFragment
            ) {
                binding.bottomNavView.isVisible = true
                binding.myToolbar.isVisible = false
            } else {
                binding.bottomNavView.isVisible = false
                binding.myToolbar.isVisible = false
            }
        }

        setSupportActionBar(binding.myToolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavView.setupWithNavController(navController)
    }

}
