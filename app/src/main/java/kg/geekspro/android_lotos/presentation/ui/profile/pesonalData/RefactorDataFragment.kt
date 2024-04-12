package kg.geekspro.android_lotos.presentation.ui.profile.pesonalData

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.databinding.FragmentRefactorDataBinding
import kg.geekspro.android_lotos.presentation.ui.data.Pref
import kg.geekspro.android_lotos.presentation.ui.fillData.PersonalData

class RefactorDataFragment : Fragment() {
    private lateinit var binding: FragmentRefactorDataBinding
    private val pref by lazy {
        Pref(requireContext())
    }

    private val getCommentMedia =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedFileUri = result.data?.data
                pref.saveImage(selectedFileUri.toString())
                /*Glide.with(binding.imageProfile).load(selectedFileUri.toString())
                    .into(binding.imageProfile)*/
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRefactorDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arguments?.getSerializable("REFACTOR_DATA") as PersonalData?
        Toast.makeText(requireContext(), "$data", Toast.LENGTH_SHORT).show()
        binding.apply {
            if (data != null) {
                etName.setText(data.name)
                etSurname.setText(data.surName)
                etDateOfBirth.setText(data.dateOfBirth)
                etPhoneNumber.setText(data.phoneNumber)
                etEmail.setText(data.email)
                etAddress.setText(data.address)
                //etPassword.setText(data.password)

                btnSaveData.setOnClickListener {
                    val ownData = data.copy(
                        name = etName.text.toString(),
                        surName = etSurname.text.toString(),
                        dateOfBirth = etDateOfBirth.text.toString(),
                        phoneNumber = etPhoneNumber.text.toString(),
                        email = etEmail.text.toString(),
                        address = etAddress.text.toString(),
                        //password = etPassword.text.toString()
                    )
                    //App.db.appDao().update(ownData)
                    findNavController().navigateUp()
                }
            } else {
                btnSaveData.setOnClickListener {
                    val data = PersonalData(
                        name = etName.text.toString(),
                        surName = etSurname.text.toString(),
                        dateOfBirth = etDateOfBirth.text.toString(),
                        phoneNumber = etPhoneNumber.text.toString(),
                        email = etEmail.text.toString(),
                        address = etAddress.text.toString(),
                        //password = etPassword.text.toString()
                    )
                    //App.db.appDao().insert(data)
                    findNavController().navigateUp()
                }
            }
        }
    }
}

          /*  *//*Glide.with(imageProfile).load(pref.getImage())
                .placeholder(R.drawable.ic_profile_placeholder).into(imageProfile)

            imageProfile.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                getCommentMedia.launch(intent)
            }*//*
        }
    }
}*/