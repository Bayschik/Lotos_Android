package kg.geekspro.android_lotos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.databinding.FragmentActionsBinding
import kg.geekspro.android_lotos.databinding.FragmentHomeBinding
import kg.geekspro.android_lotos.viewmodels.mainviewmodel.MainViewModel

@AndroidEntryPoint
class ActionsFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentActionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        viewModel.loadActionsById(id!!).observe(viewLifecycleOwner){
            binding.tvTitle.text = it.title
            binding.tvDesc.text = it.description
            Glide.with(binding.imgActionId).load(it.image).into(binding.imgActionId)
        }
    }
}