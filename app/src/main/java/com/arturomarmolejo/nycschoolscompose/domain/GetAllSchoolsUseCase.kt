package com.arturomarmolejo.nycschoolscompose.domain

import com.arturomarmolejo.nycschoolscompose.data.domain.SchoolDomain
import com.arturomarmolejo.nycschoolscompose.data.domain.mapToSchoolDomain
import com.arturomarmolejo.nycschoolscompose.data.rest.NYCSchoolsApi
import com.arturomarmolejo.nycschoolscompose.utils.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


/**
 * [GetAllSchoolsUseCase] -
 * Business logic to retrieve the list of Schools from the API
 */
class GetAllSchoolsUseCase @Inject constructor(
    private val nycSchoolsApi: NYCSchoolsApi,
    private val ioDispatcher: CoroutineDispatcher
) {

    /**
     * [invoke] -
     * Get the information of the API. Makes a call to the network
     * .. emit success with the city name
     * else
     * .. emit error with exception
     * @return a kotlin flow with the states of the response from the API
     */
    operator fun invoke(): Flow<UIState<List<SchoolDomain>>> = flow {
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

}