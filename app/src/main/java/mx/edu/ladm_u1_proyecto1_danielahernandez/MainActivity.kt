package mx.edu.ladm_u1_proyecto1_danielahernandez

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    val vector : Array<Int> = Array(10,{0})
    var cadena = ""
    var nombre = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        asignar.setOnClickListener {
            if(valor.text.toString().isEmpty() || posicion.text.toString().isEmpty() ){
                Toast.makeText(this, "Error hay un campo vacío", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            var p = posicion.text.toString().toInt()
            var v =  valor.text.toString().toInt()

            vector[p] = v
            Toast.makeText(this,"se capturo valor", Toast.LENGTH_LONG).show()
        } //fin de asignar

        mostrar.setOnClickListener {
            var data = "vector\n\n"

            (0..9).forEach {p->
                data += "["+ vector[p]+"]"
            }
            res.setText(data)
        } //fin de mostrar

        button5.setOnClickListener {
            nombre = nombreA.text.toString()
            guardarMemoriaSD(nombre)
        }

        button6.setOnClickListener {
            nombre = nombreAleer.text.toString()
            leerMemoriaSD(nombre)
            //asignarTextos(t1)
        }


    } //over

    /*fun asignarValor(){
        if(valor.text.isEmpty() || posicion.text.isEmpty() ){
            Toast.makeText(this, "Error hay un campo vacío", Toast.LENGTH_LONG).show()
        }

        var p = posicion.text.toString().toInt()
        var v = valor.text.toString().toInt()

        vector[p] = v
        Toast.makeText(this,"se capturo valor", Toast.LENGTH_LONG).show()
    }*/

    fun convertir():String{
        (0..9).forEach {
            cadena += ""+ vector[it]+"&"
        }
        return cadena
    }//convertir

    fun guardarMemoriaSD(nombre: String){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
            //permiso no concedido, entonces se solicita
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),0)
        }
        var rutaSD = Environment.getExternalStorageDirectory()
        //getDataDirectory para la lectura
        var data = convertir()
        var datosArchivo = File(rutaSD.absolutePath,nombre)
        var flujoSalida = OutputStreamWriter(FileOutputStream(datosArchivo))


        flujoSalida.write(data)
        flujoSalida.flush()
        flujoSalida.close()

       mensaje("Exito, archivo creado")
       // asignarTextos("")
    }

    fun mensaje(m:String){
        AlertDialog.Builder(this)
            .setTitle("Atencion")
            .setMessage(m)
            .setPositiveButton("Aceptar"){d,i->}
            .show()
    }

    fun leerMemoriaSD(nombre: String){
        var rutaSD = Environment.getExternalStorageDirectory()
        var datosArchivo = File(rutaSD.absolutePath, nombre)
        var flujoEntrada = BufferedReader(InputStreamReader(FileInputStream(datosArchivo)))
        var data = flujoEntrada.readLine()
        asignarTextos(data)
    }

    fun asignarTextos(t1:String){
       res.setText(t1)
    }


}   //class
