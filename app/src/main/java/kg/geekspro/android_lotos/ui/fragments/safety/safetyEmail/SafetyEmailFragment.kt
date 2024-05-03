package kg.geekspro.android_lotos.ui.fragments.safety.safetyEmail

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
import kg.geekspro.android_lotos.databinding.FragmentSafetyEmailBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SafetyEmailFragment : Fragment() {
    private lateinit var binding: FragmentSafetyEmailBinding
    private val viewModel:SafetyEmailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSafetyEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSaveData.setOnClickListener {
                if (etFillEmail.text.toString().isEmpty()) {
                    Toast.makeText(requireContext(), "Пожалуйста введите вашу эле-нную почту", Toast.LENGTH_LONG).show()
                } else {
                    val changeEmail = ChangeEmail(
                        email = etFillEmail.text.toString()
                    )
                    viewModel.viewModelScope.launch {
                        viewModel.changeEmail(changeEmail).observe(viewLifecycleOwner){
                            findNavController().navigate(R.id.safetyChangeEmailFragment)
                        }
                    }
                }
            }
            imgArrowBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

}