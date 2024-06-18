package cl.iancs.ev1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cl.iancs.ev1.Modelo.CuentaMesa
import cl.iancs.ev1.Modelo.ItemMenu
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var tvSubtotalPastel: TextView
    private lateinit var tvSubtotalCazuela: TextView
    private lateinit var tvTotalComida: TextView
    private lateinit var tvPropina: TextView
    private lateinit var tvTotalFinal: TextView
    private lateinit var etCantidadPastel: EditText
    private lateinit var etCantidadCazuela: EditText
    private lateinit var switchPropina: Switch
    private val cuentaMesa = CuentaMesa(1)
    private val pastelDeChoclo = ItemMenu("Pastel de Choclo", 12000)
    private val cazuela = ItemMenu("Cazuela", 10000)
    private val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSubtotalPastel = findViewById(R.id.tvSubtotalPastel)
        tvSubtotalCazuela = findViewById(R.id.tvSubtotalCazuela)
        tvTotalComida = findViewById(R.id.tvTotalComida)
        tvPropina = findViewById(R.id.tvPropina)
        tvTotalFinal = findViewById(R.id.tvTotalFinal)
        etCantidadPastel = findViewById(R.id.etCantidadPastel)
        etCantidadCazuela = findViewById(R.id.etCantidadCazuela)
        switchPropina = findViewById(R.id.switchPropina)

        etCantidadPastel.addTextChangedListener(textWatcher)
        etCantidadCazuela.addTextChangedListener(textWatcher)
        switchPropina.setOnCheckedChangeListener { _, _ -> actualizarMontos() }

        actualizarMontos()
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            actualizarMontos()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun actualizarMontos() {
        val cantidadPastel = etCantidadPastel.text.toString().toIntOrNull() ?: 0
        val cantidadCazuela = etCantidadCazuela.text.toString().toIntOrNull() ?: 0

        cuentaMesa.agregarItem(pastelDeChoclo, cantidadPastel)
        cuentaMesa.agregarItem(cazuela, cantidadCazuela)

        val subtotalPastel = pastelDeChoclo.precio * cantidadPastel
        val subtotalCazuela = cazuela.precio * cantidadCazuela
        val totalComida = subtotalPastel + subtotalCazuela

        tvSubtotalPastel.text = numberFormat.format(subtotalPastel)
        tvSubtotalCazuela.text = numberFormat.format(subtotalCazuela)
        tvTotalComida.text = numberFormat.format(totalComida)

        val propina = if (switchPropina.isChecked) (totalComida * 0.1).toInt() else 0
        tvPropina.text = numberFormat.format(propina)

        val totalFinal = totalComida + propina
        tvTotalFinal.text = numberFormat.format(totalFinal)
    }
}


