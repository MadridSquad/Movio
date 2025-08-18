package com.madrid.designSystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.Theme

@Composable
fun EmptySearchLayout(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    imageSize: Int = 128
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surfaces.surface),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Center
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Search Icon",
            modifier = Modifier
                .size(imageSize.dp)
                .align(CenterHorizontally),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = Theme.textStyle.title.mediumMedium16,
            color = Theme.color.surfaces.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = Theme.textStyle.label.smallRegular12,
            color = Theme.color.surfaces.onSurfaceContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}

@Preview
@Composable
private fun EmptySearchLayoutPreview() {
    EmptySearchLayout(
        title = "No Results Found",
        description = "Try searching for something else.",
        image = R.drawable.empty,
        modifier = Modifier.padding(16.dp)
    )
}