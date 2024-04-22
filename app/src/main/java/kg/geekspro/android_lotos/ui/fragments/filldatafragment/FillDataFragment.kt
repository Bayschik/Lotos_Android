package kg.geekspro.android_lotos.ui.fragments.filldatafragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentFillDataBinding
import kg.geekspro.android_lotos.models.orderhistorymodels.PersonalData
import kg.geekspro.android_lotos.viewmodels.filldata.FillDataViewModel

class FillDataFragment : Fragment() {
    private lateinit var binding: FragmentFillDataBinding
    private val viewModel: FillDataViewModel by viewModels()

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
                    etFillAddress.text.toString().isEmpty()
                ) {
                    Toast.makeText(requireContext(), "Введите ваши данные", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val data = PersonalData(
                        name = etFillName.text.toString(),
                        surName = etFillSurname.text.toString(),
                        dateOfBirth = etFillDateOfBirth.text.toString(),
                        phoneNumber = "+996${etFillPhoneNumber.text.toString()}",
                        email = etFillEmail.text.toString(),
                        address = etFillAddress.text.toString(),
                    )
                    Toast.makeText(requireContext(), data.phoneNumber, Toast.LENGTH_SHORT).show()
                    viewModel.clientCreate(data).observe(viewLifecycleOwner) {
                        findNavController().navigate(R.id.passwordCreateFragment)
                    }
                }
            }
        }
    }

}