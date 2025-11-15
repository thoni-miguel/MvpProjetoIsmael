package com.thoni.mvpprojetoismael.domain.repository

import com.thoni.mvpprojetoismael.domain.model.Employee
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    fun observeEmployees(): Flow<List<Employee>>
    suspend fun addOrUpdate(employee: Employee)
}
