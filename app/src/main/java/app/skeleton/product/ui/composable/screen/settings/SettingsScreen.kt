package app.skeleton.product.ui.composable.screen.settings

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.skeleton.product.R
import app.skeleton.product.ui.composable.shared.CardBase

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    SettingsContent(
        companyName = stringResource(R.string.company_name),
        appVersion = stringResource(R.string.app_version),
        website = stringResource(R.string.customer_support_link),
        modifier = modifier
            .padding(20.dp),
    )
}

@Composable
private fun SettingsContent(
    companyName: String,
    appVersion: String,
    website: String,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        SettingsItem(
            iconResId = null,
            label = stringResource(R.string.settings_screen_company_label),
            content = companyName,
        )

        SettingsItem(
            iconResId = null,
            label = stringResource(R.string.settings_screen_version_label),
            content = appVersion,
        )

        SettingsItem(
            iconResId = null,
            label = stringResource(R.string.settings_screen_customer_support_label),
            content = website,
            onClick = { uriHandler.openUri(website) },
        )
    }
}

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int? = null,
    iconDescription: String? = null,
    label: String? = null,
    content: String,
    onClick: (() -> Unit)? = null,
) {
    CardBase(
        modifier = modifier,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            if (iconResId != null) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = iconDescription,
                    modifier = Modifier
                        .size(32.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                if (label != null) {
                    Text(
                        text = label,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text(
                    text = content,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}