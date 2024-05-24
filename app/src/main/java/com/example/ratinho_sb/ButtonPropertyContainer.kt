package com.example.ratinho_sb

import android.content.Intent
import android.media.MediaPlayer
import android.os.SystemClock
import android.provider.MediaStore.Audio.Media
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class ButtonPropertyContainer (context: AppCompatActivity) {

    private var startPressTime = 0L
    private var activityContext : AppCompatActivity = context


    fun setButtons(button: Button, toastStr: String, toastLen: Int, rawFile: Int) : MediaPlayer {
        val butSound = MediaPlayer.create(this.activityContext, rawFile)
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
                            val fileStream = this.activityContext.resources.openRawResource(rawFile)
                            val soundToShare = File.createTempFile("soundEffect",".mp3")
                            //copies the sound to a temp file
                            this.copyFile(fileStream, FileOutputStream(soundToShare))
                            val fileUri = FileProvider.getUriForFile(this.activityContext.applicationContext,
                                BuildConfig.APPLICATION_ID + ".provider",
                                soundToShare)
                            val sendIntent : Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_STREAM, fileUri)
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                //type = "plain/text"
                                type = "audio/*"
                            }
                            this.activityContext.startActivity(Intent.createChooser(sendIntent,null))
                        }
                        catch (e : java.lang.Exception){
                            e.printStackTrace()
                        }
                    }
                    else{ //sound
                        val butToast = Toast.makeText(this.activityContext, toastStr, toastLen)
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