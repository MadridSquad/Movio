package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Composable
fun ListSelectionContent(
    initialUserLists: List<UserList>,
    onCreateNewListClick: () -> Unit,
    onSelectionChanged: ((UserList, Boolean) -> Unit)? = null
) {
    val userLists = remember { mutableStateListOf(*initialUserLists.toTypedArray()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {

        CreateListContent(
            onCreateClick = {
                onCreateNewListClick()
            }
        )

        if (userLists.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
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
