package com.madrid.designSystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme

@Composable
fun FilterBar(
    items: List<String>,
    selectedItem: String,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    scrollable: Boolean = true,
    contentHorizontalPadding: Dp = 16.dp,
) {
    if (scrollable) {
        LazyRow(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = contentHorizontalPadding),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(items) { index,item ->
                FilterChip(
                    text = item,
                    isSelected = item == selectedItem,
                    onClick = { onItemClick(item) })
            }
        }
    } else {
        Row(
            modifier = modifier
                .padding(horizontal = contentHorizontalPadding)
        ) {
            items.forEach { item ->
                FilterChip(
                    text = item,
                    isSelected = item == selectedItem,
                    onClick = { onItemClick(item) },
                )
            }
        }
    }
}


@Composable
fun FilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.brand.primary
        else Theme.color.surfaces.onSurfaceAt3,
        label = "FilterChipBackgroundColor"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.brand.onPrimary
        else Theme.color.surfaces.onSurfaceVariant,
        label = "FilterChipTextColor"
    )
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MovioText(
                text,
                color = textColor,
                textStyle = Theme.textStyle.label.smallRegular14
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun FilterBarPreview() {
    val items = listOf("Movies", "Series", "Artists", "Top Rated","Top Rated","Top Rated","Top Rated")
    val selected = remember { mutableStateOf("Movies") }

    FilterBar(
        items = items,
        selectedItem = selected.value,
        onItemClick = { selected.value = it },
    )
}