package com.pablogv63.quicklock

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.coroutines.coroutineContext


class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder> (), Filterable {

    private val credentialArray: ArrayList<Credential> = getCredentialArray() //TODO(Get credentials in a function)
    private var credentialArrayFiltered: ArrayList<Credential> = credentialArray //Filtro
    private var currentTime: String = Utilidades.getCurrentDateTimeEncoded()

    //Animation
    private var lastPosition = -1

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
        var credential = credentialArrayFiltered[position]
        holder.itemImage.setImageResource(R.drawable.ic_logo)
        holder.itemTitle.text = credential.name
        holder.itemDetail.text = Utilidades.getDateTimeDiff(credential.lastViewed,currentTime)

        //Animation
        /*
        val animation: Animation = AnimationUtils.loadAnimation(
            holder.itemImage.context,
            if (position > lastPosition) R.anim.up_from_bottom else R.anim.down_from_top
        )
        holder.itemView.startAnimation(animation)
        lastPosition = position

         */
    }

    override fun getItemCount(): Int {
        return credentialArrayFiltered.size
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
        for (i in 0..15) {
            //val iconText: String = icon.toString()
            val uniqueName = "$name $i"
            val credential = Credential(iconSrc, uniqueName)
            credentialArray.add(credential)
        }

        return credentialArray
    }


    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }

    /**
     *
     * Returns a filter that can be used to constrain data with a filtering
     * pattern.
     *
     *
     * This method is usually implemented by [android.widget.Adapter]
     * classes.
     *
     * @return a filter used to constrain data
     */
    override fun getFilter(): Filter {
        return object : Filter () {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    credentialArrayFiltered = credentialArray
                } else {
                    val resultList = ArrayList<Credential> ()
                    for (cred in credentialArray) {
                        if (cred.name.toLowerCase().contains(charSearch.toLowerCase())){
                            resultList.add(cred)
                        }
                    }
                    credentialArrayFiltered = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = credentialArrayFiltered
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                credentialArrayFiltered = results?.values as ArrayList<Credential>
                notifyDataSetChanged()
            }
        }
    }
}