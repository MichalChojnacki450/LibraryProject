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
        btn_Login.setOnClickListener {
            val  intent = Intent(this,LoginActivity::class .java)
            startActivity(intent)
        }
        btn_Register.setOnClickListener {
            if (Ed_Email.text.trim().toString().isNotEmpty()|| Ed_Password.text.trim().toString().isNotEmpty()){
                registerUser(Ed_Email.text.trim().toString(),Ed_Password.text.trim().toString())
            }else{
                Toast.makeText(this,"Input Required",Toast.LENGTH_LONG).show()
            }
        }


    }

    fun registerUser(email:String,password:String){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        Log.e("Task Message","Successful...");

                        var intent=Intent(this,DashboardActivity::class.java);
                        startActivity(intent)

                    }else{
                        Log.e("Task Message","Failed..."+task.exception)
                    }
                }

    }

}