package com.example.bikes.ui.bikes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bikes.R
import com.example.bikes.adapters.BikeStationsAdapter
import com.example.bikes.databinding.FragmentBikeStationsListBinding
import com.example.bikes.utils.collectIn
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BikeStationsListFragment : Fragment() {

    companion object {
        const val TAG = "BikeStationListFragment"
    }

    private var mBinding: FragmentBikeStationsListBinding? = null
    private val viewModel: BikeStationsListViewModel by viewModels()
    private var listAdapter: BikeStationsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBikeStationsListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        mBinding = binding
        subscribeUi()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setupPoolToRefresh()
    }

    private fun setupPoolToRefresh() {
        mBinding?.refreshContainer?.setOnRefreshListener {
            viewModel.loadBikeStationsList()
            mBinding?.refreshContainer?.isRefreshing = false
        }
    }

    private fun setupList() {
        listAdapter = BikeStationsAdapter {
            findNavController().navigate(BikeStationsListFragmentDirections.actionMap(it))
        }
        mBinding?.rvList?.adapter = listAdapter
    }

    private fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.uiState.collectIn(viewLifecycleOwner) { uiState ->
                mBinding?.progressLoading?.root?.isVisible = uiState.isDataLoading

                uiState.list?.let {
                    listAdapter?.setItemsList(it)
                }

                uiState.errorMsg?.let {
                    showErrorMsg(it)
                    viewModel.onErrorMsgShown()
                }
            }
        }
    }

    private fun showErrorMsg(msg: String) {
        view?.let { v ->
            val snackbar = Snackbar.make(v, msg, Snackbar.LENGTH_LONG)
            snackbar.setAction(getString(R.string.action_cancel)) {
                snackbar.dismiss()
            }.show()
        }
    }

}
