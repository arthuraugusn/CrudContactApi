package com.example.crudcontactapi

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crudcontactapi.api.ContactCall
import com.example.crudcontactapi.api.retrofit.RetrofitApi
import com.example.crudcontactapi.model.Contact
import com.example.crudcontactapi.ui.CadastrarContato
import com.example.crudcontactapi.ui.theme.CrudContactApiTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrudContactApiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val retrofit = RetrofitApi.getRetrofit()
    val contactCall = retrofit.create(ContactCall::class.java)
    val call = contactCall.getContacts()

    val context = LocalContext.current

    var contactsList by remember {
        mutableStateOf(listOf<Contact>())
    }

    call.enqueue(object : Callback<List<Contact>>{
        override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
            contactsList = response.body()!!
        }

        override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })
    
    Column(modifier = Modifier.fillMaxSize()) {

        LazyColumn(modifier = Modifier.fillMaxWidth()){
            items(contactsList){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    backgroundColor = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "${it.id} - ${it.name}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue
                        )
                        Text(text = it.email)
                        Text(text = it.phone)
                    }
                }
            }
        }
        Button(onClick = {
            context.startActivity(Intent(context, CadastrarContato::class.java))
        }) {
            Text(text = "Mover para outra página")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CrudContactApiTheme {
        Greeting("Android")
    }
}