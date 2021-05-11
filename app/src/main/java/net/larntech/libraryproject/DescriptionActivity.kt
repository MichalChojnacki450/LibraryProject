package net.larntech.libraryproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.android.synthetic.main.example_item.*
import kotlinx.android.synthetic.main.example_item_bookmark.*


class DescriptionActivity : AppCompatActivity() {

    val exampleListBookmark = ArrayList<ExampleItem>()
    private lateinit var adapter:ExampleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        val context: Context
        val actionBar: ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        var intent = intent
        val aName = intent.getStringExtra("iName")
        val aDesc = intent.getStringExtra("iDesc")
        val aImageView = intent.getIntExtra("iImageView", 0)

        actionBar.setTitle(aName)
        desc_Text_view_1.text = aName
        desc_text_view_2.text = aDesc
        desc_image_view.setImageResource(aImageView)

        exampleListBookmark.add(ExampleItem(R.drawable.book1,"$aName","$aDesc",0,0))

        val Name = findViewById<TextView>(R.id.desc_Text_view_1)
        val Desc = findViewById<TextView>(R.id.desc_text_view_2)

        desc_bookmark.setOnClickListener {

            val name = Name.text.toString()
            val desc = Desc.text.toString()

            val intent = Intent(this@DescriptionActivity,BookmarkActivity::class.java)

            intent.putExtra("Name",name)
            intent.putExtra("Desc",desc)
            startActivity(intent)
        }
        buy_bookmark.setOnClickListener {
            val name = Name.text.toString()
            val desc = Desc.text.toString()

            val intent = Intent(this@DescriptionActivity,BuyListActivity::class.java)

            intent.putExtra("Name",name)
            intent.putExtra("Desc",desc)
            startActivity(intent)
        }
    }
}
