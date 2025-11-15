package com.thoni.mvpprojetoismael.ui.delivery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoni.mvpprojetoismael.domain.model.Delivery
import com.thoni.mvpprojetoismael.domain.model.Employee
import com.thoni.mvpprojetoismael.domain.model.EpiType
import com.thoni.mvpprojetoismael.domain.usecase.ObserveDeliveriesUseCase
import com.thoni.mvpprojetoismael.domain.usecase.ObserveEmployeesUseCase
import com.thoni.mvpprojetoismael.domain.usecase.ObserveEpiTypesUseCase
import com.thoni.mvpprojetoismael.domain.usecase.RegisterDeliveryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeliveryListViewModel(
    private val observeDeliveries: ObserveDeliveriesUseCase,
    private val observeEmployees: ObserveEmployeesUseCase,
    private val observeEpiTypes: ObserveEpiTypesUseCase,
    private val registerDelivery: RegisterDeliveryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeliveryListUiState())
    val uiState: StateFlow<DeliveryListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeEmployees()
                .catch { throwable ->
                    _uiState.update {
                        it.copy(
                            errorMessage = it.errorMessage ?: (throwable.message ?: GENERIC_LOAD_ERROR)
                        )
                    }
                }
                .collectLatest { employees ->
                    _uiState.update { current ->
                        val selectedEmployeeId = current.selectedEmployeeId
                        val validSelection = employees.any { it.id == selectedEmployeeId }
                        current.copy(
                            employees = employees,
                            selectedEmployeeId = if (validSelection) selectedEmployeeId else null,
                            deliveries = mapDeliveries(current.deliveriesRaw, employees, current.epiTypes)
                        )
                    }
                }
        }

        viewModelScope.launch {
            observeEpiTypes()
                .catch { throwable ->
                    _uiState.update {
                        it.copy(
                            errorMessage = it.errorMessage ?: (throwable.message ?: GENERIC_LOAD_ERROR)
                        )
                    }
                }
                .collectLatest { epiTypes ->
                    _uiState.update { current ->
                        val selectedEpiTypeId = current.selectedEpiTypeId
                        val validSelection = epiTypes.any { it.id == selectedEpiTypeId }
                        current.copy(
                            epiTypes = epiTypes,
                            selectedEpiTypeId = if (validSelection) selectedEpiTypeId else null,
                            deliveries = mapDeliveries(current.deliveriesRaw, current.employees, epiTypes)
                        )
                    }
                }
        }

        viewModelScope.launch {
            observeDeliveries()
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: GENERIC_LOAD_ERROR
                        )
                    }
                }
                .collectLatest { deliveries ->
                    _uiState.update { current ->
                        current.copy(
                            deliveriesRaw = deliveries,
                            deliveries = mapDeliveries(deliveries, current.employees, current.epiTypes),
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onSelectEmployee(id: String) {
        _uiState.update { it.copy(selectedEmployeeId = id) }
    }

    fun onSelectEpiType(id: String) {
        _uiState.update { it.copy(selectedEpiTypeId = id) }
    }

    fun onRegisterDelivery() {
        val current = _uiState.value
        val employeeId = current.selectedEmployeeId
        val epiTypeId = current.selectedEpiTypeId

        if (employeeId == null || epiTypeId == null) {
            _uiState.update {
                it.copy(errorMessage = MISSING_SELECTION_ERROR)
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSaving = true,
                    errorMessage = null
                )
            }

            val result = registerDelivery(employeeId, epiTypeId)
            _uiState.update { state ->
                result.fold(
                    onSuccess = {
                        state.copy(
                            isSaving = false,
                            selectedEmployeeId = null,
                            selectedEpiTypeId = null,
                            successMessage = SUCCESS_MESSAGE
                        )
                    },
                    onFailure = { error ->
                        state.copy(
                            isSaving = false,
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

    fun onSuccessMessageShown() {
        _uiState.update { it.copy(successMessage = null) }
    }

    private fun mapDeliveries(
        deliveries: List<Delivery>,
        employees: List<Employee>,
        epiTypes: List<EpiType>
    ): List<DeliveryUiItem> {
        if (deliveries.isEmpty()) return emptyList()
        val employeeNames = employees.associateBy({ it.id }, { it.name })
        val epiTypeNames = epiTypes.associateBy({ it.id }, { it.name })
        return deliveries.map { delivery ->
            DeliveryUiItem(
                id = delivery.id,
                employeeName = employeeNames[delivery.employeeId] ?: "Funcionário desconhecido",
                epiTypeName = epiTypeNames[delivery.epiTypeId] ?: "EPI desconhecido",
                deliveredAt = delivery.deliveredAt,
                dueAt = delivery.dueAt,
                synced = delivery.synced
            )
        }
    }

    private companion object {
        const val GENERIC_LOAD_ERROR = "Não foi possível carregar as entregas."
        const val GENERIC_SAVE_ERROR = "Não foi possível registrar a entrega."
        const val MISSING_SELECTION_ERROR = "Selecione um funcionário e um EPI."
        const val SUCCESS_MESSAGE = "Entrega registrada com sucesso."
    }
}

data class DeliveryListUiState(
    val deliveriesRaw: List<Delivery> = emptyList(),
    val deliveries: List<DeliveryUiItem> = emptyList(),
    val employees: List<Employee> = emptyList(),
    val epiTypes: List<EpiType> = emptyList(),
    val selectedEmployeeId: String? = null,
    val selectedEpiTypeId: String? = null,
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

data class DeliveryUiItem(
    val id: String,
    val employeeName: String,
    val epiTypeName: String,
    val deliveredAt: Long,
    val dueAt: Long?,
    val synced: Boolean
)
