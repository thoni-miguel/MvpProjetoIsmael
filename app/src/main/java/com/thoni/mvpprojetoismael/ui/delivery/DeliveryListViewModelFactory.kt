package com.thoni.mvpprojetoismael.ui.delivery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thoni.mvpprojetoismael.domain.usecase.ObserveDeliveriesUseCase
import com.thoni.mvpprojetoismael.domain.usecase.ObserveEmployeesUseCase
import com.thoni.mvpprojetoismael.domain.usecase.ObserveEpiTypesUseCase
import com.thoni.mvpprojetoismael.domain.usecase.RegisterDeliveryUseCase

class DeliveryListViewModelFactory(
    private val observeDeliveriesUseCase: ObserveDeliveriesUseCase,
    private val observeEmployeesUseCase: ObserveEmployeesUseCase,
    private val observeEpiTypesUseCase: ObserveEpiTypesUseCase,
    private val registerDeliveryUseCase: RegisterDeliveryUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeliveryListViewModel::class.java)) {
            return DeliveryListViewModel(
                observeDeliveriesUseCase,
                observeEmployeesUseCase,
                observeEpiTypesUseCase,
                registerDeliveryUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${'$'}{modelClass.name}")
    }
}
