package kg.geekspro.android_lotos.ui.fragments.safety

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentSafetyBinding
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import javax.inject.Inject

@AndroidEntryPoint
class SafetyFragment : Fragment() {
    private lateinit var binding:FragmentSafetyBinding
    private val viewModel:DeleteAccountViewModel by viewModels()
    @Inject
    lateinit var pref: Pref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentSafetyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            cvPassword.setOnClickListener { findNavController().navigate(R.id.changePasswordFragment) }
            cvPhoneNumber.setOnClickListener { findNavController().navigate(R.id.safetyPhoneNumberFragment) }
            cvEmail.setOnClickListener { findNavController().navigate(R.id.safetyEmailFragment) }
            imgArrowBack.setOnClickListener { findNavController().navigate(R.id.profileFragment) }
            btnDeleteAccount.setOnClickListener {showDeleteAccount()}
        }
    }

    private fun showDeleteAccount() {
        val dialog = layoutInflater.inflate(R.layout.alert_delete_account, null)

        val btnYes = dialog.findViewById<MaterialButton>(R.id.btn_delete_yes)
        val btnNo = dialog.findViewById<MaterialButton>(R.id.btn_delete_no)

        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(dialog)
        val alertShow = alertDialog.create()

        btnYes.setOnClickListener {
            alertShow.dismiss()
            pref.onLogOut()
            findNavController().navigateUp()
            viewModel.deleteAccount().observe(viewLifecycleOwner) {
                if (it.equals("вышли из аккаунта")){
                    Toast.makeText(requireContext(), "вы удалили аккаунт", Toast.LENGTH_SHORT).show()
                }
            }
        }
        btnNo.setOnClickListener {
            alertShow.dismiss()
        }
        alertShow.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        alertShow.show()
    }
}