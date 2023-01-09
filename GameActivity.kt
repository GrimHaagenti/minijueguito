package com.example.firstapp.dragging

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.DragEvent
import android.view.View
import android.widget.GridLayout
import android.widget.ScrollView
import androidx.core.view.children
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityDragAndDropBinding
import com.example.firstapp.databinding.ActivityGameBinding
import com.example.firstapp.databinding.FrameChipLayoutBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.random.Random
import kotlin.reflect.safeCast

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    private lateinit var ChipSlotList: ArrayList<ChipSlot>

    private var gridSize = 4


    val timer = object: CountDownTimer(1000, 1000) {
        override fun onTick(millisUntilFinished: Long)
        {

        }

        override fun onFinish() {
            ChipSlotList[(0..ChipSlotList.size-1).random()].InitChip(1)
            start()

        }


    }.start()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)


        ChipSlotList = arrayListOf(ChipSlot(0,this))
        for (i in (1..gridSize * gridSize-1)) {
            val chippy: ChipSlot = ChipSlot(0,this)

            ChipSlotList.add(chippy)
        }

        for (i in (0 until ChipSlotList.size)) {
            val frame =  ChipSlotList[i]
            val view = layoutInflater.inflate(R.layout.frame_chip_layout, frame)
            val chip = view.findViewById<Chip>(R.id.chipTarget)
            frame.chippy = chip
            chip.id = i;
            chip.setOnLongClickListener {
                //it.visibility = View.GONE

                val shadow = View.DragShadowBuilder(it)
                it.startDragAndDrop(null, shadow, it, 0)
            }

            chip.setOnDragListener(dragListener)


            binding.gridLayout.addView(frame)
        }

        setContentView(binding.root)
    }

    private val dragListener = View.OnDragListener { destinationView, draggingData ->
        val event = draggingData.action
        val objecteMobil = draggingData.localState // És el tercer parametre

        when (event) {
            /*
            DragEvent.ACTION_DRAG_STARTED -> destinationView.setBackgroundColor(Color.MAGENTA)
            DragEvent.ACTION_DRAG_ENTERED -> destinationView.setBackgroundColor(Color.YELLOW)
            DragEvent.ACTION_DRAG_EXITED -> destinationView.setBackgroundColor(Color.MAGENTA)
            DragEvent.ACTION_DRAG_ENDED -> destinationView.setBackgroundColor(Color.CYAN)
            */
            DragEvent.ACTION_DROP -> {
                if (objecteMobil !is Chip)
                    return@OnDragListener true


                val draggedChipSlot = ChipSlotList[destinationView.id]
                println(draggedChipSlot?.mLevel)
                if (draggedChipSlot?.AddToLevel() == true) {
                    ChipGroup::class.safeCast(objecteMobil.parent)?.removeView(objecteMobil)
                }
            }
        }
        true


    }

}