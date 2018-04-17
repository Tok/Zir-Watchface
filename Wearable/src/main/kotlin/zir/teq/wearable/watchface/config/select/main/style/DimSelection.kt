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
import zir.teq.wearable.watchface.model.data.settings.style.Dim
import zir.teq.wearable.watchface.util.ViewHelper


class DimViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.list_item_main) as Button
        view.setOnClickListener { super.handleClick(view, DimSelectionActivity.EXTRA) }
    }
}

class DimSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: DimSelectionAdapter
    private lateinit var mManager: WearableLinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        val sharedDimId = intent.getStringExtra(EXTRA)
        mAdapter = DimSelectionAdapter(sharedDimId, Dim.options())
        mConfigView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mConfigView, mAdapter, mManager)
    }

    override fun onStart() {
        super.onStart()
        val index = Dim.all.indexOfFirst { it.name.equals(ConfigData.style.dim.name) } + 1
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_DIM"
    }
}

class DimSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: java.util.ArrayList<Dim>) : RecAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DimViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_circle_text, parent, false))

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val dim = mOptions[position]
        val dimViewHolder = vh as DimViewHolder
        dimViewHolder.bindDim(dim)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class DimViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle: CircularProgressLayout = view.findViewById(R.id.list_item_cicle_layout)
        val mText: TextView = view.findViewById(R.id.list_item_text)

        init {
            mView.setOnClickListener(this)
        }

        fun bindDim(dim: Dim) {
            mCircle.foreground = mView.context.getDrawable(R.drawable.icon_dummy)
            mCircle.backgroundColor = mView.context.getColor(R.color.black)
            mCircle.alpha = dim.value.toFloat()
            mCircle.strokeWidth = 1F
            mText.text = dim.name
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val dim: Dim = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                val editor = ConfigData.prefs.edit()
                editor.putString(mPrefString, dim.name)
                editor.apply()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}
