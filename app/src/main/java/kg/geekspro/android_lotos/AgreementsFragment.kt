package kg.geekspro.android_lotos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.databinding.FragmentAgreementsBinding

class AgreementsFragment : Fragment() {
    private lateinit var binding:FragmentAgreementsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAgreementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}