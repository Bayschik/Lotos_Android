package kg.geekspro.android_lotos.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentPasswordCreateBinding

class PasswordCreateFragment : Fragment() {
    private lateinit var binding:FragmentPasswordCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnContinue.setOnClickListener {
                if (etOfficialConfirmPassword.text.toString().isEmpty() || etOfficialPasswordCreate.text.toString().isEmpty()){
                    Toast.makeText(requireContext(), "Ввндите ваши данные", Toast.LENGTH_SHORT).show()
                }else{
                    findNavController().navigate(R.id.mainFragment)
                }
            }
        }
    }

}