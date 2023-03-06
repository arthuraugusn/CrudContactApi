package com.example.crudcontactapi.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crudcontactapi.MainActivity
import com.example.crudcontactapi.api.ContactCall
import com.example.crudcontactapi.api.retrofit.RetrofitApi
import com.example.crudcontactapi.model.Contact
import com.example.crudcontactapi.ui.ui.theme.CrudContactApiTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastrarContato : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrudContactApiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting2("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    val retrofit = RetrofitApi.getRetrofit()
    val contactCall = retrofit.create(ContactCall::class.java)

    var nameState by remember {
        mutableStateOf("")
    }
    var emailState by remember {
        mutableStateOf("")
    }
    var phoneState by remember {
        mutableStateOf("")
    }
    var activeState by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    Column() {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = Color(152, 206, 231, 255)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    value = nameState,
                    onValueChange = { nameState = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Name")
                    }
                )
                OutlinedTextField(
                    value = emailState,
                    onValueChange = { emailState = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Email")
                    }
                )
                OutlinedTextField(
                    value = phoneState,
                    onValueChange = { phoneState = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Phone")
                    }
                )
                Button(
                    onClick = {
                        val contact = Contact(
                            name = nameState,
                            email = emailState,
                            phone = phoneState,
                            active = activeState
                        )
                        val contactCallSave = contactCall.saveContact(contact)
                        contactCallSave.enqueue(object : Callback<Contact> {
                            override fun onResponse(
                                call: Call<Contact>,
                                response: Response<Contact>
                            ) {
                                val newContact = response.body()!!
                                Toast.makeText(
                                    context,
                                    "${newContact.id} - ${newContact.name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            override fun onFailure(call: Call<Contact>, t: Throwable) {
                                TODO("Not yet implemented")
                            }

                        })

                        context.startActivity(Intent(context, MainActivity::class.java))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Save new Game")
                }
            }
        }
    }
}

@Composable
fun DefaultPreview2() {
    CrudContactApiTheme {
        Greeting2("Android")
    }
}