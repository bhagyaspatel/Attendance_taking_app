package com.example.attendanceapp.ui.login

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.attendanceapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*

class LoginFragment : Fragment() {

    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null){
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToSectionListFragment2()
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerClickBtn.setOnClickListener{
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            )
        }

        loginBtn.setOnClickListener{
            user_password_container.error = null
            user_email_container.error = null

            val email = user_email.text.toString()
            val password = user_passwrod.toString()

            if (validate(email, password)){
                progressBarLogin.visibility = View.VISIBLE

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()){
                        progressBarLogin.visibility = View.INVISIBLE

                        if (it.isSuccessful){
                            findNavController().navigate(
                                LoginFragmentDirections.actionLoginFragmentToSectionListFragment2()
                            )
                        }else{
                            val toast = Toast.makeText(requireActivity(),
                                "Authentication failed: ${it.exception?.message}",
                                Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER_VERTICAL,0,0)
                            toast.show()
                        }
                    }
            }
        }
    }

    private fun validate(email: String, password: String): Boolean {
        var valid = true

        if (email.isBlank()){
            user_email_container.error = "Enter the email id"
            valid = false
        }
        if (password.isBlank()){
            user_password_container.error = "Enter the password"
            valid = false
        }
        if (password.length < 6){
            user_password_container.error = "Password must be of atleast 6 letters"
            valid = false
        }
        return valid
    }

}