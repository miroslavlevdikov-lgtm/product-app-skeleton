package app.skeleton.product.ui.composable.screen.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.skeleton.product.R
import app.skeleton.product.ui.composable.shared.DataBasedContainer
import app.skeleton.product.ui.composable.shared.DataEmptyContent
import app.skeleton.product.ui.state.CartItemUiState
import app.skeleton.product.ui.state.DataUiState
import app.skeleton.product.ui.viewmodel.CartViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel(),
    onNavigateToCheckoutScreen: () -> Unit,
) {
    val cartItemsState by viewModel.cartItemsState.collectAsStateWithLifecycle()
    val totalPrice by viewModel.totalPrice.collectAsStateWithLifecycle()

    val onPlusItemClick = { itemId: Int ->
        viewModel.incrementProductInCart(itemId)
    }

    val onMinusItemClick = { itemId: Int ->
        viewModel.decrementItemInCart(itemId)
    }

    CartScreenContent(
        cartItemsState = cartItemsState,
        modifier = modifier,
        totalPrice = totalPrice,
        onPlusItemClick = onPlusItemClick,
        onMinusItemClick = onMinusItemClick,
        onCompleteOrderButtonClick = onNavigateToCheckoutScreen,
    )
}

@Composable
private fun CartScreenContent(
    cartItemsState: DataUiState<List<CartItemUiState>>,
    modifier: Modifier = Modifier,
    totalPrice: Double,
    onPlusItemClick: (Int) -> Unit,
    onMinusItemClick: (Int) -> Unit,
    onCompleteOrderButtonClick: () -> Unit,
) {
    Column(modifier = modifier) {

        DataBasedContainer<List<CartItemUiState>>(
            dataState = cartItemsState,

            dataPopulated = {
                CartPopulated(
                    cartItems = (cartItemsState as DataUiState.Populated).data,
                    totalPrice = totalPrice,
                    onPlusItemClick = onPlusItemClick,
                    onMinusItemClick = onMinusItemClick,
                    onCompleteOrderButtonClick = onCompleteOrderButtonClick,
                )
            },

            dataEmpty = {
                DataEmptyContent(
                    primaryText = stringResource(R.string.cart_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun CartPopulated(
    cartItems: List<CartItemUiState>,
    modifier: Modifier = Modifier,
    totalPrice: Double,
    onPlusItemClick: (Int) -> Unit,
    onMinusItemClick: (Int) -> Unit,
    onCompleteOrderButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CartList(
            cartItems = cartItems,
            modifier = Modifier.weight(1f),
            onPlusItemClick = onPlusItemClick,
            onMinusItemClick = onMinusItemClick,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 30.dp),
            contentAlignment = Alignment.Center,
        ) {
            Button(
                onClick = onCompleteOrderButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                Text(
                    text = stringResource(R.string.button_place_order_label, totalPrice),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}