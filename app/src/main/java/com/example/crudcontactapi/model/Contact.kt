package com.example.crudcontactapi.model

data class Contact(
    val id: Long = 0,
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val active: Boolean = true
){

    override fun toString(): String {
        return "ContactItem(id=$id, name='$name', email='$email', phone='$phone', active=$active)"
    }
}