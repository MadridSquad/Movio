package com.madrid.presentation.screens.moreScreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.component.ProfilePicture
import com.madrid.designSystem.theme.LocalIsDarkTheme
import com.madrid.designSystem.theme.Theme

@Composable
fun ProfileSection(
    username: String,
    profilePicture: String?,
    onProfileClick: () -> Unit = {}
) {
    val isDarkTheme = LocalIsDarkTheme.current
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(188.dp)
        ) {
            Image(
                painter = if (isDarkTheme) {
                    painterResource(com.madrid.presentation.R.drawable.profile_bg)
                } else {
                    painterResource(com.madrid.presentation.R.drawable.profile_bg_light)
                },
                contentDescription = "Profile Background",
                modifier = Modifier
                    .matchParentSize()
            )
            ProfilePicture(
                image = profilePicture,
                size = 100.dp,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = (50).dp)
            )
            MovioIcon(
                painter = painterResource(id = R.drawable.star_img),
                contentDescription = "star Icon",
                tint = Theme.color.surfaces.onSurfaceVariant,
                modifier = Modifier
                    .width(14.dp)
                    .height(12.dp)
                    .align(Alignment.Center)
                    .offset(x = 150.dp, y = (-30).dp)
                    .background(Color.Transparent) // Adjust background if needed
            )
            MovioIcon(
                painter = painterResource(id = R.drawable.star_img_four),
                contentDescription = "star Icon",
                tint = Theme.color.surfaces.onSurfaceVariant,
                modifier = Modifier
                    .width(18.23.dp)
                    .height(15.04.dp)
                    .align(Alignment.Center)
                    .offset(x = (-150).dp, y = 0.dp)
                    .background(Color.Transparent) // Adjust background if needed
            )
        }
        Spacer(modifier = Modifier.height(56.dp))
        MovioText(
            text = username,
            textStyle = Theme.textStyle.title.mediumMedium14,
            color = Theme.color.surfaces.onSurface,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp)
        )
    }
}

@Preview
@Composable
fun ProfileSectionPreview() {
    ProfileSection(
        username = "John Doe",
        profilePicture = null,
        onProfileClick = {}
    )
}