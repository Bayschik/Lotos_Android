package kg.geekspro.android_lotos.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

import com.google.firebase.messaging.FirebaseMessaging
import kg.geekspro.android_lotos.OrderHistoryFragment
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.ActivityMainBinding
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var pref:Pref
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleIntent(intent)

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            Log.d("shamal", token)
        } // Don't touch!!!

        val navController = findNavController(R.id.nav_host_fragment)

        if (intent != null && intent.hasExtra("fragmentToOpen")) {
            val fragmentToOpen = intent.getStringExtra("fragmentToOpen")
            if (fragmentToOpen.equals("MyFragment")) {
                navController.navigate(R.id.orderHistoryFragment)
            }
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

        binding.bottomNavView.setOnItemSelectedListener { item ->
            /*bottomNavView.menu.findItem(R.id.homeFragment).setIcon(R.drawable.ic_home)
            bottomNavView.menu.findItem(R.id.aboutUsFragment).setIcon(R.drawable.ic_about_us)
            bottomNavView.menu.findItem(R.id.homeFragment).setIcon(R.drawable.ic_white_profile)
*/
            when (item.itemId) {
                R.id.homeFragment -> {
                    item.setIcon(R.drawable.ic_black_home)
                    Log.d("item", "home icon")
                    true
                }
                R.id.aboutUsFragment -> {
                    item.setIcon(R.drawable.ic_black_about_us)
                    Log.d("item", "about us icon")
                    true
                }
                R.id.profileFragment -> {
                    item.setIcon(R.drawable.ic_black_profile)
                    Log.d("item", "profile icon")
                    true
                }
                else -> true
            }
        }

        setSupportActionBar(binding.myToolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavView.setupWithNavController(navController)
    }

    private fun openFragment(fragment: Fragment) {

    }

    private fun handleIntent(intent: Intent?) {
        intent?.let {
            if (it.getBooleanExtra("openFragment", false)) {
                openFragment(OrderHistoryFragment())
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

}
