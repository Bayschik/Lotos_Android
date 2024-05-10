package kg.geekspro.android_lotos.ui.fragments.aboutus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment() {

    private lateinit var binding: FragmentAboutUsBinding
    private val fragmentList = mutableListOf<Fragment>()
    private var currentFragmentIndex = 0
    private val delayMillis = 10000L


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn1setOnClickListener()
        btn2setOnClickListener()
        btn3setOnClickListener()
    }

    private fun btn1setOnClickListener() {
        findNavController().navigate(R.id.btn_videoPlay1)
//        val youtubeUrl = "https://www.youtube.com/watch?v=c4Y4BN4wJLU&list=RD0r-P9ierpTU&index=18"
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
//        startActivity(intent)
    }

    private fun btn2setOnClickListener() {
        val youtubeUrl = "https://www.youtube.com/watch?v=c4Y4BN4wJLU&list=RD0r-P9ierpTU&index=18"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
        startActivity(intent)
    }

    private fun btn3setOnClickListener() {
        val youtubeUrl = "https://www.youtube.com/watch?v=c4Y4BN4wJLU&list=RD0r-P9ierpTU&index=18"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
        startActivity(intent)
    }
}

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



