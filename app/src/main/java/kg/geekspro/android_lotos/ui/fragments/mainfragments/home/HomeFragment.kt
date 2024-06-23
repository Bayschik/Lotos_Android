package kg.geekspro.android_lotos.ui.fragments.mainfragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentHomeBinding
import kg.geekspro.android_lotos.viewmodels.mainviewmodel.MainViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var imageAdapter: ImagePagerAdapter
    private val viewModel by viewModels<MainViewModel>()

    private val images = arrayListOf(
        R.drawable.bg_promotions,
        R.drawable.img_fg,
        R.drawable.bg_promotions,
        R.drawable.img_fg,
        R.drawable.bg_promotions,
        R.drawable.img_fg,
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageAdapter = ImagePagerAdapter(requireContext(), images)
        binding.imgSlider.adapter = imageAdapter



        binding.btnNotification.setOnClickListener{
            findNavController().navigate(R.id.notificationsFragment)
        }

        updateButtonVisibility()
        binding.btnSlideRight.setOnClickListener {
            if (binding.imgSlider.currentItem < images.size - 1) {
                binding.imgSlider.currentItem += 1
            }
            updateButtonVisibility()
        }

        binding.btnSlideLeft.setOnClickListener {
            if (binding.imgSlider.currentItem > 0) {
                binding.imgSlider.currentItem -= 1
            }
            updateButtonVisibility()
        }


        val indicator = binding.slideDotLL
        indicator.setViewPager(binding.imgSlider)

        binding.imgSlider.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.btnSlideLeft.visibility = View.GONE
                        binding.btnSlideRight.visibility = View.VISIBLE
                    }
                    images.size - 1 -> {
                        binding.btnSlideRight.visibility = View.GONE
                        binding.btnSlideLeft.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.btnSlideRight.visibility = View.VISIBLE
                        binding.btnSlideLeft.visibility = View.VISIBLE
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}


        })

        viewModel.loadFcm().observe(viewLifecycleOwner){
            lifecycleScope.launch {
                binding.tvCleaningAfter.text = it.results.joinToString { it.title }
                binding.tvCleaningAfterPrice.text = it.results.joinToString { it.one_room }
            }
        }

        binding.remodelCleaningUpContainer.setOnClickListener {
            findNavController().navigate(R.id.remodelCleaningUpFragment)
        }

        binding.deepCleaningContainer.setOnClickListener {
            findNavController().navigate(R.id.deepCleaningFragment)
        }

        binding.wetCleaningContainer.setOnClickListener {
            findNavController().navigate(R.id.wetCleaningFragment)
        }

        binding.extraCleaningContainer.setOnClickListener {
            findNavController().navigate(R.id.extraCleaningFragment)
        }

    }

    private fun updateButtonVisibility() {
        binding.btnSlideRight.visibility = if (binding.imgSlider.currentItem == images.size - 1) {
            View.GONE
        } else {
            View.VISIBLE
        }

        binding.btnSlideLeft.visibility = if (binding.imgSlider.currentItem == 0) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}