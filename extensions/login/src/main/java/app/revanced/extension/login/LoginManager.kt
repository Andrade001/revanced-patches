package app.revanced.extension.login

import android.content.Context
import android.content.Intent

object LoginManager {
    var endpoint: String = "https://example.com/api.php"

    private var context: Context? = null

    fun setContext(ctx: Context) {
        context = ctx.applicationContext
        showLogin()
    }

    private fun showLogin() {
        context?.let {
            val intent = Intent(it, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            it.startActivity(intent)
        }
    }
}
