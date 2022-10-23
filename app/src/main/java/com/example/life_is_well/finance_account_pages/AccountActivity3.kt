package com.example.life_is_well.finance_account_pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.life_is_well.FinancePageMain
import com.example.life_is_well.R

class AccountActivity3 : AppCompatActivity() {

    private lateinit var backBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account3)

        backBtn = findViewById(R.id.account3BackBtn)

        backBtn.setOnClickListener { backBtn() }

    }

    private fun backBtn(){

        val intent = Intent(this, FinancePageMain::class.java)
        startActivity(intent)
    }

}