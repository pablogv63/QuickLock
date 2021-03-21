package com.pablogv63.quicklock

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.pablogv63.quicklock.credential.Credential
import com.pablogv63.quicklock.credential.fields.CredentialFields
import com.pablogv63.quicklock.credential.Credentials
import com.pablogv63.quicklock.credential.fields.CredentialField
import com.pablogv63.quicklock.utilities.Animations
import com.pablogv63.quicklock.utilities.Utilities


class RecyclerAdapter(val context: Context): RecyclerView.Adapter<RecyclerAdapter.ViewHolder> (), Filterable {

    private val credentialList: MutableList<Credential> = getCredentialList()
    var credentialArrayFiltered: MutableList<Credential> = credentialList //Filtro
    private var currentTime: String = Utilities.getCurrentDateTimeEncoded()

    //Animation
    private var lastExpandedPos: Int = -1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cardHeader: RelativeLayout = itemView.findViewById(R.id.card_header)
        var expandedLayout: LinearLayout = itemView.findViewById(R.id.card_expanded_layout)
        private var usernameLayout: RelativeLayout = itemView.findViewById(R.id.usernameLayout)

        var image: ImageView = itemView.findViewById(R.id.card_image)
        var title: TextView = itemView.findViewById(R.id.card_title)

        var lastViewedDiff: TextView = itemView.findViewById(R.id.card_last_used)
        lateinit var lastViewed: String

        var password: TextView = itemView.findViewById(R.id.card_expanded_password_text)


        init {
            prepareCard()
        }

        var username: TextView = itemView.findViewById(R.id.card_expanded_username)
            set(value) {
                usernameLayout.visibility = View.VISIBLE
                field = value
            }

        /**
         * Prepara la cardView
         */
        private fun prepareCard() {
            expandedLayout.visibility = View.GONE

            //Username
            //usernameLayout.visibility = View.GONE //TODO(Assure every credential has username to remove this)?
            val userCopyIcon = itemView.findViewById<ImageView>(R.id.card_expanded_username_copy)
            createCopyIconListener(userCopyIcon,username,R.string.copied_username)

            //Password
            val passCopyIcon = itemView.findViewById<ImageView>(R.id.card_expanded_password_copy)
            createCopyIconListener(passCopyIcon,password,R.string.copied_password)
        }

