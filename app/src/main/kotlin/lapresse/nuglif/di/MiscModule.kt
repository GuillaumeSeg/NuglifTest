package lapresse.nuglif.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lapresse.nuglif.domain.repositories.FireworksRepository
import lapresse.nuglif.tools.ConfusingRandomLogger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MiscModule {

    @Singleton
    @Provides
    fun provideConfusingRandomLogger(fireworksRepository: FireworksRepository): ConfusingRandomLogger {
        return ConfusingRandomLogger(fireworksRepository)
    }

}