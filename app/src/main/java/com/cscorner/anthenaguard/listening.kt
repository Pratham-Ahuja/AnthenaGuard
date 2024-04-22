// File: AccelerometerListener.kt
package com.cscorner.anthenaguard

// Define an internal interface named AccelerometerListener
internal interface AccelerometerListener {
    // Method to handle changes in acceleration
    fun onAccelerationChanged(x: Float, y: Float, z: Float)

    // Method to handle shake events
    fun onShake(force: Float)
}