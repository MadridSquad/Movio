package com.madrid.presentation.screens.libraryScreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioCard
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.theme.Theme

@Composable
fun AddWatchListItem(
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        MovioCard(
            modifier = Modifier
                .padding(bottom = 2.dp)
                .size(width = 142.dp, 5.dp),
            shape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp),
            containerColor = Theme.color.surfaces.onSurfaceAt3
        )
        Box(
            contentAlignment = Alignment.Center
        ){
            MovioCard(
                modifier = Modifier.size(width = 158.dp, height = 121.dp),
                shape = RoundedCornerShape(8.dp),
                containerColor = Theme.color.surfaces.surfaceContainer
            )

            MovioIcon(
                painter = painterResource(R.drawable.outline_add_circle),
                contentDescription = stringResource(com.madrid.presentation.R.string.add_icon),
                tint = Theme.color.surfaces.onSurfaceVariant
            )
        }
    }
}