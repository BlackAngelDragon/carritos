package com.blackangeldragon.carritosv2

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.blackangeldragon.carritosv2.databinding.ActivityMainBinding
import com.ingenieriajhr.blujhr.BluJhr

class MainActivity : AppCompatActivity() {
    lateinit var blue: BluJhr
    var devicesBluetooth = ArrayList<String>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        blue = BluJhr(this)
        blue.onBluetooth()

        binding.listDeviceBluetooth.setOnItemClickListener { adapterView, view, i, l ->
            if (devicesBluetooth.isNotEmpty()){
                blue.connect(devicesBluetooth[i])
                blue.setDataLoadFinishedListener(object:BluJhr.ConnectedBluetooth{
                    override fun onConnectState(state: BluJhr.Connected) {
                        when(state){
                            BluJhr.Connected.True->{
                                Toast.makeText(applicationContext,"True",Toast.LENGTH_SHORT).show()
                                binding.listDeviceBluetooth.visibility = View.GONE
                                binding.viewConn.visibility = View.VISIBLE
                                rxReceived()
                            }

                            BluJhr.Connected.Pending->{
                                Toast.makeText(applicationContext,"Pending",Toast.LENGTH_SHORT).show()

                            }

                            BluJhr.Connected.False->{
                                Toast.makeText(applicationContext,"False",Toast.LENGTH_SHORT).show()
                            }

                            BluJhr.Connected.Disconnect->{
                                Toast.makeText(applicationContext,"Disconnect",Toast.LENGTH_SHORT).show()
                                binding.listDeviceBluetooth.visibility = View.VISIBLE
                                binding.viewConn.visibility = View.GONE
                            }
                        }
                    }
                })
            }
        }
        binding.buttonSend.setOnClickListener {
            blue.bluTx(binding.edtTx.text.toString())
        }

        binding.buttonSend.setOnLongClickListener {
            blue.closeConnection()
            true
        }
    }
    private fun rxReceived() {
        var vueltaA = 0;
        var vueltaJ = 0;
        blue.loadDateRx(object:BluJhr.ReceivedData{
            override fun rxDate(rx: String) {
               // binding.consola.text = binding.consola.text.toString()+rx

                if(rx == " E2 5E B3 1D") {
                    binding.consola.text = binding.consola.text.toString()+"Angel"+vueltaA
                    vueltaA++
                }

                if(rx == " D3 EC 36 1B") {
                    binding.consola.text = binding.consola.text.toString()+"Jose"+vueltaJ
                    vueltaJ++
                }
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (blue.checkPermissions(requestCode,grantResults)){
            Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show()
            blue.initializeBluetooth()
        }else{
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.S){
                blue.initializeBluetooth()
            }else{
                Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!blue.stateBluetoooth() && requestCode == 100){
            blue.initializeBluetooth()
        }else{
            if (requestCode == 100){
                devicesBluetooth = blue.deviceBluetooth()
                if (devicesBluetooth.isNotEmpty()){
                    val adapter = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,devicesBluetooth)
                    binding.listDeviceBluetooth.adapter = adapter
                }else{
                    Toast.makeText(this, "No tienes vinculados dispositivos", Toast.LENGTH_SHORT).show()
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}