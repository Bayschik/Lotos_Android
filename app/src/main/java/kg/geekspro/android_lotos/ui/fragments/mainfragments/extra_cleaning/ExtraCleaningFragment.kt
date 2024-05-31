package kg.geekspro.android_lotos.ui.fragments.mainfragments.extra_cleaning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentExtraCleaningBinding
import kg.geekspro.android_lotos.models.mainmodels.CleaningService
import kg.geekspro.android_lotos.ui.fragments.mainfragments.OnTotalPriceChangedListener


class ExtraCleaningFragment : Fragment(), OnTotalPriceChangedListener {

    private lateinit var binding: FragmentExtraCleaningBinding

    private lateinit var adapter: ExtraCleaningPriceListAdapter

    private val extraCleaningServices = arrayListOf(
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
        binding = FragmentExtraCleaningBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ExtraCleaningPriceListAdapter(extraCleaningServices)
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
            if(adapter.getSelectedServices().isEmpty()){
                Toast.makeText(requireContext(), "Выберите услуги!", Toast.LENGTH_SHORT).show()
            }else{
                findNavController().popBackStack()
                findNavController().navigate(R.id.calendarFragment, bundleOf("services" to adapter.getSelectedServices().toList()))
                adapter.clearServicesList()
            }

        }

    }

    override fun onTotalPriceChanged(totalPrice: Int) {
        binding.btnOrder.text = "Заказать: $totalPrice"
    }


}