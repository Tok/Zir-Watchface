package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.CircledImageView
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.Outline
import zir.teq.wearable.watchface.util.ViewHelper


class OutlineViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.config_list_item_outline) as Button
        view.setOnClickListener { super.handleClick(view, OutlineSelectionActivity.EXTRA) }
    }
}

class OutlineSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: OutlineSelectionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_outline)
        val sharedOutlineName = intent.getStringExtra(EXTRA)
        mAdapter = OutlineSelectionAdapter(sharedOutlineName, Outline.options())
        mConfigView = findViewById<View>(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, CurvedChildLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val index = Outline.all.indexOfFirst { it.name.equals(ConfigData.outline.name) } + 1
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_OUTLINE"
    }
}

class OutlineSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Outline>) : RecAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            OutlineViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_outline, parent, false))

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val outline = mOptions[position]
        val outlineViewHolder = vh as OutlineViewHolder
        outlineViewHolder.bindOutline(outline)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class OutlineViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView: CircledImageView
        init {
            mView = view.findViewById<View>(R.id.list_item_outline) as CircledImageView
            view.setOnClickListener(this)
        }

        fun bindOutline(outline: Outline) {
            val oDim = Math.max(1F, outline.dim)
            mView.setCircleBorderWidth(oDim)
            mView.setCircleColor(ConfigData.palette.half())
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val outline: Outline = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                updateSavedValue(outline)
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }

    fun updateSavedValue(outline: Outline) {
        ConfigData.outline = outline
        val editor = ConfigData.prefs.edit()
        editor.putString(mPrefString, outline.name)
        editor.commit()
    }
}
