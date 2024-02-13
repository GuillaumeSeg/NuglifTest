package lapresse.nuglif.data.repositoriesimpl

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import lapresse.nuglif.data.remote.IFeedRemoteDataSource
import lapresse.nuglif.data.repositoriesimpl.FeedRepositoryImpl.Companion.ALL
import lapresse.nuglif.domain.models.Article
import lapresse.nuglif.domain.repositories.SettingsRepository
import lapresse.nuglif.domain.repositories.SortingMethod
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime
import java.time.Month

val articlesSource = listOf(
    Article(
        id = "5150450",
        channelName = "Faits divers",
        title = "La carcasse d'un camion incendi\u00e9 sur l'A25 Nord g\u00eane toujours le trafic",
        textContent = "La circulation en direction nord dans le pont-tunnel Louis-Hippolyte-LaFontaine devrait s'am\u00e9liorer jeudi matin en raison d'une r\u00e9ouverture de voies sur l'autoroute\u00a025, au sortir du tunnel, \u00e0 Montr\u00e9al.",
        urlWebsite = "",
        urlImage = null,
        publicationDate = LocalDateTime.of(2018, Month.JANUARY, 18, 6, 5),
        modificationDate = LocalDateTime.of(2018, Month.JANUARY, 18, 6, 5),
    ),
    Article(
        id = "5150480",
        channelName = "Europe",
        title = "Temp\\u00eate: chaos dans les transports et six morts dans le nord de l'Europe",
        textContent = "Le trafic ferroviaire grandes lignes \\u00e9tait compl\\u00e8tement suspendu jeudi en Allemagne et aux Pays-Bas et de nombreux avions clou\\u00e9s au sol \\u00e0 cause d'une temp\\u00eate qui a d\\u00e9j\\u00e0 fait six morts dans le nord de l'Europe.",
        urlWebsite = "",
        urlImage = null,
        publicationDate = LocalDateTime.of(2018, Month.JANUARY, 18, 12, 0),
        modificationDate = LocalDateTime.of(2018, Month.JANUARY, 18, 12, 0),
    ),
    Article(
        id = "5150462",
        channelName = "National",
        title = "Opio\\u00efdes: recevoir la mort par la poste",
        textContent = "Surnomm\\u00e9e Pink, Pinky ou encore U-4 aux \\u00c9tats-Unis, l'U-47700\\u00a0- drogue de synth\\u00e8se qui vient d'appara\\u00eetre dans l'est du Canada -\\u00a0a fait une premi\\u00e8re victime au Qu\\u00e9bec, a appris\\u00a0<em>La Presse</em>. Le p\\u00e8re du jeune homme mort d'une surdose implore les autorit\\u00e9s canadiennes d'en faire davantage pour intercepter les opio\\u00efdes livr\\u00e9s par la poste.",
        urlWebsite = "",
        urlImage = null,
        publicationDate = LocalDateTime.of(2018, Month.JANUARY, 18, 7, 9),
        modificationDate = LocalDateTime.of(2018, Month.JANUARY, 18, 7, 9),
    ),
)

class FeedRepositoryImplTest {

    private lateinit var feedRepositoryImpl: FeedRepositoryImpl
    private lateinit var feedRemoteDataSource: IFeedRemoteDataSource
    private lateinit var settingsRepository: SettingsRepository

    @Before
    fun setup() {
        feedRemoteDataSource = mock(IFeedRemoteDataSource::class.java)
        settingsRepository = mock(SettingsRepository::class.java)
        feedRepositoryImpl = FeedRepositoryImpl(feedRemoteDataSource, settingsRepository)
    }

    @Test
    fun `sortByChannel sorts articles by channel name`() {
        // Given
        val articles = articlesSource

        runBlocking {
            feedRepositoryImpl.articles.emit(articles)
        }

        // When
        feedRepositoryImpl.sortByChannel()

        // Then
        val sortedArticles = runBlocking { feedRepositoryImpl.articles.first() }
        assertEquals("Europe", sortedArticles[0].channelName)
        assertEquals("Faits divers", sortedArticles[1].channelName)
        assertEquals("National", sortedArticles[2].channelName)
    }

    @Test
    fun `sortByDate sorts articles by date`() {
        // Given
        val articles = articlesSource

        runBlocking {
            feedRepositoryImpl.articles.emit(articles)
        }

        // When
        feedRepositoryImpl.sortByDate()

        // Then
        val sortedArticles = runBlocking { feedRepositoryImpl.articles.first() }
        assertEquals("Europe", sortedArticles[0].channelName)
        assertEquals("National", sortedArticles[1].channelName)
        assertEquals("Faits divers", sortedArticles[2].channelName)
    }

    @Test
    fun `filterByChannel filters articles by a ALL`() {
        // Given
        val articles = articlesSource
        val channelToFilter = ALL

        runBlocking {
            feedRepositoryImpl.allArticles = articles
            feedRepositoryImpl.articles.emit(articles)
        }

        // When
        `when`(settingsRepository.retrieveSortingMethod()).thenReturn(SortingMethod.UNSPECIFIED)
        feedRepositoryImpl.filterByChannel(channelToFilter)

        // Then
        val sortedArticles = runBlocking { feedRepositoryImpl.articles.first() }
        assertTrue("not filtered", sortedArticles.size == 3)
    }

    @Test
    fun `filterByChannel filters articles by a channel`() {
        // Given
        val articles = articlesSource
        val channelToFilter = "National"

        runBlocking {
            feedRepositoryImpl.allArticles = articles
            feedRepositoryImpl.articles.emit(articles)
        }

        // When
        feedRepositoryImpl.updateMapChannel(
            mapOf(
                "Faits divers" to mutableListOf("5150450"),
                "Europe" to mutableListOf("5150480"),
                "National" to mutableListOf("5150462")
            )
        )
        feedRepositoryImpl.filterByChannel(channelToFilter)

        // Then
        val filteredArticles = runBlocking { feedRepositoryImpl.articles.first() }
        assertEquals("National", filteredArticles[0].channelName)
        assertTrue("filtered", filteredArticles.size == 1)
    }
}