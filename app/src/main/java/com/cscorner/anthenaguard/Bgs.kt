import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle

class GPSTracker(private val context: Context) : LocationListener {

    // Flag to check if GPS is enabled
    private var isGPSEnabled = false

    // Flag to check if network location is enabled
    private var isNetworkEnabled = false

    // Flag to indicate if location is available
    private var isLocationAvailable = false

    // Location
    private var location: Location? = null

    // Location manager
    private var locationManager: LocationManager? = null

    init {
        getLocation()
    }

    private fun getLocation() {
        try {
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

            // Check if GPS provider is enabled
            isGPSEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false

            // Check if network provider is enabled
            isNetworkEnabled = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ?: false

            // If neither provider is enabled, location cannot be obtained
            if (!isGPSEnabled && !isNetworkEnabled) {
                isLocationAvailable = false
                return
            }

            isLocationAvailable = true

            // Get location from network provider
            if (isNetworkEnabled) {
                locationManager?.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                    this
                )
                location = locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }

            // Get location from GPS provider
            if (isGPSEnabled) {
                locationManager?.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                    this
                )
                location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Method to get latitude from the current location
    fun getLatitude(): Double {
        return location?.latitude ?: 0.0
    }

    // Method to get longitude from the current location
    fun getLongitude(): Double {
        return location?.longitude ?: 0.0
    }

    // Method to check if location is available
    fun isLocationAvailable(): Boolean {
        return isLocationAvailable
    }

    override fun onLocationChanged(p0: Location) {
        // Update location when location is changed
        location = p0
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        // Unused
    }

    override fun onProviderEnabled(provider: String) {
        // Unused
    }

    override fun onProviderDisabled(provider: String) {
        // Unused
    }

    companion object {
        // Minimum time interval between location updates (milliseconds)
        private const val MIN_TIME_BW_UPDATES: Long = 1000 * 60 * 1 // 1 minute

        // Minimum distance change for location updates (meters)
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters
    }
}
