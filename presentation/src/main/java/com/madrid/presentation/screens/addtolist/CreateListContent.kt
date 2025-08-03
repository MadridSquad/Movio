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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioButton
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun CreateListContent(
    onCreateClick: (String) -> Unit,
) {
    var listName by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(color = Theme.color.surfaces.surface)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MovioText(
                    text = "Create New List",
                    textStyle = Theme.textStyle.body.mediumMedium14,
                    color = Theme.color.surfaces.onSurface,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.size(40.dp))
            }

            MovioText(
                text = "Create a new list and keep track of your series that you want to access easily.",
                textStyle = Theme.textStyle.label.smallRegular12,
                color = Theme.color.surfaces.onSurfaceContainer,
                textAlign = TextAlign.Center
            )
//            MovioText(
//                text = "List name",
//                color = Color.White.copy(alpha = 0.5f),
//                textStyle = Theme.textStyle.body.mediumMedium12
//            )
//            MovioIcon(
//                painter = painterResource(id = R.drawable.outline_minimalistic),
//                contentDescription = "List Icon",
//                tint = Color.White.copy(alpha = 0.7f),
//                modifier = Modifier.size(20.dp)
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))

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
