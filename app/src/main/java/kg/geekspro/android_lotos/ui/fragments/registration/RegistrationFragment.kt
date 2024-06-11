package kg.geekspro.android_lotos.ui.fragments.registration

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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
                    //textWatcher()
                    etSignInPhoneNumber.boxStrokeColor=R.drawable.red_border
                    Toast.makeText(requireContext(), "Введите вашу почту", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val email = Registration(
                        email = etOfficialPhoneNumber.text.toString()
                    )
                    viewModel.verifyEmail(email).observe(viewLifecycleOwner) { if (it.toString() != "Аккаунт уже зарегистрирован"){
                            findNavController().navigate(
                                R.id.verificationCodeFragment,
                                bundleOf("PHONE_NUMBER" to etOfficialPhoneNumber.text.toString())
                            )
                        }else{
                            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            btnGoogle.setOnClickListener {
                googleSignIn()
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
                viewModel.verifyEmail(registration).observe(viewLifecycleOwner){
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

    private fun textWatcher() {
        with(binding) {
            val defaultBackground: Drawable? = ContextCompat.getDrawable(requireContext(), R.drawable.default_border)
            val errorBackground: Drawable? = ContextCompat.getDrawable(requireContext(), R.drawable.red_border)
            etOfficialPhoneNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    if (s.toString().isEmpty()) {
                        etOfficialPhoneNumber.background = errorBackground
                    } else {
                        etOfficialPhoneNumber.background = defaultBackground
                    }
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}