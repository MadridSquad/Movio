package com.madrid.presentation.component.addtolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioButton
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.component.textInputField.BasicTextInputField
import com.madrid.designSystem.theme.Theme

@Composable
fun CreateListBottomSheet(
    show: Boolean,
    isLoading: Boolean = false,
    onDismiss: () -> Unit,
    onCreateClick: (String) -> Unit,
) {
    MovioBottomSheet(
        show = show,
        onDismiss = { if (!isLoading) onDismiss() },
        containerColor = Theme.color.surfaces.surface
    ) {
        var listName by remember { mutableStateOf("") }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(239.dp)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                )
        ) {
            // Title
            MovioText(
                text = stringResource(R.string.create_list_title),
                textStyle = Theme.textStyle.body.mediumMedium14,
                color = Theme.color.surfaces.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Description
            MovioText(
                text = stringResource(R.string.create_list_description),
                textStyle = Theme.textStyle.label.smallRegular12,
                color = Theme.color.surfaces.onSurfaceContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicTextInputField(
                value = listName,
                onValueChange = { if (!isLoading) listName = it },
                hintText = stringResource(R.string.create_list_hint),
                startIconPainter = painterResource(id = R.drawable.outline_minimalistic),
                endIconPainter = null,
                borderBrushColors = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            MovioButton(
                onClick = {
                    if (listName.isNotBlank() && !isLoading) {
                        onCreateClick(listName)
                    }
                },
                enabled = listName.isNotBlank() && !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                if (isLoading) {
                    MovioIcon(
                        painter = painterResource(id = R.drawable.loading),
                        contentDescription = "Creating list...",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    MovioText(
                        text = stringResource(R.string.create_list_button),
                        color = Color.White,
                        textStyle = Theme.textStyle.label.mediumMedium16
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateListBottomSheetPreview() {
    CreateListBottomSheet(
        show = true,
        isLoading = false,
        onDismiss = {},
        onCreateClick = {}
    )
}