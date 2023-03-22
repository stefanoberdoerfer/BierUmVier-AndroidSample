package de.stefanoberdoerfer.bierumvier.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.stefanoberdoerfer.bierumvier.data.db.model.BeerEntity
import de.stefanoberdoerfer.bierumvier.data.network.model.NetReqState
import de.stefanoberdoerfer.bierumvier.ui.theme.BierUmVierTheme
import de.stefanoberdoerfer.bierumvier.ui.theme.Primary
import org.koin.androidx.viewmodel.ext.android.viewModel

class BeerListFragment : Fragment() {

    private val viewModel: BeerListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                BierUmVierTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Primary
                    ) {
                        val beers = viewModel.beers.collectAsState()
                        val netReqState =
                            viewModel.netRequestState.collectAsState(initial = NetReqState.Success)

                        val netRequestState = netReqState.value
                        if (netRequestState is NetReqState.Error) {
                            // simplistic error ui, should be improved in an production app
                            Toast.makeText(
                                LocalContext.current,
                                stringResource(netRequestState.cause.uiStringRes),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        BeersList(beers.value, netRequestState, onItemClicked = {
                            this@BeerListFragment.onBeerClicked(it)
                        }, onScrolledToTheEnd = {
                            viewModel.fetchMoreBeers()
                        })
                    }
                }
            }
        }
    }

    private fun onBeerClicked(id: Long) {
        val directions = BeerListFragmentDirections.actionToDetailFragment(id)
        findNavController().navigate(directions)
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
            BeersList(
                listOf(
                    BeerEntity(
                        0,
                        "TestBier",
                        "https://images.punkapi.com/v2/2.png",
                        "Sucht deutschlandweit seines Gleichen",
                        6.7f,
                        "Tortilla Chips\nSteak\nNüsse",
                        "Brewers Tipps",
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