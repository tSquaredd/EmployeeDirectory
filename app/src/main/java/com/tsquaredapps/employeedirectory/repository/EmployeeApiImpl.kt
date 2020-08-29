package com.tsquaredapps.employeedirectory.repository

import com.tsquaredapps.employeedirectory.model.Employee
import javax.inject.Inject

class EmployeeApiImpl
@Inject constructor(private val api: EmployeeApiService) : EmployeeApi {
    override suspend fun getEmployees(): Result<List<Employee>> = try {
        Success(api.getEmployees().employees)
    } catch (error: Throwable) {
        Failure(error)
    }
}