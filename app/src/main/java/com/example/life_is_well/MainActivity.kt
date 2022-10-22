package com.example.life_is_well

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.life_is_well.databinding.ActivityMainBinding
import com.example.life_is_well.goalsPages.GoalsPageMain
import com.example.life_is_well.home.HomeButtonAdapter
import com.example.life_is_well.home.HomeUserData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_add_item_prompt.*

class MainActivity : AppCompatActivity() {
    private lateinit var addsBtn: FloatingActionButton

    private lateinit var finIntent: Intent
    private lateinit var healthIntent: Intent
    private lateinit var mentalIntent: Intent

    private lateinit var bindingMain: ActivityMainBinding

    private lateinit var homeRecyclerView: RecyclerView

    private lateinit var homeList: ArrayList<HomeUserData>
    private lateinit var imageId: Array<Int>
    private lateinit var title: Array<String>
    private lateinit var backgroundColor: Array<Int>

    private lateinit var homeButtonAdapter: HomeButtonAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingMain = ActivityMainBinding.inflate(layoutInflater)

        setContentView(bindingMain.root)

        addsBtn = findViewById(R.id.homeAddingBtn)


        homeRecyclerView = findViewById(R.id.homeRecycler)

        finIntent = Intent(this,FinancePageMain::class.java)
        healthIntent = Intent(this,HealthPageMain::class.java)
        mentalIntent = Intent(this,MentalPageMain::class.java)


        imageId = arrayOf(R.drawable.finance, R.drawable.health, R.drawable.mental)
        title = arrayOf("Finance", "Health", "Mental")
        backgroundColor = arrayOf(R.drawable.home_page_finance_btn_background,R.drawable.home_page_health_btn_background,R.drawable.home_page_mental_btn_background)

        homeRecyclerView.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL,false)
        homeRecyclerView.setHasFixedSize(false)

        homeList = arrayListOf()
        addsBtn.setOnClickListener { addInfo() }
        homeButtonAdapter = HomeButtonAdapter(this, homeList){position: Int -> onHomeListItemClick(position)}
        getUserdata()


        home_bottom_nav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.profileBottomNav -> {
                    val intent = Intent(this, ProfilePageMain::class.java)
                    startActivity(intent)
                }

                R.id.goalsBottomNav -> {
                    val intent = Intent(this, GoalsPageMain::class.java)
                    startActivity(intent)
                }


                R.id.homeBotNav -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                R.id.settingsBottomNav -> {
                    val intent = Intent(this, SettingsPageMain::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }

    private fun onHomeListItemClick(position: Int) : Unit {
        when(position){
            0 -> startActivity(finIntent)
            1 -> startActivity(healthIntent)
            2 -> startActivity(mentalIntent)

        }

    }


    private fun getUserdata() {

        for (i in imageId.indices) {
            val homeBtn = HomeUserData(title[i], imageId[i], backgroundColor[i])
            homeList.add(homeBtn)
        }

        homeRecyclerView.adapter = HomeButtonAdapter(this, homeList){position: Int ->
            onHomeListItemClick(position)
        }
    }





    private fun addInfo() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.home_add_item_prompt, null)

        val userName = v.findViewById<EditText>(R.id.homeBtnNamePrompt)
        val userIcon = v.findViewById<EditText>(R.id.homeBtnIconPrompt)
        val userBack = v.findViewById<EditText>(R.id.homeBtnBackgroundPrompt)

        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("Accept"){
                dialog,_->


            val names = userName.text.toString()
            val icon = userIcon.text.toString().toInt()
            val bckgrnd = userBack.text.toString().toInt()


            homeList.add(HomeUserData(names,imageId[icon-1],backgroundColor[bckgrnd-1]))
            homeButtonAdapter.notifyDataSetChanged()
            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
            dialog.dismiss()

        }
        addDialog.setNegativeButton("Cancel"){
                dialog, _->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }

}
