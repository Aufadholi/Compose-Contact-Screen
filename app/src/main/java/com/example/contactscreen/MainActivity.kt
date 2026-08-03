package com.example.contactscreen

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly
import com.example.contactscreen.ui.theme.ContactScreenTheme

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
                onValueChange = { nama = it },
                label = { Text("Nama") },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                supportingText = {
                    if(nama.isBlank() || nama.length < 3){
                        Text(text = "Nama harus diisi atau lebih dari 3 huruf", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }
                }
            )
            TextField(
                value = nomor,
                onValueChange = { nomor = it},
                label = { Text("Nomor") },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                supportingText = {
                    if(nomor.isBlank() || !nomor.all { it.isDigit() } || nomor.length < 10){
                        Text(text = "Nomor harus berupa angka dan minimal 10 digit", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }
                }
            )
            TextField(
                value = email,
                onValueChange = { email = it},
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                supportingText = {
                    if(email.isBlank() || !email.contains("@")){
                        Text(text = "Email harus valid (mengandung @)", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }
                }
            )
        }
        Button(
            onClick = {
                if(nama.isBlank() || nama.length < 3){
                    namaError = "Nama harus diisi atau lebih dari 3 huruf"
                    return@Button
                }
                if(email.isBlank() || !email.contains("@")){
                    emailError = "email harus memiliki standart @"
                    return@Button
                }
                if(nomor.isBlank() || !nomor.all { it.isDigit() } || nomor.length < 10){
                    nomorError = "nomor harus lebih dari 10 digit"
                    return@Button
                }
                contacts.add(
                    Contact(nama, nomor, email)
                )
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