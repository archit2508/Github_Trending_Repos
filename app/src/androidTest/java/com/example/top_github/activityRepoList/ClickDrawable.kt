package com.example.top_github.activityRepoList

import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

/**
 * This class facilitates click on drawables inside Edit Text field by espresso.
 */
class ClickDrawableAction(
    @param:Location @field:Location
    private val drawableLocation: Int //this location denotes out of right, left, top, bottom which position to perform click on
) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return allOf(
            isAssignableFrom(TextView::class.java),
            object : BoundedMatcher<View, TextView>(TextView::class.java) {
                override fun matchesSafely(tv: TextView): Boolean {
                    //get focus so drawables are visible and if the textview has a drawable in the position then return a match
                    return tv.requestFocusFromTouch() && tv.compoundDrawables[drawableLocation] != null
                }

                override fun describeTo(description: Description) {
                    description.appendText("has drawable")
                }
            })
    }

    override fun getDescription(): String {
        return "click drawable"
    }

    override fun perform(uiController: UiController, view: View) {
        val tv = view as TextView//we matched
        if (tv.requestFocusFromTouch())
        //get focus so drawables are visible
        {
            //get the bounds of the drawable image
            val drawableBounds = tv.compoundDrawables[drawableLocation].bounds

            //calculate the drawable click location for left, top, right, bottom
            val clickPoint = arrayOfNulls<Point>(4)
            clickPoint[Left] = Point(
                tv.left + drawableBounds.width() / 2,
                (tv.pivotY + drawableBounds.height() / 2).toInt()
            )
            clickPoint[Top] = Point(
                (tv.pivotX + drawableBounds.width() / 2).toInt(),
                tv.top + drawableBounds.height() / 2
            )
            clickPoint[Right] = Point(
                tv.right + drawableBounds.width() / 2,
                (tv.pivotY + drawableBounds.height() / 2).toInt()
            )
            clickPoint[Bottom] = Point(
                (tv.pivotX + drawableBounds.width() / 2).toInt(),
                tv.bottom + drawableBounds.height() / 2
            )

            if (tv.dispatchTouchEvent(
                    MotionEvent.obtain(
                        android.os.SystemClock.uptimeMillis(),
                        android.os.SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_DOWN,
                        clickPoint[drawableLocation]?.x!!.toFloat(),
                        clickPoint[drawableLocation]?.y!!.toFloat(),
                        0
                    )
                )
            )
                tv.dispatchTouchEvent(
                    MotionEvent.obtain(
                        android.os.SystemClock.uptimeMillis(),
                        android.os.SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_UP,
                        clickPoint[drawableLocation]?.x!!.toFloat(),
                        clickPoint[drawableLocation]?.y!!.toFloat(),
                        0
                    )
                )
        }
    }

    @IntDef(Left, Top, Right, Bottom)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Location

    companion object {
        const val Left = 0
        const val Top = 1
        const val Right = 2
        const val Bottom = 3
    }
}