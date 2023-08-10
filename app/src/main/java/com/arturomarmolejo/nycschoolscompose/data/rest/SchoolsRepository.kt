package com.arturomarmolejo.nycschoolscompose.data.rest

import com.arturomarmolejo.nycschoolscompose.data.domain.SatScoreDomain
import com.arturomarmolejo.nycschoolscompose.data.domain.SchoolDomain
import com.arturomarmolejo.nycschoolscompose.data.domain.mapToSatScoreDomain
import com.arturomarmolejo.nycschoolscompose.data.domain.mapToSchoolDomain
import com.arturomarmolejo.nycschoolscompose.domain.GetAllSchoolsUseCase
import com.arturomarmolejo.nycschoolscompose.domain.GetSatScoresUseCase
import com.arturomarmolejo.nycschoolscompose.utils.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * [SchoolsRepository] -
 * Defines the methods to get the response from the API
 */
interface SchoolsRepository {

    /**
     * [getSchoolList] -
     * Gets List of Schools information from the API
     * @return the state of the school list information in a Flow
     */
    fun getSchoolList(): Flow<UIState<List<SchoolDomain>>>

    /**
     * [getSatScoresByDbn] -
     * Gets List of SAT Scores from a specific school from the API
     * @return the state of the SAT Scores information in a Flow
     */
    fun getSatScoresByDbn(dbn: String?): Flow<UIState<List<SatScoreDomain>>>

}

/**
 * [SchoolsRepositoryImpl] -
 * Implementation of [SchoolsRepository] interface
 * @param getAllSchoolsUseCase defines the specific use case functionality for getting list of schools
 * @param getSatScoresUseCase defines the specific use case functionality for getting list of scores for a school
 */

class SchoolsRepositoryImpl @Inject constructor(
    private val nycSchoolsApi: NYCSchoolsApi,
    private val ioDispatcher: CoroutineDispatcher
): SchoolsRepository {


    /**
     * [getSchoolList] -
     * This method calls the function from [getAllSchoolsUseCase]
     * to retrieve the information from the API
     * @return the state of the school list information in a Flow
     */

    override fun getSchoolList(): Flow<UIState<List<SchoolDomain>>> = flow {
        emit(UIState.LOADING)
        try {
            val response = nycSchoolsApi.getAllSchools()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.SUCCESS(it.mapToSchoolDomain()))
                } ?: throw Exception("Response body is null")
            } else {
                throw Exception(response.errorBody()?.string())
            }
        } catch (error: Exception) {
            emit(UIState.ERROR(error))
        }
    }.flowOn(ioDispatcher)

    /**
     * [getSatScoresByDbn] -
     * This method calls the function from [getSatScoresUseCase]
     * to retrieve the information from the API
     * @return the state of the score list information in a Flow
     */

    override fun getSatScoresByDbn(dbn: String?): Flow<UIState<List<SatScoreDomain>>> = flow {
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