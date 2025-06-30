package com.moreirafranco.gestiondevisitas.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.moreirafranco.gestiondevisitas.data.SessionManager
import com.moreirafranco.gestiondevisitas.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Comprueba si ya hay una sesión activa
        if (SessionManager.getAuthToken(this) != null) {
            irAListaDeVisitas()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val usuario = binding.editTextUsuario.text.toString()
            val password = binding.editTextPassword.text.toString()

            // Login local falso
            if (usuario == "admin" && password == "1234") {
                SessionManager.saveAuthToken(this, "fake_local_token")
                irAListaDeVisitas()
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun irAListaDeVisitas() {
        startActivity(Intent(this, VisitaListActivity::class.java))
        finish()
    }
}

