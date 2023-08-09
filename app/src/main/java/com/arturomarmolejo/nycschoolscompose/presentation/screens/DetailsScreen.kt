package com.arturomarmolejo.nycschoolscompose.presentation.screens

import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arturomarmolejo.nycschoolscompose.data.domain.SatScoreDomain
import com.arturomarmolejo.nycschoolscompose.data.domain.SchoolDomain
import com.arturomarmolejo.nycschoolscompose.presentation.viewmodel.SchoolsViewModel
import com.arturomarmolejo.nycschoolscompose.utils.UIState

@Composable
fun DetailsScreen(
    schoolsViewModel: SchoolsViewModel,
) {
    val schoolItem = schoolsViewModel.selectedSchool
    val selectedSchoolDbn = schoolItem?.dbn

    val selectedSatScoreItem = schoolsViewModel.selectedSatScoreItem
    val satScoreListState by schoolsViewModel.satScoreList.collectAsState()

    LaunchedEffect(selectedSchoolDbn) {
        schoolsViewModel.getSatScores(selectedSchoolDbn)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = schoolItem?.schoolName ?: "-",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = "Location: ${schoolItem?.city}, ${schoolItem?.stateCode}, ${schoolItem?.zip}",
                    style = MaterialTheme.typography.bodySmall
                    )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Phone Number: ${schoolItem?.phoneNumber ?: "-"}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "School Email: ${schoolItem?.schoolEmail ?: "-"}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "List of SAT Scores",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                when(satScoreListState) {
                    is UIState.LOADING -> {}
                    is UIState.SUCCESS -> {
                        val selectedSatScoreItem = (satScoreListState as UIState.SUCCESS<List<SatScoreDomain>>).response.firstOrNull()
                        selectedSatScoreItem?.let {
                            Text(
                                text = "Critical Reading Average Score: ${selectedSatScoreItem.satCriticalReadingAvgScore}"
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Math Average Score: ${selectedSatScoreItem.satMathAvgScore}")
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Writing Average Score: ${selectedSatScoreItem.satWritingScore}")
                        }
                    }
                    is UIState.ERROR -> {
                        Toast.makeText(LocalContext.current, "Item not found", Toast.LENGTH_LONG)
                    }
                }
            }
        }
    }
}