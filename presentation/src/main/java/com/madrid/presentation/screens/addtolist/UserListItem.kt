package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun UserListItem(
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