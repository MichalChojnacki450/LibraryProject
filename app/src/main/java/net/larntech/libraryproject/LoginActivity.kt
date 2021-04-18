package net.larntech.libraryproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.EdPassword
import kotlinx.android.synthetic.main.activity_login.btnRegister
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth= FirebaseAuth.getInstance();

        btnRegister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {

            if(EdEmail.text.trim().toString().isNotEmpty() || EdPassword.text.trim().toString().isNotEmpty()){
            signInUser(EdEmail.text.trim().toString(), EdPassword.text.trim().toString())
        }else{
            Toast.makeText(this, "Input required",Toast.LENGTH_LONG).show()
            }
        }



    }
    fun  signInUser(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    val intent=Intent(this,DashboardActivity::class.java)
                    startActivity(intent)

                }else{
                    Toast.makeText(this,"Error !!"+task.exception, Toast.LENGTH_LONG).show()
                }
            }
    }

}