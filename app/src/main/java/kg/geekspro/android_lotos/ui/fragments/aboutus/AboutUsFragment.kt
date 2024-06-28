package kg.geekspro.android_lotos.ui.fragments.aboutus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import kg.geekspro.android_lotos.models.aboutusmodels.youtubemodel.Result1
import kg.geekspro.android_lotos.ui.adapters.aboutusadapter.youtubeadapter.YoutubeAdapter
import kg.geekspro.android_lotos.ui.adapters.viewpageradapter.ViewPagerAdapter
import kg.geekspro.android_lotos.viewmodels.aboutus.contactsviewmodel.WhatsAppViewModel
import kg.geekspro.android_lotos.viewmodels.aboutus.youtubevideos.VideoViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AboutUsFragment : Fragment() {

    private lateinit var binding: FragmentAboutUsBinding
    private val viewModel by viewModels<VideoViewModel>()
    private val adapter = YoutubeAdapter(this::onClick)
    private val wpViewModel by viewModels<WhatsAppViewModel>()
    private var wp: String? = null
    private var inst: String? = null
    private var tel: String? = null
    private fun onClick(result1: Result1) {
        val bundle = Bundle().apply {
            putString("url", result1.url)
            putInt("id", result1.id)
        }
        findNavController().navigate(R.id.playFragment, bundle)
    }

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
        initButtons()
        binding.rvInAboutUs.adapter = adapter

        val fragmentList = arrayListOf(
            ProfessionalMachAndEquipmentFragment(),
            TeamOfProfessionalsFragment(),
            WideAssortmentFragment()
        )

        viewModel.loadVideo().observe(viewLifecycleOwner) { response ->
            lifecycleScope.launch {
                adapter.submitData(response)
            }
        }

        wpViewModel.loadWP().observe(viewLifecycleOwner) { respons ->
            wp = respons.firstOrNull()?.url

            Log.d("Kad", "asd:${wp}")

        }

        binding.btnWhatsApp.setOnClickListener {
            wp?.let { whatsappUrl ->
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(whatsappUrl)
                startActivity(intent)
            }
        }


        ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
    }

    private fun initButtons() {
        binding.btnStoryFirst.setOnClickListener {
            findNavController().navigate(R.id.professionalMachAndEquipmentFragment)
        }
        binding.btnStorySecond.setOnClickListener {
            findNavController().navigate(R.id.teamOfProfessionalsFragment)
        }
        binding.btnStoryThird.setOnClickListener {
            findNavController().navigate(R.id.wideAssortmentFragment)
        }
    }
    // i want to check my line
}
