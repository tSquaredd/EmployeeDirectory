package com.tsquaredapps.employeedirectory.repository

import com.tsquaredapps.employeedirectory.model.Employee

interface EmployeeApi {

    suspend fun getEmployees(): Result<List<Employee>>

}