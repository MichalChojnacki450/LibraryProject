package net.larntech.libraryproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*

class BuyListActivity : AppCompatActivity() {

    val BuyList = ArrayList<BookmarkItem>()
    private lateinit var adapter: BuyAdapter
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_list)

        auth = FirebaseAuth.getInstance()
        var intent = intent

        val Name = intent.getStringExtra("Name")
        val Desc = intent.getStringExtra("Desc")

        val testName = Name
        val testDesc = Desc

        BuyList.add(BookmarkItem(R.drawable.book1, "$testName", "$testDesc",0,0))


        var adapter = BookmarkAdapter(BuyList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
    fun  buy_dashboard(menuItem: MenuItem){
        val  intent = Intent(this,DashboardActivity::class.java)
        startActivity(intent)
    }

    fun buy_sign_out(menuItem: MenuItem){
        auth.signOut()
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.buymenu,menu);

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var  selectedOption=""
        when(item.itemId){
            R.id.Dashboard -> selectedOption = "Dashboard"
            R.id.SignOut -> selectedOption = "Sign Out"
        }
        Toast.makeText(this,"Option:"+selectedOption, Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }
}
