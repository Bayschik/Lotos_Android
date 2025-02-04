package kg.geekspro.android_lotos.ui.fragments.onboardingfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.databinding.FragmentOnBoardingBinding
import kg.geekspro.android_lotos.ui.adapters.viewpageradapter.ViewPagerAdapter
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf(
            kg.geekspro.android_lotos.ui.fragments.screens.FirstScreenFragment(),
            kg.geekspro.android_lotos.ui.fragments.screens.SecondScreenFragment(),
            kg.geekspro.android_lotos.ui.fragments.screens.ThirdScreenFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
        binding.indicator.setViewPager(binding.viewPager)
    }

}