package com.pablogv63.quicklock.credential

import com.pablogv63.quicklock.credential.fields.CredentialField
import junit.framework.TestCase
import org.junit.Test

class CredentialTest : TestCase() {

    private var credentialArray = mutableListOf<Credential>()

    //Executes before every test
    public override fun setUp() {
        super.setUp()
        //Set up dependencies
        credentialArray.clear()
    }

    //Executes after every test
    public override fun tearDown() {}

    //Create a credential with no fields
    @Test
    fun testCreateNoFields(){
        val name = "TEST_NO_FIELDS"
        val category = "TEST"
        val fields = mutableListOf<CredentialField>()

        //Create
        var credential = Credential(name = name,category = category)

        //Check
        assertEquals(name, credential.name)
        assertEquals(category, credential.category)
        assertEquals(fields, credential.fields.getCredentialFields())
    }

    //Create a credential with fields
    @Test
    fun testCreateFields(){
        val name = "TEST_FIELDS"
        val category = "TEST"
        val fields = mutableListOf<CredentialField>()

        //Create
        var credential = Credential(name = name,category = category)

        //Check

    }
}