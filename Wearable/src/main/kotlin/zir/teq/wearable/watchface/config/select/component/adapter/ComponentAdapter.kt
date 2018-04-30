package zir.teq.wearable.watchface.config.select.component.adapter

import android.app.Activity
import android.support.wear.widget.CircularProgressLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.holder.BooleanPairViewHolder
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.component.Theme
import zir.teq.wearable.watchface.model.data.types.Component
import zir.teq.wearable.watchface.config.general.ConfigItem


class ComponentAdapter(private val options: List<ConfigItem>) : RecAdapter() {
    override fun getItemViewType(position: Int) = options[position].configId
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (Component.isDoubleBoolean(viewType)) {
            val comp = Component.valueOf(viewType) as Component
            val view = inflater.inflate(R.layout.list_item_double_check, parent, false)
            BooleanPairViewHolder(view, comp.activeKey, comp.ambientKey).apply {
                updateBoxes(comp.activeKey, comp.ambientKey, comp.name)
            }
        } else {
            val view = inflater.inflate(R.layout.list_item_circle_text, parent, false)
            ThemeViewHolder(view)
        }
    }

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val item = options[position]
        when (item) {
            is Component -> if (vh is BooleanPairViewHolder) prepareDoubleCheckHolder(vh, item)
            is Theme -> if (vh is ThemeViewHolder) vh.bindTheme(item)
        }
    }

    private fun prepareDoubleCheckHolder(holder: BooleanPairViewHolder, item: Component) {
        holder.updateBoxes(item.activeKey, item.ambientKey, item.name)
    }

    override fun getItemCount(): Int = options.size

    inner class ThemeViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle: CircularProgressLayout = view.findViewById(R.id.list_item_cicle_layout)
        val mTextView: TextView = view.findViewById(R.id.list_item_text)

        init {
            mView.setOnClickListener(this)
        }

        fun bindTheme(theme: Theme) {
            mCircle.setBackgroundResource(theme.iconId)
            mTextView.text = theme.name
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val componentConfigItem: ConfigItem = options[position]
            if (componentConfigItem is Theme) {
                val activity = view.context as Activity
                Theme.saveComponentStates(componentConfigItem)
                notifyDataSetChanged()
                activity.setResult(Activity.RESULT_OK)
                activity.finish()
            }
        }
    }
}
