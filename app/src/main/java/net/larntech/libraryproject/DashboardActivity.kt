package net.larntech.libraryproject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import kotlin.random.Random
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.example_item.*
import java.util.*
import kotlin.collections.ArrayList


class DashboardActivity : AppCompatActivity() {

    private val  CHANNEL_ID = "channel_id_example_01"
    private val notification = 101

    private lateinit var adapter:ExampleAdapter
    private lateinit var auth: FirebaseAuth;
    val exampleList = ArrayList<ExampleItem>()
    val displayExampleList = ArrayList<ExampleItem>()
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()
        createNotificationChannel()

        exampleList.add(ExampleItem(R.drawable.book1,"LOTR","Fantasy",0,0))
        exampleList.add(ExampleItem(R.drawable.book1,"ST","Science",0,0))
        exampleList.add(ExampleItem(R.drawable.book1,"GOT","Fantasy",0,0))
        exampleList.add(ExampleItem(R.drawable.book1,"Avengers","Heroes",0,0))
        exampleList.add(ExampleItem(R.drawable.book1,"Fog","Horror",0,0))

        displayExampleList.addAll(exampleList)
        var adapter = ExampleAdapter(displayExampleList,this)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter = adapter

        preferences = getSharedPreferences("My_Pref",Context.MODE_PRIVATE)
        val  mSortSetting = preferences.getString("Sort", "Likes")

        if (mSortSetting=="Likes"){
            sortLikes(adapter)
        }else if (mSortSetting=="Visited"){
            sortVisited(adapter)
        }
    }
    private fun sortVisited(adapter: ExampleAdapter) {
        displayExampleList.sortWith(compareBy{it.visit})
        displayExampleList.reverse()
        adapter.notifyDataSetChanged()
    }
    private fun sortLikes(adapter: ExampleAdapter) {
        displayExampleList.sortWith(compareBy{it.like})
        adapter.notifyDataSetChanged()
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
    fun buylist(menuItem: MenuItem){
        val intent = Intent(this,BuyListActivity::class.java)
        startActivity(intent)
    }
    fun notification(menuItem: MenuItem){
        sendNotification()
    }

    fun addItem(menuItem: MenuItem) {
        var inflter = LayoutInflater.from(this)
        var v = inflter.inflate(R.layout.activity_additem, null)

        var itemName = v.findViewById<EditText>(R.id.Name)
        var itemDesc = v.findViewById<EditText>(R.id.Desc)
        var itemLikes = v.findViewById<EditText>(R.id.Likes)

        var addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") {
            dialog,_->

            var names = itemName.text.toString()
            var desc = itemDesc.text.toString()
            var like = itemLikes.inputType
            exampleList.add(ExampleItem(R.drawable.book1,"$names","$desc",like,0))
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
        exampleList.removeAt(index)
        adapter.notifyItemRemoved(index)
    }
    fun sortingbylikes(menuItem: MenuItem){
        val intent = Intent(this,SortingByLikesActivity::class.java)
        startActivity(intent)
    }

   override fun onCreateOptionsMenu(menu: Menu?): Boolean {

       menuInflater.inflate(R.menu.menu,menu);
       menuInflater.inflate(R.menu.menuitems,menu);

    var menuItem = menu!!.findItem(R.id.searchView);

        if(menuItem != null ){
            val searchView = menuItem.actionView as SearchView
            val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            editText.hint="Search..."

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
               override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
               }
                override fun onQueryTextChange(newText: String?): Boolean {

                   if (newText!!.isNotEmpty()){
                       displayExampleList.clear()
                        val search = newText.toLowerCase(Locale.getDefault())
                        exampleList.forEach{
                            if(it.name.toLowerCase(Locale.getDefault()).contains(search)|| it.desc.toLowerCase(Locale.getDefault()).contains(search) ) {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var  selectedOption=""

        when(item.itemId){
                R.id.buy_bookmark -> selectedOption = "Bookmark"
                R.id.BuyList -> selectedOption = "BuyList"
                R.id.AddItem -> selectedOption = "Add Item"
                R.id.RemoveItem -> selectedOption = "Remove item"
                R.id.Sorting -> selectedOption = "Sorting by likes"
                R.id.SignOut -> selectedOption = "Sign Out"
        }
        Toast.makeText(this,"Option:"+selectedOption,Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(){

            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)


    }
    private fun sendNotification(){
        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Example Title")
            .setContentText("Example Description")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notification,builder.build())
        }
    }
}