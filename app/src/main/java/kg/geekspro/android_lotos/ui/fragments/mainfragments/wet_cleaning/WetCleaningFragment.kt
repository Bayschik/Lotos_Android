package kg.geekspro.android_lotos.ui.fragments.mainfragments.wet_cleaning

import android.annotation.SuppressLint
import android.content.Context

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService

import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentWetCleaningBinding
import kg.geekspro.android_lotos.models.mainmodels.CleaningService
import kg.geekspro.android_lotos.ui.fragments.mainfragments.OnTotalPriceChangedListener



class WetCleaningFragment : Fragment(), OnTotalPriceChangedListener {


    private lateinit var binding: FragmentWetCleaningBinding

    private lateinit var adapter: WetCleaningPriceListAdapter

    private val selectedBackgroundColor = R.drawable.bg_cart_top_blue
    private var roomAmount = 0
    private var isClickedVersion = 0

    private var totalPrice = 0

    private val wetCleaningServices = arrayListOf(
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
        binding = FragmentWetCleaningBinding.inflate(layoutInflater)
        return binding.root
    }


    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = WetCleaningPriceListAdapter(wetCleaningServices)
        adapter.setOnTotalPriceChangedListener(this)
        binding.rvServices.adapter = adapter


        binding.btnBackToHomeFragment.setOnClickListener{
            findNavController().navigate(
                R.id.homeFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.wetCleaningFragment, true).build()
            )
        }

        binding.btnOrder.setOnClickListener{

            findNavController().popBackStack()
            findNavController().navigate(R.id.calendarFragment, bundleOf(
                "services" to adapter.getSelectedServices().toList(),
                "roomAmount" to roomAmount,
                "m2" to binding.etM2.text.toString(),
                "typeOfRoom" to binding.etTypeOfRoom.text.toString(),
                "roomAmount" to roomAmount,
                "totalPrice" to extractNumberFromText(binding.btnOrder.text.toString())

            )
            )
            adapter.clearServicesList()

            Log.d("TAG", "m2: ${binding.etM2.text.toString()}")
            Log.d("TAG", "typeOfRoom: ${binding.etTypeOfRoom.text.toString()}")
            Log.d("TAG", "roomAmount: $roomAmount")

        }

        binding.btnOneVariant.setOnClickListener{
            setButtonVariantColor(1)
            binding.btnOrder.text = "Заказать за ${totalPrice + 5000}с"
            binding.etM2.text?.clear()

        }

        binding.btnTwoVariant.setOnClickListener{
            setButtonVariantColor(2)
            binding.btnOrder.text = "Заказать за ${totalPrice + 5000}с"
            binding.etM2.text?.clear()
        }

        binding.btnThreeVariant.setOnClickListener{
            setButtonVariantColor(3)
            binding.btnOrder.text = "Заказать за ${totalPrice + 5000}с"
            binding.etM2.text?.clear()


        }

        binding.etTypeOfRoom.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                setButtonVariantColor(4)
            }
        }

        binding.etTypeOfRoom.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                v.clearFocus()
                val imm = binding.root.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                return@setOnEditorActionListener true
            }
            false
        }


        binding.etM2.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                setButtonVariantColor(4)
            }
        }


//        binding.etM2.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//
//                v.clearFocus()
//                val imm = binding.root.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow(v.windowToken, 0)
//                return@OnEditorActionListener true
//            }
//            false
//        })


        binding.etM2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isClickedVersion == 4 && !s.isNullOrEmpty()) {
                    val newTotalPrice = totalPrice + (s.toString().toInt() * 50)
                    binding.btnOrder.text = "Заказать за ${newTotalPrice}c"
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        binding.tvWhatServices.setOnClickListener{
            binding.ivWetCleaningServices.visibility = View.VISIBLE
        }

        binding.ivWetCleaningServices.setOnClickListener{
            binding.ivWetCleaningServices.visibility = View.GONE
        }

    }

    private fun extractNumberFromText(text: String): Int {
        val regex = Regex("\\d+")
        val match = regex.find(text)
        return match?.value?.toInt() ?: 0
    }

    override fun onTotalPriceChanged(totalPrice: Int) {

        this.totalPrice = totalPrice
        when (isClickedVersion) {
            0 -> {
                binding.btnOrder.text =  "Заказать за ${totalPrice}"
            }
            in 1..3 -> {
                binding.btnOrder.text =  "Заказать за ${5000 + totalPrice}с"
            }
            4 -> {
                binding.btnOrder.text =  "Заказать за ${(binding.etM2.text.toString().toInt() * 50) + totalPrice}с"
            }
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

                binding.tvOther.background = null
                binding.tvOther.setTextColor(Color.GRAY)
                binding.etM2.clearFocus()
                binding.etTypeOfRoom.clearFocus()

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

                binding.tvOther.background = null
                binding.tvOther.setTextColor(Color.GRAY)

                binding.etM2.clearFocus()
                binding.etTypeOfRoom.clearFocus()
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

                binding.tvOther.background = null
                binding.tvOther.setTextColor(Color.GRAY)

                binding.etM2.clearFocus()
                binding.etTypeOfRoom.clearFocus()

            }
            4->{
                isClickedVersion = 4
                roomAmount = 0

                binding.tvOneVariantNum.background = null
                binding.tvTwoVariantNum.background = null
                binding.tvThreeVariantNum.background = null

                binding.tvOneVariantNum.setTextColor(Color.GRAY)
                binding.tvTwoVariantNum.setTextColor(Color.GRAY)
                binding.tvThreeVariantNum.setTextColor(Color.GRAY)

                binding.tvOther.setBackgroundResource(selectedBackgroundColor)
                binding.tvOther.setTextColor(Color.WHITE)


            }
        }
    }


}