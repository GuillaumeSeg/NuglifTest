package lapresse.nuglif.presentation.views.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import lapresse.nuglif.R
import lapresse.nuglif.presentation.theme.LocalAppThemeColors
import lapresse.nuglif.presentation.theme.LocalReplacementTypography
import lapresse.nuglif.tools.Utils

@Composable
fun DetailsView(viewModel: DetailsViewModel) {

    BackHandler {
        viewModel.onBackPressed()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            viewModel.articleDetailsState.value?.let { item ->
                item.urlImg?.let {
                    val urlSafe = Utils.convertHttpToHttps(it)
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(urlSafe)
                            .crossfade(true)
                            .build(),
                        contentDescription = "")
                }
                Column(modifier = Modifier
                    .padding(5.dp)
                    .verticalScroll(rememberScrollState())) {
                    Text(
                        text = item.title,
                        style = LocalReplacementTypography.current.bigTitle.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = "[${item.channelName}]",
                        style = LocalReplacementTypography.current.headline.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    )
                    Text(
                        text = item.publicationDate,
                        style = LocalReplacementTypography.current.headline.copy(fontWeight = FontWeight.Light, fontSize = 16.sp)
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = item.textContent,
                        style = LocalReplacementTypography.current.body.copy(fontWeight = FontWeight.Normal, fontSize = 18.sp)
                    )
                    val uriHandler = LocalUriHandler.current
                    Text(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .clickable {
                                val urlSafe = Utils.convertHttpToHttps(item.urlWebsite)
                                uriHandler.openUri(urlSafe)
                            },
                        text = stringResource(id = R.string.details_read_more),
                        color = LocalAppThemeColors.current.secondary,
                        style = LocalReplacementTypography.current.body.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = stringResource(id = R.string.details_modified_label) + item.modificationDate,
                        style = LocalReplacementTypography.current.body.copy(fontWeight = FontWeight.Light, fontSize = 13.sp)
                    )
                }
            }
        }
    }

}