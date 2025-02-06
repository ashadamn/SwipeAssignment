package com.example.swipeassignment.presentation.navigation

import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swipeassignment.domain.util.ApiResult
import com.example.swipeassignment.presentation.ui.screens.home_screen.AddProductBottomSheet
import com.example.swipeassignment.presentation.ui.screens.home_screen.HomeScreen
import com.example.swipeassignment.presentation.ui.screens.home_screen.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppNavigation.Home.route
) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable(AppNavigation.Home.route) {

            val scaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
            )
            val scope = rememberCoroutineScope()

            val homeViewModel: HomeViewModel = koinViewModel()
            val productListState by homeViewModel.productListState.collectAsStateWithLifecycle()
            val addProductResponseState by homeViewModel.addProductState.collectAsStateWithLifecycle()

            LaunchedEffect(addProductResponseState) {
                if (addProductResponseState is ApiResult.Success) {
                    scaffoldState.bottomSheetState.hide()
                    homeViewModel.loadProductList()
                }
            }

            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetContent = {
                    AddProductBottomSheet(state = addProductResponseState) { productType, productName, sellingPrice, taxRate, selectedImage ->

                        //add feature to handle no internet state.....implement work manager to call this api whenever device has the internet
                        homeViewModel.addProduct(
                            productType,
                            productName,
                            sellingPrice,
                            taxRate,
                            selectedImage
                        )
                    }
                },
                sheetPeekHeight = 0.dp,
                content = {
                    HomeScreen(
                        state = productListState,
                        onFabClick = {
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                    )
                }
            )
        }
    }
}
