package kg.geekspro.android_lotos.ui.fragments.profile.password.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.viewmodels.profileviewmodels.create.PasswordCreateViewModel
import kg.geekspro.android_lotos.databinding.FragmentPasswordCreateBinding
import kg.geekspro.android_lotos.models.profile.Password
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PasswordCreateFragment : Fragment() {
    private lateinit var binding: FragmentPasswordCreateBinding
    private val viewModel: PasswordCreateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnContinue.setOnClickListener {
                if (etOfficialConfirmPassword.text.toString()
                    .isEmpty() || etOfficialPasswordCreate.text.toString().isEmpty()
                ) {
                    Toast.makeText(requireContext(), "Придумайте пароль", Toast.LENGTH_SHORT).show()
                } else if (etOfficialConfirmPassword.text.toString() != etOfficialPasswordCreate.text?.toString()) {
                    Toast.makeText(requireContext(), "Пароли должны совпадать", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val password = Password(
                        password = etOfficialPasswordCreate.text.toString(),
                        rePassword = etOfficialConfirmPassword.text.toString()
                    )
                    viewModel.viewModelScope.launch {
                        viewModel.setPassword(password).observe(viewLifecycleOwner){
                            findNavController().navigate(R.id.mainFragment)
                        }
                    }
                }
            }
        }
    }

}