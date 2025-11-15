package com.thoni.mvpprojetoismael.domain.usecase

import com.thoni.mvpprojetoismael.domain.model.EpiType
import com.thoni.mvpprojetoismael.domain.repository.EpiTypeRepository
import kotlinx.coroutines.flow.Flow

class ObserveEpiTypesUseCase(
    private val repository: EpiTypeRepository
) {
    operator fun invoke(): Flow<List<EpiType>> = repository.observeEpiTypes()
}
