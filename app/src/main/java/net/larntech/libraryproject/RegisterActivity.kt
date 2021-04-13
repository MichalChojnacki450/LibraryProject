package net.larntech.libraryproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth= FirebaseAuth.getInstance();

        var button=findViewById<Button>(R.id.btnRegister)
        button.setOnClickListener {
            if (EdEmail.text.isNotEmpty()|| EdPassword.text.isNotEmpty()){
                registerUser()
            }else{
                Toast.makeText(this,"Input Required",Toast.LENGTH_LONG).show()
            }
        }

        var button2=findViewById<Button>(R.id.btnBack)
        button2.setOnClickListener {
            val  intent = Intent(this,LoginActivity::class .java)
            startActivity(intent)
        }
    }

    fun registerUser(){
        auth.createUserWithEmailAndPassword(EdEmail.text.trim().toString(), EdPassword.text.trim().toString())
            .addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Register successful",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"Register failed"+task.exception,Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user !=null){
            val intent= Intent(this,DashboardActivity::class.java)
            startActivity(intent)
        }else{
            Log.e("user status","User null")
        }
    }
}