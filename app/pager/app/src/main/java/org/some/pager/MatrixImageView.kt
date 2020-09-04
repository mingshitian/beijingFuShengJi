package org.some.pager

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.sqrt

/**
 * @ClassName: MatrixImageView
 * @Description:  带放大、缩小、移动效果的ImageView
 * @author LinJ
 * @date 2015-1-7 上午11:15:07
 */
class MatrixImageView(
    context: Context?,
    attrs: AttributeSet?
) : AppCompatImageView(context, attrs) {
    private val mGestureDetector: GestureDetector

    /**  模板Matrix，用以初始化  */
    private val mMatrix = Matrix()

    /**  图片长度 */
    private var mImageWidth = 0f

    /**  图片高度  */
    private var mImageHeight = 0f
    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        //设置完图片后，获取该图片的坐标变换矩阵
        mMatrix.set(imageMatrix)
        val values = FloatArray(9)
        mMatrix.getValues(values)
        //图片宽度为屏幕宽度除缩放倍数
        mImageWidth = width / values[Matrix.MSCALE_X]
        mImageHeight = (height - values[Matrix.MTRANS_Y] * 2) / values[Matrix.MSCALE_Y]
    }

    inner class MatrixTouchListener : OnTouchListener {
        /**   最大缩放级别 */
        var mMaxScale = 6f

        /**   双击时的缩放级别 */
        var mDobleClickScale = 2f
        private var mMode = 0 //

        /**  缩放开始时的手指间距  */
        private var mStartDis = 0f

        /**   当前Matrix */
        private val mCurrentMatrix = Matrix()

        /** 用于记录开始时候的坐标位置  */
        private val startPoint = PointF()
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            // TODO Auto-generated method stub
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    //设置拖动模式
                    mMode = Companion.MODE_DRAG
                    startPoint[event.x] = event.y
                    isMatrixEnable
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> reSetMatrix()
                MotionEvent.ACTION_MOVE -> if (mMode == Companion.MODE_ZOOM) {
                    setZoomMatrix(event)
                } else if (mMode == Companion.MODE_DRAG) {
                    setDragMatrix(event)
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    if (mMode == Companion.MODE_UNABLE) return true
                    mMode = Companion.MODE_ZOOM
                    mStartDis = distance(event)
                }
                else -> {
                }
            }
            return mGestureDetector.onTouchEvent(event)
        }

        private fun setDragMatrix(event: MotionEvent) {
            if (isZoomChanged) {
                var dx = event.x - startPoint.x // 得到x轴的移动距离
                var dy = event.y - startPoint.y // 得到x轴的移动距离
                //避免和双击冲突,大于10f才算是拖动
                if (Math.sqrt(dx * dx + dy * dy.toDouble()) > 10f) {
                    startPoint[event.x] = event.y
                    //在当前基础上移动
                    mCurrentMatrix.set(imageMatrix)
                    val values = FloatArray(9)
                    mCurrentMatrix.getValues(values)
                    dx = checkDxBound(values, dx)
                    dy = checkDyBound(values, dy)
                    mCurrentMatrix.postTranslate(dx, dy)
                    imageMatrix = mCurrentMatrix
                }
            }
        }//获取当前X轴缩放级别
        //获取模板的X轴缩放级别，两者做比较

        /**
         * 判断缩放级别是否是改变过
         * @return   true表示非初始值,false表示初始值
         */
        private val isZoomChanged: Boolean
            get() {
                val values = FloatArray(9)
                imageMatrix.getValues(values)
                //获取当前X轴缩放级别
                val scale = values[Matrix.MSCALE_X]
                //获取模板的X轴缩放级别，两者做比较
                mMatrix.getValues(values)
                return scale != values[Matrix.MSCALE_X]
            }

        /**
         * 和当前矩阵对比，检验dy，使图像移动后不会超出ImageView边界
         * @param values
         * @param dy
         * @return
         */
        private fun checkDyBound(values: FloatArray, dy: Float): Float {
            var dy = dy
            val height = height.toFloat()
            if (mImageHeight * values[Matrix.MSCALE_Y] < height) return 0f
            if (values[Matrix.MTRANS_Y] + dy > 0) dy =
                -values[Matrix.MTRANS_Y] else if (values[Matrix.MTRANS_Y] + dy < -(mImageHeight * values[Matrix.MSCALE_Y] - height)
            ) dy =
                -(mImageHeight * values[Matrix.MSCALE_Y] - height) - values[Matrix.MTRANS_Y]
            return dy
        }

        /**
         * 和当前矩阵对比，检验dx，使图像移动后不会超出ImageView边界
         * @param values
         * @param dx
         * @return
         */
        private fun checkDxBound(values: FloatArray, dx: Float): Float {
            var dx = dx
            val width = width.toFloat()
            if (mImageWidth * values[Matrix.MSCALE_X] < width) return 0f
            if (values[Matrix.MTRANS_X] + dx > 0) dx =
                -values[Matrix.MTRANS_X] else if (values[Matrix.MTRANS_X] + dx < -(mImageWidth * values[Matrix.MSCALE_X] - width)
            ) dx =
                -(mImageWidth * values[Matrix.MSCALE_X] - width) - values[Matrix.MTRANS_X]
            return dx
        }

        /**
         * 设置缩放Matrix
         * @param event
         */
        private fun setZoomMatrix(event: MotionEvent) {
            //只有同时触屏两个点的时候才执行
            if (event.pointerCount < 2) return
            val endDis = distance(event) // 结束距离
            if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                var scale = endDis / mStartDis // 得到缩放倍数
                mStartDis = endDis //重置距离
                mCurrentMatrix.set(imageMatrix) //初始化Matrix
                val values = FloatArray(9)
                mCurrentMatrix.getValues(values)
                scale = checkMaxScale(scale, values)
                imageMatrix = mCurrentMatrix
            }
        }

        /**
         * 检验scale，使图像缩放后不会超出最大倍数
         * @param scale
         * @param values
         * @return
         */
        private fun checkMaxScale(scale: Float, values: FloatArray): Float {
            var scale = scale
            if (scale * values[Matrix.MSCALE_X] > mMaxScale) scale =
                mMaxScale / values[Matrix.MSCALE_X]
            mCurrentMatrix.postScale(
                scale,
                scale,
                width / 2.toFloat(),
                height / 2.toFloat()
            )
            return scale
        }

        /**
         * 重置Matrix
         */
        private fun reSetMatrix() {
            if (checkRest()) {
                mCurrentMatrix.set(mMatrix)
                imageMatrix = mCurrentMatrix
            }
        }

        /**
         * 判断是否需要重置
         * @return  当前缩放级别小于模板缩放级别时，重置
         */
        private fun checkRest(): Boolean {
            // TODO Auto-generated method stub
            val values = FloatArray(9)
            imageMatrix.getValues(values)
            //获取当前X轴缩放级别
            val scale = values[Matrix.MSCALE_X]
            //获取模板的X轴缩放级别，两者做比较
            mMatrix.getValues(values)
            return scale < values[Matrix.MSCALE_X]
        }//设置为不支持手势//当加载出错时，不可缩放

        /**
         * 判断是否支持Matrix
         */
        private val isMatrixEnable: Unit
            private get() {
                //当加载出错时，不可缩放
                if (scaleType != ScaleType.CENTER) {
                    scaleType = ScaleType.MATRIX
                } else {
                    mMode = Companion.MODE_UNABLE //设置为不支持手势
                }
            }

        /**
         * 计算两个手指间的距离
         * @param event
         * @return
         */
        private fun distance(event: MotionEvent): Float {
            val dx = event.getX(1) - event.getX(0)
            val dy = event.getY(1) - event.getY(0)
            /** 使用勾股定理返回两点之间的距离  */
            return sqrt(dx * dx + dy * dy.toDouble()).toFloat()
        }

        /**
         * 双击时触发
         */
        fun onDoubleClick() {
            val scale: Float = (if (isZoomChanged) 1 else mDobleClickScale) as Float
            mCurrentMatrix.set(mMatrix) //初始化Matrix
            mCurrentMatrix.postScale(
                scale,
                scale,
                width / 2.toFloat(),
                height / 2.toFloat()
            )
            imageMatrix = mCurrentMatrix
        }


    }

    companion object {
        /** 拖拉照片模式  */
        private const val MODE_DRAG = 1

        /** 放大缩小照片模式  */
        private const val MODE_ZOOM = 2

        /**  不支持Matrix  */
        private const val MODE_UNABLE = 3

        private const val TAG = "MatrixImageView"
    }

    private inner class GestureListener(private val listener: MatrixTouchListener) :
        SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            //捕获Down事件
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            //触发双击事件
            listener.onDoubleClick()
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            // TODO Auto-generated method stub
            return super.onSingleTapUp(e)
        }

        override fun onLongPress(e: MotionEvent) {
            // TODO Auto-generated method stub
            super.onLongPress(e)
        }

        override fun onScroll(
            e1: MotionEvent, e2: MotionEvent,
            distanceX: Float, distanceY: Float
        ): Boolean {
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onFling(
            e1: MotionEvent, e2: MotionEvent, velocityX: Float,
            velocityY: Float
        ): Boolean {
            // TODO Auto-generated method stub
            return super.onFling(e1, e2, velocityX, velocityY)
        }

        override fun onShowPress(e: MotionEvent) {
            // TODO Auto-generated method stub
            super.onShowPress(e)
        }

        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
            // TODO Auto-generated method stub
            return super.onDoubleTapEvent(e)
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            // TODO Auto-generated method stub
            return super.onSingleTapConfirmed(e)
        }

    }

    init {
        val mListener = MatrixTouchListener()
        setOnTouchListener(mListener)
        mGestureDetector =
            GestureDetector(getContext(), GestureListener(mListener))
        //背景设置为balck
        setBackgroundColor(Color.BLACK)
        //将缩放类型设置为FIT_CENTER，表示把图片按比例扩大/缩小到View的宽度，居中显示
        scaleType = ScaleType.FIT_CENTER
    }
}