package kg.geekspro.android_lotos.ui.fragments.aboutus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentAboutUsBinding
import kg.geekspro.android_lotos.ui.adapters.viewpageradapter.ViewPagerAdapter

class AboutUsFragment : Fragment() {

    private lateinit var binding: FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    val viewPager2 = activity?.findViewById<ViewPager2>(R.id.viewPager2inAboutUS)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStoryFirst.setOnClickListener {
            findNavController().navigate(R.id.fragmentA)
        }

        val fragmentList = arrayListOf<Fragment>(
            ProfessionalMachAndEquipmentFragment(),
            TeamOfProfessionalsFragment(),
            WideAssortmentFragment()
        )


        ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        // view.viewPager.adapter = adapter
    }
}

//    val adapter = ViewPagerAdapter(
//        fragmentList,
//        requireActivity().supportFragmentManager,
//        lifecycle
//    )


//    private fun btn1setOnClickListener() {
////        findNavController().navigate(R.id.btn_videoPlay1)
//        val youtubeUrl ="https://www.youtube.com/watch?v=c4Y4BN4wJLU&list=RD0r-P9ierpTU&index=18"
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
//        startActivity(intent)
//    }
//
//    private fun btn2setOnClickListener() {
//        val youtubeUrl = "https://www.youtube.com/watch?v=c4Y4BN4wJLU&list=RD0r-P9ierpTU&index=18"
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
//        startActivity(intent)
//    }
//
//    private fun btn3setOnClickListener() {
//        val youtubeUrl = "https://www.youtube.com/watch?v=c4Y4BN4wJLU&list=RD0r-P9ierpTU&index=18"
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
//        startActivity(intent)
//    }

/*FirebaseMessaging.getInstance().token.addOnSuccessListener { token->
    Log.d("shamal", token)
}
 */


//        fragmentList.apply {
//            add(ProfessionalMachAndEquipmentFragment())
//            add(TeamOfProfessionalsFragment())
//            add(WideAssortmentFragment())
//        }
//        startFragmentNavigation() 


//    val youtubeButton: Button = findViewById(R.id.youtube_button)
//    youtubeButton.setOnClickListener {
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"))
//        startActivity(intent)
//    }


//        ivWhatsApp.setOnClickListener {
//           // openUrl("http://example.com/link1")
//        }
//        ivTelephone.setOnClickListener {
//           // openUrl("http://example.com/link2")
//        }
//        ivInstagram.setOnClickListener {
//           // openUrl("http://example.com/link3")
//        }
//    }



