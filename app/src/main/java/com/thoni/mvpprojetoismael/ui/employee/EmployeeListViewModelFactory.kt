package com.thoni.mvpprojetoismael.ui.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thoni.mvpprojetoismael.domain.usecase.AddEmployeeUseCase
import com.thoni.mvpprojetoismael.domain.usecase.ObserveEmployeesUseCase

class EmployeeListViewModelFactory(
    private val observeEmployeesUseCase: ObserveEmployeesUseCase,
    private val addEmployeeUseCase: AddEmployeeUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeListViewModel::class.java)) {
            return EmployeeListViewModel(
                observeEmployeesUseCase,
                addEmployeeUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

