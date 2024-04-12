package kg.geekspro.android_lotos.presentation.ui.profile.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnChangePassword.setOnClickListener {
                if (etOfficialCurrentPassword.text.toString().isEmpty() ||
                    etOfficialNewPassword.text.toString().isEmpty() ||
                    etOfficialSecondNewPassword.text.toString().isEmpty()
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Поля должны быть заполнены",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (etOfficialNewPassword.text.toString() !=
                    etOfficialSecondNewPassword.text.toString()
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Новые пароли должны совпадать",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    findNavController().navigateUp()
                }
            }
            tvForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.forgotPasswordFragment)
            }
            imgArrowBack.setOnClickListener{
                findNavController().navigateUp()
            }

        }
    }

}