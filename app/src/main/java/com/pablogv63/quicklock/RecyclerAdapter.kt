package com.pablogv63.quicklock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder> () {

    private val credentialArray: ArrayList<Credential> = getCredentialArray() //TODO(Get credentials in a function)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init {
            itemImage = itemView.findViewById(R.id.card_image)
            itemTitle = itemView.findViewById(R.id.card_text)
            itemDetail = itemView.findViewById(R.id.card_text2)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.credential_card, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO(Get from a credential)
        /*holder.itemTitle.text = "SomeText"
        holder.itemDetail.text = "SomeOtherText"
        holder.itemImage.setImageResource(R.drawable.ic_logo)
        */
        var credential = credentialArray[position]
        holder.itemImage.setImageResource(R.drawable.ic_logo)
        holder.itemTitle.text = credential.name
        holder.itemDetail.text = credential.lastUsed
    }

    override fun getItemCount(): Int {
        return credentialArray.size
    }


    /**
     * Obtiene el array de credenciales
     * TODO(PENDIENTE DE IMPLEMENTAR DESDE BD O ALGO)
     */
    private fun getCredentialArray(): ArrayList<Credential> {
        //Implementación de ejemplo para testing
        var iconSrc = R.drawable.ic_logo
        var name = "Credential Name"
        var credentialArray = ArrayList<Credential>()
        //Bucle para rellenar credenciales
        for (i in 0..5) {
            //val iconText: String = icon.toString()
            val uniqueName = "$name $i"
            val credential = Credential(iconSrc, uniqueName)
            credentialArray.add(credential)
        }

        return credentialArray
    }
}