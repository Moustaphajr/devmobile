package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AjouterArticle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_article)

        var btn_clos = findViewById<FloatingActionButton>(R.id.btn_close)
        var btn_add = findViewById<Button>(R.id.btn_add)
        var inputTile = findViewById<EditText>(R.id.inputTitle)
        var inputContent = findViewById<EditText>(R.id.inputContent)
        var txTitle: String
        var txContent: String

        var db = ArticleDatabase(this)

        btn_add.setOnClickListener {
            txTitle = inputTile.text.toString().trim()
            txContent = inputContent.text.toString().trim()

            if(txTitle.isNotEmpty() && txContent.isNotEmpty()) {
                val article = Article(txTitle,txContent)
                val isInserted = db.addArticle(article)

                if(isInserted) {
                    Toast.makeText(this, "Article bien ajouté", Toast.LENGTH_LONG).show()
                    /*Intent(this, MainActivity::class.java).also {
                        it.putExtra("title",txTitle)
                        it.putExtra("content",txContent)
                        startActivity(it)
                    }*/
                    inputTile.setText("")
                    inputContent.setText("")
                }
            }else {
                Toast.makeText(this, "Champs vide ...", Toast.LENGTH_LONG).show()
            }
        }

        btn_clos.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}