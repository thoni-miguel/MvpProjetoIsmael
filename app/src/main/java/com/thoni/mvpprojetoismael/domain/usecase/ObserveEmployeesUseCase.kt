package com.thoni.mvpprojetoismael.domain.usecase

import com.thoni.mvpprojetoismael.domain.model.Employee
import com.thoni.mvpprojetoismael.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow

class ObserveEmployeesUseCase(
    private val repository: EmployeeRepository
) {
    operator fun invoke(): Flow<List<Employee>> = repository.observeEmployees()
}
