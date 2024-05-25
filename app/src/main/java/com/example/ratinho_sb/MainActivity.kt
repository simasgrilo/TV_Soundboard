package com.example.ratinho_sb

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.media.MediaPlayer
import android.view.MotionEvent
import java.io.File
import android.content.Intent
import android.net.Uri
import android.os.SystemClock
import android.renderscript.ScriptGroup.Input
import android.util.Log
import android.util.Xml
import android.view.GestureDetector
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.FragmentManager
import com.example.ratinho_sb.databinding.ActivityMainBinding
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var mDetector : GestureDetectorCompat //lateinit: avisa ao compiler que esse cara não vai ser inicializado agora. Isso evita nullchecks
    private var startPressTime = 0L
    private lateinit var binding : ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //var layout : ConstraintLayout = findViewById(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragment_container_view, RatinhoFragment(),"RatinhoFragment").commit()


        val gestureListnener = object : MyGestureListener(){
            override fun onSwipeRight(){
                /*val i : Intent = Intent(this@MainActivity,SwipeRight::class.java)
                startActivity(i)*/
                fragmentManager.beginTransaction().replace(R.id.fragment_container_view, OrafFragment(), "OrafFragment").commit()
            }

            override fun onSwipeLeft() {
                fragmentManager.beginTransaction().replace(R.id.fragment_container_view, RatinhoFragment(), "RatinhoFragment").commit()
            }
        }
        mDetector = GestureDetectorCompat(this, gestureListnener)

        val stopButton : Button = findViewById(R.id.buttonEmergencyStop)
        stopButton.setOnClickListener(){
            this@MainActivity.stopAudio();
        }

    }

    private fun stopAudio() {
        Toast.makeText(this,"Parando todos os áudios...", Toast.LENGTH_SHORT).show()
        @Suppress("UNCHECKED_CAST")
        //intent below returns the intent that initalized this activity
        val ratinhoFragment = this@MainActivity.supportFragmentManager.findFragmentByTag("RatinhoFragment")
        val orafFragment = this@MainActivity.supportFragmentManager.findFragmentByTag("OrafFragment")
        if (ratinhoFragment != null) {
            val soundButtonsRatinho : Array<MediaPlayer> = this@MainActivity.intent.extras?.get("ratinhoButtonList") as Array<MediaPlayer>
            soundButtonsRatinho.forEach {
                if (it.isPlaying()) {
                    it.stop()
                    it.prepare()
                }
            }
        }
        if (orafFragment != null) {
            val soundButtonsOraf : Array<MediaPlayer> = this@MainActivity.intent.extras?.get("orafButtonList") as Array<MediaPlayer>
            soundButtonsOraf.forEach {
                if (it.isPlaying()) {
                    it.stop()
                    it.prepare()
                }
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDetector.onTouchEvent(event)
        //this@mainActivity explicitamente define o class name no parâmetro.
        return super.onTouchEvent(event)
    }



    open class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD : Int = 30
        private val SWIPE_VELOCITY : Int = 100
        open fun onSwipeRight(){}
        open fun onSwipeLeft(){}
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }


        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result: Boolean = false
            val diffY : Float = e2.getY() - e1.getY()
            val diffX : Float = e2.getX() - e1.getX()
            if (Math.abs(diffX) > Math.abs(diffY)){
                if(Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY){
                    if(diffX < 0){
                        onSwipeRight()
                    }
                    else onSwipeLeft()
                    result = true
                }
            }
            return result
        }
    }

}