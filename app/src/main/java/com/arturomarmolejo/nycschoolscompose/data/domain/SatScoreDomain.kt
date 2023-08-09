package com.arturomarmolejo.nycschoolscompose.data.domain

import com.arturomarmolejo.nycschoolscompose.data.apiresponse.schools.SatScoreResponseItem

data class SatScoreDomain(
    val dbn: String,
    val satCriticalReadingAvgScore: String,
    val satMathAvgScore: String,
    val satWritingScore: String,
    val schoolName: String
)

fun List<SatScoreResponseItem>?.mapToSatScoreDomain(): List<SatScoreDomain> =
    this?.map {
        SatScoreDomain(
            dbn = it.dbn,
            satCriticalReadingAvgScore = it.satCriticalReadingAvgScore,
            satMathAvgScore = it.satMathAvgScore,
            satWritingScore = it.satWritingAvgScore,
            schoolName = it.schoolName
        )
    } ?: emptyList()
