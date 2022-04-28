package com.example.attendanceapp.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.attendanceapp.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_chat.*

const val MESSAGE_BASE_PATH = "messages"
class ChatFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var dbRef : FirebaseDatabase
    private lateinit var adapter : FirebaseRecyclerAdapter <User , MyViewHolder?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendBtn.setOnClickListener{
            val message = msgArea.text.toString()

            if (message.isBlank()){
                Toast.makeText(requireContext(), "Please write the message you want to send", Toast.LENGTH_SHORT).show()
            }else{
                val ref = dbRef.getReference(MESSAGE_BASE_PATH).push()
                val userMsg = User (auth.currentUser?.email?:"unknown", message)
                ref.setValue(userMsg).addOnSuccessListener {
                    msgArea.setText("")
                }
                    .addOnFailureListener{
                        Toast.makeText(requireContext(), "Failed to send the message", Toast.LENGTH_SHORT).show()
                    }
            }
        }
        chatRV.layoutManager = LinearLayoutManager(requireActivity())
        setupMessageList()
    }

    private fun setupMessageList() {
        val query : Query = dbRef.reference.child(MESSAGE_BASE_PATH)

        val option : FirebaseRecyclerOptions<User> =
            FirebaseRecyclerOptions.Builder<User>()
                .setQuery(query) {
                    User(
                        it.child("email").value.toString() ,
                        it.child("mesaage").value.toString()
                    )
                }
                .build()
        adapter = object : FirebaseRecyclerAdapter<User, MyViewHolder?>(option){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val view : View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.chat_list_item, parent, false)
                return MyViewHolder(view)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: User) {
                holder.senderMsg.text = model.message
                holder.senderEmail.text = model.email
            }

        }
    }
    class MyViewHolder(override val containerView: View) :RecyclerView.ViewHolder(containerView), LayoutContainer {
        val senderMsg = containerView.findViewById<TextView>(R.id.senderMsg)
        val senderEmail = containerView.findViewById<TextView>(R.id.senderEmail)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.stopListening()
    }

}

