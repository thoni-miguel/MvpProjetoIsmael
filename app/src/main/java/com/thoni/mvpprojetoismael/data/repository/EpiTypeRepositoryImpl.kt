package com.thoni.mvpprojetoismael.data.repository

import com.thoni.mvpprojetoismael.data.local.dao.EpiTypesDao
import com.thoni.mvpprojetoismael.domain.model.EpiType
import com.thoni.mvpprojetoismael.domain.repository.EpiTypeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EpiTypeRepositoryImpl(
    private val epiTypesDao: EpiTypesDao
) : EpiTypeRepository {
    override fun observeEpiTypes(): Flow<List<EpiType>> {
        return epiTypesDao.observeEpiTypes().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addOrUpdate(epiType: EpiType) {
        epiTypesDao.upsert(epiType.toEntity())
    }
}
