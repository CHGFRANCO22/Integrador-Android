package com.moreirafranco.gestiondevisitas.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.moreirafranco.gestiondevisitas.R
import com.moreirafranco.gestiondevisitas.data.SessionManager
import com.moreirafranco.gestiondevisitas.databinding.ActivityVisitaListBinding
import com.moreirafranco.gestiondevisitas.viewmodel.ViewModelFactory
import com.moreirafranco.gestiondevisitas.viewmodel.VisitaListViewModel

class VisitaListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVisitaListBinding
    private val viewModel: VisitaListViewModel by viewModels { ViewModelFactory() }
    private lateinit var adapter: VisitasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisitaListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        adapter = VisitasAdapter()
        binding.recyclerViewVisitas.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewVisitas.adapter = adapter

        // --- CAMBIO CLAVE AQUÍ ---
        viewModel.visitas.observe(this) { listaVisitas ->
            if (listaVisitas.isNullOrEmpty()) {
                // Si la lista está vacía, mostramos el estado vacío y ocultamos la lista
                binding.recyclerViewVisitas.visibility = View.GONE
                binding.layoutEmptyState.visibility = View.VISIBLE
            } else {
                // Si la lista tiene datos, ocultamos el estado vacío y mostramos la lista
                binding.recyclerViewVisitas.visibility = View.VISIBLE
                binding.layoutEmptyState.visibility = View.GONE
                adapter.submitList(listaVisitas)
            }
        }

        viewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }

        binding.fabAgregarVisita.setOnClickListener {
            startActivity(Intent(this, VisitaFormActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargarVisitas()
    }

    // El código del menú no cambia
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                SessionManager.clearAuthToken(this)
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}