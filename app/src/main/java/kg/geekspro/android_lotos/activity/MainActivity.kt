package kg.geekspro.android_lotos.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.ActivityMainBinding
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var pref: Pref
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            Log.d("shamal", token)
        } // Don't touch!!!

        navController = findNavController(R.id.nav_host_fragment)

        if (intent?.data != null) {
            navController.navigate(R.id.orderHistoryFragment)
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.aboutUsFragment,
                R.id.profileFragment,
                R.id.onBoardingFragment,
                R.id.exitAccountFragment
            )
        )

        if (!pref.isShow()) {
            navController.navigate(R.id.onBoardingFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment ||
                destination.id == R.id.aboutUsFragment ||
                destination.id == R.id.profileFragment ||
                destination.id == R.id.exitAccountFragment
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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val fragmentToLoad = intent.getStringExtra("fragmentToLoad")
        if (fragmentToLoad == "desiredFragment") {
            findNavController(R.id.nav_host_fragment).navigate(R.id.orderHistoryFragment)
        }
    }
}
