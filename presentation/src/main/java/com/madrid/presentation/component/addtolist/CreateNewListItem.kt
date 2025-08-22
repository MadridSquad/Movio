package com.madrid.presentation.component.addtolist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun CreateNewListItem(
    onListCreated: () -> Unit,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = isEnabled,
                role = Role.Button,
                onClickLabel = "Create a new list"
            ) {
                if (isEnabled) {
                    onListCreated()
                }
            }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Theme.color.surfaces.surface),
                contentAlignment = Alignment.Center
            ) {
                MovioIcon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "Add new list",
                    modifier = Modifier.size(24.dp),
                    tint = if (isEnabled) {
                        Theme.color.surfaces.onSurface
                    } else {
                        Theme.color.surfaces.onSurface.copy(alpha = 0.6f)
                    }
                )
            }
            MovioText(
                text = stringResource(R.string.create_new_list),
                textStyle = Theme.textStyle.label.smallRegular14,
                color = if (isEnabled) {
                    Theme.color.surfaces.onSurface
                } else {
                    Theme.color.surfaces.onSurface.copy(alpha = 0.6f)
                },
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateNewListItemPreview() {
    CreateNewListItem(
        onListCreated = {},
        isEnabled = true
    )
}