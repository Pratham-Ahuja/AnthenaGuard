package com.cscorner.anthenaguard

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import kotlin.math.abs

// Declare the AccelerometerListener interface as public
public interface AccelerometerListener {
    fun onShake(force: Float)
    fun onAccelerationChanged(x: Float, y: Float, z: Float)
}

object AccelerometerManager {
    private var aContext: Context? = null

    // Accuracy configuration
    private var threshold = 15.0f
    private var interval = 200
    private var sensor: Sensor? = null
    private var sensorManager: SensorManager? = null
    private var listener: AccelerometerListener? = null
    private var supported: Boolean? = null
    var isListening = false
        private set

    fun stopListening() {
        isListening = false
        try {
            if (sensorManager != null && sensorEventListener != null) {
                sensorManager!!.unregisterListener(sensorEventListener)
            }
        } catch (e: Exception) {
        }
    }

    fun isSupported(context: Context?): Boolean {
        aContext = context
        if (supported == null) {
            if (aContext != null) {
                sensorManager = aContext!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
                val sensors = sensorManager!!.getSensorList(Sensor.TYPE_ACCELEROMETER)
                supported = sensors.size > 0
            } else {
                supported = false
            }
        }
        return supported!!
    }

    fun configure(threshold: Int, interval: Int) {
        AccelerometerManager.threshold = threshold.toFloat()
        AccelerometerManager.interval = interval
    }

    fun startListening(accelerometerListener: AccelerometerListener?) {
        sensorManager = aContext!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors = sensorManager!!.getSensorList(Sensor.TYPE_ACCELEROMETER)
        if (sensors.size > 0) {
            sensor = sensors[0]
            isListening = sensorManager!!.registerListener(
                sensorEventListener, sensor,
                SensorManager.SENSOR_DELAY_GAME
            )
            listener = accelerometerListener
        }
    }

    fun startListening(
        accelerometerListener: AccelerometerListener?,
        threshold: Int, interval: Int
    ) {
        configure(threshold, interval)
        startListening(accelerometerListener)
    }

    private val sensorEventListener: SensorEventListener? = object : SensorEventListener {
        private var now: Long = 0
        private var timeDiff: Long = 0
        private var lastUpdate: Long = 0
        private var lastShake: Long = 0
        private var x = 0f
        private var y = 0f
        private var z = 0f
        private var lastX = 0f
        private var lastY = 0f
        private var lastZ = 0f
        private var force = 0f
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        override fun onSensorChanged(event: SensorEvent) {
            now = event.timestamp
            x = event.values[0]
            y = event.values[1]
            z = event.values[2]

            listener?.let {
                if (lastUpdate == 0L) {
                    lastUpdate = now
                    lastShake = now
                    lastX = x
                    lastY = y
                    lastZ = z
                    Toast.makeText(
                        aContext, "No Motion detected",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    timeDiff = now - lastUpdate
                    if (timeDiff > 0) {
                        force = abs((x + y + z - lastX - lastY - lastZ).toDouble()).toFloat()
                        if (force > threshold) {
                            if (now - lastShake >= interval) {
                                it.onShake(force)
                            } else {
                                Toast.makeText(
                                    aContext, "No Motion detected.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            lastShake = now
                        }
                        lastX = x
                        lastY = y
                        lastZ = z
                        lastUpdate = now
                    } else {
                        Toast.makeText(aContext, "No Motion detected", Toast.LENGTH_SHORT).show()
                    }
                }
                it.onAccelerationChanged(x, y, z)
            }
        }
    }
}