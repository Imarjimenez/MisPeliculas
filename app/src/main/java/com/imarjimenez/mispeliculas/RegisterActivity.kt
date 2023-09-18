package com.imarjimenez.mispeliculas

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import android.app.DatePickerDialog
import java.util.Calendar
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.imarjimenez.mispeliculas.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Apptheme)
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
    fun showCalendar(view: View) {
        // Mostrar un mensaje cuando se presiona el botón de calendario
        Toast.makeText(this, "Botón de calendario presionado", Toast.LENGTH_SHORT).show()

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Aquí puedes hacer algo con la fecha seleccionada
                // Por ejemplo, mostrarla en un EditText
                val formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year)
                registerBinding.editTextFecha.setText(formattedDate)
            }, year, month, day)

        // Mostrar el DatePickerDialog
        datePickerDialog.show()

    }
}