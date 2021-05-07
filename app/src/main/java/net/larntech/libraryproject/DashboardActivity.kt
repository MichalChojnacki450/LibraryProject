package net.larntech.libraryproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Index
import androidx.room.Room
import kotlin.random.Random
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.*
import kotlin.collections.ArrayList


class DashboardActivity : AppCompatActivity(),ExampleAdapter.OnItemClickListener {
    private lateinit var auth: FirebaseAuth;
    var exampleItemList = arrayOf(
            ExampleItem(R.drawable.book1,"LOTR","Fantasy"),
    )
//    var exampleList = ArrayList<ExampleItem>();
    val displayExampleList = ArrayList<ExampleItem>();
    val adapter = ExampleAdapter(displayExampleList,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()


        displayExampleList.addAll(exampleItemList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

    }

    fun  bookMark(menuItem: MenuItem){
        val  intent = Intent(this,BookmarkActivity::class.java)
        startActivity(intent)
    }

    fun signOut(menuItem: MenuItem){
        auth.signOut()
        val intent =Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

    fun addItem(menuItem: MenuItem) {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.activity_additem, null)

        val itemName = v.findViewById<EditText>(R.id.Name)
        val itemDesc = v.findViewById<EditText>(R.id.Desc)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") {
            dialog,_->
//            val index = Random.nextInt(100)
//            val newItem = ExampleItem(
//                R.drawable.book1,
//                "$itemName",
//                "$itemDesc"
//            )
            val index = 2
            val names = itemName.text.toString()
            val desc = itemDesc.text.toString()
            displayExampleList.add(ExampleItem(R.drawable.book1,"$names","$desc"))
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
            dialog,_->
            dialog.dismiss()
        }
        addDialog.create()
        addDialog.show()

    }
    fun removeItem(menuItem: MenuItem) {
        val index = Random.nextInt(1)
        displayExampleList.removeAt(index)
        adapter.notifyItemRemoved(index)
    }

    override fun  onItemClick(position: Int){
        val intent=Intent(this,DescriptionActivity::class.java)
        startActivity(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu);
        menuInflater.inflate(R.menu.menuitems,menu);

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
                        exampleItemList.forEach{
                            if(it.name.toLowerCase(Locale.getDefault()).contains(search)|| it.desc.toLowerCase(Locale.getDefault()).contains(search) ) {
                                displayExampleList.add(it)
                            }
                        }

                        recyclerView.adapter!!.notifyDataSetChanged()
                    }

                    else{
                        displayExampleList.clear()
                        displayExampleList.addAll(exampleItemList)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }

                    return true
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var  selectedOption=""
        when(item?.itemId){
            R.id.Bookmark -> selectedOption = "Bookmark"
            R.id.AddItem -> selectedOption = "Add Item"
            R.id.RemoveItem -> selectedOption = "Remove item"
            R.id.SignOut -> selectedOption = "Sign Out"
        }
        Toast.makeText(this,"Option:"+selectedOption,Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }
}