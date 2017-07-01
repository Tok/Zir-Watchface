package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.CircledImageView
import android.support.wearable.view.WearableRecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.holder.BooleanPairViewHolder
import zir.teq.wearable.watchface.config.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.manager.ScalingLayoutManager
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.component.Theme
import zir.teq.wearable.watchface.model.data.types.Component
import zir.teq.wearable.watchface.model.data.types.ComponentConfigItem
import zir.teq.wearable.watchface.util.ViewHelper


class ComponentViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.list_item_main) as Button
        view.setOnClickListener { super.handleClick(view, Theme.EXTRA) }
    }
}

class ComponentSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: ComponentSelectionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection)
        val items = Component.ALL + Theme.ALL.toList()
        mAdapter = ComponentSelectionAdapter(items)
        mConfigView = findViewById<View>(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val index = Theme.ALL.indexOfFirst { it.name.equals(ConfigData.theme.name) } + 1
        mConfigView.smoothScrollToPosition(index)
    }
}

class ComponentSelectionAdapter(private val options: List<ComponentConfigItem>) : RecAdapter() {
    override fun getItemViewType(position: Int) = options[position].configId
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        return when (viewType) {
            0 -> ThemeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
            else -> {
                val comp = Component.valueOf(viewType) as Component
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_double_check, parent, false)
                return BooleanPairViewHolder(view).apply { updateBoxes(comp.activeKey, comp.ambientKey, comp.name) }
            }
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
        val mCircleView = view.findViewById<View>(R.id.list_item_cirlce) as CircledImageView
        val mTextView = view.findViewById<View>(R.id.list_item_text) as TextView

        init {
            mView.setOnClickListener(this)
        }

        fun bindTheme(theme: Theme) {
            mCircleView.setImageResource(theme.iconId)
            mTextView.text = theme.name
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val componentConfigItem: ComponentConfigItem = options[position]
            if (componentConfigItem is Theme) {
                val activity = view.context as Activity
                Theme.saveComponentStates(componentConfigItem)
                activity.setResult(Activity.RESULT_OK)
                activity.finish()
            }
        }
    }
}
