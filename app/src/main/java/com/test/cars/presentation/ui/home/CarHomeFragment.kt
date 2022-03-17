package com.test.cars.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.test.cars.databinding.FragmentCarHomeBinding
import com.test.cars.presentation.viewmodel.ManufacturerHomeViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel

class CarHomeFragment : Fragment() {

    private var _binding: FragmentCarHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var carsDetailAdapter: CarsAdapter
    private val carHomeViewModel by viewModel<ManufacturerHomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        with(binding) {
            recyclerView.apply {
                carsDetailAdapter = CarsAdapter(requireContext())
                adapter = carsDetailAdapter
            }
            carsDetailAdapter.addLoadStateListener { state ->
                when (state.refresh) {
                    is LoadState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is LoadState.NotLoading -> {
                        binding.progressBar.isVisible = false
                    }
                    is LoadState.Error -> {
                        binding.progressBar.isVisible = false
                    }
                }
            }
            carsDetailAdapter.bindClickListener {
               // val action = CarHomeFragmentDirections.actionHomeFragmentAnimation()
                //action.brandId = it
                //findNavController().navigate(action)
            }
        }
    }

    private fun initViewModel() {
        lifecycleScope.launchWhenStarted {
            carHomeViewModel.allCarListDataState.collectLatest {
                binding.progressBar.isVisible = false
                carsDetailAdapter.submitData(it)
            }
        }
    }
}