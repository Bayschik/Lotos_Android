package kg.geekspro.android_lotos.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentLogBinding

@AndroidEntryPoint
class LogFragment : Fragment() {
    private lateinit var binding: FragmentLogBinding
    private val viewModel: LogInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogIn.setOnClickListener {
                if (etOfficialLogEmail.text.toString().isEmpty()
                    || etOfficialLogPassword.text.toString().isEmpty()
                ) {
                    Toast.makeText(requireContext(), "Введите ваши данные", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val log = LogIn(
                        email = etOfficialLogEmail.text.toString(),
                        password = etOfficialLogPassword.text.toString()
                    )
                    viewModel.logIn(log).observe(viewLifecycleOwner) {
                        Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.homeFragment)
                    }
                }
            }
            tvSignIn.setOnClickListener {
                findNavController().navigate(R.id.registrationFragment)
            }
        }
    }
}