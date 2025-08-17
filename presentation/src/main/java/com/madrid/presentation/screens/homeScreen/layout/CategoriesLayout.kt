package com.madrid.presentation.screens.homeScreen.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.madrid.designSystem.component.FilterBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.designSystem.modifier.ShimmerCard
import com.madrid.designSystem.modifier.removeWidthPaddingFromParent
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
    onMediaItemClicked: (Int, MediaType) -> Unit,
    isLoading: Boolean
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 102.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(top = 132.dp)
            .background(Theme.color.surfaces.surface),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            FilterBar(
                modifier = Modifier.removeWidthPaddingFromParent(16.dp).padding(top = 4.dp),
                contentHorizontalPadding = 16.dp,
                items = categories.map { it.name },
                selectedItem = selectedCategory.name,
                onItemClick = { category ->
                    onCategorySelected(
                        categories.find { it.name == category } ?: CategoryUiState()
                    )
                }
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            FilterBar(
                modifier = Modifier.padding(bottom = 12.dp),
                contentHorizontalPadding = 0.dp,
                items = SortingType.entries.map { it.value },
                selectedItem = sortingType.value,
                onItemClick = { sortingValue ->
                    val sorting = SortingType.entries.find { it.value == sortingValue }
                        ?: SortingType.ALL
                    onSortingTypeSelected(sorting)
                }
            )
        }

        if (mediaItems.loadState.refresh is LoadState.Loading || isLoading) {
            items(9) {
                ShimmerCard(
                    isLoading = true,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .width(150.dp)
                        .height(180.dp)
                )
            }
        } else {
            items(mediaItems.itemCount) { index ->
                MovioVerticalCard(
                    description = mediaItems[index]?.title ?: "",
                    movieImage = mediaItems[index]?.imageUrl ?: "",
                    rate = mediaItems[index]?.rating ?: "",
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