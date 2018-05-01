package zir.teq.wearable.watchface.config.select.adapter

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
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.general.types.CheckboxItem
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.types.WaveProps
import zir.teq.wearable.watchface.util.ViewHelper


class WavePropsAdapter(private val options: List<Item>) : RecAdapter() {
    override fun getItemViewType(position: Int) = options[position].code

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
        if (item is CheckboxItem == false) {
            (vh as Holder).bindProps(item)
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

        fun bindProps(props: Item) {
            mCircle.backgroundColor = Zir.color(ConfigData.palette().lightId)
            mCircle.setBackgroundResource(props.iconId)
            mTextView.text = props.name
        }

        override fun onClick(view: View) {
            val ctx = this.mView.context
            val activity = view.context as Activity
            val item: Item = options[adapterPosition]
            val intent = Intent(ctx, item.activity)
            startActivity(ctx, intent, null)
            activity.setResult(Activity.RESULT_OK)
            activity.finish()
        }
    }
}
