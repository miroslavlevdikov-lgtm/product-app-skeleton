package app.skeleton.product.ui.composable.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.skeleton.product.ui.state.CartItemUiState

@Composable
fun CartList(
    cartItems: List<CartItemUiState>,
    modifier: Modifier = Modifier,
    onPlusItemClick: (Int) -> Unit,
    onMinusItemClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        items(cartItems) { cartItem ->
            CartItemCard(
                modifier = Modifier.fillMaxWidth(),
                cartItem = cartItem,
                onPlusClick = { onPlusItemClick(cartItem.productId) },
                onMinusClick = { onMinusItemClick(cartItem.productId) },
            )
        }
    }
}