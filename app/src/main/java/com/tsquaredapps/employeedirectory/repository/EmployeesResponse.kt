package com.tsquaredapps.employeedirectory.repository

import com.tsquaredapps.employeedirectory.model.Employee
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class EmployeesResponse(
    @SerialName("employees") val employees: List<Employee>
)