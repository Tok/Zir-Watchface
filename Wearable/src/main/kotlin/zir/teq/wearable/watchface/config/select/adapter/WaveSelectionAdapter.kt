package config.select.adapter

import android.app.Activity
import android.support.wearable.view.CircledImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.wave.Wave

class WaveSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Wave>) : RecAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            WaveViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_wave, parent, false))

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val wave = mOptions[position]
        val waveViewHolder = vh as WaveViewHolder
        waveViewHolder.bindWave(wave)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class WaveViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle = view.findViewById<View>(R.id.list_item_wave_circle) as CircledImageView
        val mText = view.findViewById<View>(R.id.list_item_wave_text) as TextView
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
