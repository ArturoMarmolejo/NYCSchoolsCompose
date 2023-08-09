package com.arturomarmolejo.nycschoolscompose.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arturomarmolejo.nycschoolscompose.data.apiresponse.schools.SatScoreResponseItem
import com.arturomarmolejo.nycschoolscompose.data.domain.SatScoreDomain
import com.arturomarmolejo.nycschoolscompose.data.domain.SchoolDomain
import com.arturomarmolejo.nycschoolscompose.data.rest.SchoolsRepository
import com.arturomarmolejo.nycschoolscompose.domain.GetAllSchoolsUseCase
import com.arturomarmolejo.nycschoolscompose.domain.GetSatScoresUseCase
import com.arturomarmolejo.nycschoolscompose.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * [SchoolsViewModel] -
 * Defines the ViewModel for the application
 * Contains the information that may be shared between different composable functions
 * @param getAllSchoolsUseCase Use case to get the list of schools from the API
 * @param getSatScoresUseCase Use case to get the SAT scores for a particular schools
 */
private const val TAG = "SchoolsViewModel"
@HiltViewModel
class SchoolsViewModel @Inject constructor(
    private val getAllSchoolsUseCase: GetAllSchoolsUseCase,
    private val getSatScoresUseCase: GetSatScoresUseCase
): ViewModel() {

    var selectedSchool: SchoolDomain? = null
    var selectedSatScoreItem: SatScoreResponseItem? = null
    var selectedSchoolDbn: String? = ""

    private val _schoolList: MutableStateFlow<UIState<List<SchoolDomain>>> =
        MutableStateFlow(UIState.LOADING)
    val schoolList: StateFlow<UIState<List<SchoolDomain>>> get() = _schoolList

    private val _satScoreList: MutableStateFlow<UIState<List<SatScoreDomain>>> =
        MutableStateFlow(UIState.LOADING)
    val satScoreList: StateFlow<UIState<List<SatScoreDomain>>> get() = _satScoreList

    init {
        getAllSchools()
    }

    /**
     * [getAllSchools] -
     * Retrieve the information from the API and save it in a mutable state
     */

    fun getAllSchools() {
        viewModelScope.launch {
            getAllSchoolsUseCase().collect {
                _schoolList.value = it
            }
        }
    }

    /**
     * [getSatScores] -
     * @param dbn Specific school Id to retrieve its list of Sat Scores
     * Retrieve the information from the API and save it in a mutable state
     */

    fun getSatScores(dbn: String? = null) {
        viewModelScope.launch {
            getSatScoresUseCase(dbn).collect {
                selectedSchoolDbn = dbn
                Log.d(TAG, "getSatScores: $selectedSchoolDbn")
                _satScoreList.value = it
            }
        }
    }



}