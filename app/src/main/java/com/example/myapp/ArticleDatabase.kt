package com.example.myapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getStringOrNull

class ArticleDatabase(
    mContext : Context, var database: String = DB_NAME, var version: Int = DB_VERSION
) : SQLiteOpenHelper(
    mContext,database,null, version
){
    override fun onCreate(db: SQLiteDatabase?) {
        //creation des tables
        val createTableArticle = """
            CREATE TABLE article(
                $ARTICLE_ID INT PRIMARY KEY,
                $ARTICLE_TITLE VARCHAR(30),
                $ARTICLE_CONTENT VARCHAR(100)
            ) 
        """.trimIndent()

        db?.execSQL(createTableArticle)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // suppression des anciennes tables
        // creations des nouvelles tables
        db?.execSQL("DROP TABLE IF EXISTS $ARTICLE_TABLE")
        onCreate(db)
    }

    fun addArticle(article : Article) : Boolean{
        val db = writableDatabase
        val values = ContentValues()
        values.put(ARTICLE_TITLE, article.title)
        values.put(ARTICLE_CONTENT, article.content)

        val res = db.insert(ARTICLE_TABLE, null, values).toInt()
        db.close()

        return res != -1
    }

    fun readArticle(): Cursor {
        val db = readableDatabase
        /*
        val order = "$ARTICLE_ID DESC"
        val projection = "SELECT * FROM $ARTICLE_TABLE"
        val datas = db.rawQuery(projection, null)
        */
        var datas = db.query(ARTICLE_TABLE, arrayOf(ARTICLE_ID, ARTICLE_TITLE, ARTICLE_CONTENT), null, null, null, null, ARTICLE_TITLE)

        return datas
    }

    fun deleteArticle(titre: String) : Boolean{
        val db = writableDatabase
        val retour = db.delete(ARTICLE_TABLE, "title = ?", arrayOf(titre.toString()))
        return retour > 0
    }

    companion object{
        private val DB_NAME = "db_article"
        private val DB_VERSION = 1
        private val ARTICLE_TABLE = "article"
        private val ARTICLE_ID = "id"
        private val ARTICLE_TITLE = "title"
        private val ARTICLE_CONTENT = "content"
    }

}