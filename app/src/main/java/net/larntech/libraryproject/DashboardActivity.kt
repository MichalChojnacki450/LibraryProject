package net.larntech.libraryproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.view.*
import kotlinx.android.synthetic.main.row_items.*

public class DashboardActivity : AppCompatActivity(), ItemAdapter.ClickedItem {
    private lateinit var auth: FirebaseAuth;
    var itemListModal = arrayOf(
        ItemModal(R.drawable.book1,"Star Wars", "Pif paf"),
        ItemModal(R.drawable.book2,"LOTR", "Rings, sword,bows,axes"),
        ItemModal(R.drawable.book3,"Avengers", "Heroes vs Villains"),
        ItemModal(R.drawable.book4,"Justice league", "Bat,Alien,Robot,Amazon"),
        ItemModal(R.drawable.book5,"Game of Throne", "Dragons,Kings,Zombies,Thrones"),
    )

    var itemModalList = ArrayList<ItemModal>()
    var itemAdapter: ItemAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        auth = FirebaseAuth.getInstance()

        for (item in itemListModal){
            itemModalList.add(item)
        }
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        itemAdapter = ItemAdapter(this);
        itemAdapter!!.setData(itemModalList)
        recyclerView.adapter = itemAdapter

        btnSignOut.setOnClickListener {
            auth.signOut()
            val intent=Intent(this,LoginActivity::class.java);
            startActivity(intent)
        }
    }

    override fun clickedItem(itemModal: ItemModal) {
        var itemModal = itemModal;
        var name = itemModal.name

        when(name){
            "Book1"->
                startActivity(Intent(this@DashboardActivity,Book1Activity::class.java).putExtra("data",itemModal))
            "Book2"->
                startActivity(Intent(this@DashboardActivity,Book2Activity::class.java).putExtra("data",itemModal))
            "Book3"->
                startActivity(Intent(this@DashboardActivity,Book3Activity::class.java).putExtra("data",itemModal))
            "Book4"->
                startActivity(Intent(this@DashboardActivity,Book4Activity::class.java).putExtra("data",itemModal))
            "Book5"->
                startActivity(Intent(this@DashboardActivity,Book5Activity::class.java).putExtra("data",itemModal))


        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        var  menuItem = menu!!.findItem(R.id.searchView);
        var searchView = menuItem.actionView as SearchView

        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                itemAdapter!!.filter.filter(p0);
                return true
            }

        })
        return true;
    }
}