package com.pablogv63.quicklock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : AppCompatActivity() {

    lateinit var passwordText : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        //Button
        buttonCreate.setOnClickListener { _ ->
            if (checkInputs()){
                saveCredential()
                //TODO(Go to some activity)
                finish() // Finaliza y vuelve a la actividad anterior
            }
        }

        //Prepare inputs call
        prepareInputs()
        //IDEA: Poner el típico DONE en la barra en vez del botón //

    }

    private fun prepareInputs() {
        //Name
        textFieldName.editText?.doOnTextChanged { text, start, before, count ->
            //Respond to text change
        }

        //Password
        textFieldPassword.editText?.doAfterTextChanged { text ->
            passwordText = text.toString()
            textFieldPasswordDuplicate.visibility = View.VISIBLE
        }
        textFieldPasswordDuplicate.visibility = View.GONE
        textFieldPasswordDuplicate.editText?.doAfterTextChanged { text ->
            //get other text
            val passwordDuplicate = text.toString()
            if (passwordDuplicate != passwordText){
                //If distinct from password
                textFieldPasswordDuplicate.error = getString(R.string.invalid_password_duplicate)
            } else {
                textFieldPasswordDuplicate.error = null
            }
        }

        //Category list
        //Añadimos alguna categoría de momento -> TEMPORAL
        CredentialCategories.addOrUpdateCategory("Importante")
        CredentialCategories.addOrUpdateCategory("Importante")
        //Rellenamos el spinner
        val items = CredentialCategories.getCategoriesAsText()
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (textFieldCategory.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    /**
     * Triggers when button is clicked
     * Gets all inputs, checks them and proceeds to save
     */
    private fun checkInputs() : Boolean {
        var fields = mutableListOf<CredentialField>()

        //Name
        val inputName = textFieldName.editText?.text.toString()

        //Username
        val inputUsername = textFieldUsername.editText?.text.toString()
        fields.add(CredentialField(Credentials.FieldNames.USERNAME.ordinal, inputUsername))

        //Password
        val inputPassword = textFieldPassword.editText?.text.toString()
        fields.add(CredentialField(Credentials.FieldNames.PASSWORD.ordinal, inputPassword))
        /*
        Error in password field
        //Set
        textFieldPasswordDuplicate.error = getString(R.string.invalid_password)
        //Clear
        //textFieldPasswordDuplicate.error = null
         */

        //Category
        val inputCategory = textFieldCategory.editText?.text.toString()

        //End: If everything goes alright add to credential array
        var credential = Credential(inputName,inputCategory, fields)
        Credentials.add(credential)
        return true
    }

    /**
     * Saves the credential
     */
    fun saveCredential() {

    }
}