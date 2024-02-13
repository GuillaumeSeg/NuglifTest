package lapresse.nuglif.domain.repositories

enum class SortingMethod(val keySetting: String) {
    BY_DATE("by_date"),
    BY_CHANNEL("by_channel"),
    UNSPECIFIED("unspecified");

    companion object {
        fun fromString(key: String): SortingMethod {
            return entries.find { it.keySetting == key } ?: UNSPECIFIED
        }
    }
}

/**
 *  SettingsRepository
 *  Responsible to save and retrieve the sorting method chosen by the user
 */
interface SettingsRepository {

    fun saveSortingMethod(sortingMethod: SortingMethod)

    fun retrieveSortingMethod(): SortingMethod

}