package kg.geekspro.android_lotos.presentation.ui.profile.pesonalData

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentPersonalDataBinding
import kg.geekspro.android_lotos.presentation.ui.data.Pref
import kg.geekspro.android_lotos.presentation.ui.data.db.App


class PersonalDataFragment : Fragment() {
    private lateinit var binding: FragmentPersonalDataBinding
    private val pref by lazy {
        Pref(requireContext())
    }

    private val getCommentMedia =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedFileUri = result.data?.data
                pref.saveImage(selectedFileUri.toString())
//                Glide.with(binding.imgPersonalDataProfile).load(selectedFileUri.toString())
//                    .into(binding.imgPersonalDataProfile)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

        /*binding.apply {

            val data = App.db.appDao().getAll()
            Toast.makeText(requireContext(), "$data", Toast.LENGTH_SHORT).show()
            if (data != null) {
                tvOfficialName.text = data.name
                tvOfficialSurname.text = data.surName
                tvOfficialEmail.text = data.email
                tvOfficialDateOfBirth.text = data.dateOfBirth
                tvOfficialPhoneNumber.text = "+996${data.phoneNumber}"
                tvOfficialAddress.text = data.address
                tvOfficialPassword.text = data.password
            } else {
                Toast.makeText(requireContext(), "$data", Toast.LENGTH_SHORT).show()
            }
            btnChangePersonalData.setOnClickListener {
                findNavController().navigate(
                    R.id.refactorDataFragment,
                    bundleOf("REFACTOR_DATA" to data)
                )
            }

            Glide.with(imgPersonalDataProfile).load(pref.getImage())
                .placeholder(R.drawable.ic_profile_placeholder).into(imgPersonalDataProfile)

            imgPersonalDataProfile.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                getCommentMedia.launch(intent)
            }


        }


    }

         */