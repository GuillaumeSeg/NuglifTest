package lapresse.nuglif.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import lapresse.nuglif.data.remote.FeedRemoteDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideFeedRemoteDataSource(@ApplicationContext context: Context): FeedRemoteDataSource {
        return FeedRemoteDataSource(context)
    }

}