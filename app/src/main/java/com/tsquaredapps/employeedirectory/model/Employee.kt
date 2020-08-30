package com.tsquaredapps.employeedirectory.model

import com.tsquaredapps.employeedirectory.directory.adapter.DirectoryScreenModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Employee(
    @SerialName("uuid") val id: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("phone_number") val phoneNumber: String? = null,
    @SerialName("email_address") val emailAddress: String,
    @SerialName("biography") val biography: String? = null,
    @SerialName("photo_url_small") val smallPhotoUrl: String? = null,
    @SerialName("photo_url_large") val largePhotoUrl: String? = null,
    @SerialName("team") val team: String,
    @SerialName("employee_type") val type: EmployeeType
) : DirectoryScreenModel

@Serializable
enum class EmployeeType {
    @SerialName("FULL_TIME")
    FULL_TIME,

    @SerialName("PART_TIME")
    PART_TIME,

    @SerialName("CONTRACTOR")
    CONTRACTOR
}