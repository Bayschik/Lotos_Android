package kg.geekspro.android_lotos.ui.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogContinue.setOnClickListener {
                if (etOfficialPhoneNumber.text.toString().isEmpty() ||
                    etOfficialPassword.text.toString().isEmpty() ) {
                    Toast.makeText(
                        requireContext(),
                        "Введите данные чтобы войти в аккаунт",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    findNavController().navigate(R.id.mainFragment)
                }
            }
            tvSignIn.setOnClickListener {
                findNavController().navigate(R.id.signOrLogFragment)
            }
        }
    }

}