package zir.teq.wearable.watchface.config.select.wave.adapter

import android.app.Activity
import android.support.wear.widget.CircularProgressLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.wave.activity.WaveSpectrumActivity
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.wave.Spectrum


class WaveSpectrumAdapter(private val mOptions: ArrayList<Spectrum>) : RecAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WaveViewHolder(inflater.inflate(R.layout.list_item_circle_text, parent, false))
    }

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val wave = mOptions[position]
        val waveViewHolder = vh as WaveViewHolder
        waveViewHolder.bindWaveSpectrum(wave)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class WaveViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle: CircularProgressLayout = view.findViewById(R.id.list_item_cicle_layout)
        val mText: TextView = view.findViewById(R.id.list_item_text)

        init {
            mView.setOnClickListener(this)
        }

        fun bindWaveSpectrum(wave: Spectrum) {
            //mCircle.setBackgroundResource(wave.iconId)
            mCircle.setBackgroundResource(R.drawable.icon_dummy)
            mText.text = wave.name
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val spectrum: Spectrum = mOptions[position]
            val activity = view.context as Activity
            val editor = ConfigData.prefs.edit()
            editor.putString(spectrum.pref, spectrum.name)
            editor.apply()
            activity.setResult(Activity.RESULT_OK)
            activity.finish()
        }
    }
}