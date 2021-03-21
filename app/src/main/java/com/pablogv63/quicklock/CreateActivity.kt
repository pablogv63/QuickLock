package com.pablogv63.quicklock

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import com.pablogv63.quicklock.credential.Credential
import com.pablogv63.quicklock.credential.CredentialCategories
import com.pablogv63.quicklock.credential.Credentials
import com.pablogv63.quicklock.credential.fields.CredentialField
import com.pablogv63.quicklock.credential.fields.CredentialFields
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : AppCompatActivity() {

    lateinit var passwordText : String

    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        setSupportActionBar(findViewById(R.id.top_app_bar_create))

        //Prepare inputs call
        prepareInputs()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_create,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_done_create -> {
            if (checkAndSave()) {
                setResult(RESULT_OK)
                finish()
            }
            true
        }
        else -> false
    }

    private fun prepareInputs() {
        //Name
        /* Respond to text change
        textFieldName.editText?.doOnTextChanged { text, start, before, count -> }
        */

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
        (textFieldCategory.editText as? AutoCompleteTextView)?.setText(items[0])
    }

    /**
     * Triggers when button is clicked
     * Gets all inputs, checks them and proceeds to save
     * TODO(Check if empty or not valid)
     */
    private fun checkAndSave() : Boolean {
        val fields = CredentialFields()

        //Name
        val inputName = textFieldName.editText?.text.toString()

        //Username
        val inputUsername = textFieldUsername.editText?.text.toString()
        fields.add(CredentialField(CredentialFields.FieldType.USERNAME, inputUsername))

        //Password
        val inputPassword = textFieldPassword.editText?.text.toString()
        fields.add(CredentialField(CredentialFields.FieldType.PASSWORD, inputPassword))

        //Category
        val inputCategory = textFieldCategory.editText?.text.toString()

        //End: If everything goes alright add to credential array
        val credential = Credential(inputName,inputCategory, fields)
        Credentials.add(credential)
        Credentials.save(context)
        return true
    }
}