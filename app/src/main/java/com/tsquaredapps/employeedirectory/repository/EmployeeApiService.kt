package com.tsquaredapps.employeedirectory.repository

import retrofit2.http.GET

interface EmployeeApiService {

    @GET("/sq-mobile-interview/employees.json")
    suspend fun getEmployees(): EmployeesResponse

}