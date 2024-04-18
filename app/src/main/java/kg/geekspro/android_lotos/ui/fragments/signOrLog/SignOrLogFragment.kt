package kg.geekspro.android_lotos.ui.fragments.signOrLog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentSignOrLogBinding

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
                findNavController().navigate(R.id.logInFragment)
            }
            btnRegistration.setOnClickListener {
                findNavController().navigate(R.id.registrationFragment)
            }
        }
    }
}