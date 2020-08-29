package com.tsquaredapps.employeedirectory.repository

import com.tsquaredapps.employeedirectory.model.Employee
import retrofit2.http.GET

interface EmployeeApiService {

    @GET("/employees")
    suspend fun getEmployees(): List<Employee>

}