package app.skeleton.product.ui.composable.screen.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.skeleton.product.R
import app.skeleton.product.data.model.Product
import app.skeleton.product.data.model.ProductCategory
import app.skeleton.product.ui.composable.shared.DataBasedContainer
import app.skeleton.product.ui.composable.shared.DataEmptyContent
import app.skeleton.product.ui.state.DataUiState
import app.skeleton.product.ui.viewmodel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onNavigateToProductDetails: (productId: Int) -> Unit,
) {
    val productsState by viewModel.productsState.collectAsState()
    val selectedCategoriesState by viewModel.selectedCategoriesState.collectAsState()

    ProductsContent(
        productsState = productsState,
        selectedCategories = selectedCategoriesState,
        modifier = modifier,
        onCategoryClick = viewModel::selectCategory,
        onNavigateToProductDetails = onNavigateToProductDetails,
    )
}

@Composable
private fun ProductsContent(
    productsState: DataUiState<List<Product>>,
    selectedCategories: Set<ProductCategory>,
    modifier: Modifier = Modifier,
    onCategoryClick: (ProductCategory) -> Unit,
    onNavigateToProductDetails: (productId: Int) -> Unit,
) {
    Column(modifier = modifier) {

        DataBasedContainer<List<Product>>(
            dataState = productsState,

            dataPopulated = {
                // Place the list of products, filters, and other data-dependent content here.
                // If you want to place data-independent elements (e.g. a screen title),
                // put them outside the DataBasedContainer.
                ProductsPopulated(
                    products = (productsState as DataUiState.Populated).data,
                    selectedCategories = selectedCategories,
                    onCategoryClick = onCategoryClick,
                    onNavigateToProductDetails = onNavigateToProductDetails,
                )
            },

            dataEmpty = {
                DataEmptyContent(
                    primaryText = stringResource(R.string.products_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun ProductsPopulated(
    products: List<Product>,
    selectedCategories: Set<ProductCategory>,
    modifier: Modifier = Modifier,
    onCategoryClick: (ProductCategory) -> Unit,
    onNavigateToProductDetails: (productId: Int) -> Unit,
) {
    Column(modifier) {
        ProductFilter(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface),
            selectedCategories = selectedCategories,
            onCategoryClick = onCategoryClick,
        )

        ProductList(
            products = products,
            onNavigateToProductDetails = onNavigateToProductDetails,
        )
    }
}