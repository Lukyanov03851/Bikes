package com.example.bikes.ui.bikes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikes.data.BikeStationsRepo
import com.example.bikes.model.domain.BikeStationModel
import com.example.bikes.network.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BikeStationsListViewModel @Inject constructor(
    private val bikeStationsRepo: BikeStationsRepo
) : ViewModel() {

    companion object {
        const val TAG = "BikeStationsViewModel"
    }

    private val _uiState = MutableStateFlow(BikeStationsListUiState())
    val uiState: StateFlow<BikeStationsListUiState> = _uiState.asStateFlow()

    init {
        loadBikeStationsList()
    }

    fun loadBikeStationsList(){
        viewModelScope.launch {
            showLoading(true)
            val response = bikeStationsRepo.getBikeStationsList()
            if (response.status == Status.SUCCESS && response.data != null){
                _uiState.update { it.copy(list = response.data) }
            } else {
                _uiState.update { it.copy(errorMsg = response.message) }
            }
            showLoading(false)
        }
    }

    private fun showLoading(value: Boolean){
        _uiState.update { it.copy(isDataLoading = value) }
    }

    fun onErrorMsgShown() {
        _uiState.update {
            it.copy(errorMsg = null)
        }
    }

}

data class BikeStationsListUiState(
    val isDataLoading: Boolean = false,
    val errorMsg: String? = null,
    val list: List<BikeStationModel>? = null,
)
