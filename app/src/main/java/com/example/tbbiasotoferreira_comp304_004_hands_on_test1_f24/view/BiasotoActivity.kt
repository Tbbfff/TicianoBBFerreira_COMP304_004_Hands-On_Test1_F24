//Student#: 301326841
package com.example.tbbiasotoferreira_comp304_004_hands_on_test1_f24.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.SnackbarHostState
import com.example.tbbiasotoferreira_comp304_004_hands_on_test1_f24.data.Contact
import com.example.tbbiasotoferreira_comp304_004_hands_on_test1_f24.viewModel.ContactsViewModel
import kotlinx.coroutines.launch

class BiasotoActivity : ComponentActivity() {
    private val viewModel: ContactsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactsScreen(viewModel)
        }
    }

    @Composable
    fun ContactsScreen(viewModel: ContactsViewModel) {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Add New Contact", fontSize = 24.sp)

                // Contact details
                OutlinedTextField(
                    value = viewModel.name,
                    onValueChange = { viewModel.name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = viewModel.phone,
                    onValueChange = { viewModel.phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Since my first name is Ticiano, I am using radio buttons
                Text("Contact Type")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    listOf("Friend", "Family", "Work").forEach { type ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            RadioButton(
                                selected = viewModel.contactType == type,
                                onClick = { viewModel.contactType = type }
                            )
                            Text(text = type)
                        }
                    }
                }

                // Add Contact
                Button(
                    onClick = {
                        viewModel.addContact()
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Added: ${viewModel.name}, ${viewModel.phone}, ${viewModel.email}, ${viewModel.contactType}"
                            )
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Add Contact")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Contacts List
                Text("Contacts List", fontSize = 20.sp)
                val contacts = viewModel.contactsList.collectAsState().value
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(contacts) { contact ->
                        ContactItem(contact)
                    }
                }
            }
        }
    }

    @Composable
    fun ContactItem(contact: Contact) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp)
        ) {
            Text("Name: ${contact.name}", fontSize = 16.sp)
            Text("Phone: ${contact.phone}", fontSize = 16.sp)
            Text("Email: ${contact.email}", fontSize = 16.sp)
            Text("Type: ${contact.type}", fontSize = 16.sp)
        }
    }
}