package com.example.ratinho_sb

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ratinho_sb.ButtonPropertyContainer

class RatinhoFragment : Fragment(R.layout.fragment_ratinho) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        //this is the method in which the fragment is rendered

        val view : View = inflater.inflate(R.layout.fragment_ratinho, container, false);
        val uepaButton : Button = view.findViewById(R.id.buttonUepa)
        val ratinhoButton : Button = view.findViewById(R.id.buttonRatinho)
        val rapazButton : Button = view.findViewById(R.id.buttonRapaz)
        val comboButton : Button = view.findViewById(R.id.buttonCombo)
        val ihaButton : Button = view.findViewById(R.id.ihaButton)
        val naoPaiButton : Button = view.findViewById(R.id.buttonNaoPai);

        val propertyContainer = ButtonPropertyContainer(activity as AppCompatActivity)

        val uepaButtonSb = propertyContainer.setButtons(uepaButton,"UEPAAAAA - Eu não deixava", Toast.LENGTH_SHORT,R.raw.uepaaa)
        val ratinhoButtonSb = propertyContainer.setButtons(ratinhoButton,"RATINHOOOO", Toast.LENGTH_SHORT,R.raw.ratinhoooo)
        val rapazButtonSb = propertyContainer.setButtons(rapazButton,"Rapaaaaz, que coisa...", Toast.LENGTH_SHORT,R.raw.rapazzz)
        val comboButtonSb = propertyContainer.setButtons(comboButton,"Combo master blaster", Toast.LENGTH_SHORT,R.raw.combo)
        val ihaButtonSb = propertyContainer.setButtons(ihaButton, toastStr="Ihaaaaaaa", toastLen= Toast.LENGTH_SHORT, R.raw.iha)
        val naoPaiButtonSb = propertyContainer.setButtons(naoPaiButton, toastStr = "É o pai?", toastLen= Toast.LENGTH_SHORT, R.raw.nao_pai)

        val ratinhoButtonList : Array<MediaPlayer> = arrayOf(uepaButtonSb,ratinhoButtonSb,rapazButtonSb,comboButtonSb,ihaButtonSb,naoPaiButtonSb)

        (activity as AppCompatActivity).intent.putExtra("ratinhoButtonList", ratinhoButtonList)
        //val intent = Intent(requireContext(), MainActivity::class.java)
        //intent.putExtra("ratinhoButtonList", ratinhoButtonList)

        return view

    }



}

