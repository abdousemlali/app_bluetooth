package com.example.blth1

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var bluetoothStatusTv: TextView
    private lateinit var bluetoothIv: ImageView
    private lateinit var turnOnBtn: Button
    private lateinit var turnOffBtn: Button
    private lateinit var discoverableBtn: Button
    private lateinit var pairedBtn: Button
    private lateinit var pairedTv: TextView

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        bluetoothStatusTv = findViewById(R.id.bluetoothStatusTv)
        bluetoothIv = findViewById(R.id.bluetoothIv)
        turnOnBtn = findViewById(R.id.turnOnBtn)
        turnOffBtn = findViewById(R.id.turnOffBtn)
        discoverableBtn = findViewById(R.id.discoverableBtn)
        pairedBtn = findViewById(R.id.pairedBtn)
        pairedTv = findViewById(R.id.pairedTv)



        turnOnBtn.setOnClickListener {
            if (bluetoothAdapter.isEnabled){
                Toast.makeText(this, "Already On", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_ENABLE_BT)
            }
        }





        turnOffBtn.setOnClickListener {
            if (!bluetoothAdapter.isEnabled){
                Toast.makeText(this, "Already Off", Toast.LENGTH_SHORT).show()
            }else{
                bluetoothAdapter.disable()
                bluetoothIv.setImageResource(R.drawable.ic_bluetooth_off)
            }
        }

        discoverableBtn.setOnClickListener {
            if (!bluetoothAdapter.isDiscovering){
                Toast.makeText(this, "Making your Device Discoverable", Toast.LENGTH_SHORT).show()
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                startActivityForResult(intent, DISCOVERABLE_BT_REQUEST_CODE)
                bluetoothIv.setImageResource(R.drawable.ic_bluetooth_on)
            }
        }


        pairedBtn.setOnClickListener {
            if (bluetoothAdapter.isEnabled){
                pairedTv.text = "Paired Devices : "

                val devices = bluetoothAdapter.bondedDevices
                for (device in devices){
                    val deviceName = device.name
                    val deviceAdress = device.address
                    pairedTv.append("\nDevice : $deviceName , $deviceAdress ")
                }
            }else{
                Toast.makeText(this, "Turn on bluetooth first", Toast.LENGTH_SHORT).show()
            }

        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            REQUEST_ENABLE_BT ->
                if (resultCode == Activity.RESULT_OK){
                    bluetoothIv.setImageResource(R.drawable.ic_bluetooth_on)
                    Toast.makeText(this, "Bluetooth is On", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(this, "Could not On bluetooth", Toast.LENGTH_SHORT).show()

                }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val REQUEST_ENABLE_BT = 1
        private const val DISCOVERABLE_BT_REQUEST_CODE = 1001
    }

}