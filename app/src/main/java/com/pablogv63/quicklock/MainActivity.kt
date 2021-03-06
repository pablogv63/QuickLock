package com.pablogv63.quicklock

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.pablogv63.quicklock.utilities.SwipeCallback
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
        setSupportActionBar(findViewById(R.id.top_app_bar_main))

        //Decoración entre elementos
        recyclerView_feed.addItemDecoration(
            DividerItemDecoration(
                contextApp,
                DividerItemDecoration.VERTICAL
            )
        )

        //Animación (quita parpadeo)
        (recyclerView_feed.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false


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

        //Adaptador de recyclerView
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        //Adaptador de recyclerView
        layoutManager = LinearLayoutManager(this)
        recyclerView_feed.layoutManager = layoutManager

        adapter = RecyclerAdapter(this)
        recyclerView_feed.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(SwipeCallback(adapter, context = contextApp))
        itemTouchHelper.attachToRecyclerView(recyclerView_feed)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            0 -> {
                //Llamada a AddCredential
            }
            1 -> {
                //Llamada a EditActivity
                adapter.notifyDataSetChanged()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    @Override
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_main_view, menu)
        //Búsqueda
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        searchView.queryHint = getString(R.string.top_bar_search_hint)

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
                val intent = Intent(contextApp, CreateActivity::class.java).apply {
                    //putExtra para mandar mensaje (esto puede servir para editar)
                }
                startActivity(intent)
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
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.error_msg_auth_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        biometricPrompt.authenticate(promptInfo)
    }
}