package com.example.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var db : ArticleDatabase
    lateinit var recycleview : RecyclerView
    lateinit var btn_plus : FloatingActionButton

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycleview = findViewById(R.id.recycleview)
        btn_plus = findViewById(R.id.btn_plus)

        btn_plus.setOnClickListener {
            intent = Intent(this, AjouterArticle::class.java)
            startActivity(intent)
        }

        var title = intent.getStringExtra("title").toString()
        var content = intent.getStringExtra("content").toString()

        db = ArticleDatabase(this)
        var data = db.readArticle()
        var items = ArrayList<Article>()

        if(data.count > 0 && data.moveToFirst()){
            while (data.moveToNext()){
                items.add(
                    Article(data.getString(1),data.getString(2))
                )
                println(data.getString(1))
                println(data.getString(2))
            }
        }

        recycleview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ArticleAdapter(items)
        }
    }
}