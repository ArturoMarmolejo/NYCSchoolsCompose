package com.arturomarmolejo.nycschoolscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturomarmolejo.nycschoolscompose.presentation.navigation.Navigation
import com.arturomarmolejo.nycschoolscompose.presentation.viewmodel.SchoolsViewModel
import com.arturomarmolejo.nycschoolscompose.ui.theme.NYCSchoolsComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NYCSchoolsComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   val schoolsViewModel: SchoolsViewModel = hiltViewModel()
                    Navigation(viewModel = schoolsViewModel)
                }
            }
        }
    }
}

