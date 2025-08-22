package com.madrid.presentation.screens.homeScreen.layout

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.madrid.designSystem.component.DialogWithButtonLayout
import com.madrid.designSystem.component.FilterBar
import com.madrid.designSystem.modifier.ShimmerCard
import com.madrid.designSystem.modifier.removeWidthPaddingFromParent
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.viewModel.homeViewModel.SortingType
import com.madrid.presentation.viewModel.shared.CategoryUiState
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CategoriesLayout(
    modifier: Modifier = Modifier,
    categories: List<CategoryUiState>,
    selectedCategory: CategoryUiState?,
    sortingType: SortingType,
    onSortingTypeSelected: (SortingType) -> Unit,
    onCategorySelected: (CategoryUiState) -> Unit,
    mediaItems: LazyPagingItems<MediaUiState>,
    onMediaItemClicked: (Int, MediaType) -> Unit,
    onTryAgainClicked: () -> Unit,
    isLoading: Boolean
) {
    val context = LocalContext.current
    val allCategory = listOf(
        CategoryUiState(
            id = -1,
            name = stringResource(R.string.all),
        )
    )
    val displayedCategories = allCategory + categories
    val rowState = rememberLazyListState()
    Box(
        Modifier
            .fillMaxSize()
            .padding(top = 132.dp)
    ) {
        when (mediaItems.loadState.refresh) {
            is LoadState.Loading -> {
                CategorySection(
                    displayedCategories = displayedCategories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = onCategorySelected,
                    rowState = rowState
                )
                SortingSection(
                    modifier = Modifier.padding(top = 44.dp),
                    sortingType = sortingType,
                    onSortingTypeSelected = onSortingTypeSelected,
                    context = context
                )
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 101.33.dp),
                    modifier = modifier
                        .fillMaxSize()
                        .padding(top = 100.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(12) {
                        ShimmerCard(
                            isLoading = true,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .width(150.dp)
                                .height(180.dp)
                        )
                    }
                }
            }

            is LoadState.Error -> {
                CategorySection(
                    displayedCategories = displayedCategories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = onCategorySelected,
                    rowState = rowState
                )
                SortingSection(
                    modifier = Modifier.padding(top = 44.dp),
                    sortingType = sortingType,
                    onSortingTypeSelected = onSortingTypeSelected,
                    context = context
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    DialogWithButtonLayout(
                        title = stringResource(R.string.empty_no_internet_title),
                        description = stringResource(R.string.empty_no_internet_description),
                        image = Theme.drawables.noInternetId,
                        buttonText = stringResource(R.string.try_again),
                        onClick = onTryAgainClicked,
                        imageSize = 150,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 32.dp)
                    )
                }
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 102.dp),
                    modifier = modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        CategorySection(
                            modifier = Modifier
                                .removeWidthPaddingFromParent(16.dp),
                            displayedCategories = displayedCategories,
                            selectedCategory = selectedCategory,
                            onCategorySelected = onCategorySelected,
                            rowState = rowState
                        )
                    }

                    item(span = { GridItemSpan(maxLineSpan) }) {
                        SortingSection(
                            modifier = Modifier,
                            sortingType = sortingType,
                            onSortingTypeSelected = onSortingTypeSelected,
                            context = context,
                            contentHorizontalPadding = 0.dp
                        )
                    }
                    items(mediaItems.itemCount) { index ->
                        BoxWithConstraints {
                            val cardWidth = maxWidth
                            val cardHeight = cardWidth * (136f / 101.33f)
                            MovioVerticalCard(
                                modifier = Modifier.width(cardWidth),
                                description = mediaItems[index]?.title ?: "",
                                movieImage = mediaItems[index]?.imageUrl ?: "",
                                rate = mediaItems[index]?.rating ?: "",
                                imageHeight = cardHeight,
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
        }
    }
}

@Composable
private fun CategorySection(
    modifier: Modifier = Modifier,
    displayedCategories: List<CategoryUiState>,
    selectedCategory: CategoryUiState?,
    onCategorySelected: (CategoryUiState) -> Unit,
    rowState: LazyListState,
) {
    FilterBar(
        modifier = modifier,
        contentHorizontalPadding = 16.dp,
        items = displayedCategories.map { it.name },
        selectedItem = selectedCategory?.name ?: stringResource(R.string.all),
        onItemClick = { category ->
            onCategorySelected(
                displayedCategories.find { it.name == category } ?: CategoryUiState()
            )
        },
        rowState = rowState
    )
}

@Composable
private fun SortingSection(
    modifier: Modifier = Modifier,
    sortingType: SortingType,
    onSortingTypeSelected: (SortingType) -> Unit,
    context: Context = LocalContext.current,
    contentHorizontalPadding: Dp = 16.dp,
) {
    FilterBar(
        modifier = modifier,
        contentHorizontalPadding = contentHorizontalPadding,
        items = SortingType.entries.map { stringResource(it.value) },
        selectedItem = stringResource(sortingType.value),
        onItemClick = { sortingValue ->
            val sorting =
                SortingType.entries.find { context.getString(it.value) == sortingValue }
                    ?: SortingType.ALL
            onSortingTypeSelected(sorting)
        }
    )
}