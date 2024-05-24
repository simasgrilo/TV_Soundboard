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
        fragmentManager.beginTransaction().replace(R.id.fragment_container_view, RatinhoFragment()).commit()


        val gestureListnener = object : MyGestureListener(){
            override fun onSwipeRight(){
                val i : Intent = Intent(this@MainActivity,SwipeRight::class.java)
                startActivity(i)
            }
        }
        mDetector = GestureDetectorCompat(this, gestureListnener)

/*
        val uepaButton : Button = findViewById(R.id.buttonUepa)
        val ratinhoButton : Button = findViewById(R.id.buttonRatinho)
        val rapazButton : Button = findViewById(R.id.buttonRapaz)
        val comboButton : Button = findViewById(R.id.buttonCombo)
        val ihaButton : Button = findViewById(R.id.ihaButton)
        val naoPaiButton : Button = findViewById(R.id.buttonNaoPai);

        val uepaButtonSb = setButtons(uepaButton,"UEPAAAAA - Eu não deixava",Toast.LENGTH_SHORT,R.raw.uepaaa)
        val ratinhoButtonSb = setButtons(ratinhoButton,"RATINHOOOO",Toast.LENGTH_SHORT,R.raw.ratinhoooo)
        val rapazButtonSb = setButtons(rapazButton,"Rapaaaaz, que coisa...",Toast.LENGTH_SHORT,R.raw.rapazzz)
        val comboButtonSb = setButtons(comboButton,"Combo master blaster",Toast.LENGTH_SHORT,R.raw.combo)
        val ihaButtonSb = setButtons(ihaButton, toastStr="Ihaaaaaaa", toastLen=Toast.LENGTH_SHORT, R.raw.iha)
        val naoPaiButtonSb = setButtons(naoPaiButton, toastStr = "É o pai?", toastLen=Toast.LENGTH_SHORT, R.raw.nao_pai)

        //TODO a better way to control all buttons? -> property container?
        val soundButtons : MutableList<MediaPlayer> = mutableListOf(uepaButtonSb,ratinhoButtonSb,rapazButtonSb,
        comboButtonSb,ihaButtonSb, naoPaiButtonSb)

        */
        val stopButton : Button = findViewById(R.id.buttonEmergencyStop)
        stopButton.setOnClickListener(){
            this@MainActivity.stopAudio();
        }

    }

    private fun stopAudio() {
        Toast.makeText(this,"Parando todos os áudios...", Toast.LENGTH_SHORT).show()
        @Suppress("UNCHECKED_CAST")
        //intent below returns the intent that initalized this activity
        val soundButtons : Array<MediaPlayer> = this@MainActivity.intent.extras?.get("ratinhoButtonList") as Array<MediaPlayer>
        soundButtons.forEach {
            if (it.isPlaying()) {
                it.stop()
                it.prepare()
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

//TODO below will go to property container (or inside the fragment)
    /*
    fun setButtons(button: Button, toastStr: String, toastLen: Int, rawFile: Int) : MediaPlayer {
        val butSound = MediaPlayer.create(this, rawFile)
        button.setOnTouchListener(){ v, event ->
            val movAction = event.action
            var endTime = 0L

            when(movAction){
                MotionEvent.ACTION_DOWN ->{
                    //event.getPointerId(event.actionIndex)
                    startPressTime = SystemClock.elapsedRealtime()
                }
                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP -> {
                    endTime = SystemClock.elapsedRealtime()
                    if (endTime - startPressTime > 1000){ //share the file
                        try{
                            val fileStream = resources.openRawResource(rawFile)
                            val soundToShare = File.createTempFile("soundEffect",".mp3")
                            //copies the sound to a temp file
                            this.copyFile(fileStream, FileOutputStream(soundToShare))
                            val fileUri = FileProvider.getUriForFile(applicationContext,
                                BuildConfig.APPLICATION_ID + ".provider",
                                soundToShare)
                            val sendIntent : Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_STREAM, fileUri)
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                //type = "plain/text"
                                type = "audio/*"
                            }
                            startActivity(Intent.createChooser(sendIntent,null))
                        }
                        catch (e : java.lang.Exception){
                            e.printStackTrace()
                        }
                    }
                    else{ //sound
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
                    true
                    startPressTime = 0L
                }
                else -> {}
            }
            false
        }
        return butSound;

    }
    fun copyFile(originFile : InputStream, resFile: OutputStream){
        val buffer = ByteArray(1024);
        var read: Int
        read = 0
        while( read != -1) {
            read = originFile.read(buffer)
            if (read == -1) break;
            resFile.write(buffer,0,read)
        }
    }
    */
     */


}