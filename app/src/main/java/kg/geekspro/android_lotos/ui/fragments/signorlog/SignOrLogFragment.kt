package kg.geekspro.android_lotos.ui.fragments.signorlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentSignOrLogBinding

@AndroidEntryPoint
class SignOrLogFragment : Fragment() {

    private lateinit var binding: FragmentSignOrLogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentSignOrLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogIn.setOnClickListener {
                findNavController().navigate(R.id.logFragment)
            }
            btnRegistration.setOnClickListener {
                findNavController().navigate(R.id.registrationFragment)
            }
            tvSkip.setOnClickListener {
                findNavController().navigate(R.id.homeFragment)
            }
        }
    }
}