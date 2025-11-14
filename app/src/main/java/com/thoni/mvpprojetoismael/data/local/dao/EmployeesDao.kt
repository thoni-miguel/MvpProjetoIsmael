package com.thoni.mvpprojetoismael.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.thoni.mvpprojetoismael.data.local.entity.EmployeeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeesDao {
    @Query("SELECT * FROM employees ORDER BY name")
    fun observeEmployees(): Flow<List<EmployeeEntity>>

    @Upsert
    suspend fun upsert(employee: EmployeeEntity)
}
