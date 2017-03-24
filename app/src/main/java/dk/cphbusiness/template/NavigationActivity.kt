package dk.cphbusiness.template

import android.Manifest.permission.*
import android.app.Activity
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_navigation.*
import org.jetbrains.anko.locationManager
import org.jetbrains.anko.longToast
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class NavigationActivity : Activity(), LocationListener {

  override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
    }

  override fun onProviderEnabled(provider: String) {
    longToast("$provider enabled")
    }

  override fun onProviderDisabled(provider: String) {
    longToast("$provider disabled")
    }

  override fun onLocationChanged(location: Location) {
    latitudeText.text = location.latitude.toString()
    longitudeText.text = location.longitude.toString()
    altitudeText.text = location.altitude.toString()
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_navigation)
    ActivityCompat.requestPermissions(
        this,
        arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION),
        4711
        )
    ActivityCompat.requestPermissions(
        this,
        arrayOf(RECEIVE_SMS, READ_SMS),
        4712
        )
    lastLocationButton.onClick {
      // Java
      //LocationManager locationManager = (LocationManager)Xyz(...

      if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
          latitudeText.text = "LAT: ${location.latitude.toString()}"
          longitudeText.text = location.longitude.toString()
          }
        }
      else toast("GPS Unavailable")
      }
    }

  override fun onStart() {
    super.onStart()
    locationManager.requestLocationUpdates(
        LocationManager.GPS_PROVIDER, 0, 0f, this
        )
    }

  override fun onStop() {
    locationManager.removeUpdates(this)
    super.onStop()
    }

  }
