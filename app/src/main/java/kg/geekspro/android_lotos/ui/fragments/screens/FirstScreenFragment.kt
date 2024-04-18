package kg.geekspro.android_lotos.ui.fragments.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentFirstScreenBinding

class FirstScreenFragment : Fragment() {
    private lateinit var binding:FragmentFirstScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.btnFirstNext.setOnClickListener {
            viewPager?.currentItem = 1
        }
        binding.tvSkip.setOnClickListener {
            findNavController().navigate(R.id.signOrLogFragment)
        }
    }

}