package com.thoni.mvpprojetoismael.domain.repository

import com.thoni.mvpprojetoismael.domain.model.EpiType
import kotlinx.coroutines.flow.Flow

interface EpiTypeRepository {
    fun observeEpiTypes(): Flow<List<EpiType>>
    suspend fun addOrUpdate(epiType: EpiType)
}
