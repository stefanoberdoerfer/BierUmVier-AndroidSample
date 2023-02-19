package de.stefanoberdoerfer.bierumvier.ui.detail

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import de.stefanoberdoerfer.bierumvier.R
import de.stefanoberdoerfer.bierumvier.data.db.model.BeerEntity
import de.stefanoberdoerfer.bierumvier.ui.theme.BierUmVierTheme
import de.stefanoberdoerfer.bierumvier.ui.theme.Grey
import de.stefanoberdoerfer.bierumvier.ui.theme.Primary
import de.stefanoberdoerfer.bierumvier.ui.theme.PrimaryDark
import de.stefanoberdoerfer.bierumvier.ui.theme.White

@Composable
fun BeerDetails(beer: BeerEntity?, onEvaluationChanged: ((value: Float) -> Unit)?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            TitleImage(beer?.imgUrl, beer?.name ?: "")
        }
        item {
            TitleBlock(beer?.name ?: "", beer?.tagline ?: "")
        }
        item {
            TextContentBlock(
                stringResource(R.string.label_foodpairings),
                beer?.foodPairings ?: "---"
            )
        }
        item {
            TextContentBlock(
                stringResource(R.string.label_contributor),
                beer?.contributedBy ?: "---"
            )
        }
        item {
            EvaluationSlider(beer, onEvaluationChanged)
        }
    }
}

@Composable
fun TitleImage(imgUrl: String?, contentDesc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 16.dp)
            .statusBarsPadding(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imgUrl)
                .build(),
            contentDescription = contentDesc,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .padding(8.dp),
            fallback = painterResource(id = R.drawable.local_drink_white_24)
        )
    }
}

@Composable
fun TitleBlock(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = subtitle,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TextContentBlock(label: String, content: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EvaluationSlider(beer: BeerEntity?, onEvaluationChanged: ((value: Float) -> Unit)?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.label_evaluation),
            style = MaterialTheme.typography.titleLarge
        )
    }


    var sliderValueRaw by remember { mutableStateOf(beer?.evaluation ?: 5f) }
    // getting current interaction with slider - are we pressing or dragging?
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isDragged by interactionSource.collectIsDraggedAsState()
    val isInteracting = isPressed || isDragged

    // calculating actual slider value to display depending on whether we are interacting or not
    // using either the local value, or the one from our database / repository
    val determinedValueToShow by remember(isInteracting, sliderValueRaw, beer?.evaluation ?: 5f) {
        derivedStateOf {
            if (isInteracting) {
                sliderValueRaw
            } else {
                beer?.evaluation ?: 5f
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Slider(
            modifier = Modifier.width(300.dp),
            value = determinedValueToShow,
            interactionSource = interactionSource,
            valueRange = 0f..10f,
            colors = SliderDefaults.colors(
                activeTrackColor = White,
                inactiveTrackColor = Grey,
                activeTickColor = Primary,
                inactiveTickColor = PrimaryDark
            ),
            steps = 9,
            thumb = {
                Icon(
                    painterResource(id = R.drawable.baseline_star_rate_24),
                    "star",
                    Modifier
                        .height(48.dp)
                        .width(48.dp)
                )
            },
            onValueChange = { sliderValueRaw = it },
            onValueChangeFinished = { onEvaluationChanged?.invoke(sliderValueRaw) }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val formattedValue = "%.0f".format(determinedValueToShow)
        Text(
            text = "$formattedValue/10",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BeerDetailsPreview() {
    BierUmVierTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Primary
        ) {
            BeerDetails(
                BeerEntity(
                    0,
                    "TestBier",
                    "https://images.punkapi.com/v2/2.png",
                    "Sucht deutschlandweit seines Gleichen",
                    6.7f,
                    "Tortilla Chips\nSteak\nNüsse",
                    "Brewers Tips",
                    "Test"
                ),
                null
            )
        }
    }
}