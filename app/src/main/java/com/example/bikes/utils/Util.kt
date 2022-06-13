package com.example.bikes.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.bikes.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect


suspend inline fun <T> Flow<T>.collectIn(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend CoroutineScope.(T) -> Unit
) = lifecycleOwner.repeatOnLifecycle(minActiveState) {
    collect {
        action(it)
    }
}

fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, R.drawable.marker_background)?.let { background ->
        background.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
        ContextCompat.getDrawable(context, vectorResId)?.let {vectorDrawable->
            vectorDrawable.setBounds(
                20,
                20,
                vectorDrawable.intrinsicWidth-20,
                vectorDrawable.intrinsicHeight-20
            )
            val bitmap = Bitmap.createBitmap(
                background.intrinsicWidth,
                background.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            background.draw(canvas)
            vectorDrawable.draw(canvas)
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

}