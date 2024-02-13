package lapresse.nuglif.tools

object Utils {

    fun convertHttpToHttps(url: String): String {
        return if (url.startsWith("http://")) {
            "https://" + url.substring(7)
        } else {
            url
        }
    }

}