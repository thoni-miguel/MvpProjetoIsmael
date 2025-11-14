package com.thoni.mvpprojetoismael.data.repository

import com.thoni.mvpprojetoismael.data.local.dao.EmployeesDao
import com.thoni.mvpprojetoismael.domain.model.Employee
import com.thoni.mvpprojetoismael.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EmployeeRepositoryImpl(
    private val employeesDao: EmployeesDao
) : EmployeeRepository {
    override fun observeEmployees(): Flow<List<Employee>> {
        return employeesDao.observeEmployees().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addOrUpdate(employee: Employee) {
        employeesDao.upsert(employee.toEntity())
    }
}
