package kg.geekspro.android_lotos.ui.fragments.mainfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentMainBinding
import kg.geekspro.android_lotos.ui.adapters.mainadapter.ImageSlideAdapter

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val imageList = ArrayList<Int>()
    private lateinit var imageSlideAdapter: ImageSlideAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageList.add(R.drawable.img_slide_1)
        imageList.add(R.drawable.img_slide_2)
        imageList.add(R.drawable.img_slide_3)
        imageSlideAdapter = ImageSlideAdapter(imageList)
        binding.imgSlider.adapter = imageSlideAdapter
        TabLayoutMediator(binding.slideDotLL, binding.imgSlider) { tab: TabLayout.Tab, _: Int ->
            tab.setIcon(
                R.drawable.tab_selector
            )
        }.attach()
    }
}