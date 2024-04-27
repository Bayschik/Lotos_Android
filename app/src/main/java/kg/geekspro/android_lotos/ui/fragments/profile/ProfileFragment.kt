package kg.geekspro.android_lotos.ui.fragments.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.viewmodels.profileviewmodels.ProfileViewModel
import kg.geekspro.android_lotos.databinding.FragmentProfileBinding
import kg.geekspro.android_lotos.ui.adapters.orderhistory.OrderHistoryAdapter
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    @Inject
    lateinit var pref: Pref
    private lateinit var dialog: BottomSheetDialog
    private val adapter = OrderHistoryAdapter()
    private val viewModel: ProfileViewModel by viewModels()

    private val getCommentMedia =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedFileUri = result.data?.data
                pref.saveImage(selectedFileUri.toString())
                Glide.with(binding.imgProfile).load(selectedFileUri.toString())
                    .into(binding.imgProfile)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnPersonalData.setOnClickListener {
                findNavController().navigate(R.id.personalDataFragment)
            }
            setImageFromPhone()
            btnOrderHistory.setOnClickListener {showBottomNavSheet()}
            btnExit.setOnClickListener {showLogOut()}
            btnSafetyPassword.setOnClickListener {
                findNavController().navigate(R.id.safetyFragment)
            }
            viewModel.getProfile().observe(viewLifecycleOwner){
                tvUserFullName.text = "${it.lastName} ${it.firstName}"
            }
        }
    }

    private fun setImageFromPhone() = with(binding){
        Glide.with(imgProfile).load(pref.getImage()).placeholder(R.drawable.ic_profile_placeholder)
            .into(imgProfile)

        imgProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            getCommentMedia.launch(intent)
        }
    }

    private fun showLogOut() {
        val dialog = layoutInflater.inflate(R.layout.alert_dialog_layout, null)

        val btnYes = dialog.findViewById<MaterialButton>(R.id.btn_yes)
        val btnNo = dialog.findViewById<MaterialButton>(R.id.btn_no)

        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(dialog)
        val alertShow = alertDialog.create()

        btnYes.setOnClickListener {
            Toast.makeText(requireContext(), "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
            alertShow.dismiss()
        }
        btnNo.setOnClickListener {
            Toast.makeText(requireContext(), "Не вышли из аккаунта", Toast.LENGTH_SHORT).show()
            alertShow.dismiss()
        }
        alertShow.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        alertShow.show()
    }

    private fun showBottomNavSheet() {
        val bottomSheet = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        val rvOrder = bottomSheet.findViewById<RecyclerView>(R.id.rv_order_history)
        val imgArrowBack = bottomSheet.findViewById<ImageView>(R.id.img_order_back)
        imgArrowBack?.setOnClickListener{
            dialog.hide()
        }
        rvOrder.adapter = adapter
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(bottomSheet)
        dialog.show()
    }

}