package kg.geekspro.android_lotos.ui.fragments.registration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentRegistrationBinding
import kg.geekspro.android_lotos.models.registrationmodel.Registration
import kg.geekspro.android_lotos.viewmodels.registrationviewmodel.RegistrationViewModel

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var googleApiClient: GoogleSignInClient
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
                    etSignInPhoneNumber.error = "Заполните поле"
                } else {
                    val email = Registration(
                        email = etOfficialPhoneNumber.text.toString()
                    )
                    viewModel.verifyEmail(email).observe(viewLifecycleOwner) {
                        if (it.toString() != "Аккаунт уже зарегистрирован") {
                            findNavController().navigate(
                                R.id.verificationCodeFragment,
                                bundleOf("PHONE_NUMBER" to etOfficialPhoneNumber.text.toString())
                            )
                        } else {
                            etSignInPhoneNumber.error = it.toString()
                            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            btnGoogle.setOnClickListener {
                googleSignIn()
            }
            tvAgreement.setOnClickListener {
                findNavController().navigate(R.id.agreementsFragment)
            }
        }
    }

    private fun googleSignIn() = with(binding) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleApiClient = GoogleSignIn.getClient(requireContext(), gso)

        val signInIntent = googleApiClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val email = account.email
                val registration = Registration(
                    email = email!!
                )
                viewModel.verifyEmail(registration).observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                    findNavController().navigate(
                        R.id.homeFragment,
                        bundleOf("PHONE_NUMBER" to binding.etOfficialPhoneNumber.text.toString())
                    )
                }
                Toast.makeText(requireContext(), "Email: $email", Toast.LENGTH_SHORT).show()
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), e.statusCode, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}