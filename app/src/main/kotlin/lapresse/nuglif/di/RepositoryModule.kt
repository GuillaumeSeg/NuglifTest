package lapresse.nuglif.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lapresse.nuglif.data.local.SettingsLocalDataSource
import lapresse.nuglif.data.remote.FeedRemoteDataSource
import lapresse.nuglif.data.repositoriesimpl.FeedRepositoryImpl
import lapresse.nuglif.data.repositoriesimpl.FireworksRepositoryImpl
import lapresse.nuglif.data.repositoriesimpl.SettingsRepositoryImpl
import lapresse.nuglif.domain.repositories.FeedRepository
import lapresse.nuglif.domain.repositories.FireworksRepository
import lapresse.nuglif.domain.repositories.SettingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideFeedRepository(feedRemoteDataSource: FeedRemoteDataSource, settingsRepository: SettingsRepository): FeedRepository {
        return FeedRepositoryImpl(feedRemoteDataSource, settingsRepository)
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(settingsLocalDataSource: SettingsLocalDataSource): SettingsRepository {
        return SettingsRepositoryImpl(settingsLocalDataSource)
    }

    @Singleton
    @Provides
    fun provideFireworksRepository(): FireworksRepository {
        return FireworksRepositoryImpl()
    }

}