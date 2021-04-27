package net.larntech.libraryproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlin.random.Random
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.*
import kotlin.collections.ArrayList


class DashboardActivity : AppCompatActivity(),ExampleAdapter.OnItemClickListener {
    private lateinit var auth: FirebaseAuth;
    private val exampleList = generateDummyList(20)
    val displayExampleList = generateDummyList(20)
    val adapter = ExampleAdapter(displayExampleList,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()

        displayExampleList.addAll(exampleList)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


        btnSignOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun addItem(view: View) {
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
        adapter.notifyItemChanged(position)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu);

        var menuItem = menu!!.findItem(R.id.searchView);

        if(menuItem != null ){
            val searchView = menuItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText!!.isNotEmpty()){
                        displayExampleList.clear()
                        val search = newText.toLowerCase(Locale.getDefault())
                        exampleList.forEach{
                            if(it.name.toLowerCase(Locale.getDefault()).contains(search)) {
                                displayExampleList.add(it)
                            }
                        }

                        recyclerView.adapter!!.notifyDataSetChanged()
                    }

                    else{
                        displayExampleList.clear()
                        displayExampleList.addAll(exampleList)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }

                    return true
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }
}