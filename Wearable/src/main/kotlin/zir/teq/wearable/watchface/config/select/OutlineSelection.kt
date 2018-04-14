package zir.teq.wearable.watchface.config.select

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
import zir.teq.wearable.watchface.model.data.settings.style.Outline
import zir.teq.wearable.watchface.util.ViewHelper


class OutlineViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.list_item_main) as Button
        view.setOnClickListener { super.handleClick(view, OutlineSelectionActivity.EXTRA) }
    }
}

class OutlineSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: OutlineSelectionAdapter
    private lateinit var mManager: WearableLinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        val sharedOutlineName = intent.getStringExtra(EXTRA)
        mAdapter = OutlineSelectionAdapter(sharedOutlineName, Outline.options())
        mConfigView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mConfigView, mAdapter, mManager)
    }

    override fun onStart() {
        super.onStart()
        val index = Outline.all.indexOfFirst { it.name.equals(ConfigData.style.outline.name) } + 1
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
            OutlineViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_circle_text, parent, false))

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val outline = mOptions[position]
        val outlineViewHolder = vh as OutlineViewHolder
        outlineViewHolder.bindOutline(outline)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class OutlineViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle: CircularProgressLayout = view.findViewById(R.id.list_item_cicle_layout)
        val mText: TextView = view.findViewById(R.id.list_item_text)

        init {
            view.setOnClickListener(this)
        }

        fun bindOutline(outline: Outline) {
            mCircle.foreground = mView.context.getDrawable(outline.iconId)
            mCircle.backgroundColor = ConfigData.palette.half()
            mText.text = outline.name
            mCircle.strokeWidth = 1F
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
        val editor = ConfigData.prefs.edit()
        editor.putString(mPrefString, outline.name)
        editor.apply()
    }
}
