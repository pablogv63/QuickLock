package com.pablogv63.quicklock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.pablogv63.quicklock.credential.Credential
import com.pablogv63.quicklock.credential.CredentialCategories
import com.pablogv63.quicklock.credential.Credentials
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    private lateinit var credential: Credential

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val positionInList = intent.getIntExtra("CREDENTIAL_POSITION", -1)
        //If no credential return to MainActivity
        if (positionInList == -1) {
            setResult(RESULT_CANCELED)
            finish()
        } else {
            credential = Credentials.get(positionInList)
            bindData()
        }

        //Handle top bar
        setSupportActionBar(findViewById(R.id.top_app_bar_edit))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_done -> {
            submit()
            setResult(RESULT_OK)
            finish()
            true
        }
        else -> false
    }

    /**
     * Binds the data to the inputs
     */
    private fun bindData() {
        //Name
        editTextFieldName.setText(credential.name)

        //Category
        val items = CredentialCategories.getCategoriesAsText()
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        editTextFieldCategory.setAdapter(adapter)
        editTextFieldCategory.setText(credential.category)
        editTextFieldCategory.inputType = InputType.TYPE_NULL

        //Fields
        for (field in credential.fields) {
            when (field.id) {
                Credentials.FieldNames.USERNAME.ordinal -> editTextFieldUsername.setText(field.value)
                Credentials.FieldNames.PASSWORD.ordinal -> editTextFieldPassword.setText(field.value)
            }
        }

        //Last edited
        val lastEditedText = getString(R.string.last_edited) + " " + credential.lastChanged
        textViewLastEdited.text = lastEditedText
    }

    private fun submit(){
        //Name
        credential.name = editTextFieldName.text.toString()
        //Category
        credential.category = editTextFieldCategory.text.toString()
        //Fields
        for (field in credential.fields) {
            when (field.id) {
                Credentials.FieldNames.USERNAME.ordinal -> field.value = editTextFieldUsername.text.toString()
                Credentials.FieldNames.PASSWORD.ordinal -> field.value = editTextFieldPassword.text.toString()
            }
        }
        //Update lastChanged
        credential.updateChanged()
        //Save in list
        Credentials.update(credential)
    }
}