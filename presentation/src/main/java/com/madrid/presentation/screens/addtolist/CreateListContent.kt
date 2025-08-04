package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioButton
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.component.textInputField.BasicTextInputField
import com.madrid.designSystem.theme.Theme

@Composable
fun CreateListContent(
    onCreateClick: (String) -> Unit,
) {
    var listName by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp,
                )
            )
            .background(color = Theme.color.surfaces.surface)
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 24.dp
            )
    ) {
        MovioText(
            text = "Create New List",
            textStyle = Theme.textStyle.body.mediumMedium14,
            color = Theme.color.surfaces.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        MovioText(
            text = "Create a new list and keep track of your series that you want to access easily.",
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
            onValueChange = { listName = it },
            hintText = "List name",
            startIconPainter = painterResource(id = R.drawable.outline_minimalistic),
            endIconPainter = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        MovioButton(
            onClick = {
                if (listName.isNotBlank()) {
                    onCreateClick(listName)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Theme.color.brand.primary,
                        shape = RoundedCornerShape(28.dp)
                    ),
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateListContentPreview() {
    CreateListContent(
        onCreateClick = {}
    )
}