package com.lla.panicbutton.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lla.panicbutton.R
import com.lla.panicbutton.db.Recording
import kotlinx.android.synthetic.main.item_recording.view.*
import java.util.concurrent.TimeUnit

class RecordingAdapter(var recordings: List<Recording>) :
    RecyclerView.Adapter<RecordingAdapter.RecordingViewHolder>() {

    inner class RecordingViewHolder(recordingView: View) : RecyclerView.ViewHolder(recordingView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recording, parent, false)
        return RecordingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordingViewHolder, position: Int) {
        holder.itemView.apply {
            title.text = recordings[position].audioTitle
            length.text = convertTime(recordings[position].timeInMillis)
        }
    }

    override fun getItemCount(): Int {
        return recordings.size
    }

    fun convertTime(millis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        if (hours == 0L) {
            return String.format("%02d:%02d", minutes, seconds)
        }
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}