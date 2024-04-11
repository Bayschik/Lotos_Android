package kg.geekspro.android_lotos.presentation.ui.onBoarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentThirdScreenBinding
import kg.geekspro.android_lotos.presentation.ui.data.Pref


class ThirdScreenFragment : Fragment() {

    private lateinit var binding:FragmentThirdScreenBinding
    private val pref by lazy {
        Pref(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnThirdNext.setOnClickListener {
            pref.onShowed()
            findNavController().navigate(R.id.signOrLogFragment)
        }
        binding.tvThirdSkip.setOnClickListener {
            findNavController().navigate(R.id.signOrLogFragment)
        }
    }

}