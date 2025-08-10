// SuccessNotificationRow.kt
package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.ui.text.TextStyle
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.domain.entity.WatchList
import kotlinx.coroutines.delay

@Composable
fun SuccessNotificationRow(
    isVisible: Boolean,
    message: String = "Successfully added to your collection.",
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Auto-dismiss after 3 seconds
    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(3000)
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeIn(animationSpec = tween(300)),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(200)
        ) + fadeOut(animationSpec = tween(200)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(
                    Color(0xFF1A162F)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
                MovioIcon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = com.madrid.designSystem.R.drawable.archive_tick),
                    contentDescription = "Success",
                )


            // Success message
            MovioText(
                modifier = Modifier.weight(1f), // Modifier should be first
                text = message,
                color = Color.White,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
fun ListManagementBottomSheetWithNotification(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    initialUserLists: List<WatchList>,
    onListCreated: (String) -> Unit,
    onSelectionChanged: ((WatchList, Boolean) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var currentMode by remember { mutableStateOf(ListBottomSheetMode.LIST_SELECTION) }
    var showSuccessNotification by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }

    LaunchedEffect(initialUserLists) {
        val selectedLists = initialUserLists.filter { it.isSelected }
        if (selectedLists.isNotEmpty()) {
            successMessage = when (selectedLists.size) {
                1 -> "Successfully added to ${selectedLists.first().name}."
                else -> "Successfully added to ${selectedLists.size} lists."
            }
            showSuccessNotification = true
        }
    }

    Column {
        SuccessNotificationRow(
            isVisible = showSuccessNotification,
            message = successMessage,
            onDismiss = { showSuccessNotification = false },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        MovioBottomSheet(
            show = isVisible,
            onDismiss = {
                currentMode = ListBottomSheetMode.LIST_SELECTION
                onDismiss()
            },
            containerColor = if (currentMode == ListBottomSheetMode.CREATE_NEW_LIST)
                Color.Transparent else Theme.color.surfaces.surface,
        ) {
            AnimatedContent(
                targetState = currentMode,
                transitionSpec = {
                    slideInHorizontally(initialOffsetX = { if (targetState == ListBottomSheetMode.CREATE_NEW_LIST) it else -it }) togetherWith
                            slideOutHorizontally(targetOffsetX = { if (targetState == ListBottomSheetMode.CREATE_NEW_LIST) -it else it })
                },
                label = "ListBottomSheetAnimation"
            ) { mode ->
                when (mode) {
                    ListBottomSheetMode.LIST_SELECTION -> {
                        ListSelectionContent(
                            initialUserLists = initialUserLists,
                            onCreateNewListClick = {
                                currentMode = ListBottomSheetMode.CREATE_NEW_LIST
                            },
                            onSelectionChanged = { userList, isSelected ->
                                onSelectionChanged?.invoke(userList, isSelected)
                                if (isSelected) {
                                    successMessage = "Successfully added to ${userList.name}."
                                    showSuccessNotification = true
                                }
                            }
                        )
                    }
                    ListBottomSheetMode.CREATE_NEW_LIST -> {
                        CreateListBottomSheet(
                            show = true,
                            onCreateClick = { listName ->
                                onListCreated(listName)
                                currentMode = ListBottomSheetMode.LIST_SELECTION
                                successMessage = "Successfully created list: $listName"
                                showSuccessNotification = true
                            },
                            onDismiss = {
                                currentMode = ListBottomSheetMode.LIST_SELECTION
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSuccessNotificationRow() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        SuccessNotificationRow(
            isVisible = true,
            message = "Successfully added to your collection."
        )
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewListManagementBottomSheetWithNotification() {
//    ListManagementBottomSheetWithNotification(
//        isVisible = true,
//        onDismiss = { },
//        initialUserLists = listOf(
//            WatchList("1", "Watch later", isSelected = true),
//            WatchList("2", "Watching after exam", isSelected = false),
//            WatchList("3", "Watching soon", isSelected = false),
//            WatchList("4", "Adventure movies", isSelected = false)
//        ),
//        onListCreated = { listName ->
//            println("Creating list: $listName")
//        },
//        onSelectionChanged = { userList, isSelected ->
//            println("Selection changed: ${userList.name} -> $isSelected")
//        }
//    )
//}