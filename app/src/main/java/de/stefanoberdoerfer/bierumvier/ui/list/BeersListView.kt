package de.stefanoberdoerfer.bierumvier.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import de.stefanoberdoerfer.bierumvier.R
import de.stefanoberdoerfer.bierumvier.data.db.model.BeerEntity
import de.stefanoberdoerfer.bierumvier.data.network.model.NetReqState
import de.stefanoberdoerfer.bierumvier.ui.theme.BierUmVierTheme
import de.stefanoberdoerfer.bierumvier.ui.theme.Grey
import de.stefanoberdoerfer.bierumvier.ui.theme.Primary
import de.stefanoberdoerfer.bierumvier.ui.theme.White

fun LazyListState.isScrolledToTheEnd() = layoutInfo.totalItemsCount != 0 &&
        layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

@Composable
fun BeersList(
    beers: List<BeerEntity>,
    netReqState: NetReqState,
    onItemClicked: ((id: Long) -> Unit)?,
    onScrolledToTheEnd: (() -> Unit)?
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(top = 16.dp, bottom = 64.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 16.dp)
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.beers_list_title),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        items(beers) { beer ->
            BeerItem(beer.id, beer.name, beer.tagline, beer.imgUrl, onItemClicked)
        }
        if (netReqState == NetReqState.Loading) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(color = White)
                }
            }
        }
        if (listState.isScrolledToTheEnd()) {
            onScrolledToTheEnd?.invoke()
        }
    }
}

@Composable
fun BeerItem(id: Long, name: String, desc: String?, imgUrl: String?, onClick: ((Long) -> Unit)?) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(color = Grey),
            ) {
                onClick?.invoke(id)
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 0.dp
        )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imgUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .padding(8.dp),
                fallback = painterResource(id = R.drawable.local_drink_24)
            )
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = desc ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BeersListPreview() {
    BierUmVierTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Primary
        ) {
            BeersList(
                listOf(
                    BeerEntity(
                        0,
                        "TestBier",
                        "https://images.punkapi.com/v2/2.png",
                        "Sucht deutschlandweit seines Gleichen",
                        6.7f,
                        "Tortilla Chips\nSteak\nNüsse",
                        "Brewers Tips",
                        "Test"
                    )
                ),
                NetReqState.Loading,
                null,
                null
            )
        }
    }
}