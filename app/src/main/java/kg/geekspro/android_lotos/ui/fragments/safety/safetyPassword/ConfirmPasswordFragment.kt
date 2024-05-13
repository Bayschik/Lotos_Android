package kg.geekspro.android_lotos.ui.fragments.safety.safetyPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentConfirmPasswordBinding

@AndroidEntryPoint
class ConfirmPasswordFragment : Fragment() {
    private lateinit var binding:FragmentConfirmPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSave.setOnClickListener {
                if (etOfficialConfirmPassword.text.toString() != etOfficialNewPassword.text.toString()) {
                    Toast.makeText(requireContext(), "Пароли должны совпадать", Toast.LENGTH_SHORT)
                        .show()
                } else if (etOfficialConfirmPassword.text.toString()
                        .isEmpty() || etOfficialNewPassword.text.toString().isEmpty()
                ) {
                    Toast.makeText(requireContext(), "Заполните поля", Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(R.id.profileFragment)
                }
            }
            imgArrowBack.setOnClickListener{
                findNavController().navigateUp()
            }
        }
    }
}