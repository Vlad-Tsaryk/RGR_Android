package com.example.myapplication

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class Adapter(private val userList: ArrayList<User>) :
    RecyclerView.Adapter<Adapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.user_item,
            parent, false
        )
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.firstName.text = currentitem.firstName
        holder.lastName.text = currentitem.lastName
        holder.age.text = currentitem.age
        if (currentitem.profileImage != null) {
            val name = currentitem.profileImage
            val storageRef = FirebaseStorage.getInstance().reference.child("images/$name.jpg")
            val localFile = File.createTempFile("tmpFile", "jpg")
            storageRef.getFile(localFile).addOnSuccessListener {
                holder.profileImage.setImageURI(localFile.toUri())
            }
        }
    }

    override fun getItemCount(): Int {

        return userList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val firstName: TextView = itemView.findViewById(R.id.tvfirstName)
        val lastName: TextView = itemView.findViewById(R.id.tvlastName)
        val age: TextView = itemView.findViewById(R.id.tvage)
        val profileImage: ImageView = itemView.findViewById(R.id.tvimage)

    }

}