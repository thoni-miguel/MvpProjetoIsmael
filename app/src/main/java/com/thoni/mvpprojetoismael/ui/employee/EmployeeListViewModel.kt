package com.thoni.mvpprojetoismael.ui.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoni.mvpprojetoismael.domain.model.Employee
import com.thoni.mvpprojetoismael.domain.usecase.AddEmployeeUseCase
import com.thoni.mvpprojetoismael.domain.usecase.ObserveEmployeesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmployeeListViewModel(
    private val observeEmployees: ObserveEmployeesUseCase,
    private val addEmployee: AddEmployeeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmployeeListUiState())
    val uiState: StateFlow<EmployeeListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeEmployees()
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
                .collectLatest { employees ->
                    _uiState.update { current ->
                        current.copy(
                            employees = employees,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onAddEmployee(name: String, sector: String?) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSaving = true,
                    errorMessage = null,
                    shouldClearInput = false
                )
            }
            val result = addEmployee(name, sector)
            _uiState.update { current ->
                result.fold(
                    onSuccess = {
                        current.copy(
                            isSaving = false,
                            errorMessage = null,
                            shouldClearInput = true
                        )
                    },
                    onFailure = { error ->
                        current.copy(
                            isSaving = false,
                            errorMessage = error.message ?: GENERIC_SAVE_ERROR,
                            shouldClearInput = false
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
        const val GENERIC_LOAD_ERROR = "Não foi possível carregar os funcionários."
        const val GENERIC_SAVE_ERROR = "Não foi possível salvar o funcionário."
    }
}

data class EmployeeListUiState(
    val employees: List<Employee> = emptyList(),
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val shouldClearInput: Boolean = false
)

