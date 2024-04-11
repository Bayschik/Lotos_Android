package kg.geekspro.android_lotos.presentation.ui.fillData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentFillDataBinding
import kg.geekspro.android_lotos.presentation.ui.data.db.App
import kg.geekspro.android_lotos.presentation.ui.model.PersonalData

class FillDataFragment : Fragment() {
    private lateinit var binding: FragmentFillDataBinding

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
                    etFillEmail.text.toString().isEmpty() ||
                    etFillAddress.text.toString().isEmpty() ||
                    etFillPassword.text.toString().isEmpty()
                ) {
                    Toast.makeText(requireContext(), "Введите ваши данные", Toast.LENGTH_SHORT).show()
                } else {
                    val data = PersonalData(
                        name = etFillName.text.toString(),
                        surName = etFillSurname.text.toString(),
                        dateOfBirth = etFillDateOfBirth.text.toString(),
                        phoneNumber = etFillPhoneNumber.text.toString(),
                        email = etFillEmail.text.toString(),
                        address = etFillAddress.text.toString(),
                        password = etFillPassword.text.toString()
                    )
                    App.db.appDao().insert(data)
                    findNavController().navigate(R.id.mainFragment)
                }
            }
        }
    }


}