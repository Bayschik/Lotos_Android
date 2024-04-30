package kg.geekspro.android_lotos.ui.fragments.aboutus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment() {

    private lateinit var binding: FragmentAboutUsBinding
    private val fragmentList = mutableListOf<Fragment>()
    private var currentFragmentIndex = 0
    private val handler = Handler(Looper.getMainLooper())
    private val delayMillis = 10000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutUsBinding.inflate(layoutInflater, container, false)

        fragmentList.apply {
            add(AboutUsFragment())
            add(ProfessionalMachAndEquipmentFragment())
            add(TeamOfProfessionalsFragment())
            add(WideAssortmentFragment())
        }

        // Запускаем автоматическую навигацию между фрагментами с интервалом в 10 секунд
        startFragmentNavigation()

        return binding.root
    }

    private fun startFragmentNavigation() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                // Переходим к следующему фрагменту в списке
                currentFragmentIndex = (currentFragmentIndex + 1) % fragmentList.size
                val fragment = fragmentList[currentFragmentIndex]
                val fragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.aboutUsFragment, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()

                // Запускаем задачу снова после задержки
                handler.postDelayed(this, delayMillis)
            }
        }, delayMillis)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Очищаем все задачи хендлера при уничтожении представления
        handler.removeCallbacksAndMessages(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val youTubeUrl = "http://api.example.org/accounts/?page=4"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubeUrl))
        startActivity(intent)

        val ivWhatsApp = binding.root.findViewById<Button>(R.id.ivWhatsApp)
        val ivTelephone = binding.root.findViewById<Button>(R.id.ivTelephone)
        val ivInstagram = binding.root.findViewById<Button>(R.id.ivInstagram)

        ivWhatsApp.setOnClickListener {
           // openUrl("http://example.com/link1")
        }
        ivTelephone.setOnClickListener {
           // openUrl("http://example.com/link2")
        }
        ivInstagram.setOnClickListener {
           // openUrl("http://example.com/link3")
        }
    }
}



