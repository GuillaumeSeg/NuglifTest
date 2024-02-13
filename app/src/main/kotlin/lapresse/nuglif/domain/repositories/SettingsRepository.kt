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

interface SettingsRepository {

    fun saveSortingMethod(sortingMethod: SortingMethod)

    fun retrieveSortingMethod(): SortingMethod

}