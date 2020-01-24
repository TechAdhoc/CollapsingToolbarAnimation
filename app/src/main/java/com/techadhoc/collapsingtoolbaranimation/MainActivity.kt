package com.techadhoc.collapsingtoolbaranimation

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout


class MainActivity : AppCompatActivity() {
    private var appBarExpanded: Boolean=false
    private var scrollRange=0;
    private var offsetFactor:Float=0f;
    private var scaleFactor:Float=0f;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myToolbar: Toolbar =  findViewById(R.id.my_toolbar)
        var appBarLayout: AppBarLayout= findViewById(R.id.appbar)
        var img:ImageView=findViewById(R.id.my_img)
        var imageExpendHight:Int=  getResources().getDimensionPixelOffset(R.dimen.imageExpendHight);
        var imageCollapsHight:Int= getResources().getDimensionPixelOffset(R.dimen.imageCollapsHight);
        var topMargin:Int=  getResources().getDimensionPixelOffset(R.dimen.imageTopMarginCollapsed);
        var leftMargin:Int=  getResources().getDimensionPixelOffset(R.dimen.imageLeftMarginCollapsed);
        setSupportActionBar(myToolbar);
        getSupportActionBar()?.setHomeButtonEnabled(true)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        var myTitle:CollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        myTitle.setTitle("TechAdhoc")

        appBarLayout.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            Log.d(
               "TechAdhoc",
                "onOffsetChanged-: verticalOffset: $verticalOffset"
            )
            scrollRange = appBarLayout.totalScrollRange
            offsetFactor =
                (-verticalOffset).toFloat() / scrollRange.toFloat()
            Log.d(
                "TechAdhoc",
                "onOffsetChanged(), offsetFactor = $offsetFactor"
            )
            scaleFactor = 1f - offsetFactor * .5f

            img.setScaleX(scaleFactor)
            img.setScaleY(scaleFactor)

            val topOffset =
                ((topMargin - img.top) * offsetFactor) - verticalOffset
            val leftOffset = ((250 - 200) * offsetFactor)
            img.pivotX=0f
            img.pivotY=0f
            img.translationY=topOffset
            img.translationX=leftOffset


            //  Vertical offset == 0 indicates appBar is fully expanded.
            if (Math.abs(verticalOffset) > 160) {
                appBarExpanded = false
                Handler().postDelayed({
                    appBarLayout.setExpanded(true)
                }, 400)
            } else {
                appBarExpanded = true
            }
        })

    }

    val gestureDetector = GestureDetector(object : SimpleOnGestureListener() {
        override fun onLongPress(e: MotionEvent) {
            Log.e("", "Longpress detected")
        }
    })

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }
}