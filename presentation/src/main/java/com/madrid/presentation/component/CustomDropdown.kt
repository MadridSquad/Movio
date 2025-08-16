package com.madrid.presentation.component


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> CustomDropdown(
    items: List<T>,
    selectedItem: T?,
    labelSelector: (T) -> String,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    dropdownWidth: Dp = 120.dp
) {
    var isVisibleContextItem by remember { mutableStateOf(false) }
    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    var density = LocalDensity.current
    var itemHeight by remember { mutableStateOf(0.dp) }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        Box(
            modifier = modifier
                .width(dropdownWidth)
                .onSizeChanged { itemHeight = with(density) { it.height.toDp() } }
                .background(
                    color = Theme.color.surfaces.surfaceContainer,
                    shape = RoundedCornerShape(32.dp)
                )
                .border(
                    width = 1.dp,
                    color = Theme.color.surfaces.onSurfaceAt2,
                    shape = RoundedCornerShape(32.dp)
                )
                .clip(RoundedCornerShape(32.dp))
                .clickable { expanded = true },
            contentAlignment = Alignment.Center
        ) {
            Row(
                Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .pointerInput(true) {
                        detectTapGestures(
                            onLongPress = {
                                isVisibleContextItem = true
                                pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                            },
                        )
                    },
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MovioText(
                    text = selectedItem?.let { labelSelector(it) } ?: "",
                    color = Theme.color.surfaces.onSurfaceContainer,
                    textStyle = Theme.textStyle.label.smallRegular12
                )
                MovioIcon(
                    painter = painterResource(com.madrid.designSystem.R.drawable.icon_arrow_down),
                    contentDescription = "icon arrow down icon",
                    tint = Theme.color.surfaces.onSurfaceVariant
                )
            }
            if (items.size != 1) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = pressOffset.copy(
                        y = pressOffset.y - itemHeight * 2
                    ),
                    modifier = Modifier
                        .width(dropdownWidth)
                        .background(
                            color = Theme.color.surfaces.surface,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Theme.color.surfaces.onSurfaceAt2,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                onItemSelected(item)
                                expanded = false
                            }
                        ) {
                            MovioText(
                                text = labelSelector(item),
                                color = Theme.color.surfaces.onSurfaceContainer,
                                textStyle = Theme.textStyle.label.smallRegular14
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun CustomDropdownPreview() {
    MovioTheme {
        var selectedItem by remember { mutableStateOf("Option 1") }
        CustomDropdown(
            items = listOf("Option 1", "Option 2", "Option 3"),
            selectedItem = selectedItem,
            labelSelector = { it },
            onItemSelected = { selectedItem = it }
        )
    }
}