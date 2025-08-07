package com.madrid.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.navigation.Destinations

@Preview(showBackground = true)
@Composable
fun CustomBottomBarPreview() {
    MovioTheme {
        CustomBottomBar(
            currentDestination = Destinations.HomeScreen,
            navItems = getNavBarDestinations(),
            onNavDestinationClicked = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
        )
    }
}
data class NavBarItem(
    val destination: Destinations,
    val label: String,
    val contentDescription: String = "none",
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int,
)

@Composable
fun getNavBarDestinations() = listOf(
    NavBarItem(
        destination = Destinations.HomeScreen,
        label = stringResource(R.string.home),
        contentDescription = "Home Screen",
        selectedIcon = R.drawable.icon_home_selected,
        unSelectedIcon = R.drawable.icon_home
    ),
    NavBarItem(
        destination = Destinations.SearchScreen,
        label = stringResource(R.string.search),
        selectedIcon = R.drawable.icon_search_selected,
        unSelectedIcon = R.drawable.icon_search
    ),
    NavBarItem(
        destination = Destinations.LibraryScreen,
        label = stringResource(R.string.library),
        selectedIcon = R.drawable.icon_library_selected,
        unSelectedIcon = R.drawable.icon_library,
    ),
    NavBarItem(
        destination = Destinations.MoreScreen,
        label = stringResource(R.string.more),
        selectedIcon = R.drawable.icon_more_selected,
        unSelectedIcon = R.drawable.icon_more
    )
)
@Composable
fun CustomBottomBar(
    currentDestination: Destinations,
    onNavDestinationClicked: (Destinations) -> Unit,
    navItems: List<NavBarItem>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme.color.surfaces.surface,
    selectedTextStyle: TextStyle = Theme.textStyle.label.mediumMedium12,
    unselectedTextStyle: TextStyle = Theme.textStyle.label.smallRegular12,
    selectedTextColor: Color = Theme.color.brand.primary,
    unSelectedTextColor: Color = Theme.color.surfaces.onSurfaceVariant,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(74.dp)
            .background(color = backgroundColor)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navItems.forEach { item ->
            val isSelected = currentDestination::class == item.destination::class
            CustomNavBarItem(
                isSelected = isSelected,
                onNavDestinationClicked = onNavDestinationClicked,
                item = item,
                iconRes = if (isSelected) item.selectedIcon else item.unSelectedIcon,
                selectedTextStyle = selectedTextStyle,
                unselectedTextStyle = unselectedTextStyle,
                labelColor = if (isSelected) selectedTextColor else unSelectedTextColor
            )
        }
    }
}
@Composable
private fun CustomNavBarItem(
    isSelected: Boolean,
    onNavDestinationClicked: (Destinations) -> Unit,
    item: NavBarItem,
    @DrawableRes iconRes: Int,
    selectedTextStyle: TextStyle,
    unselectedTextStyle: TextStyle,
    labelColor: Color,
) {
    val iconBrush = if (isSelected) {
        Theme.color.gradients.iconGradient
    } else {
        null
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (!isSelected) {
                    onNavDestinationClicked(item.destination)
                }
            }
    ) {
        MovioIcon(
            painter = painterResource(id = iconRes),
            contentDescription = item.contentDescription,
            modifier = Modifier.size(24.dp),
            brush = iconBrush,
            tint =Theme.color.surfaces.onSurfaceVariant,
        )
        MovioText(
            text = item.label,
            textStyle = if (isSelected) selectedTextStyle else unselectedTextStyle,
            color = labelColor,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
