package com.moreirafranco.gestiondevisitas.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.moreirafranco.gestiondevisitas.R
import com.moreirafranco.gestiondevisitas.data.SessionManager
import com.moreirafranco.gestiondevisitas.data.Visita
import com.moreirafranco.gestiondevisitas.databinding.ItemVisitaBinding

class VisitasAdapter : RecyclerView.Adapter<VisitasAdapter.VisitaViewHolder>() {
    private var visitas: List<Visita> = emptyList()

    class VisitaViewHolder(val binding: ItemVisitaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(visita: Visita) {
            binding.textViewNombreCliente.text = visita.nombreCliente
            binding.textViewDireccion.text = visita.direccion
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitaViewHolder {
        val binding = ItemVisitaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VisitaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VisitaViewHolder, position: Int) {
        val visita = visitas[position]
        holder.bind(visita)

        val context = holder.itemView.context

        fun updateFavoriteIcon() {
            val isFavorite = SessionManager.isFavorite(context, visita.id)
            if (isFavorite) {
                holder.binding.imageViewFavorite.setImageResource(R.drawable.ic_star_filled)
                // CORREGIDO: Usamos un color naranja estándar de Android que siempre existe.
                holder.binding.imageViewFavorite.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_orange_dark))
            } else {
                holder.binding.imageViewFavorite.setImageResource(R.drawable.ic_star_border)
                // Usamos un color gris estándar que siempre existe.
                holder.binding.imageViewFavorite.setColorFilter(ContextCompat.getColor(context, android.R.color.darker_gray))
            }
        }

        updateFavoriteIcon()

        holder.binding.imageViewFavorite.setOnClickListener {
            if (SessionManager.isFavorite(context, visita.id)) {
                SessionManager.removeFavorite(context, visita.id)
            } else {
                SessionManager.addFavorite(context, visita.id)
            }
            updateFavoriteIcon()
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, VisitaDetailActivity::class.java).apply {
                putExtra(VisitaDetailActivity.EXTRA_VISITA_ID, visita.id)
                putExtra(VisitaDetailActivity.EXTRA_VISITA_NOMBRE, visita.nombreCliente)
                putExtra(VisitaDetailActivity.EXTRA_VISITA_DIRECCION, visita.direccion)
                putExtra(VisitaDetailActivity.EXTRA_VISITA_DETALLES, visita.detalles)
                // Se comprueba si createdAt es nulo para evitar errores
                visita.createdAt?.let { createdAt ->
                    putExtra(VisitaDetailActivity.EXTRA_VISITA_CREATED_AT, createdAt)
                }
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = visitas.size

    fun submitList(nuevaLista: List<Visita>) {
        visitas = nuevaLista
        notifyDataSetChanged()
    }
}
