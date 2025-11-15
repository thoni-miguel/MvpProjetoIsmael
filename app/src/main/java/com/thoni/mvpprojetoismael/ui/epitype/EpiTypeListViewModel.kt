package com.thoni.mvpprojetoismael.ui.epitype

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoni.mvpprojetoismael.domain.model.EpiType
import com.thoni.mvpprojetoismael.domain.usecase.AddEpiTypeUseCase
import com.thoni.mvpprojetoismael.domain.usecase.ObserveEpiTypesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EpiTypeListViewModel(
    private val observeEpiTypes: ObserveEpiTypesUseCase,
    private val addEpiType: AddEpiTypeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EpiTypeListUiState())
    val uiState: StateFlow<EpiTypeListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeEpiTypes()
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .catch { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: GENERIC_LOAD_ERROR
                        )
                    }
                }
                .collectLatest { epiTypes ->
                    _uiState.update { current ->
                        current.copy(
                            epiTypes = epiTypes,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onAddEpiType(name: String, monthsValidityInput: String) {
        viewModelScope.launch {
            val trimmedMonths = monthsValidityInput.trim()
            val parsedMonths = trimmedMonths.toIntOrNull()

            if (trimmedMonths.isEmpty()) {
                _uiState.update {
                    it.copy(errorMessage = MISSING_MONTHS_ERROR, shouldClearInput = false)
                }
                return@launch
            }

            if (parsedMonths == null) {
                _uiState.update {
                    it.copy(errorMessage = INVALID_MONTHS_ERROR, shouldClearInput = false)
                }
                return@launch
            }

            _uiState.update {
                it.copy(
                    isSaving = true,
                    errorMessage = null,
                    shouldClearInput = false
                )
            }

            val result = addEpiType(name, parsedMonths)
            _uiState.update { current ->
                result.fold(
                    onSuccess = {
                        current.copy(
                            isSaving = false,
                            shouldClearInput = true,
                            errorMessage = null
                        )
                    },
                    onFailure = { error ->
                        current.copy(
                            isSaving = false,
                            shouldClearInput = false,
                            errorMessage = error.message ?: GENERIC_SAVE_ERROR
                        )
                    }
                )
            }
        }
    }

    fun onErrorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun onInputsCleared() {
        _uiState.update { it.copy(shouldClearInput = false) }
    }

    private companion object {
        const val GENERIC_LOAD_ERROR = "Não foi possível carregar os EPIs."
        const val GENERIC_SAVE_ERROR = "Não foi possível salvar o EPI."
        const val MISSING_MONTHS_ERROR = "Informe a validade em meses."
        const val INVALID_MONTHS_ERROR = "Validade deve ser um número inteiro."
    }
}

data class EpiTypeListUiState(
    val epiTypes: List<EpiType> = emptyList(),
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val shouldClearInput: Boolean = false
)
