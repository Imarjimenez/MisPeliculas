package com.imarjimenez.mispeliculas

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.imarjimenez.mispeliculas.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        val adapter = ArrayAdapter.createFromResource(this, R.array.combo_cities, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        registerBinding.spinnerCities.adapter = adapter

        viewModel.selectedDate.observe(this, Observer { formattedDate ->
            registerBinding.editTextFecha.setText(formattedDate)
        })

        viewModel.selectedCity.observe(this, Observer { city ->
            // Puedes manejar la selección de la ciudad aquí si es necesario
        })

        registerBinding.registerButton.setOnClickListener {
            val email = registerBinding.emailEditText.text.toString()
            val password = registerBinding.passwordEditText.text.toString()
            val repPassword = registerBinding.repPasswordEditText.text.toString()
            val fechaNacimiento = registerBinding.editTextFecha.text.toString()
            val ciudad = registerBinding.spinnerCities.selectedItem.toString()

            if (email.isEmpty() || password.isEmpty() || repPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password != repPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Construir el mensaje de información
            val genre = if (registerBinding.femaleRadioButton.isChecked) "Femenino" else "Masculino"
            val favoritesGenres = mutableListOf<String>()

            if (registerBinding.AmorCheckBox.isChecked) favoritesGenres.add("Amor")
            if (registerBinding.AccionCheckBox.isChecked) favoritesGenres.add("Acción")
            if (registerBinding.HumorCheckBox.isChecked) favoritesGenres.add("Humor")
            if (registerBinding.TerrorCheckBox.isChecked) favoritesGenres.add("Terror")
            if (registerBinding.AventuraCheckBox.isChecked) favoritesGenres.add("Aventura")
            if (registerBinding.MusicalCheckBox.isChecked) favoritesGenres.add("Musical")
            if (registerBinding.DramaCheckBox.isChecked) favoritesGenres.add("Drama")
            if (registerBinding.CrimenCheckBox.isChecked) favoritesGenres.add("Crimen")
            if (registerBinding.InfantilCheckBox.isChecked) favoritesGenres.add("Infantil")

            val info = "Email: $email\nGénero: $genre\nGéneros favoritos: ${favoritesGenres.joinToString(", ")}\nContraseña: $password\nFecha de nacimiento: $fechaNacimiento\nCiudad: $ciudad"

            // Mostrar la información en el TextView
            registerBinding.infoTextView.text = info

            val mensaje = "Registro exitoso."
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        }
    }
}