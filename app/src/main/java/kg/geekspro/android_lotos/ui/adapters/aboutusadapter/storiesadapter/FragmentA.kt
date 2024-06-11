package kg.geekspro.android_lotos.ui.adapters.aboutusadapter.storiesadapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kg.geekspro.android_lotos.databinding.FragmentABinding


class FragmentA : Fragment() {

    private lateinit var binding: FragmentABinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*
        Handler().postDelayed({

        }, 10000)
         */
        binding = FragmentABinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPagerAboutUS.adapter =
            AboutUsStoriesViewPagerAdapter(childFragmentManager, lifecycle)

        binding.indicator.setViewPager(binding.viewPagerAboutUS)
    }
}
