package com.example.ratinho_sb

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.GestureDetectorCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.ratinho_sb.databinding.ActivitySwipeRightBinding
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class SwipeRight : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mDetector : GestureDetectorCompat
    private var startPressTime = 0L

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
        val pareButton : Button = findViewById(R.id.pareButton)

        val cavaloButtonSb = setButtons(cavaloButton,"Cavalo.", Toast.LENGTH_SHORT,R.raw.cavalo)
        val uiButtonSb = setButtons(uiButton,"UUUUUUIIIII", Toast.LENGTH_SHORT,R.raw.ui)
        val ablublubleButtonSb = setButtons(ablublubleButton,"Ablublublebleb", Toast.LENGTH_SHORT,R.raw.ablubluble)
        val eleGostaButtonSb = setButtons(eleGostaButton,"Ele gosta...", Toast.LENGTH_SHORT,R.raw.elegosta)
        val dancaGatinhoButtonSb = setButtons(dancaGatinhoButton, "Dança, gatinho, Dança...", Toast.LENGTH_SHORT, R.raw.dancagatinhodanca)
        val pareButtonSb = setButtons(pareButton, "PARE!", Toast.LENGTH_SHORT, R.raw.pare)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDetector.onTouchEvent(event)
        //this@mainActivity explicitamente define o class name no parâmetro.
        return super.onTouchEvent(event)
    }

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

}