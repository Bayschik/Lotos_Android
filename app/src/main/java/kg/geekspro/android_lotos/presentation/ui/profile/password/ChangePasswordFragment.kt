package kg.geekspro.android_lotos.presentation.ui.profile.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment : Fragment() {
    private lateinit var binding:FragmentChangePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}