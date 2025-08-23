package com.madrid.presentation.screens.searchScreen.features.recentSearchLayout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioRecentSearchText
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R

fun LazyGridScope.recentSearchScreen(
    searchHistory: List<String>,
    searchQuery: String,
    onSearchItemClick: (String) -> Unit,
    onRemoveItem: (String) -> Unit,
    onSearchItem: (String) -> Unit = {},
    onClearAll: () -> Unit,
    highlightCharactersInText: (String, String, Color, Color, TextStyle) -> AnnotatedString,
    isWrite: Boolean = false,
    sizeOfSuggestionKeywords : Int = 0
) {

    item(span = { GridItemSpan(maxLineSpan) }) {
        AnimatedVisibility(isWrite || searchQuery.trim().isEmpty() || searchQuery.trim().isBlank() ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp,top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MovioText(
                    text = stringResource(id = R.string.recent_search),
                    textStyle = Theme.textStyle.title.mediumMedium16,
                    color = Theme.color.surfaces.onSurface
                )
                MovioText(
                    text = stringResource(id = R.string.clear_all),
                    textStyle = Theme.textStyle.label.smallRegular14,
                    color = Theme.color.surfaces.onSurfaceVariant,
                    modifier = Modifier.clickable { onClearAll() }
                )
            }
        }
        AnimatedVisibility(!isWrite || searchQuery.trim().isNotEmpty() || searchQuery.trim().isNotBlank()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp,)
            ) {

            }
        }
    }

    items(
        count = searchHistory.size,
        span = { GridItemSpan(maxLineSpan) }
    ) { index ->
        val searchText = searchHistory[index]
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clickable { onSearchItemClick(searchText) }
                .padding(bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MovioRecentSearchText(
                text = highlightCharactersInText(
                    searchText,
                    if(isWrite) searchQuery else "",
                    Theme.color.surfaces.onSurface ,
                    if(isWrite) Theme.color.surfaces.onSurfaceVariant else Theme.color.surfaces.onSurface,
                    Theme.textStyle.label.smallRegular14
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textStyle = Theme.textStyle.label.smallRegular14,
                textAlign = null,
                startIcon = if(index < sizeOfSuggestionKeywords ) {
                    painterResource(com.madrid.designSystem.R.drawable.search_normal)
                }else{
                    painterResource(com.madrid.designSystem.R.drawable.outline_history)
                },
                endIcon = if (isWrite || searchQuery.trim().isEmpty() || searchQuery.trim().isBlank() ) {
                    painterResource(com.madrid.designSystem.R.drawable.outline_add)
                } else {
                    painterResource(com.madrid.designSystem.R.drawable.send)

                },
                onEndIconClick = if(isWrite || searchQuery.trim().isEmpty() || searchQuery.trim().isBlank() ) {
                    { onRemoveItem(searchText) }
                }else{
                    { onSearchItem(searchText) }
                }
            )
        }
    }
}