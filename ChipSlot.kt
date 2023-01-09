package com.example.firstapp.dragging

import android.content.Context
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ChipSlot(level: Int, context: Context?): ChipGroup(context) {

        var mLevel: Int = level
        var canBlowUp = false
        lateinit var chippy:Chip

    fun AddToLevel():Boolean{
        if(mLevel>0 && mLevel<3){
            mLevel = mLevel+1
            chippy.text = mLevel.toString()
            println("AAAAAAAAA")
            return true;
        }
        canBlowUp = true;
        return false
    }

    fun InitChip(level: Int){
        mLevel = level
        chippy.text = mLevel.toString()
    }
}
