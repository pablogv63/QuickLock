package com.pablogv63.quicklock.credential

import junit.framework.TestCase

class CredentialTest : TestCase() {

    private var credentialArray = mutableListOf<Credential>()

    public override fun setUp() {
        super.setUp()
        //Set up dependencies
        credentialArray.clear()
    }

    public override fun tearDown() {}

    //Test to create a credential
    fun testCreate(){
        val name = "TEST_NAME"
        val category = "TEST_CATEGORY"
        val fields = mutableListOf<CredentialField>()

        //Create
        var credential = Credential(name = name,category = category, fields = fields)

        //Check
        
    }
}