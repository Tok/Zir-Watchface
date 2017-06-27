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
import zir.teq.wearable.watchface.model.data.settings.Stroke
import zir.teq.wearable.watchface.util.ViewHelper


class StrokeViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.config_list_item) as Button
        view.setOnClickListener { super.handleClick(view, StrokeSelectionActivity.EXTRA) }
    }
}

class StrokeSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: StrokeSelectionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection)
        val sharedStrokeName = intent.getStringExtra(EXTRA)
        mAdapter = StrokeSelectionAdapter(sharedStrokeName, Stroke.options())
        mConfigView = findViewById<View>(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, CurvedChildLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val index = Stroke.all.indexOfFirst { it.name.equals(ConfigData.stroke.name) }
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_STROKE"
    }
}

class StrokeSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Stroke>) : RecAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            StrokeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_stroke, parent, false))

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val stroke = mOptions[position]
        val strokeViewHolder = vh as StrokeViewHolder
        strokeViewHolder.bindStroke(stroke)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class StrokeViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView: CircledImageView
        init {
            mView = view.findViewById<View>(R.id.list_item_stroke) as CircledImageView
            view.setOnClickListener(this)
        }

        fun bindStroke(stroke: Stroke) {
            val oDim = Math.max(1F, ConfigData.outline.dim)
            mView.setCircleBorderWidth(oDim)
            mView.circleRadius = stroke.dim + oDim
            mView.setCircleColor(ConfigData.palette.half())
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val stroke = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                ConfigData.stroke = stroke
                val editor = ConfigData.prefs.edit()
                editor.putString(mPrefString, stroke.name)
                editor.commit()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}
