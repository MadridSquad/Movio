package com.madrid.designSystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.madrid.designSystem.theme.Theme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.MovioTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovioBottomSheet(
    show: Boolean,
    onDismiss: () -> Unit,
    containerColor: Color = Theme.color.surfaces.surface,
    content: @Composable () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (show) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = containerColor,
        ) {
            content()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MovioBottomSheetPreview() {
    var show by remember { mutableStateOf(true) }

    MovioTheme {
        MovioBottomSheet(
            show = show,
            onDismiss = { show = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Hello from Bottom Sheet")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { show = false }) {
                    Text(text = "Close")
                }
            }
        }
    }
}