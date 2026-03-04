package app.skeleton.product.ui.composable.screen.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.skeleton.product.R
import app.skeleton.product.ui.composable.shared.BottomButtonBox
import app.skeleton.product.ui.composable.shared.DataBasedContainer
import app.skeleton.product.ui.composable.shared.DataEmptyContent
import app.skeleton.product.ui.composable.shared.ItemsList
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

        DataBasedContainer(
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
        ItemsList(
            items = cartItems,
            modifier = modifier,
            itemCard = { cartItem ->
                CartItem(
                    cartItem = cartItem,
                    onPlusClick = { onPlusItemClick(cartItem.productId) },
                    onMinusClick = { onMinusItemClick(cartItem.productId) },
                )
            }
        )

        BottomButtonBox(
            onButtonClick = onCompleteOrderButtonClick,
            buttonText = stringResource(R.string.button_place_order_label, totalPrice),
        )
    }
}