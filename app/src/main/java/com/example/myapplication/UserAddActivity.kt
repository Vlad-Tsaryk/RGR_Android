package com.example.myapplication


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class UserAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.cancel.setOnClickListener {
            finish()
        }

        binding.selectImage.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent, 100)

        }

        binding.save.setOnClickListener {
            val firstName = binding.editFirstName.text.toString()
            val lastName = binding.editLastName.text.toString()
            val age = binding.editAge.text.toString()
            var newUser: User? = null
            newUser = if (this::imageUri.isInitialized){
                val fileName = UUID.randomUUID().toString()
                val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName.jpg")
                storageReference.putFile(imageUri)
                User(firstName,lastName,age, fileName)
            } else{
                User(firstName,lastName,age)
            }
            database = FirebaseDatabase.getInstance().getReference("User")
            database.push().setValue(newUser).addOnSuccessListener {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, UserListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK){
            imageUri = data?.data!!
            binding.imageView2.setImageURI(imageUri)
        }
    }
}