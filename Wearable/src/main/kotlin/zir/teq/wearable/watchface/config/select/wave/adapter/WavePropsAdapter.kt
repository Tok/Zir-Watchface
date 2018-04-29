package zir.teq.wearable.watchface.config.select.wave.adapter

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.wear.widget.CircularProgressLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.select.wave.activity.*
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.types.WaveProps
import zir.teq.wearable.watchface.model.data.types.WaveResolution
import zir.teq.wearable.watchface.util.ViewHelper


class WavePropsAdapter(private val options: List<WaveProps>) : RecAdapter() {
    override fun getItemViewType(position: Int) = options[position].configId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (WaveProps.isBooleanProp(viewType)) {
            return ViewHelper.createViewHolder(parent, viewType)
        } else {
            val view = inflater.inflate(R.layout.list_item_circle_text, parent, false)
            Holder(view)
        }
    }

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val item = options[position]
        if (!item.isBooleanProp() && vh is Holder) {
            vh.bindWaveProps(item)
        }
    }

    override fun getItemCount(): Int = options.size

    inner class Holder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle: CircularProgressLayout = view.findViewById(R.id.list_item_cicle_layout)
        val mTextView: TextView = view.findViewById(R.id.list_item_text)

        init {
            mView.setOnClickListener(this)
        }

        fun bindWaveProps(waveProps: WaveProps) {
            mCircle.backgroundColor = Zir.color(ConfigData.palette().lightId)
            mCircle.setBackgroundResource(waveProps.iconId ?: R.drawable.icon_dummy)
            mTextView.text = waveProps.name
        }

        override fun onClick(view: View) {
            val ctx = this.mView.context
            val activity = view.context as Activity
            val item: WaveProps = options[adapterPosition]
            when (item) {
                WaveProps.SPECTRUM -> {
                    val intent = Intent(ctx, WaveSpectrumActivity.CLASS)
                    intent.putExtra(WaveSpectrumActivity.EXTRA, item.configId)
                    startActivity(ctx, intent, null)
                }
                WaveProps.VELOCITY -> {
                    val intent = Intent(ctx, WaveVelocityActivity.CLASS)
                    intent.putExtra(WaveVelocityActivity.EXTRA, item.configId)
                    startActivity(ctx, intent, null)
                }
                WaveProps.FREQUENCY -> {
                    val intent = Intent(ctx, WaveFrequencyActivity.CLASS)
                    intent.putExtra(WaveFrequencyActivity.EXTRA, item.configId)
                    startActivity(ctx, intent, null)
                }
                WaveProps.INTENSITY -> {
                    val intent = Intent(ctx, WaveIntensityActivity.CLASS)
                    intent.putExtra(WaveIntensityActivity.EXTRA, item.configId)
                    startActivity(ctx, intent, null)
                }
                WaveProps.RESOLUTION -> {
                    val intent = Intent(ctx, WaveResolutionActivity.CLASS)
                    intent.putExtra(WaveResolutionActivity.EXTRA, item.configId)
                    startActivity(ctx, intent, null)
                }
                WaveProps.AMBIENT_RESOLUTION -> {
                    val intent = Intent(ctx, WaveAmbientResolutionActivity.CLASS)
                    intent.putExtra(WaveAmbientResolutionActivity.EXTRA, item.configId)
                    startActivity(ctx, intent, null)
                }
                else -> throw IllegalArgumentException("Unknown waveSpectrum prop: $item")
            }

            activity.setResult(Activity.RESULT_OK)
            activity.finish()
        }
    }
}
