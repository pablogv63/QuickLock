package com.pablogv63.quicklock

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var contextApp: Context

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contextApp = this.applicationContext

        //Decoración entre elementos
        recyclerView_feed.addItemDecoration(DividerItemDecoration(contextApp, DividerItemDecoration.VERTICAL))

        //Animación
        val resId = R.anim.layout_animation_fall_down
        val animation = AnimationUtils.loadLayoutAnimation(contextApp,resId)
        recyclerView_feed.layoutAnimation = animation

        //Biometría
        val executor = ContextCompat.getMainExecutor(this)
        val biometricManager = BiometricManager.from(this)

        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                authUser(executor)
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Toast.makeText(
                    this,
                    getString(R.string.error_msg_no_biometric_hardware),
                    Toast.LENGTH_LONG
                ).show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                Toast.makeText(
                    this,
                    getString(R.string.error_msg_biometric_not_setup),
                    Toast.LENGTH_LONG
                ).show()
        }
        //Llamada a listCredentials
        //listCredentials()

        //__________________________________
        layoutManager = LinearLayoutManager(this)
        recyclerView_feed.layoutManager = layoutManager

        adapter = RecyclerAdapter()
        recyclerView_feed.adapter = adapter
    }

    /**
     * Lista las credenciales en el feed
     */
    private fun listCredentials(){

        //Primero sacamos el feed
        var feed = findViewById<RecyclerView>(R.id.recyclerView_feed)

        //Obtenemos el array de credenciales
        //Incompleto!
        val credentialArray = getCredentialArray()

        //Vamos insertando tarjetas en el feed
        for (c in credentialArray) {
            val cardView = createCredentialCard(c)
            feed.addView(cardView)
        }
        val a = 1
    }

    /**
     * Crea el elemento CardView de una credencial
     * cardView -> linearLayout(horizontal) -> elems
     */
    private fun createCredentialCard(c: Credential): View?{
        //Instanciamos un cardView
        var cardView = LayoutInflater.from(applicationContext).inflate(R.layout.credential_card,null)

        //Vamos especificando los valores



        return cardView
    }


    /**
     * Obtiene el array de credenciales
     * PENDIENTE DE IMPLEMENTAR DESDE BD O ALGO
     */
    private fun getCredentialArray(): ArrayList<Credential> {
        //Implementación de ejemplo para testing
        var icon = R.drawable.ic_logo
        var name = "Credential Name"
        var credentialArray = ArrayList<Credential>()
        //Bucle para rellenar credenciales
        for (i in 0..5) {
            val iconText: String = icon.toString()
            val uniqueName = "$name $i"
            val credential = Credential(icon, uniqueName)
            credentialArray.add(credential)
        }

        return credentialArray
    }

    /**
     * Autentica al usuario
     */
    private fun authUser(executor: Executor){
        // 1
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            // 2
            .setTitle(getString(R.string.auth_title))
            // 3
            .setSubtitle(getString(R.string.auth_subtitle))
            // 4
            .setDescription(getString(R.string.auth_description))
            // 5
            .setDeviceCredentialAllowed(true)
            // 6
            .build()

        // 1
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                // 2
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    //WHAT TO EXECUTE IF SUCCESS

                    //main_layout.visibility = View.VISIBLE
                    //Hago visible el feed
                    //linearLayout_feed.visibility = View.VISIBLE

                    //Llamada a listCredentials
                    //listCredentials()
                }
                // 3
                @SuppressLint("StringFormatInvalid")
                override fun onAuthenticationError(
                    errorCode: Int, errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.error_msg_auth_error, errString),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // 4
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext,
                        getString(R.string.error_msg_auth_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        biometricPrompt.authenticate(promptInfo)
    }
}