package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
        horizontalArrangement = Arrangement.Start
    ) {
        MovioIcon(
            painter = painterResource(id = R.drawable.bold_add_circle),
            contentDescription = "Create new list",
            modifier = Modifier.size(32.dp),
            tint = Theme.color.surfaces.surfaceContainer,
        )

        Spacer(modifier = Modifier.width(12.dp))

        MovioText(
            text = "Create a new list",
            textStyle = Theme.textStyle.body.mediumMedium14,
            color = Theme.color.surfaces.onSurface
        )
    }
}