package kg.geekspro.android_lotos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.databinding.FragmentOrderHistoryBinding
import kg.geekspro.android_lotos.ui.adapters.orderhistory.OrderHistoryAdapter
import kg.geekspro.android_lotos.viewmodels.profileviewmodels.ProfileViewModel

@AndroidEntryPoint
class OrderHistoryFragment : Fragment() {
    private lateinit var binding:FragmentOrderHistoryBinding
    private val viewModel: ProfileViewModel by viewModels()
    private val adapter = OrderHistoryAdapter(this::nextFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            imgOrderBack.setOnClickListener {
                findNavController().navigateUp()
            }
            viewModel.getHistoryList().observe(viewLifecycleOwner) {
                if (it == null) {
                    tvNoOrder.visibility = View.VISIBLE
                } else {
                    rvOrderHistory.adapter = adapter
                    tvNoOrder.visibility = View.GONE
                    adapter.getOrderList(it.results)
                }
            }
        }
    }

    private fun nextFragment(id: Int) {
        findNavController().popBackStack()
        findNavController().navigate(R.id.orderFragment, bundleOf("ORDER_ID" to id))
    }
}