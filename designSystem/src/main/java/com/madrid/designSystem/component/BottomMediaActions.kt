package com.madrid.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.BottomSheet.AddToListBottomSheetContent
import com.madrid.designSystem.component.BottomSheet.UserList
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.ButtomSheet.AuthRequiredBottomSheetContent
import com.madrid.designSystem.component.ButtomSheet.CreateListBottomSheet
import com.madrid.designSystem.component.ButtomSheet.RatingBottomSheetContent
import com.madrid.designSystem.component.ButtomSheet.ShareViaBottomSheetContent
import com.madrid.designSystem.theme.Theme

@Composable
fun BottomMediaActions(
    onRateClick: () -> Unit,
    onPlayClick: () -> Unit,
    onAddToListClick: () -> Unit,
    modifier: Modifier = Modifier,
    isUserAuthenticated: Boolean = false,
    movieTitle: String = "",
    moviePosterResId: Int = R.drawable.library_main_icon,
    initialUserLists: List<UserList> = emptyList(),
    onLoginRequested: () -> Unit = {},
    onListCreated: (String) -> Unit = {},
    onRatingSubmitted: (Int) -> Unit = {},
    onShareOptionClick: (String) -> Unit = {}
) {
    var showRatingBottomSheet by remember { mutableStateOf(false) }
    var showAddToListBottomSheet by remember { mutableStateOf(false) }
    var showCreateListBottomSheet by remember { mutableStateOf(false) }
    var showShareBottomSheet by remember { mutableStateOf(false) }
    var showAuthRequiredBottomSheet by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = {
                if (isUserAuthenticated) {
                    showRatingBottomSheet = true
                } else {
                    showAuthRequiredBottomSheet = true
                }
            },
            modifier = Modifier.size(48.dp)
        ) {
            MovioIcon(
                painter = painterResource(id = R.drawable.outline_star),
                contentDescription = "Rate",
                tint = Theme.color.surfaces.onSurface
            )
        }

        IconButton(
            onClick = onPlayClick,
            modifier = Modifier.size(48.dp)
        ) {
            MovioIcon(
                painter = painterResource(id = R.drawable.bold_video_circle),
                contentDescription = "Play",
                tint = Theme.color.surfaces.onSurface
            )
        }

        IconButton(
            onClick = {
                if (isUserAuthenticated) {
                    showAddToListBottomSheet = true
                } else {
                    showAuthRequiredBottomSheet = true
                }
            },
            modifier = Modifier.size(48.dp)
        ) {
            MovioIcon(
                painter = painterResource(id = R.drawable.outline_minimalistic),
                contentDescription = "Add to list",
                tint = Theme.color.surfaces.onSurface
            )
        }

        IconButton(
            onClick = { showShareBottomSheet = true },
            modifier = Modifier.size(48.dp)
        ) {
            MovioIcon(
                painter = painterResource(id = R.drawable.outline_share),
                contentDescription = "Share",
                tint = Theme.color.surfaces.onSurface
            )
        }
    }

    // Rating Bottom Sheet
    if (showRatingBottomSheet) {
        RatingBottomSheetContent(
            movieTitle = movieTitle,
            moviePosterResId = moviePosterResId,
            onRatingSubmitted = { rating ->
                onRatingSubmitted(rating)
                showRatingBottomSheet = false
            },
            onDismiss = { showRatingBottomSheet = false }
        )
    }

    // Add to List Bottom Sheet
    if (showAddToListBottomSheet) {
        AddToListBottomSheetContent(
            initialUserLists = initialUserLists,
            onListCreated = { showCreateListBottomSheet = true },
            onDismiss = { showAddToListBottomSheet = false },
            onSelectionChanged = { list, selected ->
                // Handle selection change
            }
        )
    }

    // Create List Bottom Sheet
    if (showCreateListBottomSheet) {
        CreateListBottomSheet(
            isVisible = true,
            onDismiss = { showCreateListBottomSheet = false },
            onCreateClick = { listName ->
                onListCreated(listName)
                showCreateListBottomSheet = false
                showAddToListBottomSheet = false
            }
        )
    }

    // Share Bottom Sheet
    if (showShareBottomSheet) {
        ShareViaBottomSheetContent(
            onDismiss = { showShareBottomSheet = false },
            onShareOptionClick = { option ->
                onShareOptionClick(option)
                showShareBottomSheet = false
            }
        )
    }

    // Auth Required Bottom Sheet
    if (showAuthRequiredBottomSheet) {
        AuthRequiredBottomSheetContent(
            title = "Authentication Required",
            description = "Please log in to rate movies or add them to your lists.",
            buttonText = "Login",
            onLoginClick = {
                onLoginRequested()
                showAuthRequiredBottomSheet = false
            },
            onDismiss = { showAuthRequiredBottomSheet = false }
        )
    }
}