package vsl.bhavesh.googlemapdemo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.location.places.Place
import android.content.Intent





class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Runtime Permission [ START ]
        var status = ContextCompat.checkSelfPermission(this@MainActivity,
               android.Manifest.permission.ACCESS_COARSE_LOCATION)

        var status1 = ContextCompat.checkSelfPermission(this@MainActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION)

        // Permission not granted..so make Request to user for permission
        if (status==PackageManager.PERMISSION_DENIED && status1==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION),0)
        }
        // Runtime Permission [ END ]







        }// onCreate

    // Get Location Function [ START ]
    fun getLocation(){

        var lManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,
                10f,
                object : LocationListener {
                    override fun onLocationChanged(p0: Location?) {
                        var lati = p0!!.latitude
                        var logi = p0!!.longitude
                        tv1.text = lati.toString()
                        tv2.text = logi.toString()
                        lManager.removeUpdates(this) // it will stop location update It will required tracking
                    }
                    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                    }
                    override fun onProviderEnabled(p0: String?) {
                    }
                    override fun onProviderDisabled(p0: String?) {
                    }
                })
    }
    // Get Location Function [ END ]


    // Pick Location Function [ START ]
    var PLACE_PICKER_REQUEST = 1
    fun pickLocation(){

        val builder = PlacePicker.IntentBuilder()
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)

    }
    // Pick Location Function [ END ]


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(data!!, this)
                val toastMsg = String.format("Place: %s", place.name)
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()

                // set the pickup location lati long
                tv1.text = place.latLng.latitude.toString()
                tv2.text = place.latLng.longitude.toString()
            }
        }
    }



// Permission Result [ START ]
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"Cool..Permission Granted...!",Toast.LENGTH_LONG).show()
                // Call the function after Permission granted  [ START ]
                getLocation() // Call function
                location_pin.setOnClickListener {
                    pickLocation() // Pick location
                }
            // Call the function after Permission granted [ END ]

        }else{
            Toast.makeText(this,"You cannot access..OOPS..!Permission Deny",Toast.LENGTH_LONG).show()
        }
    }
// Permission Result [ END ]

}// MainActivity
