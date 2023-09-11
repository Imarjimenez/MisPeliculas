package com.imarjimenez.mispeliculas

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.*
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.imarjimenez.mispeliculas.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding
    var edit_text_fecha: EditText ?= null
    var button_calendar: ImageButton ?= null
    var date_picker_fecha: DatePicker ?= null
    var spinner_cities: Spinner ?= null
    var text_view_state_cities: TextView ?= null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Apptheme)

        super.onCreate(savedInstanceState)
        registerBinding= ActivityRegisterBinding.inflate(layoutInflater)
        val view = registerBinding.root
        setContentView(view)
        edit_text_fecha = findViewById(R.id.edit_text_fecha)
        button_calendar = findViewById(R.id.button_calendar)
        date_picker_fecha = findViewById(R.id.date_picker_fecha)
        spinner_cities = findViewById(R.id.spinner_cities)

        val adapter = ArrayAdapter.createFromResource(this, R.array.combo_cities, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_cities?.adapter = adapter
        spinner_cities?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position)
                if (selectedItem != null) {
                    Toast.makeText(this@RegisterActivity, "Seleccionado: $selectedItem", Toast.LENGTH_LONG).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Se llama cuando no se selecciona ningún elemento en el Spinner
            }
        })




        edit_text_fecha?.setText(getFechaDatePicker())

        date_picker_fecha?.setOnDateChangedListener{
            date_picker_fecha,year,month,day->
            edit_text_fecha?.setText(getFechaDatePicker())
            date_picker_fecha?.visibility = View.GONE
            edit_text_fecha?.visibility = View.VISIBLE
            registerBinding.registerButton.visibility = View.VISIBLE
            registerBinding.infoTextView.visibility = View.VISIBLE
            registerBinding.buttonCalendar.visibility = View.VISIBLE
            registerBinding.tableLayout.visibility = View.VISIBLE
            registerBinding.favoriteGenreTextView.visibility = View.VISIBLE
            registerBinding.spinnerCities.visibility = View.VISIBLE
            registerBinding.femaleRadioButton.visibility = View.VISIBLE
            registerBinding.maleRadioButton.visibility = View.VISIBLE
            registerBinding.repPasswordEditText.visibility = View.VISIBLE

        }



        registerBinding.registerButton.setOnClickListener {
            // Obtener los valores de los campos
            val email: String = registerBinding.emailEditText.text.toString()
            val password: String = registerBinding.passwordEditText.text.toString()
            val repPassword: String = registerBinding.repPasswordEditText.text.toString()
            val Fecha_nacimiento: String = registerBinding.editTextFecha.text.toString()
            val ciudad: String = registerBinding.spinnerCities?.selectedItem.toString()


            // Verificar que ningún campo esté vacío
            if (email.isEmpty() || password.isEmpty() || repPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_LONG).show()
            } else {
                // Verificar que las contraseñas sean iguales
                if (password == repPassword) {
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

                    val info = "Email: $email\nGénero: $genre\nGéneros favoritos: ${favoritesGenres.joinToString(", ")}\ncontraseña: $password\nFecha de nacimiento: $Fecha_nacimiento\nCiudad: $ciudad"


                    // Mostrar la información en el TextView
                    registerBinding.infoTextView.text = info
                } else {
                    Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun getFechaDatePicker():String{
        var day = date_picker_fecha?.dayOfMonth.toString().padStart(2,'0')
        var month = (date_picker_fecha!!.month+1).toString().padStart(2,'0')
        var year = date_picker_fecha?.year.toString().padStart(4,'0')
        return day+"/"+month+"/"+year
    }
    fun showCalendar(view: View){
        edit_text_fecha?.visibility = View.GONE
        registerBinding.registerButton.visibility = View.GONE
        registerBinding.infoTextView.visibility = View.GONE
        registerBinding.buttonCalendar.visibility = View.GONE
        registerBinding.tableLayout.visibility = View.GONE
        registerBinding.favoriteGenreTextView.visibility = View.GONE
        registerBinding.spinnerCities.visibility = View.GONE
        registerBinding.femaleRadioButton.visibility = View.GONE
        registerBinding.maleRadioButton.visibility = View.GONE
        registerBinding.repPasswordEditText.visibility = View.GONE
        date_picker_fecha?.visibility = View.VISIBLE


    }

}