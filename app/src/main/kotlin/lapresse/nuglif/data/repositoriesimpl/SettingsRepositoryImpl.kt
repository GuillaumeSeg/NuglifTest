package lapresse.nuglif.data.repositoriesimpl

import lapresse.nuglif.data.local.SettingsLocalDataSource
import lapresse.nuglif.domain.repositories.SettingsRepository
import lapresse.nuglif.domain.repositories.SortingMethod

class SettingsRepositoryImpl(
    private val settingsLocalDataSource: SettingsLocalDataSource
) : SettingsRepository {

    override fun saveSortingMethod(sortingMethod: SortingMethod) {
        settingsLocalDataSource.putString(SORTING_METHOD_SETTING, sortingMethod.keySetting)
    }

    override fun retrieveSortingMethod(): SortingMethod {
        return settingsLocalDataSource.getString(SORTING_METHOD_SETTING)?.let {
            SortingMethod.fromString(it)
        } ?: run {
            SortingMethod.UNSPECIFIED
        }
    }

    companion object {
        const val SORTING_METHOD_SETTING = "sorting-method-key"
    }
}