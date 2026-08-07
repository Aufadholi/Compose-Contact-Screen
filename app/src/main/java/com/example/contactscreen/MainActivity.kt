package com.example.contactscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    var Search by rememberSaveable() {
        mutableStateOf("")
    }

    var nama by rememberSaveable {
        mutableStateOf("")
    }
    var nomor by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var count by rememberSaveable {
        mutableStateOf(0)
    }
    val JumlahKontak = contacts.size

    val StatusKontak = if(JumlahKontak>=5) "Kontak banyak" else "Kontak sedikit"

    val filteredContacts = contacts.filter{
        it.nama.contains(Search, ignoreCase = true)
    }

    var namaError by rememberSaveable { mutableStateOf("") }
    var nomorError by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            InputField(
                value = Search,
                label = "Cari Kontak",
                error = "",
                onValueChange = {
                    Search = it
                }
            )
            InputField(
                value = nama,
                label = "Nama",
                error = namaError,
                onValueChange = {
                    nama = it
                    namaError = ""
                }
            )
            InputField(
                value = nomor,
                label = "Nomor",
                error = nomorError,
                onValueChange = {
                    nomor = it
                    nomorError = ""
                }
            )
            InputField(
                value = email,
                label = "Email",
                error = emailError,
                onValueChange = {
                    email = it
                    emailError = ""
                }
            )

        }
        ButtonCount(
            count = count,
            onPlus = {count++},
            onMinus = {if (count > 0){
                count--
            } }
        )
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
                if(!valid) return@Button
                contacts.add(
                    Contact(nama, nomor, email)
                )
                nama = ""
                nomor = ""
                email = ""
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ){
            Text("Tambah Kontak")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                    .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            Text(
                text = "Jumlah Kontak ada $JumlahKontak, Status $StatusKontak",
                style = MaterialTheme.typography.titleMedium
            )
        }
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(filteredContacts) { contact ->
                ContactsCard(
                    contact = contact,
                    onHapus = {
                        contacts.remove(contact)
                    }
                )
            }
        }
    }
}

@Composable
fun ContactsCard(
    contact: Contact,
    onHapus: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = contact.nama)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = contact.nomor)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = contact.email)
            }

            Button(
                onClick = onHapus,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.White)
            ) {
                Text(text = "Hapus")
            }
        }
    }
}

@Composable
fun InputField(
    value: String,
    label: String,
    error: String,
    onValueChange: (String) -> Unit
){
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        shape = MaterialTheme.shapes.medium,
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = error.isNotBlank(),
        supportingText = {
            if(error.isNotBlank()){
                Text(
                    text = error, color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }
        }

    )
}

@Composable
fun ButtonCount(
    count: Int,
    onPlus: () -> Unit,
    onMinus: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            "Count: $count",
            style = MaterialTheme.typography.titleLarge
        )

        Button(
            onClick = onPlus,
            modifier = Modifier.padding(start = 8.dp)
        ){
            Text("Tombol Plus")
        }
        Button(
            onClick = onMinus,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text("Tombol Minus")
        }
    }
}


