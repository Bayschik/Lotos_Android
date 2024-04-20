package kg.geekspro.android_lotos.ui.fragments.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentSecondScreenBinding

class SecondScreenFragment : Fragment() {
    private lateinit var binding:FragmentSecondScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.btnSecondNext.setOnClickListener {
            viewPager?.currentItem = 2
        }
        binding.tvSecondSkip.setOnClickListener {
            findNavController().navigate(R.id.signOrLogFragment)
        }
    }
}