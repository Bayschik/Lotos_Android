package kg.geekspro.android_lotos.ui.adapters.aboutusadapter

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentBBinding

class FragmentB() : Fragment() {

    private lateinit var binding: FragmentBBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private  const val POSITION_ARG = "position_arg"
        @JvmStatic
        fun newInstance(position: Int) = FragmentB().apply {
            arguments = Bundle().apply {
                putInt(POSITION_ARG, position)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(POSITION_ARG)?:0
        //val viewPager = binding?.btnBack

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        when(position){
            0 -> {
                binding.icImgProfMachine.setImageResource(R.drawable.ic_prof_and_machine_equipment)
                binding.titleTv.text = "Профессиональная техника и оборудование"
                binding.tvDesc.text = "Они позволяют эффективно удалять любые загрязнения.\n" +
                        "Наше специальное оборудование пылесосы для сухой и влажной уборки, парогенераторы, роторные и экстракторные машины и многое другое."
                //text 1
            }
            1 -> {
                binding.icImgProfMachine.setImageResource(R.drawable.ic_wide_assortment)
                binding.titleTv.text = "Команда проффессионалов"
                binding.tvDesc.text = "В клининговой компании « Лотос» работают специалисты с опытом работы не менее трех лет, как справиться с поставленной задачей максимально быстро и эффективно. \n" +
                        "Это один из главных наших секретов!"
                //text 2
            }
            2 -> {
                binding.icImgProfMachine.setImageResource(R.drawable.ic_team_of)
                binding.titleTv.text = "Широкий ассортимент средств для уборки"
                binding.tvDesc.text = "Мы не используем бытовую химию из масс-маркета, по скольку  они вызывают аллергические реакции и не подходят для профессиональной уборки. Мы выбираем сертифицированные экологичные средства, безопасные для людей и животных!\n" +
                        "После уборки вы заметите, на сколько легче стало дышать, ведь мы уничтожаем не только грязь, но и болезнетворные бактерии и аллергены."
                //text 3
            }
        }
    }

}