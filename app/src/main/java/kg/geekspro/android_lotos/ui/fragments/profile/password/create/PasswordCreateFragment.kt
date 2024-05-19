package kg.geekspro.android_lotos.ui.fragments.profile.password.create

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentPasswordCreateBinding
import kg.geekspro.android_lotos.models.profile.Password
import kg.geekspro.android_lotos.viewmodels.fcmviewmodel.FcmViewModel
import kg.geekspro.android_lotos.viewmodels.profileviewmodels.create.PasswordCreateViewModel

@AndroidEntryPoint
class PasswordCreateFragment : Fragment() {
    private lateinit var binding: FragmentPasswordCreateBinding
    private val viewModel: PasswordCreateViewModel by viewModels()
    private val viewModelFcm by viewModels<FcmViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fcmToken = arguments?.getString("fcmToken")
        Log.d("FCMToken", "FCM Token from arguments: $fcmToken")

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
                    viewModel.setPassword(password).observe(viewLifecycleOwner) {
                        findNavController().navigate(R.id.mainFragment)
                        fcmToken?.let { it1 ->
                            Log.d("FCMToken", "Sending FCM token to server: $it1")
                            viewModelFcm.loadFcm(fcmToken = it1).observe(viewLifecycleOwner) { response ->
                                Toast.makeText(requireContext(), "FCM Token sent successfully", Toast.LENGTH_SHORT).show()
                            }
                        }

                            }
                        }
                    }
                        } // Don't touch!!!

                    }


}