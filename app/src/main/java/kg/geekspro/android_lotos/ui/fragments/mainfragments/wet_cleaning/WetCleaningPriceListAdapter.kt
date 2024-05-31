package kg.geekspro.android_lotos.ui.fragments.mainfragments.wet_cleaning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.geekspro.android_lotos.databinding.ItemPriceListBinding
import kg.geekspro.android_lotos.models.mainmodels.CleaningService
import kg.geekspro.android_lotos.ui.fragments.mainfragments.OnTotalPriceChangedListener

class WetCleaningPriceListAdapter(
    private val serviceList:ArrayList<CleaningService>
): RecyclerView.Adapter<WetCleaningPriceListAdapter.WCPriceListViewHolder>() {

    private var totalPriceListener: OnTotalPriceChangedListener? = null

    fun setOnTotalPriceChangedListener(listener: OnTotalPriceChangedListener) {
        totalPriceListener = listener
    }

    private var totalPrice = 0

    private var selectedServices = mutableListOf<CleaningService>()

    fun getSelectedServices(): MutableList<CleaningService> {
        return selectedServices
    }

    inner class WCPriceListViewHolder(private val binding: ItemPriceListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(service: CleaningService){
            binding.tvServiceName.text = service.name
            binding.tvAmount.text = service.amount.toString()
            binding.tvServicePrice.text = "${service.price}сом"

            binding.btnIncrement.setOnClickListener{
                service.amount++
                binding.tvAmount.text = service.amount.toString()
                totalPrice += service.price
                totalPriceListener?.onTotalPriceChanged(totalPrice)
                addService(service)
            }

            binding.btnDecrement.setOnClickListener{
                if (service.amount > 0) {
                    service.amount--
                    binding.tvAmount.text = service.amount.toString()
                    totalPrice -= service.price
                    totalPriceListener?.onTotalPriceChanged(totalPrice)
                    removeService(service)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WCPriceListViewHolder {
        return WCPriceListViewHolder(ItemPriceListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = serviceList.size

    override fun onBindViewHolder(holder: WCPriceListViewHolder, position: Int) {
        holder.bind(serviceList[position])
    }

    fun addService(service: CleaningService) {
        val existingService = selectedServices.find { it.id == service.id }
        if (existingService != null) {
            existingService.amount++
        } else {
            val newService = service.copy(amount = 1)
            selectedServices.add(newService)
        }
    }

    fun removeService(service: CleaningService) {
        val existingService = selectedServices.find { it.id == service.id }
        if (existingService != null) {
            existingService.amount--
            if (existingService.amount == 0) {
                selectedServices.remove(existingService)
            }
        }
    }

    fun clearServicesList(){
        selectedServices.clear()
        totalPrice = 0
        for(i in serviceList){
            i.amount = 0
        }
    }

}