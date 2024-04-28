package kg.geekspro.android_lotos.ui.fragments.safetyPhone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentSafetyPhoneNumberBinding

class SafetyPhoneNumberFragment : Fragment() {
    private lateinit var binding:FragmentSafetyPhoneNumberBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentSafetyPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSaveNumber.setOnClickListener {
                if (etFillPhoneNumber.text.toString().isEmpty()){
                    Toast.makeText(requireContext(), "Пожалуйста введите ваш номер телефона", Toast.LENGTH_LONG).show()
                }else{
                    findNavController().navigate(R.id.safetyUpdatePhoneFragment)
                }
            }
            imgArrowBack.setOnClickListener{
                findNavController().navigateUp()
            }
        }
    }

}