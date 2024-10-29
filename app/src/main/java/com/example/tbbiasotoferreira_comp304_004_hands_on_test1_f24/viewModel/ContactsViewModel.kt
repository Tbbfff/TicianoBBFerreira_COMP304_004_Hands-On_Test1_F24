//Student#: 301326841
package com.example.tbbiasotoferreira_comp304_004_hands_on_test1_f24.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.tbbiasotoferreira_comp304_004_hands_on_test1_f24.data.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ContactsViewModel : ViewModel() {

    var name by mutableStateOf("")
    var phone by mutableStateOf("")
    var email by mutableStateOf("")
    var contactType by mutableStateOf("Friend")

    private val _contactsList = MutableStateFlow<List<Contact>>(emptyList())
    val contactsList: StateFlow<List<Contact>> get() = _contactsList

    fun addContact() {
        val newContact = Contact(name, phone, email, contactType)
        _contactsList.value = _contactsList.value + newContact
        name = ""
        phone = ""
        email = ""
    }
}