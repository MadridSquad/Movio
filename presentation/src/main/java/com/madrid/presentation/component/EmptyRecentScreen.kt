package com.madrid.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R

fun LazyGridScope.emptyRecentSearch() {
    item(span = { GridItemSpan(maxLineSpan) }) {

        Column(
            modifier = Modifier
                .padding(top = 200.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MovioIcon(
                painter = painterResource(id = R.drawable.img_search_explore),
                contentDescription = null,
                modifier = Modifier.size(150.dp),
            )
            MovioText(
                text = stringResource(id = R.string.empty_recent_search_title),
                textStyle = Theme.textStyle.title.mediumMedium16,
                color = Theme.color.surfaces.onSurface,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            MovioText(
                text = stringResource(id = R.string.empty_recent_search_body),
                textStyle = Theme.textStyle.label.smallRegular12,
                color = Theme.color.surfaces.onSurfaceContainer
            )
        }
    }
}

