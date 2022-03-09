package com.test.cars.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.cars.R
import com.test.cars.data.common.getQueryTextChangeStateFlow
import com.test.cars.domain.model.Manufacturer
import com.test.cars.databinding.FragmentCarSearchBinding
import com.test.cars.presentation.model.ManufacturerSearchState
import com.test.cars.presentation.model.ManufacturerSearchState.*
import com.test.cars.presentation.viewmodel.ManufacturerSearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.koin.android.viewmodel.ext.android.viewModel

class CarSearchFragment : Fragment() {

    private var _binding: FragmentCarSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var carSearchAdapter: CarSearchAdapter
    private val list = mutableListOf<Manufacturer>()
    private val carSearchViewModel by viewModel<ManufacturerSearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeSearchState()
    }

    private fun initRecyclerView() {
        carSearchAdapter = CarSearchAdapter(list, requireContext())
        binding.searchRecyclerView.adapter = carSearchAdapter
        binding.searchView.queryHint = "Search Manufacturer";
        val options: Int = binding.searchView.imeOptions
        binding.searchView.imeOptions = options or EditorInfo.IME_FLAG_NO_EXTRACT_UI
    }

    @FlowPreview
    private fun observeSearchState() {
        lifecycleScope.launchWhenStarted {
            carSearchViewModel.searchState.collect {
                handleSearchState(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            binding.searchView.getQueryTextChangeStateFlow()
                .debounce(300)
                .distinctUntilChanged()
                .flowOn(Dispatchers.Default)
                .collect { query ->
                    if (query.isEmpty()) {
                        carSearchViewModel.resetSearch()
                    } else {
                        list.clear()
                        carSearchViewModel.getSearchedCarsListData(query)
                    }
                }
        }
    }

    private fun handleSearchState(searchState: ManufacturerSearchState) {
        when (searchState) {
            is NoResult -> {
                with(binding) {
                    progressBar.isVisible = false
                    displayNoResultDialog(getString(R.string.No_result_found_dialog_message))
                    list.clear()
                    carSearchAdapter.notifyDataSetChanged()
                }
            }
            is Loading -> {
                binding.progressBar.isVisible = true
            }
            is SearchError -> {
                binding.progressBar.isVisible = false
                displayNoResultDialog(getString(R.string.No_result_found_dialog_error_message))
            }
            is ClearSearchResult -> {
                binding.progressBar.isVisible = false
                list.clear()
                carSearchAdapter.notifyDataSetChanged()
            }
            is MatchingResult -> {
                binding.progressBar.isVisible = false
                list.addAll(searchState.list)
                carSearchAdapter.notifyDataSetChanged()
            }
            else -> Unit
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

}