package com.thoni.mvpprojetoismael.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.thoni.mvpprojetoismael.data.local.entity.DeliveryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeliveriesDao {
    @Query("SELECT * FROM deliveries ORDER BY deliveredAt DESC")
    fun observeDeliveries(): Flow<List<DeliveryEntity>>

    @Query("SELECT * FROM deliveries WHERE synced = 0")
    suspend fun pendingSync(): List<DeliveryEntity>

    @Upsert
    suspend fun upsert(delivery: DeliveryEntity)
}
