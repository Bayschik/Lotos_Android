package kg.geekspro.android_lotos.ui.adapters.aboutusadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class AboutUsStoriesViewPagerAdapter(
    // list: ArrayList<FragmentB>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return FragmentB.newInstance(position)
        // to add newInstance after nur
    }
}

/*
 override fun createFragment(position: Int): Fragment {
        val fragmentB = FragmentA()
        fragmentB.arguments = Bundle().apply {
            putInt(ARG_KEY, position + 1)
        }
        return FragmentB(position)
    }
 */