package com.madrid.presentation.screens.homeScreen.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.madrid.designSystem.component.FilterBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.viewModel.homeViewModel.CategoryUiState
import com.madrid.presentation.viewModel.homeViewModel.SortingType
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState

@Composable
fun CategoriesLayout(
    modifier: Modifier = Modifier,
    categories: List<CategoryUiState>,
    selectedCategory: CategoryUiState,
    sortingType: SortingType,
    onSortingTypeSelected: (SortingType) -> Unit,
    onCategorySelected: (CategoryUiState) -> Unit,
    mediaItems: LazyPagingItems<MediaUiState>,
    onMediaItemClicked: (Int, MediaType) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(top = 17.dp),
    ) {
        FilterBar(
            items = categories.map { it.name },
            selectedItem = selectedCategory.name,
            onItemClick = { category ->
                onCategorySelected(categories.find { it.name == category } ?: CategoryUiState())
            },
        )
        FilterBar(
            modifier = Modifier.padding(top = 12.dp),
            items = SortingType.entries.map { it.value },
            selectedItem = sortingType.value,
            onItemClick = { sortingValue ->
                val sorting =
                    SortingType.entries.find { it.value == sortingValue } ?: SortingType.ALL
                onSortingTypeSelected(sorting)
            },
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 102.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surfaces.surface)
                .statusBarsPadding(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(mediaItems.itemCount) { index ->
                MovioVerticalCard(
                    description = mediaItems[index]?.title ?: "",
                    movieImage = mediaItems[index]?.imageUrl ?: "",
                    rate = mediaItems[index]?.rating?.take(3) ?: "",
                    height = 180.dp,
                    onClick = {
                        onMediaItemClicked(
                            mediaItems[index]?.id?.toIntOrNull() ?: 0,
                            mediaItems[index]?.mediaType ?: MediaType.MOVIE
                        )
                    }
                )
            }
        }
    }
}