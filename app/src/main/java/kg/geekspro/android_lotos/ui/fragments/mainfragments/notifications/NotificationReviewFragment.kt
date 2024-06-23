package kg.geekspro.android_lotos.ui.fragments.mainfragments.notifications

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentNotificationReviewBinding
import kg.geekspro.android_lotos.ui.fragments.mainfragments.calendar.ImagesAdapter


class NotificationReviewFragment : Fragment() {

    lateinit var binding: FragmentNotificationReviewBinding

    private lateinit var imagesAdapter: ImagesAdapter
    private val PICK_IMAGES_REQUEST_CODE = 123
    private val imagesList = mutableListOf<Uri>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationReviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibAddImage.setOnClickListener{
            openGallery()
        }


    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                imagesList.clear()
                val clipData = data.clipData
                for (i in 0 until clipData!!.itemCount) {

                    val imageUri: Uri = clipData.getItemAt(i).uri
                    imagesList.add(imageUri)
                }
            } else if (data?.data != null) {
                imagesList.clear()
                val imageUri: Uri = data.data!!
                imagesList.add(imageUri)
            }
            if(imagesList.size > 0){
                binding.ibAddImage.setColorFilter(ContextCompat.getColor(requireContext(),
                    R.color.blue
                ), PorterDuff.Mode.SRC_IN)
            }else{
                binding.ibAddImage.setColorFilter(ContextCompat.getColor(requireContext(),
                    R.color.gray_
                ), PorterDuff.Mode.SRC_IN)
            }

            binding.rvImages.visibility = View.VISIBLE
            imagesAdapter = ImagesAdapter(requireContext(), imagesList)
            binding.rvImages.layoutManager = GridLayoutManager(requireContext(), 3)
            binding.rvImages.adapter = imagesAdapter

        }
    }

}