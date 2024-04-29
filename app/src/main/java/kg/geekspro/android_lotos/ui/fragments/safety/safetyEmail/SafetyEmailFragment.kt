package kg.geekspro.android_lotos.ui.fragments.safety.safetyEmail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentSafetyEmailBinding

@AndroidEntryPoint
class SafetyEmailFragment : Fragment() {
    private lateinit var binding: FragmentSafetyEmailBinding

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
                    findNavController().navigate(R.id.safetyChangeEmailFragment)
                }
            }
            imgArrowBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

}