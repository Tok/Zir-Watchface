package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.WearableRecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.manager.ScalingLayoutManager
import zir.teq.wearable.watchface.config.select.config.ConfigItemTypes
import zir.teq.wearable.watchface.config.select.config.Item
import zir.teq.wearable.watchface.config.select.config.StyleType
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.util.ViewHelper


class StyleViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.list_item_main) as Button
        view.setOnClickListener { super.handleClick(view, StyleSelectionActivity.EXTRA, StyleSelectionActivity.REQ) }
    }
}

class StyleSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: StyleSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection)
        mAdapter = StyleSelectionAdapter(Item.createStyleConfig(this))
        mConfigView = findViewById<View>(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_STYLE"
        val REQ = 111
    }
}

class StyleSelectionAdapter(private val options: ConfigItemTypes) : RecAdapter() {
    override fun getItemViewType(position: Int) = options[position].configType
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        return ViewHelper.createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecHolder, position: Int) {
        val item = options[position] as Item
        when (holder) {
            is RecSelectionViewHolder -> {
                holder.setName(item.name)
                holder.setSharedPrefString(item.pref)
                holder.bindIcon((item.type as StyleType).iconId, ConfigData.palette.lightId)
                holder.setActivity(item.activity)
            }
        }
    }

    override fun getItemCount() = options.size
}
