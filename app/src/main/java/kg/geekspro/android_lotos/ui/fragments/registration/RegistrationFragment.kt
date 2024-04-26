package kg.geekspro.android_lotos.ui.fragments.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.viewmodels.registrationviewmodel.RegistrationViewModel
import kg.geekspro.android_lotos.databinding.FragmentRegistrationBinding
import kg.geekspro.android_lotos.models.registrationmodel.Registration

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var googleApiClient: GoogleApiClient
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnContinue.setOnClickListener {
                if (etOfficialPhoneNumber.text.toString().isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Введите вашу почту",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val email = Registration(
                        email = etOfficialPhoneNumber.text.toString()
                    )
                    viewModel.verifyEmail(email).observe(viewLifecycleOwner) {
                        Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                        findNavController().navigate(
                            R.id.verificationCodeFragment,
                            bundleOf("PHONE_NUMBER" to etOfficialPhoneNumber.text.toString())
                        )
                    }
                }
            }
            btnGoogle.setOnClickListener {
                //googleSignIn()
            }
        }
    }

    private fun googleSignIn() = with(binding) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleApiClient = GoogleApiClient.Builder(requireContext())
            .enableAutoManage(requireContext() as FragmentActivity) { connectionResult ->
                Toast.makeText(
                    requireContext(),
                    "Ошибка авторизации: ${connectionResult.errorMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        // Нажатие на кнопку регистрации через Google
        btnGoogle.setOnClickListener {
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}