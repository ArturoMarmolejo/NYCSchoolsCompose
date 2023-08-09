package com.arturomarmolejo.nycschoolscompose.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arturomarmolejo.nycschoolscompose.R
import com.arturomarmolejo.nycschoolscompose.data.domain.SchoolDomain
import com.arturomarmolejo.nycschoolscompose.presentation.navigation.Routes
import com.arturomarmolejo.nycschoolscompose.presentation.viewmodel.SchoolsViewModel
import com.arturomarmolejo.nycschoolscompose.utils.UIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(schoolsViewModel: SchoolsViewModel, navController: NavController) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    Column(Modifier.fillMaxSize()) {
        Text(
            text = "NYC Schools",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .wrapContentHeight(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "Search for a specific school",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .wrapContentHeight()
                .padding(2.dp),
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = searchQuery,
            onValueChange = {searchQuery = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = { Text(text = "Search")},
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        when(val state = schoolsViewModel.schoolList.collectAsState(UIState.LOADING).value) {
            is UIState.LOADING -> {}
            is UIState.SUCCESS -> {
                FilteredSchoolList(
                    items = state.response,
                    navController = navController,
                    selectedItem = {
                      schoolsViewModel.selectedSchool = it
                    },
                    searchQuery = searchQuery
                )
            }
            is UIState.ERROR -> {
                Toast.makeText(LocalContext.current, "Item not found", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun SchoolList(
    items: List<SchoolDomain>,
    navController: NavController? = null,
    selectedItem: ((SchoolDomain) -> Unit)? = null
) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        LazyColumn(content = {
            itemsIndexed(items = items) { _, item ->
                SchoolItem(item = item, navController, selectedItem)

            }
        })
    }
}

@Composable
fun FilteredSchoolList(
    items: List<SchoolDomain>,
    searchQuery: String,
    selectedItem: ((SchoolDomain) -> Unit)? = null,
    navController: NavController? = null
) {
    val filteredItemState = remember(searchQuery) {
        derivedStateOf {
            if(searchQuery.isEmpty()) {
                items
            } else {
                items.filter {
                    it.schoolName.contains(searchQuery, ignoreCase = true)
                }
            }
        }
    }
    val filteredItems = filteredItemState.value

    SchoolList(items = filteredItems, navController = navController, selectedItem = selectedItem)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolItem(
    item: SchoolDomain,
    navController: NavController? = null,
    selectedItem: ((SchoolDomain) -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        onClick = {
            selectedItem?.invoke(item)
            navController?.navigate(Routes.DetailsScreen.route)
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = item.schoolName,
                fontWeight = FontWeight.Bold,
                )
            Text(
                text = "${item.city}, ${item.stateCode}",
            )
        }

    }
}