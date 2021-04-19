package net.larntech.libraryproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.row_items.*

class Book2Activity : AppCompatActivity() {

    var itemModal: ItemModal? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book2)

        itemModal = intent.getSerializableExtra("data") as ItemModal

        imageView.setImageResource(itemModal!!.image)
        tvName.text=itemModal!!.name
    }
}