package com.example.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.splashscreen.databinding.ActivityLogindanregisterBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class logindanregister : AppCompatActivity() {

    lateinit var binding :ActivityLogindanregisterBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLogindanregisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity2::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            val email = binding.edtEmailLogin.text.toString()
            val password = binding.edtPasswordLogin.text.toString()

            //Validasi Email
            if (email.isEmpty()) {
                binding.edtEmailLogin.error = "Email Harus Di isi"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }
            //Validasi Email Tidak Sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmailLogin.error = "Email Tidak Valid"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }
            //Validasi password
            if (password.isEmpty()) {
                binding.edtPasswordLogin.error = "Password Harus Diisi"
                binding.edtPasswordLogin.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(email,password)
        }
    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Selamat datang $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent (this, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}