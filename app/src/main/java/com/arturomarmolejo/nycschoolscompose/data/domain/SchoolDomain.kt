package com.arturomarmolejo.nycschoolscompose.data.domain

import com.arturomarmolejo.nycschoolscompose.data.apiresponse.satscores.SchoolResponseItem

data class SchoolDomain(
    val schoolName: String,
    val city: String,
    val dbn: String,
    val stateCode: String,
    val zip: String,
    val phoneNumber: String,
    val schoolEmail: String?,
)

fun List<SchoolResponseItem>?.mapToSchoolDomain(): List<SchoolDomain> =
    this?.map {
        SchoolDomain(
            schoolName = it.schoolName,
            city = it.city,
            dbn = it.dbn,
            stateCode = it.stateCode,
            zip = it.zip,
            phoneNumber = it.phoneNumber,
            schoolEmail = it.schoolEmail
        )
    } ?: emptyList()