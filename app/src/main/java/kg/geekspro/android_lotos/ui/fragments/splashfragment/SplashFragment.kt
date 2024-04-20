package kg.geekspro.android_lotos.ui.fragments.splashfragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref

class SplashFragment : Fragment() {
    private val pref by lazy {
        Pref(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed({
            if (pref.isShow()){
                findNavController().navigate(R.id.mainFragment)
            }else{
                findNavController().navigate(R.id.onBoardingFragment)
            }
        },2000)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}