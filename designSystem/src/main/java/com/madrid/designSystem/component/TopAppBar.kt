package com.madrid.designSystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.activity.ComponentActivity
import com.madrid.designSystem.R
import com.madrid.designSystem.component.ButtomSheet.ShareViaBottomSheetContent
import com.madrid.designSystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    text: String?,
    modifier: Modifier = Modifier,
    firstIcon: Int? = R.drawable.arrow_left,
    secondIcon: Int? = R.drawable.share_arrow,
    thirdIcon: Int? = R.drawable.outline_heart,
    onFirstIconClick: (() -> Unit)? = null, // Make nullable to use default back behavior
    onSecondIconClick: () -> Unit = {},
    onThirdIconClick: (() -> Unit)? = null, // Make nullable to use default favorite behavior
    onShareOptionClick: (String) -> Unit = {},
    isFavorite: Boolean = false
) {
    var showShareBottomSheet by remember { mutableStateOf(false) }
    var isFavoriteState by remember { mutableStateOf(isFavorite) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current

    LaunchedEffect(showShareBottomSheet) {
        if (!showShareBottomSheet) {
            sheetState.hide()
        }
    }
    LaunchedEffect(isFavorite) {
        isFavoriteState = isFavorite
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(1f)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        firstIcon?.let { iconRes ->
            MovioIcon(
                painter = painterResource(id = iconRes),
                contentDescription = "back_button",
                tint = Theme.color.surfaces.onSurface,
                modifier = Modifier.clickable {
                    // Use custom callback if provided, otherwise use default back behavior
                    if (onFirstIconClick != null) {
                        onFirstIconClick()
                    } else {
                        // Default back behavior - finish activity or navigate back
                        (context as? ComponentActivity)?.onBackPressedDispatcher?.onBackPressed()
                    }
                }
            )
        } ?: Box(modifier = Modifier.size(24.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (text != null) {
                MovioText(
                    text = text,
                    textStyle = Theme.textStyle.headline.largeBold18,
                    color = Theme.color.surfaces.onSurface
                )
            }
        }
        Row(
            modifier = Modifier.padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            secondIcon?.let { iconRes ->
                MovioIcon(
                    painter = painterResource(id = iconRes),
                    contentDescription = "share_button",
                    tint = Theme.color.surfaces.onSurface,
                    modifier = Modifier.clickable {
                        showShareBottomSheet = true
                        onSecondIconClick()
                    }
                )
            }
            thirdIcon?.let { iconRes ->
                MovioIcon(
                    painter = painterResource(
                        id = if (isFavoriteState) R.drawable.bold_heart
                        else R.drawable.outline_heart
                    ),
                    contentDescription = "favorite_button",
                    tint = if (isFavoriteState) Color.Red // Pure red color as shown in image
                    else Theme.color.surfaces.onSurface,
                    modifier = Modifier.clickable {
                        // Use custom callback if provided, otherwise toggle favorite state
                        if (onThirdIconClick != null) {
                            onThirdIconClick()
                        } else {
                            // Default favorite toggle behavior
                            isFavoriteState = !isFavoriteState
                        }
                    }
                )
            }
        }
    }
    if (showShareBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showShareBottomSheet = false },
            sheetState = sheetState,
            containerColor = Theme.color.surfaces.surface,
            scrimColor = Color.Black.copy(alpha = 0.5f),
            dragHandle = null,
            windowInsets = WindowInsets(0)
        ) {
            ShareViaBottomSheetContent(
                onShareOptionClick = { option ->
                    onShareOptionClick(option)
                    showShareBottomSheet = false
                }
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithFavoriteState(
    text: String?,
    isFavorite: Boolean,
    onFavoriteChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    firstIcon: Int? = R.drawable.arrow_left,
    secondIcon: Int? = R.drawable.share_arrow,
    thirdIcon: Int? = R.drawable.outline_heart,
    onFirstIconClick: (() -> Unit)? = null,
    onSecondIconClick: () -> Unit = {},
    onShareOptionClick: (String) -> Unit = {}
) {
    var showShareBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current

    LaunchedEffect(showShareBottomSheet) {
        if (!showShareBottomSheet) {
            sheetState.hide()
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(1f)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        firstIcon?.let { iconRes ->
            MovioIcon(
                painter = painterResource(id = iconRes),
                contentDescription = "back_button",
                tint = Theme.color.surfaces.onSurface,
                modifier = Modifier.clickable {
                    if (onFirstIconClick != null) {
                        onFirstIconClick()
                    } else {
                        (context as? ComponentActivity)?.onBackPressedDispatcher?.onBackPressed()
                    }
                }
            )
        } ?: Box(modifier = Modifier.size(24.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (text != null) {
                MovioText(
                    text = text,
                    textStyle = Theme.textStyle.headline.largeBold18,
                    color = Theme.color.surfaces.onSurface
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            secondIcon?.let { iconRes ->
                MovioIcon(
                    painter = painterResource(id = iconRes),
                    contentDescription = "share_button",
                    tint = Theme.color.surfaces.onSurface,
                    modifier = Modifier.clickable {
                        showShareBottomSheet = true
                        onSecondIconClick()
                    }
                )
            }
            thirdIcon?.let { iconRes ->
                MovioIcon(
                    painter = painterResource(
                        id = if (isFavorite) R.drawable.bold_heart
                        else R.drawable.outline_heart
                    ),
                    contentDescription = "favorite_button",
                    tint = if (isFavorite) Color.Red
                    else Theme.color.surfaces.onSurface,
                    modifier = Modifier.clickable {
                        onFavoriteChanged(!isFavorite)
                    }
                )
            }
        }
    }
    if (showShareBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showShareBottomSheet = false },
            sheetState = sheetState,
            containerColor = Theme.color.surfaces.surface,
            scrimColor = Color.Black.copy(alpha = 0.5f),
            dragHandle = null,
            windowInsets = WindowInsets(0)
        ) {
            ShareViaBottomSheetContent(
                onShareOptionClick = { option ->
                    onShareOptionClick(option)
                    showShareBottomSheet = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    TopAppBar(
        text = "Movie Details",
        onFirstIconClick = { println("Back clicked") },
        onSecondIconClick = { println("Share clicked") },
        onThirdIconClick = { println("Favorite clicked") },
        onShareOptionClick = { option -> println("Share via: $option") },
        isFavorite = false
    )
}

@Preview(showBackground = true)
@Composable
fun TopAppBarFavoritePreview() {
    TopAppBar(
        text = "Movie Details",
        isFavorite = true
    )
}