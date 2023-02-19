package de.stefanoberdoerfer.bierumvier.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import de.stefanoberdoerfer.bierumvier.data.db.model.BeerEntity
import de.stefanoberdoerfer.bierumvier.ui.theme.BierUmVierTheme
import de.stefanoberdoerfer.bierumvier.ui.theme.Primary

class BeerDetailFragment : Fragment() {
    private lateinit var viewModel: BeerDetailViewModel
    private val args: BeerDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = BeerDetailViewModelFactory.createFactory(args.beerId)
        viewModel = ViewModelProvider(this, viewModelFactory)[BeerDetailViewModel::class.java]

        return ComposeView(requireContext()).apply {
            setContent {
                BierUmVierTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Primary
                    ) {
                        val beer = viewModel.beer.collectAsState()
                        BeerDetails(beer = beer.value) { newEvaluationValue ->
                            viewModel.updateEvaluation(newEvaluationValue)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
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
                    "Brewers Tipps",
                    "Test"
                ),
                null
            )
        }
    }
}