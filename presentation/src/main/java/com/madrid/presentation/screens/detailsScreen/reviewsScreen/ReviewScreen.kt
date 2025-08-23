package com.madrid.presentation.screens.detailsScreen.reviewsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.component.TopAppBar
import com.madrid.presentation.R
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.detailsScreen.reviewsScreen.composables.ReviewCard
import com.madrid.presentation.viewModel.detailsViewModel.review.ReviewsScreenUiState
import com.madrid.presentation.viewModel.detailsViewModel.review.ReviewsScreenViewModel
import com.madrid.presentation.viewModel.shared.parser.formatDate

@Composable
fun ReviewsScreen(viewModel: ReviewsScreenViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    ReviewsScreenContent(
        uiState = uiState,
        onClickBack = { navController.popBackStack() }
    )
}

@Composable
fun ReviewsScreenContent(
    uiState: ReviewsScreenUiState,
    onClickBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .navigationBarsPadding()
    ) {
        TopAppBar(
            text = stringResource(R.string.reviews),
            secondIcon = null,
            thirdIcon = null,
            modifier = Modifier.padding(start = 16.dp, top = 36.dp),
            onFirstIconClick = { onClickBack() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val reviews = uiState.reviews
        if (reviews.isNotEmpty()) {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 40.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(reviews) { review ->
                    ReviewCard(
                        reviewerName = review.reviewerName,
                        reviewerImageUrl = review.reviewerImageUrl.toString(),
                        rating = review.rating,
                        date = formatDate(review.date),
                        content = review.content,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}