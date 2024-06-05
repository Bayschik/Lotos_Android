package kg.geekspro.android_lotos.ui.fragments.mainfragments.deep_cleaning

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentDeepCleaningBinding
import kg.geekspro.android_lotos.models.mainmodels.CleaningService
import kg.geekspro.android_lotos.ui.fragments.mainfragments.OnTotalPriceChangedListener


class DeepCleaningFragment : Fragment(), OnTotalPriceChangedListener {

    private lateinit var binding: FragmentDeepCleaningBinding

    private lateinit var adapter: DeepCleaningPriceListAdapter


    private val selectedBackgroundColor = R.drawable.bg_cart_top_blue
    private var roomAmount = 0
    private var isClickedVersion = 0

    private val deepCleaningServices = arrayListOf(
        CleaningService(id=0,name = "Мытье духовного шкафа", price = 300),
        CleaningService(id=1,name = "Мытье холодильника", price = 300),
        CleaningService(id=2,name = "Мытье микроволновой печи", price = 300),
        CleaningService(id=3,name = "Мытье кух гарнитуры", price = 300),
        CleaningService(id=4,name = "Диван (1 посад.место)", price = 300),
        CleaningService(id=5,name = "Кресло (1 посад.место)", price = 300),
        CleaningService(id=6,name = "Стулья (1 шт)", price = 300),
        CleaningService(id=7,name = "Матрасс односпальный", price = 300),
        CleaningService(id=8,name = "Ковролин", price = 300),
        CleaningService(id=9,name = "Кухонный уголок", price = 300),
        CleaningService(id=10,name = "Потолок в ручную", price = 300),
        CleaningService(id=11,name = "Кухня", price = 300),
        CleaningService(id=12,name = "Сан узел", price = 300),
        CleaningService(id=13,name = "Окно генеральная", price = 300),
        CleaningService(id=14,name = "Окно после ремонта", price = 300),
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeepCleaningBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DeepCleaningPriceListAdapter(deepCleaningServices)
        adapter.setOnTotalPriceChangedListener(this)
        binding.rvServices.adapter = adapter


        binding.btnBackToHomeFragment.setOnClickListener{
            findNavController().navigate(
                R.id.homeFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.deepCleaningFragment, true).build()
            )
        }

        binding.btnOrder.setOnClickListener{

            if(adapter.getSelectedServices().isEmpty()){
                Toast.makeText(requireContext(), "Выберите услуги!", Toast.LENGTH_SHORT).show()
            }else{
                findNavController().popBackStack()
                findNavController().navigate(R.id.calendarFragment, bundleOf(
                    "services" to adapter.getSelectedServices().toList(),
                    "roomAmount" to roomAmount,
                    "m2" to binding.etM2.text.toString(),
                    "typeOfRoom" to binding.etTypeOfRoom.text.toString(),
                    "roomAmount" to roomAmount

                )
                )
                adapter.clearServicesList()
            }
            Log.d("TAG", "m2: ${binding.etM2.text.toString()}")
            Log.d("TAG", "typeOfRoom: ${binding.etTypeOfRoom.text.toString()}")
            Log.d("TAG", "roomAmount: $roomAmount")

        }


        binding.btnOneVariant.setOnClickListener{
            setButtonVariantColor(1)


        }

        binding.btnTwoVariant.setOnClickListener{
            setButtonVariantColor(2)
        }

        binding.btnThreeVariant.setOnClickListener{
            setButtonVariantColor(3)

        }

        binding.etTypeOfRoom.setOnFocusChangeListener { v, hasFocus ->
            etOnFocus(hasFocus)

        }

        binding.etM2.setOnFocusChangeListener { v, hasFocus ->
            etOnFocus(hasFocus)

        }

    }

    override fun onTotalPriceChanged(totalPrice: Int) {
        binding.btnOrder.text = "Заказать: $totalPrice"
    }

    private fun etOnFocus(etHasFocus: Boolean){
        if (etHasFocus){
            binding.tvOther.setBackgroundResource(selectedBackgroundColor)
            binding.tvOther.setTextColor(Color.WHITE)
        }else{
            binding.tvOther.setTextColor(Color.GRAY)
        }

    }

    private fun setButtonVariantColor(num: Int){
        when (num) {
            1 -> {
                isClickedVersion = 1
                roomAmount = 1
                binding.tvOneVariantNum.setBackgroundResource(selectedBackgroundColor)
                binding.tvTwoVariantNum.background = null
                binding.tvThreeVariantNum.background = null

                binding.tvOneVariantNum.setTextColor(Color.WHITE)
                binding.tvTwoVariantNum.setTextColor(Color.GRAY)
                binding.tvThreeVariantNum.setTextColor(Color.GRAY)

            }
            2 -> {
                isClickedVersion = 2
                roomAmount = 2
                binding.tvOneVariantNum.background = null
                binding.tvTwoVariantNum.setBackgroundResource(selectedBackgroundColor)
                binding.tvThreeVariantNum.background = null

                binding.tvOneVariantNum.setTextColor(Color.GRAY)
                binding.tvTwoVariantNum.setTextColor(Color.WHITE)
                binding.tvThreeVariantNum.setTextColor(Color.GRAY)
            }
            3 -> {
                isClickedVersion = 3
                roomAmount = 3

                binding.tvOneVariantNum.background = null
                binding.tvTwoVariantNum.background = null
                binding.tvThreeVariantNum.setBackgroundResource(selectedBackgroundColor)

                binding.tvOneVariantNum.setTextColor(Color.GRAY)
                binding.tvTwoVariantNum.setTextColor(Color.GRAY)
                binding.tvThreeVariantNum.setTextColor(Color.WHITE)

            }
        }

        binding.tvOther.background = null
        binding.etM2.clearFocus()
        binding.etTypeOfRoom.clearFocus()
    }


}