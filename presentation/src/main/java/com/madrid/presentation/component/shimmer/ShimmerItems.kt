package com.madrid.presentation.component.shimmer

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.designSystem.component.shimmerEffect
import com.madrid.designSystem.theme.Theme

@Composable
fun ShimmerItem(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading) {
        Row(
            modifier = modifier
                .padding(top = 132.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box() {
                ShimmerPagerCard(
                    Modifier
                        .offset(x = -150.dp, y = 30.dp)
                        .rotate(-20f)
                        .width(150.dp)
                        .height(200.dp)
                )
                ShimmerPagerCard(
                    Modifier
                        .offset(x = 200.dp, y = 30.dp)
                        .rotate(20f)
                        .width(150.dp)
                        .height(200.dp)
                )
                ShimmerPagerCard()
            }
        }
    }
    if (!isLoading) {
        contentAfterLoading()
    }
}

@Composable
fun ShimmerPagerCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(width = 200.dp, height = 260.dp)
            .clip(RoundedCornerShape(12.dp))
            .shimmerEffect()
    ) {
        Box(
            modifier = Modifier
                .padding(top = 207.dp, start = 8.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF101128))
                .width(44.dp)
                .height(20.dp)
        )
        Box(
            modifier = Modifier
                .padding(top = 232.dp, start = 8.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF101128))
                .width(44.dp)
                .height(20.dp)
        )
        Box(
            modifier = Modifier
                .padding(top = 232.dp, start = 59.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF101128))
                .width(44.dp)
                .height(20.dp)
        )
        Box(
            modifier = Modifier
                .padding(top = 232.dp, start = 112.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF101128))
                .width(44.dp)
                .height(20.dp)
        )
    }
}

@Composable
fun ShimmerBlurredImage(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading)
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(413.dp)
                .background(Theme.color.surfaces.surface)
        )
    else {
        contentAfterLoading()
    }
}

@Composable
fun ShimmerCard(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentAfterLoading: @Composable () -> Unit = {}
) {
    if (isLoading) {
        Log.d("TAG in shimmer", "ShimmerCard: ")
        Box(modifier = modifier.shimmerEffect())
    } else {
        contentAfterLoading()
    }
}


@Composable
fun ShimmerGrid(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    numberOfColumns: Int = 3,
    contentAfterLoading: @Composable () -> Unit = {},
) {
    if (isLoading) {
        LazyVerticalGrid(
            GridCells.Fixed(numberOfColumns),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier.padding(horizontal = 16.dp),
        ) {
            items(20) {
                Box(
                    Modifier
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .shimmerEffect()
                )
            }
        }
    } else {
        contentAfterLoading()
    }
}

@Composable
fun ShimmerHorizontalCard(
    isLoading: Boolean,
    primaryTextForCustomTextTitle: String,
    modifier: Modifier = Modifier,
    headerModifier: Modifier = Modifier,
    startIconForPrimaryTextTitle: Painter? = null,
    secondaryTextForCustomTextTitle: String? = null,
    endIconForCustomTextTitle: Painter? = null,
    onSeeAllClick: (() -> Unit)? = null,
    itemCount: Int = 5,
    contentAfterLoading: @Composable () -> Unit,
) {
    Column(modifier = modifier) {

        if (isLoading) {
            CustomTextTitle(
                modifier = headerModifier.padding(bottom = 12.dp),
                primaryText = primaryTextForCustomTextTitle,
                startIcon = startIconForPrimaryTextTitle,
                secondaryText = secondaryTextForCustomTextTitle,
                endIcon = endIconForCustomTextTitle,
                onSeeAllClick = onSeeAllClick,
                isListEmpty = isLoading
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(200.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(itemCount) {
                    Column {
                        Box(
                            modifier = Modifier
                                .size(width = 124.dp, height = 160.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .width(112.dp)
                                .height(15.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .shimmerEffect()
                        )
                    }
                }
            }
        } else {
            contentAfterLoading()
        }
    }
}
