package com.cscorner.anthenaguard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Display
import android.view.View


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun register(v: View?) {
        val i_register = Intent(
            this@MainActivity,
            Register::class.java
        )
        startActivity(i_register)
    }

    fun display_no(v: View?) {
        val i_view = Intent(this@MainActivity, Display::class.java)
        startActivity(i_view)
    }

    fun instruct(v: View?) {
        val i_help = Intent(
            this@MainActivity,
            Instructions::class.java
        )
        startActivity(i_help)
    }

    fun verify (v: View?) {
        val i_verify = Intent(this@MainActivity, Verify::class.java)
        startActivity(i_verify)
    }
}

