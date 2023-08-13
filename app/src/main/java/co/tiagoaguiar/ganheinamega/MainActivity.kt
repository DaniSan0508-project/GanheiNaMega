package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private val NUMBERS_KEY = "numbers"
    private val PREFS_KEY = "prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_number)
        val textResult: TextView = findViewById(R.id.text_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)
        val btnClear: Button = findViewById(R.id.btn_clear)


        prefs = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        textResult.text = prefs.getString(NUMBERS_KEY,"Nenhum registro informado")



        btnGenerate.setOnClickListener{
            val serializedText = editText.text.toString()
            numberGenerator(serializedText, textResult)
        }

        btnClear.setOnClickListener {
            clearNumbers(textResult, editText)
        }
    }

    private fun numberGenerator(text: String, textResult: TextView) {
        val qtd = text.toIntOrNull()

        if (qtd == null || qtd !in 6..15) {
            showToast("Informe um número entre 6 e 15.")
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (numbers.size < qtd) {
            numbers.add(random.nextInt(60) + 1)
        }

        textResult.text = numbers.joinToString(" - ")
        prefs.edit().putString(NUMBERS_KEY,numbers.joinToString(" - ")).apply()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun clearNumbers(textResult: TextView, editText: EditText) {
        textResult.text = ""
        editText.setText("")
        prefs.edit().remove(NUMBERS_KEY).apply()
    }
}