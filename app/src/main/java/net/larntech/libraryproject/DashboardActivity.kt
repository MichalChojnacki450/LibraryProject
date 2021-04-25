package net.larntech.libraryproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlin.random.Random
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : AppCompatActivity(),ExampleAdapter.OnItemClickListener {
    private lateinit var auth: FirebaseAuth;
    private val exampleList = generateDummyList(20)
    private val adapter = ExampleAdapter(exampleList,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


        btnSignOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun insertItem(view: View) {
        val index = Random.nextInt(8)
        val newItem = ExampleItem(
                R.drawable.book1,
                "New item at position $index",
                "Line 2"
        )
        exampleList.add(index, newItem)
        adapter.notifyItemInserted(index)
    }
    fun removeItem(view: View) {
        val index = Random.nextInt(8)
        exampleList.removeAt(index)
        adapter.notifyItemRemoved(index)
    }

    override fun  onItemClick(position: Int){
        Toast.makeText(this,"Item $position clicked",Toast.LENGTH_SHORT).show()
        var clickedItem = exampleList[position]
        clickedItem.name="Clicked"
        adapter.notifyDataSetChanged()
        // tutaj otwieranie danego itemu ///
    }

    private fun generateDummyList(size: Int): ArrayList<ExampleItem> {
        val list = ArrayList<ExampleItem>()
        for (i in 0 until size) {
            val drawable = when (i % 3) {
                0 -> R.drawable.book1
                1 -> R.drawable.book1
                else -> R.drawable.book1
            }
            val item = ExampleItem(drawable, "Item $i", "Line 2")
            list += item
        }
        return list
    }
}