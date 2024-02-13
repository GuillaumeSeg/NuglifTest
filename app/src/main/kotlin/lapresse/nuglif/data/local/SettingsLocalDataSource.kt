package lapresse.nuglif.data.local

import android.content.Context
import android.content.SharedPreferences
import lapresse.nuglif.R

class SettingsLocalDataSource(context: Context) {

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(
            context.getString(R.string.shared_pref_key),
            Context.MODE_PRIVATE
        )
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

}