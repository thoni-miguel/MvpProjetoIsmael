package com.thoni.mvpprojetoismael.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.thoni.mvpprojetoismael.data.local.entity.EpiTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpiTypesDao {
    @Query("SELECT * FROM epi_types ORDER BY name")
    fun observeEpiTypes(): Flow<List<EpiTypeEntity>>

    @Upsert
    suspend fun upsert(type: EpiTypeEntity)
}
