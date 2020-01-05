package com.example.demo.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.*
import android.net.ConnectivityManager
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.OnDoubleTapListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.demo.R
import com.ncornette.cache.OkCacheControl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


/**
 * Created by Harshal Chaudhari on 18/12/19.
 */


internal inline fun <T> retroCall(call: Call<T>, crossinline block: (RetroResponse<T>) -> Unit) {
    call.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.body() != null && response.isSuccessful && response.code() == 200) {
                block(RetroResponse.Success(response.body()!!))
            } else {
                block(RetroResponse.NullData())
            }

        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            when (t) {
                is IOException -> block(RetroResponse.OnNetworkChange(true, "Please check your Internet Connection "))
                else -> block(RetroResponse.Failure(t))
            }
        }
    })

}

fun Application.toast(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

}


sealed class RetroResponse<out T> {
    data class Success<T>(val data: T) : RetroResponse<T>()
    data class Failure<T>(val throwable: Throwable) : RetroResponse<T>()
    class NullData<T> : RetroResponse<T>()
    data class OnNetworkChange<T>(val isChanged: Boolean = false, val message: String = "") :
        RetroResponse<T>()
}

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .apply(
            RequestOptions()
                .fitCenter()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .override(Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.ic_restaurant_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

class TouchImageView : ImageView, GestureDetector.OnGestureListener,
    OnDoubleTapListener {
    var matrixs: Matrix? = null
    var mode = NONE
    // Remember some things for zooming
    var last = PointF()
    var start = PointF()
    var minScale = 1f
    var maxScale = 3f
    lateinit var m: FloatArray
    var viewWidth = 0
    var viewHeight = 0
    var saveScale = 1f
    protected var origWidth = 0f
    protected var origHeight = 0f
    var oldMeasuredWidth = 0
    var oldMeasuredHeight = 0
    var mScaleDetector: ScaleGestureDetector? = null
    var contexts: Context? = null

    constructor(context: Context) : super(context) {
        sharedConstructing(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        sharedConstructing(context)
    }

    var mGestureDetector: GestureDetector? = null
    private fun sharedConstructing(context: Context) {
        super.setClickable(true)
        this.contexts = context
        mGestureDetector = GestureDetector(context, this)
        mGestureDetector!!.setOnDoubleTapListener(this)
        mScaleDetector = ScaleGestureDetector(context, ScaleListener())
        matrixs = Matrix()
        m = FloatArray(9)
        imageMatrix = matrixs
        scaleType = ScaleType.MATRIX
        setOnTouchListener { v, event ->
            mScaleDetector!!.onTouchEvent(event)
            mGestureDetector!!.onTouchEvent(event)
            val curr = PointF(event.x, event.y)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    last.set(curr)
                    start.set(last)
                    mode = DRAG
                }
                MotionEvent.ACTION_MOVE -> if (mode == DRAG) {
                    val deltaX = curr.x - last.x
                    val deltaY = curr.y - last.y
                    val fixTransX = getFixDragTrans(
                        deltaX, viewWidth.toFloat(),
                        origWidth * saveScale
                    )
                    val fixTransY = getFixDragTrans(
                        deltaY, viewHeight.toFloat(),
                        origHeight * saveScale
                    )
                    matrixs!!.postTranslate(fixTransX, fixTransY)
                    fixTrans()
                    last[curr.x] = curr.y
                }
                MotionEvent.ACTION_UP -> {
                    mode = NONE
                    val xDiff = Math.abs(curr.x - start.x).toInt()
                    val yDiff = Math.abs(curr.y - start.y).toInt()
                    if (xDiff < CLICK && yDiff < CLICK) performClick()
                }
                MotionEvent.ACTION_POINTER_UP -> mode = NONE
            }
            imageMatrix = matrixs
            invalidate()
            true // indicate event was handled
        }
    }

    fun setMaxZoom(x: Float) {
        maxScale = x
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        return false
    }

    override fun onDoubleTap(e: MotionEvent): Boolean { // Double tap is detected
        Log.i("MAIN_TAG", "Double tap detected")
        val origScale = saveScale
        val mScaleFactor: Float
        if (saveScale == maxScale) {
            saveScale = minScale
            mScaleFactor = minScale / origScale
        } else {
            saveScale = maxScale
            mScaleFactor = maxScale / origScale
        }
        matrixs!!.postScale(
            mScaleFactor, mScaleFactor, viewWidth / 2.toFloat(),
            viewHeight / 2.toFloat()
        )
        fixTrans()
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent) {}
    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent) {}
    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    private inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            mode = ZOOM
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            var mScaleFactor = detector.scaleFactor
            val origScale = saveScale
            saveScale *= mScaleFactor
            if (saveScale > maxScale) {
                saveScale = maxScale
                mScaleFactor = maxScale / origScale
            } else if (saveScale < minScale) {
                saveScale = minScale
                mScaleFactor = minScale / origScale
            }
            if (origWidth * saveScale <= viewWidth
                || origHeight * saveScale <= viewHeight
            ) matrixs!!.postScale(
                mScaleFactor, mScaleFactor, viewWidth / 2.toFloat(),
                viewHeight / 2.toFloat()
            ) else matrixs!!.postScale(
                mScaleFactor, mScaleFactor,
                detector.focusX, detector.focusY
            )
            fixTrans()
            return true
        }
    }

    fun fixTrans() {
        matrixs!!.getValues(m)
        val transX = m[Matrix.MTRANS_X]
        val transY = m[Matrix.MTRANS_Y]
        val fixTransX =
            getFixTrans(transX, viewWidth.toFloat(), origWidth * saveScale)
        val fixTransY = getFixTrans(
            transY, viewHeight.toFloat(), origHeight
                    * saveScale
        )
        if (fixTransX != 0f || fixTransY != 0f) matrixs!!.postTranslate(fixTransX, fixTransY)
    }

    fun getFixTrans(
        trans: Float,
        viewSize: Float,
        contentSize: Float
    ): Float {
        val minTrans: Float
        val maxTrans: Float
        if (contentSize <= viewSize) {
            minTrans = 0f
            maxTrans = viewSize - contentSize
        } else {
            minTrans = viewSize - contentSize
            maxTrans = 0f
        }
        if (trans < minTrans) return -trans + minTrans
        return if (trans > maxTrans) -trans + maxTrans else 0f
    }

    fun getFixDragTrans(
        delta: Float,
        viewSize: Float,
        contentSize: Float
    ): Float {
        return if (contentSize <= viewSize) {
            0f
        } else delta
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        //
// Rescales image on rotation
//
        if (oldMeasuredHeight == viewWidth && oldMeasuredHeight == viewHeight || viewWidth == 0 || viewHeight == 0
        ) return
        oldMeasuredHeight = viewHeight
        oldMeasuredWidth = viewWidth
        if (saveScale == 1f) { // Fit to screen.
            val scale: Float
            val drawable = drawable
            if (drawable == null || drawable.intrinsicWidth == 0 || drawable.intrinsicHeight == 0
            ) return
            val bmWidth = drawable.intrinsicWidth
            val bmHeight = drawable.intrinsicHeight
            Log.d("bmSize", "bmWidth: $bmWidth bmHeight : $bmHeight")
            val scaleX = viewWidth.toFloat() / bmWidth.toFloat()
            val scaleY = viewHeight.toFloat() / bmHeight.toFloat()
            scale = Math.min(scaleX, scaleY)
            matrixs!!.setScale(scale, scale)
            // Center the image
            var redundantYSpace = (viewHeight.toFloat()
                    - scale * bmHeight.toFloat())
            var redundantXSpace = (viewWidth.toFloat()
                    - scale * bmWidth.toFloat())
            redundantYSpace /= 2.toFloat()
            redundantXSpace /= 2.toFloat()
            matrixs!!.postTranslate(redundantXSpace, redundantYSpace)
            origWidth = viewWidth - 2 * redundantXSpace
            origHeight = viewHeight - 2 * redundantYSpace
            imageMatrix = matrixs
        }
        fixTrans()
    }

    companion object {
        // We can be in one of these 3 states
        const val NONE = 0
        const val DRAG = 1
        const val ZOOM = 2
        const val CLICK = 3
    }
}

object NetworkUtil {

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }

    val okCacheControl = {app: Application ->
        OkCacheControl.NetworkMonitor {
            isNetworkConnected(app.applicationContext)
        }
    }
}

class RoundRectCornerImageView : ImageView {
    private val radius = 18.0f
    private var path: Path? = null
    private var rect: RectF? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        path = Path()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        path?.addRoundRect(rect!!, radius, radius, Path.Direction.CW)
        canvas.clipPath(path!!)
        super.onDraw(canvas)
    }
}

internal inline fun <T> ObservableField<T>.dslPropertyChangeCallback(crossinline block: (T) -> Unit) {
    this.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            this@dslPropertyChangeCallback.get()?.let(block)
        }
    })
}




