package lapresse.nuglif.presentation.views.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import lapresse.nuglif.R
import lapresse.nuglif.domain.models.ui.ArticleHeadline
import lapresse.nuglif.domain.repositories.SortingMethod
import lapresse.nuglif.presentation.theme.LocalAppThemeColors
import lapresse.nuglif.presentation.theme.LocalReplacementTypography
import nl.dionsegijn.konfetti.compose.KonfettiView

@Composable
fun FeedView(viewModel: FeedViewModel) {

    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {

        val confettiState = viewModel.partyState.value
        confettiState?.let {
            KonfettiView(
                modifier = Modifier.fillMaxSize(),
                parties = it,
            )
        }
        Column(modifier = Modifier.fillMaxSize()) {
            var menuFilterExpanded by remember { mutableStateOf(false) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var menuSortingExpanded by remember { mutableStateOf(false) }
                Text(
                    text = stringResource(id = R.string.feed_title),
                    style = LocalReplacementTypography.current.bigTitle
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.TopEnd)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(42.dp)
                            .clickable(
                                onClick = { menuFilterExpanded = !menuFilterExpanded }
                            ),
                        imageVector = Icons.Filled.FilterAlt,
                        contentDescription = "",
                        tint = LocalAppThemeColors.current.onBackground,
                    )
                    Icon(
                        modifier = Modifier
                            .size(42.dp)
                            .clickable(
                                onClick = { menuSortingExpanded = !menuSortingExpanded }
                            ),
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = "",
                        tint = LocalAppThemeColors.current.onBackground,
                    )
                    DropdownMenu(
                        modifier = Modifier.width(200.dp),
                        expanded = menuSortingExpanded,
                        onDismissRequest = { menuSortingExpanded = false },
                    ) {
                        val sortedMethod = viewModel.sortedMethodState.value
                        DropdownMenuItem(
                            text = {
                                Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = stringResource(id = R.string.feed_sort_by_date))
                                    if (sortedMethod == SortingMethod.BY_DATE) {
                                        Icon(
                                            modifier = Modifier.size(26.dp),
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = "",
                                            tint = LocalAppThemeColors.current.accent,
                                        )
                                    }
                                }
                            },
                            onClick = { viewModel.onSortByDateClicked() }
                        )
                        DropdownMenuItem(
                            text = {
                                Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = stringResource(id = R.string.feed_sort_by_channel))
                                    if (sortedMethod == SortingMethod.BY_CHANNEL) {
                                        Icon(
                                            modifier = Modifier.size(26.dp),
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = "",
                                            tint = LocalAppThemeColors.current.accent,
                                        )
                                    }
                                }
                            },
                            onClick = { viewModel.onSortByChannelClicked() }
                        )
                    }
                }
            }
            if (menuFilterExpanded) {
                MenuFilter(viewModel)
            }
            LazyColumn {
                val articles = viewModel.articleHeadlineStateList
                items(articles.size) { index ->
                    val currentItem = articles[index]
                    ItemArticle(item = currentItem, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun ItemArticle(item: ArticleHeadline, viewModel: FeedViewModel) {
    Row(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .height(100.dp)
        .clickable(
            onClick = { viewModel.onItemClicked(item.id) }
        )
    ) {
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            thickness = 6.dp,
            color = LocalAppThemeColors.current.onBackground)
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "[${item.channelName}]",
                    style = LocalReplacementTypography.current.body.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = item.publicationDate.toString(),
                    style = LocalReplacementTypography.current.body.copy(fontWeight = FontWeight.Light)
                )
            }
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = item.title,
                style = LocalReplacementTypography.current.body)
            HorizontalDivider(
                modifier = Modifier.width(64.dp),
                thickness = 6.dp,
                color = LocalAppThemeColors.current.onBackground)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuFilter(viewModel: FeedViewModel) {
    val channels = viewModel.channels
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(channels[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.CenterEnd)
            .padding(horizontal = 22.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                channels.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            viewModel.onChannelClicked(item)
                            selectedText = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}