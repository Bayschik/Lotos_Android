package kg.geekspro.android_lotos.ui.fragments.mainfragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentPromotionBinding


class PromotionFragment : Fragment() {

    lateinit var binding: FragmentPromotionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPromotionBinding.inflate(layoutInflater)
        val imageResId = arguments?.getInt("image_res_id")
        val title = arguments?.getString("title")
        val description = arguments?.getString("description")

        imageResId?.let { binding.imageView.setImageResource(it) }
        binding.titleTextView.text = title
        binding.descriptionTextView.text = description

        return view
    }

    companion object {
        fun newInstance(imageResId: Int, title: String, description: String): PromotionFragment {
            val fragment = PromotionFragment()
            val args = Bundle().apply {
                putInt("image_res_id", imageResId)
                putString("title", title)
                putString("description", description)
            }
            fragment.arguments = args
            return fragment
        }
    }

}