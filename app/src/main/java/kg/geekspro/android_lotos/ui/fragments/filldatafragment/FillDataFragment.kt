package kg.geekspro.android_lotos.ui.fragments.filldatafragment

import android.app.DatePickerDialog
import android.os.Bundle
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
import kg.geekspro.android_lotos.databinding.FragmentFillDataBinding
import kg.geekspro.android_lotos.models.orderhistorymodels.PersonalData
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import kg.geekspro.android_lotos.viewmodels.filldata.FillDataViewModel
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class FillDataFragment : Fragment() {
    private lateinit var binding: FragmentFillDataBinding
    private val viewModel: FillDataViewModel by viewModels()
    @Inject
    lateinit var pref: Pref
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFillDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnSaveData.setOnClickListener {
                if (etFillName.text.toString().isEmpty() ||
                    etFillSurname.text.toString().isEmpty() ||
                    etFillDateOfBirth.text.toString().isEmpty() ||
                    etFillPhoneNumber.text.toString().isEmpty() ||
                    etFillAddress.text.toString().isEmpty()
                ) {
                    Toast.makeText(requireContext(), "Введите ваши данные", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val data = PersonalData(
                        firstName = etFillName.text.toString(),
                        lastName = etFillSurname.text.toString(),
                        dateOfBirth = etFillDateOfBirth.text.toString(),
                        phoneNumber = "+996${etFillPhoneNumber.text.toString()}",
                        address = etFillAddress.text.toString(),
                    )
                    viewModel.clientCreate(data).observe(viewLifecycleOwner) {
                        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->

                            val bundle = Bundle()
                            bundle.putString("fcmToken", token)
                            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT)
                                .show()
                            pref.saveNumber(etFillPhoneNumber.text.toString())
                            if (it != "Пользователь с таким номером существует! Выберите другой номер."){
                                findNavController().popBackStack()
                                findNavController().navigate(R.id.passwordCreateFragment, bundle)
                            }
                            if (etFillPhoneNumber.text.toString().length < 9){
                                etFillPhoneNumberLayout.error = "Введите полный номер телефона"
                            }else{
                                etFillPhoneNumberLayout.error = "Пользователь с таким номером существует! Выберите другой номер."
                            }
                        }
                    }
                }
            }

            etFillDateOfBirth.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

                val dialog = DatePickerDialog(
                    requireContext(),
                    R.style.CustomDatePickerDialog, { _, year, month, dayOfMonth ->
                        binding.etFillDateOfBirth.setText("$dayOfMonth.${month + 1}.$year")
                    }, year, month, dayOfMonth

                )
                dialog.show()
            }
        }
    }

}