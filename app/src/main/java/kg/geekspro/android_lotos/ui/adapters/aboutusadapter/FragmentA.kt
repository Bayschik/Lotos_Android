package kg.geekspro.android_lotos.ui.adapters.aboutusadapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentABinding


class FragmentA : Fragment() {

    private lateinit var binding: FragmentABinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentABinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    private fun getTitle(position: Int): String? {
        return when (position) {
            0 -> "Профессиональная техника и оборудование"
            1 -> "Команда проффессионалов"
            2 -> "Широкий ассортимент средств для уборки"
            else  -> null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ViewPager2>(R.id.viewPager).adapter =
            AboutUsStoriesViewPagerAdapter(childFragmentManager, lifecycle)

    }
}