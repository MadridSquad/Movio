// ListManagementBottomSheet.kt
package com.madrid.designSystem.component.BottomSheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.MovioButton
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import kotlinx.coroutines.launch

data class UserList(
    val id: String,
    val name: String,
    var isSelected: Boolean = false,
    var isLoading: Boolean = false
)

enum class ListBottomSheetMode {
    LIST_SELECTION,
    CREATE_NEW_LIST
}

@Composable
fun ListManagementBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    initialUserLists: List<UserList>,
    onListCreated: (String) -> Unit,
    onSelectionChanged: ((UserList, Boolean) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var currentMode by remember { mutableStateOf(ListBottomSheetMode.LIST_SELECTION) }

    MovioBottomSheet(
        show = isVisible,
        onDismiss = {
            currentMode = ListBottomSheetMode.LIST_SELECTION
            onDismiss()
        },
        containerColor = if (currentMode == ListBottomSheetMode.CREATE_NEW_LIST) Color.Transparent else Color(0xFF1A1B23)
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
                        onSelectionChanged = onSelectionChanged
                    )
                }
                ListBottomSheetMode.CREATE_NEW_LIST -> {
                    CreateListContent(
                        onCreateClick = { listName ->
                            onListCreated(listName)
                            currentMode = ListBottomSheetMode.LIST_SELECTION
                        },
                        onBackClick = {
                            currentMode = ListBottomSheetMode.LIST_SELECTION
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ListSelectionContent(
    initialUserLists: List<UserList>,
    onCreateNewListClick: () -> Unit,
    onSelectionChanged: ((UserList, Boolean) -> Unit)? = null
) {
    val userLists = remember { mutableStateListOf(*initialUserLists.toTypedArray()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        // Header
        MovioText(
            text = "Add to List",
            textStyle = Theme.textStyle.headline.largeBold18,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Create new list item
        CreateNewListItem(
            onListCreated = onCreateNewListClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Existing lists
        if (userLists.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                items(userLists) { userList ->
                    UserListItem(
                        userList = userList,
                        onToggleSelection = { toggledList ->
                            val index = userLists.indexOf(toggledList)
                            if (index != -1 && !toggledList.isLoading) {
                                userLists[index] = toggledList.copy(isLoading = true)
                                kotlinx.coroutines.GlobalScope.launch {
                                    kotlinx.coroutines.delay(1500)
                                    val newSelectionState = !toggledList.isSelected
                                    userLists[index] = toggledList.copy(
                                        isSelected = newSelectionState,
                                        isLoading = false
                                    )

                                    onSelectionChanged?.invoke(toggledList, newSelectionState)

                                    println("${toggledList.name} is now ${if (newSelectionState) "selected" else "deselected"}")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CreateListContent(
    onCreateClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var listName by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(color = Color(0xFF0D1226))
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header with back button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MovioButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    MovioIcon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                MovioText(
                    text = "Create New List",
                    textStyle = Theme.textStyle.headline.largeBold18,
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                // Spacer to balance the back button
                Spacer(modifier = Modifier.size(40.dp))
            }

            // Description
            MovioText(
                text = "Create a new list and keep track of your series that you want to access easily.",
                textStyle = Theme.textStyle.body.smallRegular10.copy(lineHeight = 20.sp),
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
                    MovioText(
                        text = "List name",
                        color = Color.White.copy(alpha = 0.5f),
                        textStyle = Theme.textStyle.body.mediumMedium12
                    )
                    MovioIcon(
                        painter = painterResource(id = R.drawable.outline_minimalistic),
                        contentDescription = "List Icon",
                        tint = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(20.dp)
                    )

            Spacer(modifier = Modifier.height(8.dp))

            // Create button
            MovioButton(
                onClick = {
                    if (listName.text.isNotBlank()) {
                        onCreateClick(listName.text)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.color.brand.primary),
                    contentAlignment = Alignment.Center
                ) {
                    MovioText(
                        text = "Create",
                        color = Color.White,
                        textStyle = Theme.textStyle.label.mediumMedium16
                    )
                }
            }
        }
    }
}

@Composable
private fun UserListItem(
    userList: UserList,
    onToggleSelection: (UserList) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(
                enabled = !userList.isLoading,
                role = Role.Checkbox,
                onClickLabel = if (userList.isSelected) "Remove from list" else "Add to list"
            ) { onToggleSelection(userList) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MovioText(
            text = userList.name,
            textStyle = Theme.textStyle.body.mediumMedium12,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                userList.isLoading -> {
                    MovioIcon(
                        painter = painterResource(id = R.drawable.loading),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                userList.isSelected -> {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        MovioIcon(
                            painter = painterResource(id = R.drawable.bold_check_circle),
                            contentDescription = null,
                            tint = Color(0xFF8B5CF6),
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .border(
                                width = 2.dp,
                                color = Theme.color.surfaces.onSurfaceContainer,
                                shape = CircleShape
                            )
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        MovioIcon(
                            painter = painterResource(id = R.drawable.add),
                            tint = Theme.color.surfaces.onSurfaceContainer,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CreateNewListItem(
    onListCreated: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(
                role = Role.Button,
                onClickLabel = "Create a new list"
            ) { onListCreated() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
        ) {
            MovioIcon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
        MovioText(
            text = "Create a new list",
            textStyle = Theme.textStyle.body.mediumMedium12,
            color = Color.White,
        )
    }
}

// Usage Example
@Preview(showBackground = true)
@Composable
fun PreviewListManagementBottomSheet() {
    ListManagementBottomSheet(
        isVisible = true,
        onDismiss = { },
        initialUserLists = listOf(
            UserList("1", "Watch later", isSelected = true),
            UserList("2", "Watching after exam", isSelected = false),
            UserList("3", "Watching soon", isSelected = false),
            UserList("4", "Adventure movies", isSelected = false)
        ),
        onListCreated = { listName ->
            println("Creating list: $listName")
        },
        onSelectionChanged = { userList, isSelected ->
            println("Selection changed: ${userList.name} -> $isSelected")
        }
    )
}