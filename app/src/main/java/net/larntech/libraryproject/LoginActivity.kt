package net.larntech.libraryproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth= FirebaseAuth.getInstance();

        var button=findViewById<Button>(R.id.btnLogin)
        button.setOnClickListener {
            if (EdUsername.text.trim().isNotEmpty()|| EdPassword.text.trim().isNotEmpty()){
                signInUser()
            }else{
                Toast.makeText(this,"Input required",Toast.LENGTH_LONG).show()
            }
        }
        var  button2 = findViewById<Button>(R.id.btnRegister)
        button2.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        var button3 = findViewById<Button>(R.id.btnBack)
        button3.setOnClickListener {
            val intent =Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun  signInUser(){
        auth.createUserWithEmailAndPassword(EdUsername.text.trim().toString(), EdPassword.text.trim().toString())
            .addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    val intent=Intent(this,DashboardActivity::class.java)
                    startActivity(intent)

                }else{
                    Toast.makeText(this,"Authentication Error"+task.exception, Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user !=null){
            val intent = Intent(this,DashboardActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(this,"User first time login",Toast.LENGTH_LONG).show()
        }
    }
}