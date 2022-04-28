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
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginClickBtn.setOnClickListener{
            findNavController().navigate(
                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            )
        }

        registerBtn.setOnClickListener{
            user_email_container_register.error = null
            user_password_container_register.error = null

            val email = user_email_register.text.toString()
            val pass = user_passwrod_register.text.toString()

            if (validateInput(email, pass)){
                progressBarRegister.visibility = View.VISIBLE

                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(requireActivity()){ task ->
                        progressBarRegister.visibility = View.INVISIBLE

                        if (task.isSuccessful){
                            findNavController().navigate(
                                RegisterFragmentDirections.actionRegisterFragmentToSectionListFragment2()
                            )
                        }else{
                            val toast = Toast.makeText(requireActivity(),
                                "Registration failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER_VERTICAL,0,0)
                            toast.show()
                        }
                    }
            }
        }
    }

    private fun validateInput(email: String, pass: String): Boolean {
        var valid = true

        //.error is a facility provided by TextInputEditText
        if (email.isBlank()){
            user_email_container_register.error = "Please enter the email address."
            valid = false
        }
        if (pass.isBlank()){
            user_password_container_register.error = "Please enter the password."
            valid = false
        }
        if(pass.length < 6){
            user_password_container_register.error = "Password must be atleast 6 characters long"
            valid = false
        }
        return valid
    }

}