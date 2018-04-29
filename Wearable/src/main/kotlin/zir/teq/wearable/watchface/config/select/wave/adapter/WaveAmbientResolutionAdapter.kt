package zir.teq.wearable.watchface.config.select.wave.adapter

import android.app.Activity
import android.support.wear.widget.CircularProgressLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.types.wave.WaveAmbientResolution

class WaveAmbientResolutionAdapter(private val options: List<WaveAmbientResolution>) : RecAdapter() {
    override fun getItemViewType(position: Int) = options[position].configId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_circle_text, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val item = options[position]
        if (vh is Holder) {
            vh.bindProps(item)
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

        fun bindProps(props: WaveAmbientResolution) {
            //mCircle.setBackgroundResource(props.iconId)
            mCircle.setBackgroundResource(R.drawable.icon_dummy)
            mTextView.text = props.name
        }

        override fun onClick(view: View) {
            val activity = view.context as Activity
            val item: WaveAmbientResolution = options[adapterPosition]
            val editor = ConfigData.prefs.edit()
            editor.putString(item.pref, item.name)
            editor.apply()
            activity.setResult(Activity.RESULT_OK)
            activity.finish()
        }
    }
}
