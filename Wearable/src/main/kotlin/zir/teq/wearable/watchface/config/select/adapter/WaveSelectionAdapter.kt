package config.select.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Wave

class WaveSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Wave>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            WaveViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_wave, parent, false))

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val wave = mOptions[position]
        val waveViewHolder = vh as WaveViewHolder
        waveViewHolder.bindWave(wave)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class WaveViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle = view.findViewById(R.id.list_item_wave_circle) as CircledImageView
        val mText = view.findViewById(R.id.list_item_wave_text) as TextView
        init {
            mView.setOnClickListener(this)
        }

        fun bindWave(wave: Wave) {
            mCircle.setImageResource(wave.iconId)
            mText.text = wave.name
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val wave: Wave = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                ConfigData.wave = wave
                val editor = ConfigData.prefs.edit()
                editor.putString(mPrefString, wave.name)
                editor.commit()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}
