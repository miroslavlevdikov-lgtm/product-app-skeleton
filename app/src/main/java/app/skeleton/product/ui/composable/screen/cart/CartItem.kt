package app.skeleton.product.ui.composable.screen.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.skeleton.product.R
import app.skeleton.product.ui.state.CartItemUiState

@Composable
fun CartItemCard(
    modifier: Modifier = Modifier,
    cartItem: CartItemUiState,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            cartItem.productImageRes?.let { imageRes ->
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = cartItem.productTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(104.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = cartItem.productTitle,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(
                            R.string.price,
                            cartItem.productPrice * cartItem.quantity
                        ),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(
                            onClick = onMinusClick,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.minus_svgrepo_com),
                                contentDescription = stringResource(R.string.decrease_quantity_icon_description),
                                modifier = Modifier.size(26.dp),
                            )
                        }

                        Text(
                            text = cartItem.quantity.toString(),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )

                        IconButton(
                            onClick = onPlusClick,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.plus_svgrepo_com),
                                contentDescription = stringResource(R.string.increase_quantity_icon_description),
                                modifier = Modifier.size(26.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}