package com.pablogv63.quicklock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Llamada a listCredentials
        listCredentials()
    }

    /**
     * Lista las credenciales en el feed
     */
    fun listCredentials(){

        //Primero sacamos el feed
        var feed = findViewById<LinearLayout>(R.id.linearLayout_feed)

        //Obtenemos el array de credenciales
        //Incompleto!
        val credentialArray = getCredentialArray()

        //Vamos insertando tarjetas en el feed
        for (c in credentialArray) {
            val cardView = createCredentialCard(c)
            feed.addView(cardView)
        }

    }

    /**
     * Crea el elemento CardView de una credencial
     * cardView -> linearLayout(horizontal) -> elems
     */
    private fun createCredentialCard(c: Credential): CardView{
        val cardView = findViewById<CardView>(R.id.cardView_example)
        var cardView_new = cardView


        var linearLayout = LinearLayout(applicationContext)



        return cardView
    }


    /**
     * Obtiene el array de credenciales
     * PENDIENTE DE IMPLEMENTAR DESDE BD O ALGO
     */
    private fun getCredentialArray(): Array<Credential> {
        //Implementación de ejemplo para testing
        var icon = R.drawable.ic_logo
        var name = "Credential Name"
        var credentialArray = emptyArray<Credential>()
        //Bucle para rellenar credenciales
        for (i in 0..5) {
            val iconText: String = icon.toString()
            val uniqueName = "$name $i"
            val credential = Credential(iconText, uniqueName)
            credentialArray[i] = credential
        }

        return credentialArray
    }
}