package com.arturomarmolejo.nycschoolscompose.domain

import com.arturomarmolejo.nycschoolscompose.data.domain.SatScoreDomain
import com.arturomarmolejo.nycschoolscompose.data.domain.mapToSatScoreDomain
import com.arturomarmolejo.nycschoolscompose.data.rest.NYCSchoolsApi
import com.arturomarmolejo.nycschoolscompose.utils.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * [GetSatScoresUseCase] -
 * Business logic to retrieve the list of Scores from a particular school from the API
 */
class GetSatScoresUseCase @Inject constructor(
    private val nycSchoolsApi: NYCSchoolsApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    /**
     * [invoke] -
     * Get the information of sat scores for a particular School
     * @return a kotlin flow with the states of the response from the API
     */
    operator fun invoke(dbn: String?): Flow<UIState<List<SatScoreDomain>>> = flow {
        emit(UIState.LOADING)
        try {
            val response = nycSchoolsApi.getSatScoresByDbn(dbn)
            if(response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.SUCCESS(it.mapToSatScoreDomain()))
                } ?: throw Exception("Response body is null")
            } else {
                throw Exception(response.errorBody()?.string())
            }
        } catch(error: Exception) {
            emit(UIState.ERROR(error))
        }
    }.flowOn(ioDispatcher)
}