package com.example.ratinho_sb

import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class OrafFragment : Fragment(R.layout.activity_oraf) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_oraf, container, false)


        val cavaloButton : Button = view.findViewById(R.id.cavaloButton)
        val uiButton : Button = view.findViewById(R.id.uiButton)
        val ablublubleButton : Button = view.findViewById(R.id.ablubluble)
        val eleGostaButton : Button = view.findViewById(R.id.eleGostaButton)
        val dancaGatinhoButton : Button = view.findViewById(R.id.dancaGatinho)
        val pareButton : Button = view.findViewById(R.id.pareButton)


        val propertyContainer = ButtonPropertyContainer(activity as AppCompatActivity)

        val cavaloButtonSb = propertyContainer.setButtons(cavaloButton,"Cavalo.", Toast.LENGTH_SHORT,R.raw.cavalo)
        val uiButtonSb = propertyContainer.setButtons(uiButton,"UUUUUUIIIII", Toast.LENGTH_SHORT,R.raw.ui)
        val ablublubleButtonSb = propertyContainer.setButtons(ablublubleButton,"Ablublublebleb", Toast.LENGTH_SHORT,R.raw.ablubluble)
        val eleGostaButtonSb = propertyContainer.setButtons(eleGostaButton,"Ele gosta...", Toast.LENGTH_SHORT,R.raw.elegosta)
        val dancaGatinhoButtonSb = propertyContainer.setButtons(dancaGatinhoButton, "Dança, gatinho, Dança...", Toast.LENGTH_SHORT, R.raw.dancagatinhodanca)
        val pareButtonSb = propertyContainer.setButtons(pareButton, "PARE!", Toast.LENGTH_SHORT, R.raw.pare)

        val orafButtonList : Array<MediaPlayer> = arrayOf(cavaloButtonSb, uiButtonSb, ablublubleButtonSb, eleGostaButtonSb, dancaGatinhoButtonSb, pareButtonSb)

        (activity as AppCompatActivity).intent.putExtra("orafButtonList", orafButtonList)
        return view
    }

}