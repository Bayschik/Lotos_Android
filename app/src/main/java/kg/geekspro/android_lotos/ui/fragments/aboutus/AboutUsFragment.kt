package kg.geekspro.android_lotos.ui.fragments.aboutus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentAboutUsBinding
import kg.geekspro.android_lotos.ui.adapters.aboutusadapter.youtubeadapter.YoutubeAdapter
import kg.geekspro.android_lotos.ui.adapters.viewpageradapter.ViewPagerAdapter
import kg.geekspro.android_lotos.viewmodels.aboutus.youtubevideos.VideoViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AboutUsFragment : Fragment() {

    private lateinit var binding: FragmentAboutUsBinding
    private val viewModel by viewModels<VideoViewModel>()
    //@Inject
    private val adapter = YoutubeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    val viewPager2 = activity?.findViewById<ViewPager2>(R.id.viewPager2inAboutUS)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initViewPager()
        initButtons()
        binding.rvInAboutUs.adapter = adapter

//        binding.btnStoryFirst.setOnClickListener {
//            findNavController().navigate(R.id.fragmentA)
//        }
        val fragmentList = arrayListOf<Fragment>(
            ProfessionalMachAndEquipmentFragment(),
            TeamOfProfessionalsFragment(),
            WideAssortmentFragment()
        )

        viewModel.loadVideo().observe(viewLifecycleOwner) { response ->
            lifecycleScope.launch {
                adapter.submitData(response)
            }
        }

        ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
    }

//    private fun initViewPager() {
//        viewPager2 = binding.viewPager2inAboutUS
//
////        viewPager = binding.viewPagerAboutUS
////        viewPagerAdapter = AboutUsStoriesViewPagerAdapter(childFragmentManager, lifecycle)
////        viewPager.adapter = viewPagerAdapter
////        binding.indicator.setViewPager(viewPager)
//    }

    private fun initButtons() {
        binding.btnStoryFirst.setOnClickListener {
            findNavController().navigate(R.id.professionalMachAndEquipmentFragment)
            // fragmentA
        }
        binding.btnStorySecond.setOnClickListener {
            //viewPager2?.setCurrentItem(1, true)
            findNavController().navigate(R.id.teamOfProfessionalsFragment)
        }
        binding.btnStoryThird.setOnClickListener {
            findNavController().navigate(R.id.wideAssortmentFragment)
        }
    }
}


/*

private var viewPager2: ViewPager2? = null
    private fun initButtons() {
        binding.btnStoryFirst.setOnClickListener {
            viewPager2?.setCurrentItem(0, true)
        }
        binding.btnStorySecond.setOnClickListener {
            viewPager2?.setCurrentItem(1, true)
        }
        binding.btnStoryThird.setOnClickListener {
            viewPager2?.setCurrentItem(2, true)
        }
    }
 */

//    private fun btn1setOnClickListener() {
////        findNavController().navigate(R.id.btn_videoPlay1)
//        val youtubeUrl ="https://www.youtube.com/watch?v=c4Y4BN4wJLU&list=RD0r-P9ierpTU&index=18"
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
//        startActivity(intent)
//    }

//        fragmentList.apply {
//            add(ProfessionalMachAndEquipmentFragment())
//            add(TeamOfProfessionalsFragment())
//            add(WideAssortmentFragment())
//        }





