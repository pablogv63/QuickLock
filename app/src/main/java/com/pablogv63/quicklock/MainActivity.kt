package com.pablogv63.quicklock

import android.annotation.SuppressLint
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var contextApp: Context

    private var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contextApp = this.applicationContext

        //Decoración entre elementos
        recyclerView_feed.addItemDecoration(DividerItemDecoration(contextApp, DividerItemDecoration.VERTICAL))
        //recyclerView_feed.visibility =

        //Animación -> No sé si dejarla o no, de momento se quita
        /*
        val resId = R.anim.layout_animation_fall_down
        val animation = AnimationUtils.loadLayoutAnimation(contextApp,resId)
        recyclerView_feed.layoutAnimation = animation
        */

        /*//Barra de navegación
        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.new_credential -> {
                    // Handle favorite icon press
                    true
                }
                R.id.search -> {
                    // Handle search icon press
                    true
                }
                else -> false
            }
        }*/

        //Visibilidad -> en DEBUG esta línea se queda quitada
        //recyclerView_feed.visibility = View.GONE

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
    }

    /**
     * Lista las credenciales en el feed
     */
    private fun listCredentials(){

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

        //__________________________________
        layoutManager = LinearLayoutManager(this)
        recyclerView_feed.layoutManager = layoutManager


        adapter = RecyclerAdapter()
        recyclerView_feed.adapter = adapter

    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        //Búsqueda
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        searchView.queryHint = getString(R.string.top_bar_search_hint)


        /*val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }*/

        return true
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                //Handle search
                //TODO (Search in recyclerView)
                Toast.makeText(this, "Search selected", Toast.LENGTH_SHORT)
                    .show();
                true
            }
            R.id.action_create -> {
                //Handle new credential
                //TODO (Go to create credential activity)
                Toast.makeText(this, "New selected", Toast.LENGTH_SHORT)
                    .show();
                true
            }
            else -> false
        }
        return false
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

                    recyclerView_feed.visibility = View.VISIBLE

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