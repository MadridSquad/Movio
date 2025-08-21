package com.madrid.presentation.screens.detailsScreen.seriesDetails.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsInteractionListener

@Composable
fun GussetView(
    listener: SeriesDetailsInteractionListener
) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.library_main_icon),
            contentDescription = stringResource(com.madrid.presentation.R.string.search),
            modifier = Modifier
                .size(width = 60.dp, height = 66.dp)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
        )
        MovioText(
            text = stringResource(com.madrid.presentation.R.string.you_dont_have_an_account),
            textStyle = Theme.textStyle.title.mediumMedium16,
            color = Theme.color.surfaces.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        MovioText(
            text = stringResource(com.madrid.presentation.R.string.this_rating_is_only_available_to_registered_users_Login_to_share_your_rating),
            textStyle = Theme.textStyle.label.smallRegular12,
            color = Theme.color.surfaces.onSurfaceContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 40.dp,
                    bottom = 32.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .height(48.dp),
            onClick = {
                listener.onDismissShareBottomSheetClick()
                listener.onLoginButtonClick()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Theme.color.brand.primary,
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = ButtonDefaults.elevation(0.dp)
        ) {
            MovioText(
                text = stringResource(com.madrid.presentation.R.string.login),
                textStyle = Theme.textStyle.label.mediumMedium14,
                color = Theme.color.brand.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}