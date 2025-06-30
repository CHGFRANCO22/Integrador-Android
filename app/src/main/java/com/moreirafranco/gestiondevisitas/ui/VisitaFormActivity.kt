package com.moreirafranco.gestiondevisitas.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moreirafranco.gestiondevisitas.databinding.ActivityVisitaFormBinding
import com.moreirafranco.gestiondevisitas.network.VisitaPayload
import com.moreirafranco.gestiondevisitas.viewmodel.ViewModelFactory
import com.moreirafranco.gestiondevisitas.viewmodel.VisitaFormViewModel

class VisitaFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVisitaFormBinding
    private val viewModel: VisitaFormViewModel by viewModels { ViewModelFactory() }
    private var idVisitaAEditar: Int? = null

    companion object {
        const val EXTRA_EDIT_VISITA_ID = "edit_visita_id"
        const val EXTRA_EDIT_VISITA_NOMBRE = "visita_nombre"
        const val EXTRA_EDIT_VISITA_DIRECCION = "visita_direccion"
        const val EXTRA_EDIT_VISITA_DETALLES = "visita_detalles"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisitaFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idRecibido = intent.getIntExtra(EXTRA_EDIT_VISITA_ID, -1)
        if (idRecibido != -1) {
            idVisitaAEditar = idRecibido
            title = "Editar Visita"
            binding.editTextNombre.setText(intent.getStringExtra(EXTRA_EDIT_VISITA_NOMBRE))
            binding.editTextDireccion.setText(intent.getStringExtra(EXTRA_EDIT_VISITA_DIRECCION))
            binding.editTextDetalles.setText(intent.getStringExtra(EXTRA_EDIT_VISITA_DETALLES))
        } else {
            title = "Nueva Visita"
        }

        binding.buttonGuardar.setOnClickListener {
            guardarVisita()
        }

        viewModel.operationComplete.observe(this) {
            Toast.makeText(this, "Operación exitosa", Toast.LENGTH_SHORT).show()
            finish()
        }
        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun guardarVisita() {
        val nombre = binding.editTextNombre.text.toString().trim()
        val direccion = binding.editTextDireccion.text.toString().trim()
        val detalles = binding.editTextDetalles.text.toString().trim()

        if (nombre.isEmpty() || direccion.isEmpty()) {
            Toast.makeText(this, "Nombre y dirección son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val payload = VisitaPayload(nombre, direccion, detalles)
        if (idVisitaAEditar == null) {
            viewModel.addVisita(payload)
        } else {
            viewModel.updateVisita(idVisitaAEditar!!, payload)
        }
    }
}
