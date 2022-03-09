package com.test.cars.presentation.ui.detail

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.cars.R
import com.test.cars.data.common.dismissKeyboard
import com.test.cars.data.common.getQueryTextChangeStateFlow
import com.test.cars.databinding.FragmentCarDetailBinding
import com.test.cars.presentation.model.ManufacturerModelSearchState
import com.test.cars.presentation.model.ManufacturerModelSearchState.*
import com.test.cars.presentation.model.ManufacturerSearchState
import com.test.cars.presentation.viewmodel.ManufacturerModelDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import org.koin.android.viewmodel.ext.android.viewModel


class CarDetailFragment : Fragment() {

    private var _binding: FragmentCarDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var carsDetailAdapter: CarsDetailAdapter
    private val carDetailViewModel by viewModel<ManufacturerModelDetailViewModel>()
    private val args: CarDetailFragmentArgs by navArgs()
    private val searchList: MutableList<Pair<String, String>> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        initUI()
        args.brandId?.let {
            carDetailViewModel.getAllModelList(it)
        }
        observeSearchState()
    }


    private fun initUI() {
        with(binding) {
            val options: Int = binding.searchView.imeOptions
            binding.searchView.imeOptions = options or EditorInfo.IME_FLAG_NO_EXTRACT_UI
            binding.searchView.queryHint = getString(R.string.hint_car_model_search_view)
            recyclerView.apply {
                carsDetailAdapter = CarsDetailAdapter(requireContext(), searchList)
                adapter = carsDetailAdapter
            }
        }
    }


    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            carDetailViewModel.modelState.collect {
                binding.progressBar.isVisible = false
                handleSearchState(it)
            }
        }
    }

    private fun retainSearchedState() {
        val query = binding.searchView.getQueryTextChangeStateFlow().value
        if (query.isEmpty()) {
            carDetailViewModel.resetSearch()
            dismissKeyboard(false)
        } else {
            carDetailViewModel.searchCarByModel(query)
        }
    }

    @Synchronized
    private fun clearAdapter() {
        searchList.clear()
        carsDetailAdapter.notifyDataSetChanged()
    }

    @Synchronized
    private fun updateAdapter(list: List<Pair<String, String>>) {
        searchList.clear()
        searchList.addAll(list.toList())
        carsDetailAdapter.notifyDataSetChanged()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        retainSearchedState()
    }

    @FlowPreview
    private fun observeSearchState() {
        val queryFlowListener = binding.searchView.getQueryTextChangeStateFlow()
            .debounce(DEBOUNCE_TIME)
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)

        lifecycleScope.launchWhenStarted {
            queryFlowListener.collect { query ->
                if (query.isEmpty()) {
                    carDetailViewModel.resetSearch()
                    dismissKeyboard(false)
                    binding.searchView.clearFocus()
                } else {
                    carDetailViewModel.searchCarByModel(query)
                }
            }
        }
    }

    private fun handleSearchState(searchState: ManufacturerModelSearchState) {
        when (searchState) {
            is NoResult -> {
                with(binding) {
                    progressBar.isVisible = false
                    clearAdapter()
                    displayNoResultDialog(getString(R.string.No_result_found_dialog_message))
                }
            }
            is Loading -> {
                binding.progressBar.isVisible = true
            }
            is ClearSearchResult -> {
                binding.progressBar.isVisible = false
                updateAdapter(searchState.list.toList())
            }
            is MatchingResult -> {
                binding.progressBar.isVisible = false
                updateAdapter(searchState.list.toList())
            }
            is SearchError -> {
                binding.progressBar.isVisible = false
                displayNoResultDialog(getString(R.string.No_result_found_dialog_error_message))
            }
        }
    }

    private fun displayNoResultDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.No_result_found_dialog_title))
            .setMessage(message)
            .setPositiveButton(
                getString(R.string.No_result_found_dialog_button_ok)
            ) { _, i -> }
            .show()
    }

    companion object {
        private const val DEBOUNCE_TIME = 300L
    }
}