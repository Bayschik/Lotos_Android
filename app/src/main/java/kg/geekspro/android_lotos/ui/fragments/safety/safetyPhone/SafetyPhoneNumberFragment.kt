package kg.geekspro.android_lotos.ui.fragments.safety.safetyPhone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentSafetyPhoneNumberBinding
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import javax.inject.Inject

@AndroidEntryPoint
class SafetyPhoneNumberFragment : Fragment() {
    private lateinit var binding:FragmentSafetyPhoneNumberBinding
    @Inject
    lateinit var pref: Pref

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
            tvRule.text = "Текущий номер вашего мобильного телефона-+996${pref.getNumber()}? Удостоверьтесь, что ваша контактная информация указана верно, чтобы у вас было возможности сбрасывать пароль, получать уведомления"
            btnSaveNumber.setOnClickListener {
                if (etFillPhoneNumberLayout.editText?.text.toString().isEmpty()){
                    Toast.makeText(requireContext(), "Пожалуйста введите ваш номер телефона", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Переход на следующий фрагмент", Toast.LENGTH_SHORT).show()
                }
                findNavController().navigate(R.id.safetyChangePhoneFragment)
            }
            imgArrowBack.setOnClickListener{
                findNavController().navigateUp()
            }
        }
    }

}