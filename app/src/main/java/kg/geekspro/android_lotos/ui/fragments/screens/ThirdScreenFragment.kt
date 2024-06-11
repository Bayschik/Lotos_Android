package kg.geekspro.android_lotos.ui.fragments.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentThirdScreenBinding
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import javax.inject.Inject

@AndroidEntryPoint
class ThirdScreenFragment : Fragment() {
    private lateinit var binding:FragmentThirdScreenBinding
    @Inject
    lateinit var pref: Pref
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
            findNavController().popBackStack(R.id.thirdScreenFragment, false)
        }
        binding.tvThirdSkip.setOnClickListener {
            pref.onShowed()
            findNavController().navigate(R.id.signOrLogFragment)
            findNavController().popBackStack(R.id.thirdScreenFragment, false)
        }
    }

}