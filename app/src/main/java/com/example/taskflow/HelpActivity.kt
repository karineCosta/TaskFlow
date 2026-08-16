package com.example.taskflow

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HelpActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_help)

        // Recupera os dados salvos pelo MainActivity
        val preferences = getSharedPreferences(
            "TaskFlowPrefs",
            MODE_PRIVATE
        )

        val titulo = preferences.getString(
            "ultima_tarefa_titulo",
            "Nenhuma tarefa cadastrada"
        )

        val descricao = preferences.getString(
            "ultima_tarefa_descricao",
            ""
        )

        Toast.makeText(
            this,
            "Última tarefa: $titulo",
            Toast.LENGTH_LONG
        ).show()

        // Configuração do WebView
        webView = findViewById(R.id.webView)

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.webViewClient = WebViewClient()

        webView.loadUrl("https://www.google.com")
    }
}