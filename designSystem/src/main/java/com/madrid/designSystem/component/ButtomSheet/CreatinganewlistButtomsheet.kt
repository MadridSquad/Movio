package com.madrid.designSystem.component.ButtomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateListBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onCreateClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(isVisible) {
        if (!isVisible) {
            sheetState.hide()
        }
    }

    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            modifier = modifier,
            sheetState = sheetState,
            containerColor = Color.Transparent,
            scrimColor = Color.Black.copy(alpha = 0.5f),
            dragHandle = null,
            windowInsets = WindowInsets(0)
        ) {
            CreateListContent(
                onCreateClick = onCreateClick,
                onDismiss = onDismiss
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateListContent(
    onCreateClick: (String) -> Unit,
    onDismiss: () -> Unit
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MovioText(
                    text = "Create a new list",
                    textStyle = Theme.textStyle.headline.largeBold18,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                MovioText(
                    text = "Create a new list and keep track of your series that you want to access easily.",
                    textStyle = Theme.textStyle.body.smallRegular10.copy(lineHeight = 20.sp),
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
            OutlinedTextField(
                value = listName,
                onValueChange = { listName = it },
                placeholder = {
                    MovioText(
                        text = "List name",
                        color = Color.White.copy(alpha = 0.5f),
                        textStyle = Theme.textStyle.body.mediumMedium12
                    )
                },
                leadingIcon = {
                    MovioIcon(
                        painter = painterResource(id = R.drawable.outline_minimalistic),
                        contentDescription = "List Icon",
                        tint = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(20.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1A1B2F),
                    unfocusedContainerColor = Color(0xFF1A1B2F),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFF2A2B3F),
                    unfocusedBorderColor = Color(0xFF2A2B3F),
                    cursorColor = Color.White
                ),
                textStyle = Theme.textStyle.body.mediumMedium12.copy(color = Color.White),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (listName.text.isNotBlank()) {
                        onCreateClick(listName.text)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(28.dp),
                enabled = listName.text.isNotBlank()
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

@Preview(showBackground = true)
@Composable
fun CreateListContentPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.Black)
    ) {
        CreateListContent(
            onCreateClick = { listName ->
                println("Creating list: $listName")
            },
            onDismiss = {}
        )
    }
}