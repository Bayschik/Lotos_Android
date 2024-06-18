package kg.geekspro.android_lotos.ui.fragments.aboutus

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R

class WideAssortmentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wide_assortment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtnStories(view)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            }
        )
    }

    private fun initBtnStories(view: View) {
        val btnBackStory1: ImageView? = view.findViewById(R.id.btnBackStory3)
        btnBackStory1?.setOnClickListener {
            requireActivity().onBackPressed()
        } ?: Log.e("WideAssortmentFragment", "Back button not found")
    }
}