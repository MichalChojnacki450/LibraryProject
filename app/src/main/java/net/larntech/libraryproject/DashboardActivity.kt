package net.larntech.libraryproject

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.view.*

public class DashboardActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    var itemListModal = arrayOf(
        itemModal(R.drawable.book1,"Star Wars", "Pif paf"),
        itemModal(R.drawable.book2,"LOTR", "Rings, sword,bows,axes"),
        itemModal(R.drawable.book3,"Avengers", "Heroes vs Villains"),
        itemModal(R.drawable.book4,"Justice league", "Bat,Alien,Robot,Amazon"),
        itemModal(R.drawable.book5,"Game of Throne", "Dragons,Kings,Zombies,Thrones"),
    )

    var itemModalList = ArrayList<itemModal>()
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

        itemAdapter = ItemAdapter(itemModalList);
        recyclerView.adapter = itemAdapter


        btnLogout.setOnClickListener {
            auth.signOut();
            val  intent=Intent(this,LoginActivity::class.java);
            startActivity(intent)
        }
    }
}