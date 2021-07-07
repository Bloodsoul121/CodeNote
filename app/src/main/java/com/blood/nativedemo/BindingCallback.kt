package com.blood.nativedemo

interface BindingCallback<T> {
    fun onItemClick(t: T)
}