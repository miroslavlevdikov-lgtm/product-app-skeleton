package app.skeleton.product.ui.composable.screen.orders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.skeleton.product.R
import app.skeleton.product.data.entity.OrderEntity
import app.skeleton.product.ui.composable.shared.DataBasedContainer
import app.skeleton.product.ui.composable.shared.DataEmptyContent
import app.skeleton.product.ui.composable.shared.ItemsList
import app.skeleton.product.ui.state.DataUiState
import app.skeleton.product.ui.viewmodel.OrderViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = koinViewModel(),
) {
    val ordersState by viewModel.ordersState.collectAsState()

    OrdersContent(
        ordersState = ordersState,
        modifier = modifier,
    )
}

@Composable
private fun OrdersContent(
    ordersState: DataUiState<List<OrderEntity>>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {

        DataBasedContainer(
            dataState = ordersState,

            dataPopulated = {
                OrdersPopulated(
                    orders = (ordersState as DataUiState.Populated).data,
                )
            },

            dataEmpty = {
                DataEmptyContent(
                    primaryText = stringResource(R.string.orders_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun OrdersPopulated(
    orders: List<OrderEntity>,
    modifier: Modifier = Modifier,
) {

    ItemsList(
        items = orders,
        modifier = modifier,
        itemCard = { order -> OrderItem(order) }
    )
}