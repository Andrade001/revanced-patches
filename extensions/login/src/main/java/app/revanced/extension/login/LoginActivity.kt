package app.revanced.extension.login

import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            Thread {
                try {
                    val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID) ?: ""
                    val params = "username=" + URLEncoder.encode(username.text.toString(), "UTF-8") +
                            "&password=" + URLEncoder.encode(password.text.toString(), "UTF-8") +
                            "&android_id=" + URLEncoder.encode(androidId, "UTF-8")
                    val connection = URL(LoginManager.endpoint).openConnection() as HttpURLConnection
                    connection.requestMethod = "POST"
                    connection.doOutput = true
                    OutputStreamWriter(connection.outputStream).use { it.write(params) }
                    val result = connection.inputStream.bufferedReader().readText()
                    runOnUiThread {
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show()
                        finish()
                    }
                } catch (t: Throwable) {
                    runOnUiThread {
                        Toast.makeText(this, t.message, Toast.LENGTH_LONG).show()
                    }
                }
            }.start()
        }
    }
}
