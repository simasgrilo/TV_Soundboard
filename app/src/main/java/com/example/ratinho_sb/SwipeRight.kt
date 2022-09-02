package com.example.ratinho_sb

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.ratinho_sb.databinding.ActivitySwipeRightBinding

class SwipeRight : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mDetector : GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_right)

        //voltar para a activity anterior swipando para a direita
        val gestureListnener = object : MainActivity.MyGestureListener(){
            override fun onSwipeLeft(){
                val i : Intent = Intent(this@SwipeRight,MainActivity::class.java)
                startActivity(i)
            }
        }
        mDetector = GestureDetectorCompat(this, gestureListnener)

        val cavaloButton : Button = findViewById(R.id.cavaloButton)
        val uiButton : Button = findViewById(R.id.uiButton)
        val ablublubleButton : Button = findViewById(R.id.ablubluble)
        val eleGostaButton : Button = findViewById(R.id.eleGostaButton)
        val dancaGatinhoButton : Button = findViewById(R.id.dancaGatinho)

        val cavaloButtonSb = setButtons(cavaloButton,"Cavalo.", Toast.LENGTH_SHORT,R.raw.cavalo)
        val uiButtonSb = setButtons(uiButton,"UUUUUUIIIII", Toast.LENGTH_SHORT,R.raw.ui)
        val ablublubleButtonSb = setButtons(ablublubleButton,"Ablublublebleb", Toast.LENGTH_SHORT,R.raw.ablubluble)
        val eleGostaButtonSb = setButtons(eleGostaButton,"Combo master blaster", Toast.LENGTH_SHORT,R.raw.elegosta)
        val dancaGatinhoButtonSb = setButtons(dancaGatinhoButton, "Dança, gatinho, Dança...", Toast.LENGTH_SHORT, R.raw.dancagatinhodanca)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDetector.onTouchEvent(event)
        //this@mainActivity explicitamente define o class name no parâmetro.
        return super.onTouchEvent(event)
    }

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