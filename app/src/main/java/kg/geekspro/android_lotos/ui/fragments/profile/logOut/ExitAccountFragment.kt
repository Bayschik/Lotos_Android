package kg.geekspro.android_lotos.ui.fragments.profile.logOut

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentExitAccountBinding

@AndroidEntryPoint
class ExitAccountFragment : Fragment() {
    private lateinit var binding:FragmentExitAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExitAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogIn.setOnClickListener {findNavController().navigate(R.id.logFragment)}
            btnSingIn.setOnClickListener {findNavController().navigate(R.id.registrationFragment)}
        }
    }

}