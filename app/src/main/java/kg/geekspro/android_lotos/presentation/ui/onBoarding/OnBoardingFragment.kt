package kg.geekspro.android_lotos.presentation.ui.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.geekspro.android_lotos.databinding.FragmentOnBoardingBinding
import kg.geekspro.android_lotos.presentation.ui.data.Pref
import kg.geekspro.android_lotos.presentation.ui.onBoarding.screens.FirstScreenFragment
import kg.geekspro.android_lotos.presentation.ui.onBoarding.screens.SecondScreenFragment
import kg.geekspro.android_lotos.presentation.ui.onBoarding.screens.ThirdScreenFragment

class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    private val pref by lazy {
        Pref(requireContext())
    }

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
            FirstScreenFragment(),
            SecondScreenFragment(),
            ThirdScreenFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
    }

}