package com.moreirafranco.gestiondevisitas.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.moreirafranco.gestiondevisitas.data.Visita
import com.moreirafranco.gestiondevisitas.databinding.ActivityVisitaDetailBinding
import com.moreirafranco.gestiondevisitas.viewmodel.ViewModelFactory
import com.moreirafranco.gestiondevisitas.viewmodel.VisitaDetailViewModel

class VisitaDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVisitaDetailBinding
    private val viewModel: VisitaDetailViewModel by viewModels { ViewModelFactory() }
    private var visita: Visita? = null

    companion object {
        const val EXTRA_VISITA_ID = "visita_id"
        const val EXTRA_VISITA_NOMBRE = "visita_nombre"
        const val EXTRA_VISITA_DIRECCION = "visita_direccion"
        const val EXTRA_VISITA_DETALLES = "visita_detalles"
        // --- AÑADIDO: Constante para la nueva fecha ---
        const val EXTRA_VISITA_CREATED_AT = "visita_created_at"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisitaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- CORREGIDO: Reconstruimos el objeto Visita con todos sus campos ---
        visita = Visita(
            id = intent.getIntExtra(EXTRA_VISITA_ID, -1),
            nombreCliente = intent.getStringExtra(EXTRA_VISITA_NOMBRE) ?: "",
            direccion = intent.getStringExtra(EXTRA_VISITA_DIRECCION) ?: "",
            detalles = intent.getStringExtra(EXTRA_VISITA_DETALLES) ?: "",
            // Recibimos la fecha de creación desde el Intent
            createdAt = intent.getStringExtra(EXTRA_VISITA_CREATED_AT) ?: ""
        )

        binding.textViewDetailNombre.text = visita?.nombreCliente
        binding.textViewDetailDireccion.text = visita?.direccion
        binding.textViewDetailDetalles.text = visita?.detalles

        binding.buttonEliminar.setOnClickListener {
            mostrarDialogoDeConfirmacion()
        }

        binding.buttonEditar.setOnClickListener {
            val intent = Intent(this, VisitaFormActivity::class.java).apply {
                putExtra(VisitaFormActivity.EXTRA_EDIT_VISITA_ID, visita?.id)
                putExtra(VisitaFormActivity.EXTRA_EDIT_VISITA_NOMBRE, visita?.nombreCliente)
                putExtra(VisitaFormActivity.EXTRA_EDIT_VISITA_DIRECCION, visita?.direccion)
                putExtra(VisitaFormActivity.EXTRA_EDIT_VISITA_DETALLES, visita?.detalles)
            }
            startActivity(intent)
        }

        viewModel.operationComplete.observe(this) {
            Toast.makeText(this, "Visita eliminada", Toast.LENGTH_SHORT).show()
            finish()
        }
        viewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume()
    { super.onResume()

    }

    private fun mostrarDialogoDeConfirmacion() {
        AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro?")
            .setPositiveButton("Eliminar") { _, _ ->
                visita?.id?.let { viewModel.deleteVisita(it) }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
