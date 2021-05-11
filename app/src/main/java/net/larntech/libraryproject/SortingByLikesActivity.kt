package net.larntech.libraryproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_description.*

class SortingByLikesActivity : AppCompatActivity() {

    var BookmarkList = ArrayList<BookmarkItem>()
    private lateinit var adapter:BookmarkAdapter
    private lateinit var auth: FirebaseAuth;
    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorting_by_likes)

        auth = FirebaseAuth.getInstance()

        BookmarkList.add(BookmarkItem(R.drawable.book1,"LOTR","Fantasy",10,10))
        BookmarkList.add(BookmarkItem(R.drawable.book1,"ST","Science",8,8))
        BookmarkList.add(BookmarkItem(R.drawable.book1,"GOT","Fantasy",5,5))
        BookmarkList.add(BookmarkItem(R.drawable.book1,"Avengers","Heroes",9,9))
        BookmarkList.add(BookmarkItem(R.drawable.book1,"Fog","Horror",3,3))



        adapter = BookmarkAdapter(BookmarkList,this)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter = adapter

        preferences =getSharedPreferences("My_Pref",Context.MODE_PRIVATE)
        val mSortSetting = preferences.getString("Sort","Likes")

        if(mSortSetting=="Likes"){
            sortLikes(adapter)
        }else if(mSortSetting=="Vies"){
            sortVies(adapter)
        }

    }

    private fun sortVies(adapter: BookmarkAdapter) {
        BookmarkList.sortWith(compareBy { it.visit })
        BookmarkList.reverse()
        adapter.notifyDataSetChanged()
    }

    private fun sortLikes(adapter: BookmarkAdapter) {
        BookmarkList.sortWith(compareBy { it.likes })
        adapter.notifyDataSetChanged()
    }

    fun  dashboard(menuItem: MenuItem){
        val  intent = Intent(this,DashboardActivity::class.java)
        startActivity(intent)
    }

    fun signOut(menuItem: MenuItem){
        auth.signOut()
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.sortingmenu,menu);

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val  id = item.itemId
        if (id==R.id.Sorting)
            sortDialog()
        return super.onOptionsItemSelected(item)
    }

    private fun sortDialog() {
        val options = arrayOf("Likes","Vies")
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Sort By")
        builder.setIcon(R.drawable.ic_action_name)

        builder.setItems(options){dialog, which ->

            if (which==0){
                val editor:SharedPreferences.Editor = preferences.edit()
                editor.putString("Sort","Likes")
                editor.apply()
                sortLikes(adapter)
                Toast.makeText(this@SortingByLikesActivity,"Likes Order",Toast.LENGTH_LONG).show()
            }
            if (which ==1){
                val editor:SharedPreferences.Editor = preferences.edit()
                editor.putString("Sort","Vies")
                editor.apply()
                sortVies(adapter)
                Toast.makeText(this@SortingByLikesActivity,"Vies Order",Toast.LENGTH_LONG).show()
            }
        }
        builder.create().show()
    }

}
