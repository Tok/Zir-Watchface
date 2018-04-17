package zir.teq.wearable.watchface.config.select.main.style

import android.app.Activity
import android.os.Bundle
import android.support.wear.widget.CircularProgressLayout
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.style.Stroke
import zir.teq.wearable.watchface.util.ViewHelper


class StrokeViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.list_item_main) as Button
        view.setOnClickListener { super.handleClick(view, StrokeSelectionActivity.EXTRA) }
    }
}

class StrokeSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: StrokeSelectionAdapter
    private lateinit var mManager: WearableLinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        val sharedStrokeName = intent.getStringExtra(EXTRA)
        mAdapter = StrokeSelectionAdapter(sharedStrokeName, Stroke.options())
        mConfigView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mConfigView, mAdapter, mManager)
    }

    override fun onStart() {
        super.onStart()
        val index = Stroke.all.indexOfFirst { it.name.equals(ConfigData.style.stroke.name) }
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
            StrokeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_circle_text, parent, false))

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val stroke = mOptions[position]
        val strokeViewHolder = vh as StrokeViewHolder
        strokeViewHolder.bindStroke(stroke)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class StrokeViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle: CircularProgressLayout = view.findViewById(R.id.list_item_cicle_layout)
        val mText: TextView = view.findViewById(R.id.list_item_text)

        init {
            view.setOnClickListener(this)
        }

        fun bindStroke(stroke: Stroke) {
            mCircle.foreground = mView.context.getDrawable(stroke.iconId)
            mCircle.backgroundColor = ConfigData.palette.half()
            mText.text = stroke.name
            mCircle.strokeWidth = 1F
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val stroke = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                val editor = ConfigData.prefs.edit()
                editor.putString(mPrefString, stroke.name)
                editor.apply()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}
