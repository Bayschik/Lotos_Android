package kg.geekspro.android_lotos.ui.fragments.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentProfileBinding
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.ui.adapters.orderhistory.OrderHistoryAdapter
import kg.geekspro.android_lotos.ui.fragments.profile.logOut.LogOutMessage
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import kg.geekspro.android_lotos.viewmodels.profileviewmodels.ProfileViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var pref: Pref
    private lateinit var dialog: BottomSheetDialog
    private val adapter = OrderHistoryAdapter(this::nextFragment)
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (pref.isLogOut()) {
                Toast.makeText(requireContext(), "isLogOut ${pref.isLogOut()}", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.exitAccountFragment)
            } else {
                val accessToken = Token(pref.getAccessToken())
                val verifyToken = TokenVerify(
                    detail = "Токен недействителен или просрочен",
                    code = "token_not_valid"
                )

                viewModel.checkUser(accessToken).observe(viewLifecycleOwner) { token ->
                    if (token != verifyToken) {
                        viewModel.getProfile(pref.getAccessToken()!!)
                            .observe(viewLifecycleOwner) {
                                tvUserFullName.text = "${it.lastName} ${it.firstName}"
                                btnPersonalData.setOnClickListener {
                                    findNavController().navigate(
                                        R.id.personalDataFragment
                                    )
                                }
                                if (it.photo.isNullOrEmpty()) {
                                    Glide.with(imgProfile).load(R.drawable.ic_profile_placeholder)
                                        .into(imgProfile)
                                } else {
                                    setImageFromPhone(it)
                                }
                                btnOrderHistory.setOnClickListener { showBottomNavSheet() }
                                btnExit.setOnClickListener { showLogOut() }
                                btnAgreement.setOnClickListener { findNavController().navigate(R.id.agreementsFragment) }
                                btnSafetyPassword.setOnClickListener {
                                    findNavController().navigate(R.id.safetyFragment)
                                }
                            }
                    } else {
                        val refreshToken = RefreshToken(
                            refresh = pref.getRefresh()!!
                        )
                        viewModel.refreshToken(refreshToken).observe(viewLifecycleOwner) {
                            pref.saveAccessToken(it.access)
                            viewModel.getProfile(pref.getAccessToken()!!)
                                .observe(viewLifecycleOwner) {
                                    tvUserFullName.text = "${it.lastName} ${it.firstName}"
                                    btnPersonalData.setOnClickListener {
                                        findNavController().navigate(
                                            R.id.personalDataFragment
                                        )
                                    }
                                    setImageFromPhone(it)
                                    btnOrderHistory.setOnClickListener { showBottomNavSheet() }
                                    btnExit.setOnClickListener { showLogOut() }
                                    btnAgreement.setOnClickListener { findNavController().navigate(R.id.agreementsFragment) }
                                    btnSafetyPassword.setOnClickListener {
                                        findNavController().navigate(R.id.safetyFragment)
                                    }
                                }
                        }
                    }
                }
            }
        }
    }

    private fun setImageFromPhone(model: Profile) = with(binding) {
        Glide.with(imgProfile).load("https://lotos.pp.ua${model.photo}")
            .placeholder(R.drawable.ic_profile_placeholder)
            .into(imgProfile)
    }

    private fun showLogOut() {
        val dialog = layoutInflater.inflate(R.layout.alert_dialog_layout, null)

        val btnYes = dialog.findViewById<MaterialButton>(R.id.btn_yes)
        val btnNo = dialog.findViewById<MaterialButton>(R.id.btn_no)

        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(dialog)
        val alertShow = alertDialog.create()

        btnYes.setOnClickListener {
            alertShow.dismiss()
            val message = LogOutMessage(
                message = "Вы успешно вышли из системы."
            )
            viewModel.logOut().observe(viewLifecycleOwner) {
                pref.onLogOut()
                findNavController().navigate(R.id.action_profileFragment_to_exitAccountFragment)
            }
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
        val noOrder = bottomSheet.findViewById<TextView>(R.id.tv_no_order)
        val imgArrowBack = bottomSheet.findViewById<ImageView>(R.id.img_order_back)
        imgArrowBack?.setOnClickListener {
            dialog.hide()
        }
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(bottomSheet)
        dialog.show()
        viewModel.getHistoryList().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                noOrder.visibility = View.VISIBLE
            } else {
                noOrder.visibility = View.GONE
                adapter.getOrderList(it)
                rvOrder.adapter = adapter
            }
        }
    }

    private fun nextFragment(id: Int) {
        findNavController().popBackStack()
        findNavController().navigate(R.id.orderFragment, bundleOf("ORDER_ID" to id))
        dialog.hide()
    }

}