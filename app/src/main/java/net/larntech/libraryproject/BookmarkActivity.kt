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
import kotlinx.android.synthetic.main.example_item_bookmark.*
import java.time.temporal.ValueRange
import java.util.*
import kotlin.collections.ArrayList

class BookmarkActivity : AppCompatActivity() {

    val BookmarkList = ArrayList<BookmarkItem>()
    private lateinit var adapter:BookmarkAdapter
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        auth = FirebaseAuth.getInstance()
        var intent = intent

        val Name = intent.getStringExtra("Name")
        val Desc = intent.getStringExtra("Desc")

        val testName = Name
        val testDesc = Desc

        BookmarkList.add(BookmarkItem(R.drawable.book1,"$testName","$testDesc",0,0))

        var adapter = BookmarkAdapter(BookmarkList,this)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter = adapter
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

        menuInflater.inflate(R.menu.bookmarkmenu,menu);

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
