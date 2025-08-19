package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun CreateNewListItem(
    modifier: Modifier = Modifier,
    onListCreated: () -> Unit,
    isEnabled: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable(
                enabled = isEnabled,
                role = Role.Button,
                onClickLabel = "Create a new list"
            ) {
                if (isEnabled) {
                    onListCreated()
                }
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MovioText(
            text = "Create new list",
            textStyle = Theme.textStyle.body.mediumMedium14,
            color = if (isEnabled) {
                Theme.color.brand.primary
            } else {
                Theme.color.brand.primary.copy(alpha = 0.6f)
            },
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            MovioIcon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Create new list",
                modifier = Modifier.size(24.dp),
                tint = if (isEnabled) {
                    Theme.color.brand.primary
                } else {
                    Theme.color.brand.primary.copy(alpha = 0.6f)
                }
            )
        }
    }
}