        /**
         * Crea un Listener para el icono que copia el texto al portapapeles
         */
        private fun createCopyIconListener(copyIcon: ImageView, textView: TextView, stringResourceId: Int){
            //Clipboard Manager
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            copyIcon.setOnClickListener { _ ->
                val clipData = ClipData.newPlainText(title.text,textView.text)
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(context, context.getText(stringResourceId), Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * Asigna los datos de la credencial a los campos
         */
        fun bind (credential: Credential) {
            title.text = credential.name
            lastViewedDiff.text = Utilities.getDateTimeDiff(credential.lastViewed)
            lastViewed = credential.lastViewed

            for (field in credential.fields.getCredentialFields()) {
                when (field.id) {
                    CredentialFields.FieldType.USERNAME -> {
                        username.text = field.value
                    }
                    CredentialFields.FieldType.PASSWORD -> {
                        password.text = field.value
                    }
                    else -> {}
                }
            }
        }

        /**
         * Añade un proceso que actualiza el dato lastViewed cada 1000ms
         */
        fun setLastViewedRefresher(){
            val mainHandler = Handler(Looper.getMainLooper())

            mainHandler.post(object: Runnable {
                override fun run() {
                    lastViewedDiff.text = Utilities.getDateTimeDiff(lastViewed)
                    mainHandler.postDelayed(this, 1000)
                }
            })

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.credential_card, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        //Se llama solamente para colapsar el elemento en lastExpanded
        onBindViewHolder(holder,position)
        //Mirar si se le pide colapsarse (hay datos en payloads)
        if (payloads.isNotEmpty()) {
            val credential = credentialArrayFiltered[position]
            collapseOrExpand(holder, credential)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val credential = credentialArrayFiltered[position]

        //Setear datos
        holder.bind(credential)

        //Expandir o colapsar
        holder.cardHeader.setOnClickListener { _ ->
            collapseOrExpand(holder,credential)
            //Collapsar el anterior si no es el mismo
            lastExpandedPos = if (lastExpandedPos != -1) {
                //El anterior expandido es otro
                if (lastExpandedPos != position) {
                    notifyItemChanged(lastExpandedPos, listOf(position))
                    position
                } else { //El anterior expandido es el mismo
                    -1
                }
            } else {
                position
            }
            notifyItemChanged(position)
        }

        //Establecer el refresco
        holder.setLastViewedRefresher()

    }

    private fun collapseOrExpand(holder: ViewHolder, credential: Credential) {
        val v = holder.image
        val expanded = toggleLayout(credential.expanded, v, holder.expandedLayout)
        credential.expanded = !expanded
    }

    private fun toggleLayout(isExpanded: Boolean, v: View, layoutExpand: LinearLayout): Boolean {
        Animations.toggleArrow(v,isExpanded)
        if (!isExpanded) {
            Animations.expand(layoutExpand)
        } else {
            Animations.collapse(layoutExpand)
        }
        return isExpanded
    }

    override fun getItemCount(): Int {
        return credentialArrayFiltered.size
    }


    /**
     * Obtiene el array de credenciales
     * TODO(PENDIENTE DE IMPLEMENTAR DESDE BD O ALGO)
     */
    private fun getCredentialArray(): MutableList<Credential> {
        //Implementación de ejemplo para testing
        val name = context.getString(R.string.default_credential_name)
        val category = context.getString(R.string.no_category)
        val fields = CredentialFields()
        fields.add(CredentialField(CredentialFields.FieldType.USERNAME,"username"))
        fields.add(CredentialField(CredentialFields.FieldType.PASSWORD,"12345678"))
        //Bucle para rellenar credenciales
        val list = Credentials.getList(context)
        if (list.isEmpty()) {
            for (i in 0..15) {
                val uniqueName = "$name $i"
                val credential = Credential(uniqueName, category, fields)
                Credentials.add(credential)
            }
        }

        return list
    }

    /**
     * Obtiene el array de credenciales desde donde toca
     */
    private fun getCredentialList(): MutableList<Credential> {
        return Credentials.getList(context)
    }

    /**
     * Removes a credential from the list (and in general)
     */
    fun remove(position: Int){
        //TODO("Not yet implemented")
    }


    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }

    /**
     * Filtra los resultados al buscar
     */
    override fun getFilter(): Filter {
        return object : Filter () {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                credentialArrayFiltered = if (charSearch.isEmpty()) {
                    credentialList
                } else {
                    val resultList = ArrayList<Credential> ()
                    for (cred in credentialList) {
                        if (cred.name.toLowerCase().contains(charSearch.toLowerCase())){
                            resultList.add(cred)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = credentialArrayFiltered
                //Collapse all TODO(Make it work)
                if (lastExpandedPos != -1) notifyItemChanged(lastExpandedPos, mutableListOf(true))
                lastExpandedPos = -1

                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                credentialArrayFiltered = results?.values as ArrayList<Credential>
                notifyDataSetChanged()
            }
        }
    }

    /**
     * Gets the credential
     */
    fun getCredential(position: Int): Credential {
        return credentialArrayFiltered[position]
    }

    private fun getCredentialPosition(position: Int): Int {
        return credentialArrayFiltered[position].positionInList
    }

    fun openEditActivity(positionInLayout: Int) {
        val intent = Intent(context,EditActivity::class.java).apply {
            putExtra("CREDENTIAL_POSITION", getCredentialPosition(positionInLayout))
        }
        ActivityCompat.startActivityForResult(context as Activity, intent, 1, null) //LLAMADA A EditActivity
        notifyDataSetChanged()
    }
}