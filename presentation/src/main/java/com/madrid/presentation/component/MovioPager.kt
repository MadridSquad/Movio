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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment.Companion.Unbounded
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import com.madrid.designSystem.theme.Theme
import com.madrid.detectImageContent.FilteredImage
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState
import kotlin.math.absoluteValue

@Composable
fun MovioPager(
    movies: List<MediaUiState>,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val pagerState = rememberPagerState(
        initialPage = movies.size / 2,
        pageCount = { movies.size }
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(413.dp)
            .shadow(elevation = 8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        FilteredImage(
            imageUrl = movies[pagerState.currentPage].imageUrl,
            contentDescription = "null",
            modifier = Modifier
                .matchParentSize()
                .blur(radius = 16.dp, edgeTreatment = Unbounded),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                    lerp(20f, 0f, 1f - absOffset.coerceIn(0f, 1f)) * if (pageOffset < 0) 1f else -1f
                val alphaa = lerp(0.4f, 1f, 1f - absOffset.coerceIn(0f, 1f))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                            .zIndex(1f - absOffset)
                            .clip(RoundedCornerShape(8.dp))
                            .size(width = 200.dp, height = 260.dp),
                        movieId = movies[page].imageUrl,
                        name = "Movie $page",
                        genres = listOf("Action", "Adventure"),
                        onClick = onClickItem,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(movies.size) { index ->
                    val isSelected = pagerState.currentPage == index
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
            Spacer(modifier = Modifier.height(4.dp))
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
            category = "Sci-Fi"
        ),
        MediaUiState(
            id = "2",
            mediaType = MediaType.TV_SERIES,
            title = "Breaking Bad",
            imageUrl = "https://image.tmdb.org/t/p/w500/x9HeaagUAyyGl1fQ6exQcpELBxP.jpg",
            rating = "9.5",
            category = "Drama"
        ),
        MediaUiState(
            id = "3",
            mediaType = MediaType.MOVIE,
            title = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/aFRDH3P7TX61FVGpaLhKr6QiOC1.jpg",
            rating = "9.0",
            category = "Action"
        )
    )
    MovioPager(
        movies = fakeMediaList,
        onClickItem = {}
    )
}