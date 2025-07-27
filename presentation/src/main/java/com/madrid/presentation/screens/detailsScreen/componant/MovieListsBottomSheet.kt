package com.madrid.presentation.screens.detailsScreen.componant

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme

@Composable
fun MovieListsBottomSheet(
    show: Boolean,
    onDismiss: () -> Unit,
    collections: List<String>,
    isLoading: Boolean = false,
    currentLoadingItem: String? = null,
    onListClicked: (String) -> Unit,
    onAddToList:  (String) -> Unit,
    onCreateNewList: () -> Unit
) {

    if (show) {
        MovioBottomSheet(
            show = true,
            onDismiss = onDismiss,
            containerColor = Theme.color.surfaces.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = Theme.color.surfaces.surfaceContainer,
                                shape = CircleShape
                            )
                            .clickable { onCreateNewList() }
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        MovioIcon(
                            painter = painterResource(R.drawable.add),
                            contentDescription = "Add",
                            tint = Theme.color.surfaces.onSurface,
                            modifier = Modifier.size(24.dp)

                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))
                    MovioText(
                        text = "Create a new list",
                        textStyle = Theme.textStyle.label.smallRegular14,
                        color = Theme.color.surfaces.onSurface
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Theme.color.surfaces.onSurfaceAt3)
                )
                LazyColumn {
                    items(collections) { collectionName ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                          ,  verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            MovioText(
                                text = collectionName,
                                textStyle = Theme.textStyle.title.mediumMedium14,
                                color = Theme.color.surfaces.onSurface,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onListClicked(collectionName) }
                                    .padding(start = 8.dp)
                            )
                            if (isLoading && currentLoadingItem == collectionName) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Theme.color.surfaces.onSurfaceContainer,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .border(
                                            width = 1.dp,
                                            color = Theme.color.surfaces.onSurfaceContainer,
                                            shape = CircleShape
                                        )
                                        .clickable { onAddToList(collectionName) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    MovioIcon(
                                        painter = painterResource(R.drawable.add),
                                        contentDescription = "Add to $collectionName",
                                        tint = Theme.color.surfaces.onSurfaceContainer,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewMovieListsBottomSheet() {
    MovioTheme {
        var isLoading by remember { mutableStateOf(false) }
        var currentLoadingItem by remember { mutableStateOf<String?>(null) }
        MovieListsBottomSheet(
            show = true,
            onDismiss = {},
            collections = listOf("Watch Later", "Favorites", "To Watch"),
            isLoading = isLoading,
            currentLoadingItem = currentLoadingItem,
            onListClicked = { println("Viewing: $it") },
            onAddToList = {
                isLoading = true
                currentLoadingItem = it


            },
            onCreateNewList = { println("Create new list") }
        )
    }
}