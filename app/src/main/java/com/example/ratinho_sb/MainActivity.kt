package com.example.ratinho_sb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.media.MediaPlayer
import android.media.MediaSyncEvent
import android.provider.MediaStore.Audio.Media
import android.view.MotionEvent
import androidx.core.net.toUri
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import android.content.Intent
import android.util.Log
import android.view.GestureDetector
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GestureDetectorCompat


 class MainActivity : AppCompatActivity() {


    private lateinit var mDetector : GestureDetectorCompat //lateinit: avisa ao compiler que esse cara não vai ser inicializado agora. Isso evita nullchecks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //var layout : ConstraintLayout = findViewById(R.layout.activity_main)

        val gestureListnener = object : MyGestureListener(){
            override fun onSwipeRight(){
                val i : Intent = Intent(this@MainActivity,SwipeRight::class.java)
                startActivity(i)
            }
        }
        mDetector = GestureDetectorCompat(this, gestureListnener)


        val uepaButton : Button = findViewById(R.id.buttonUepa)
        val ratinhoButton : Button = findViewById(R.id.buttonRatinho)
        val rapazButton : Button = findViewById(R.id.buttonRapaz)
        val comboButton : Button = findViewById(R.id.buttonCombo)
        val ihaButton : Button = findViewById(R.id.ihaButton)

        val uepaButtonSb = setButtons(uepaButton,"UEPAAAAA - Eu não deixava",Toast.LENGTH_SHORT,R.raw.uepaaa)
        val ratinhoButtonSb = setButtons(ratinhoButton,"RATINHOOOO",Toast.LENGTH_SHORT,R.raw.ratinhoooo)
        val rapazButtonSb = setButtons(rapazButton,"Rapaaaaz, que coisa...",Toast.LENGTH_SHORT,R.raw.rapazzz)
        val comboButtonSb = setButtons(comboButton,"Combo master blaster",Toast.LENGTH_SHORT,R.raw.combo)
        val ihaButtonSb = setButtons(ihaButton, toastStr="Ihaaaaaaa", toastLen=Toast.LENGTH_SHORT, R.raw.iha)

        //TODO a better way to control all buttons?
        val soundButtons : MutableList<MediaPlayer> = mutableListOf(uepaButtonSb,ratinhoButtonSb,rapazButtonSb,
        comboButtonSb,ihaButtonSb)


        val stopButton : Button = findViewById(R.id.buttonEmergencyStop)
        stopButton.setOnClickListener(){
            Toast.makeText(this,"Parando todos os áudios...", Toast.LENGTH_SHORT).show()
            //this.recreate()
            soundButtons.forEach {
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



    //open: faz a classe não ser final. por definição, classes em kotlin são final.
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
                    if(diffX > 0){
                        onSwipeRight()
                    }
                    else onSwipeLeft()
                    result = true
                }
            }
            return result
        }
    }

/*
    override fun onTouchEvent(touchEvent: MotionEvent ) : Boolean{
        var x1 : Float = touchEvent.getX()
        var x2 : Float = touchEvent.getX()
        var y1 : Float
        var y2 : Float
        if (touchEvent.action == MotionEvent.ACTION_DOWN){
                x1 = touchEvent.getX()
                y1 = touchEvent.getY()
            }
        if (touchEvent.action == MotionEvent.ACTION_UP){
                x2 = touchEvent.getX()
                y2 = touchEvent.getY()
            if (x1 > x2){
                var intent : Intent = Intent(this, SwipeRight::class.java)
                startActivity(intent)
            }
            }
        val test : Toast = Toast.makeText(this, "cu", Toast.LENGTH_SHORT)
        test.show()

        return false
    }*/



    fun setButtons(button: Button, toastStr: String, toastLen: Int, rawFile: Int) : MediaPlayer {
        val butSound = MediaPlayer.create(this, rawFile)
        button.setOnClickListener(){
            val butToast = Toast.makeText(this, toastStr, toastLen)
            butToast.show()
            //val butSound = MediaPlayer.create(this, rawFile)
            print(butSound.toString())
            if (butSound.isPlaying()){
                butSound.stop()
                butSound.prepare()
                butSound.start()
            }
            else
                butSound.start()
        }
        return butSound;

    }

}