package com.madrid.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment.Companion.Unbounded
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import com.madrid.designSystem.component.ImageViewer
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.viewModel.shared.CategoryUiState
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun MovioPager(
    medias: List<MediaUiState>,
    modifier: Modifier = Modifier,
    onClickItem: (Int) -> Unit = {},
    isLoading: Boolean = true,
    onClickMediaButton: (Int) -> Unit = {},
) {
    if (medias.isNotEmpty()) {
        val currentLayoutDirection = LocalLayoutDirection.current
        val isRtl = currentLayoutDirection == LayoutDirection.Rtl
        val pagerState = rememberPagerState(
            initialPage = if (isRtl) medias.size - 1 else 0,
            pageCount = { medias.size }
        )

        LaunchedEffect(medias) {
            while (true) {
                delay(6000)
                val nextPage = if (isRtl) {
                    if (pagerState.currentPage == 0) medias.size - 1 else pagerState.currentPage - 1
                } else {
                    (pagerState.currentPage + 1) % medias.size
                }
                pagerState.animateScrollToPage(nextPage)
            }
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(413.dp)
                ,
            contentAlignment = Alignment.BottomCenter
        ) {
            ImageViewer(
                model = medias[pagerState.currentPage].imageUrl,
                contentDescription = "null",
                modifier = Modifier
                    .matchParentSize()
                    .blur(radius = 20.dp, edgeTreatment = Unbounded),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    HorizontalPager(
                        modifier = Modifier.fillMaxWidth(),
                        pageSpacing = (-240).dp,
                        state = pagerState,
                        verticalAlignment = Alignment.CenterVertically,
                    ) { page ->
                        val pageOffset =
                            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)

                        val absOffset = pageOffset.absoluteValue

                        val scale = lerp(0.7f, 1f, 1f - absOffset.coerceIn(0f, 1f))
                        val rotation =
                            lerp(
                                20f,
                                0f,
                                1f - absOffset.coerceIn(0f, 1f)
                            ) * if (pageOffset < 0) 1f else -1f
                        val alphaa = lerp(0.4f, 1f, 1f - absOffset.coerceIn(0f, 1f))
                        val zIndex = if (page == pagerState.currentPage) {
                            medias.size.toFloat()
                        } else {
                            medias.size - absOffset
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .then(
                                    if (pagerState.currentPage == page) Modifier.zIndex(1f) else Modifier
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            MovieHomeCard(
                                modifier = Modifier
                                    .graphicsLayer {
                                        scaleX = scale
                                        scaleY = scale
                                        rotationZ = rotation
                                        alpha = alphaa
                                        cameraDistance = 12 * density
                                    }
                                    .clip(RoundedCornerShape(8.dp))
                                    .size(width = 200.dp, height = 260.dp),
                                movieId = medias[page].imageUrl,
                                name = medias[page].title,
                                genres = medias[page].category.map { it.name },
                                onClickButton = { onClickMediaButton(page) },
                                onClick = { onClickItem(medias[page].id.toInt()) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                MovioPagerIndicator(
                    pageCount = medias.size,
                    currentPage = pagerState.currentPage,
                    isRtl = isRtl,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }


}

@Composable
private fun MovioPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    isRtl: Boolean,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            val indicatorRange =
                if (isRtl) (pageCount - 1) downTo 0 else 0 until pageCount
            repeat(pageCount) { index ->
                val isSelected = currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 15.dp else 5.dp, 5.dp)
                        .clip(if (isSelected) RoundedCornerShape(50) else CircleShape)
                        .background(
                            if (isSelected)
                                Theme.color.surfaces.onSurfaceAt1
                            else
                                Theme.color.surfaces.onSurfaceAt2
                        )
                )
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovioSliderPreview() {
    val fakeMediaList = listOf(
        MediaUiState(
            id = "1",
            mediaType = MediaType.MOVIE,
            title = "Inception",
            imageUrl = "https://image.tmdb.org/t/p/w500/53dsJ3oEnBhTBVMigWJ9tkA5bzJ.jpg",
            rating = "8.8",
            category = listOf(
                CategoryUiState(
                    name = "Sci-Fi"
                )
            ),
        ),
        MediaUiState(
            id = "2",
            mediaType = MediaType.TV_SHOW,
            title = "Breaking Bad",
            imageUrl = "https://image.tmdb.org/t/p/w500/x9HeaagUAyyGl1fQ6exQcpELBxP.jpg",
            rating = "9.5",
            category = listOf(
                CategoryUiState(
                    name = "Drama"
                )
            ),
        ),
        MediaUiState(
            id = "3",
            mediaType = MediaType.MOVIE,
            title = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/aFRDH3P7TX61FVGpaLhKr6QiOC1.jpg",
            rating = "9.0",
            category = listOf(
                CategoryUiState(
                    name = "Action"
                )
            ),
        )
    )
    MovioPager(
        medias = fakeMediaList,
        onClickItem = {}
    )
}