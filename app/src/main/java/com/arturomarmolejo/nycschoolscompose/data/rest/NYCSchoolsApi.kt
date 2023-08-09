package com.arturomarmolejo.nycschoolscompose.data.rest

import com.arturomarmolejo.nycschoolscompose.data.apiresponse.satscores.SchoolResponseItem
import com.arturomarmolejo.nycschoolscompose.data.apiresponse.schools.SatScoreResponse
import com.arturomarmolejo.nycschoolscompose.data.apiresponse.schools.SatScoreResponseItem
import com.arturomarmolejo.nycschoolscompose.data.rest.NYCSchoolsApi.Companion.SAT_SCORES
import com.arturomarmolejo.nycschoolscompose.data.rest.NYCSchoolsApi.Companion.SCHOOLS_LIST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * [NYCSchoolsApi] -
 * Defines methods to call the API with Retrofit
 */
interface NYCSchoolsApi {
    /**
     * [getAllSchools] -
     * Gets List of Schools from the API
     * @return Response<List<SchoolResponseItem>
     */
    @GET(SCHOOLS_LIST)
    suspend fun getAllSchools(): Response<List<SchoolResponseItem>>


    /**
     * [getSatScoresByDbn] -
     * Gets List of Schools from the API
     * @param dbn String
     * @return Response<List<SchoolResponseItem>
     */
    @GET(SAT_SCORES)
    suspend fun getSatScoresByDbn(
        @Query("dbn") dbn: String?
    ): Response<List<SatScoreResponseItem>>

    companion object {
        //https://data.cityofnewyork.us/resource/f9bf-2cp4.json
        const val BASE_URL = "https://data.cityofnewyork.us/resource/"
        const val SCHOOLS_LIST = "s3k6-pzi2.json"
        const val SAT_SCORES = "f9bf-2cp4.json"
    }


}
