package com.thoni.mvpprojetoismael.ui.epitype

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thoni.mvpprojetoismael.domain.usecase.AddEpiTypeUseCase
import com.thoni.mvpprojetoismael.domain.usecase.ObserveEpiTypesUseCase

class EpiTypeListViewModelFactory(
    private val observeEpiTypesUseCase: ObserveEpiTypesUseCase,
    private val addEpiTypeUseCase: AddEpiTypeUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EpiTypeListViewModel::class.java)) {
            return EpiTypeListViewModel(
                observeEpiTypesUseCase,
                addEpiTypeUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
