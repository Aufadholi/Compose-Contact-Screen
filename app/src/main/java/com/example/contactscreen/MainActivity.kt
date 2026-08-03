package com.example.contactscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactScreen()
        }
    }
}

data class Contact(
    val nama: String,
    val nomor: String,
    val email: String
)

@Composable
fun ContactScreen(){
    val contacts = remember {
        mutableStateListOf(
            Contact("Andrew", "08123456789", "andre.as@gmail.com"),
            Contact("Budi", "08123456780", "budispeeed@gmail.com"),
            Contact("Caca", "08123456781", "caca@gmail.com"),
            Contact("Deni", "08123456782", "denior@gmail.com"),
            Contact("Eka", "08123456783", "ekaluya@gmail.com")
        )
    }
    var nama by remember {
        mutableStateOf("")
    }
    var nomor by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    var namaError by remember { mutableStateOf("") }
    var nomorError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            TextField(
                value = nama,
                onValueChange = {
                    nama = it
                    namaError = ""
                },
                label = { Text("Nama") },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                isError = namaError.isNotBlank(),
                supportingText = {
                    if(namaError.isNotBlank()){
                        Text(text = namaError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }
                }
            )
            TextField(
                value = nomor,
                onValueChange = {
                    nomor = it
                    nomorError = ""
                },
                label = { Text("Nomor") },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                isError = nomorError.isNotBlank(),
                supportingText = {
                    if(nomorError.isNotBlank()){
                        Text(text = nomorError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }
                }
            )
            TextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = ""
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                isError = emailError.isNotBlank(),
                supportingText = {
                    if (emailError.isNotBlank()){
                        Text(text = emailError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }
                }
            )
        }
        Button(
            onClick = {
                var valid = true
                if(nama.isBlank() || nama.length < 3){
                    namaError = "Nama harus diisi atau lebih dari 3 huruf"
                    valid = false
                }
                if(email.isBlank() || !email.contains("@")){
                    emailError = "email harus memiliki standart @"
                    valid = false
                }
                if(nomor.isBlank() || !nomor.all { it.isDigit() } || nomor.length < 10){
                    nomorError = "nomor harus lebih dari 10 digit"
                    valid = false
                }
                contacts.add(
                    Contact(nama, nomor, email)
                )
                if(!valid) return@Button
                nama = ""
                nomor = ""
                email = ""
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ){
            Text("Tambah Kontak")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(contacts) { contact ->
                ContactsCard(contact = contact)
            }
        }
    }
}

@Composable
fun ContactsCard(
    contact: Contact
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Text(text = contact.nama)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = contact.nomor)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = contact.email)
        }
    }
}