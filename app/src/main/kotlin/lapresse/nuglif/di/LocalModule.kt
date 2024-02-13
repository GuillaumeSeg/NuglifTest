package lapresse.nuglif.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import lapresse.nuglif.data.local.SettingsLocalDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideSettingsLocalDataSource(@ApplicationContext context: Context): SettingsLocalDataSource {
        return SettingsLocalDataSource(context)
    }

}