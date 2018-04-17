package zir.teq.wearable.watchface.config.select.main.color

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
import zir.teq.wearable.watchface.model.data.settings.color.Background
import zir.teq.wearable.watchface.util.ViewHelper


class BackgroundViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.list_item_main) as Button
        view.setOnClickListener { super.handleClick(view, BackgroundSelectionActivity.EXTRA) }
    }
}

class BackgroundSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: BackgroundSelectionAdapter
    private lateinit var mManager: WearableLinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        val sharedBackgroundId = intent.getStringExtra(EXTRA)
        mAdapter = BackgroundSelectionAdapter(sharedBackgroundId, Background.options())
        mConfigView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mConfigView, mAdapter, mManager)
    }

    override fun onStart() {
        super.onStart()
        val index = Background.all.indexOfFirst { it.name.equals(ConfigData.background.name) } + 1
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_BACKGROUND"
    }
}

class BackgroundSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Background>) : RecAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            BackgroundViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_circle_text, parent, false))

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val bg = mOptions[position]
        val colorViewHolder = vh as BackgroundViewHolder
        colorViewHolder.bindBackground(bg)
    }

    override fun getItemCount(): Int = mOptions.size

    inner class BackgroundViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle: CircularProgressLayout = view.findViewById(R.id.list_item_cicle_layout)
        val mText: TextView = view.findViewById(R.id.list_item_text)

        init {
            mView.setOnClickListener(this)
        }

        fun bindBackground(background: Background) {
            mCircle.foreground = mView.context.getDrawable(R.drawable.icon_dummy)
            mCircle.backgroundColor = mCircle.context.getColor(background.id)
            mText.text = background.name
            mCircle.strokeWidth = 1F
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val background: Background = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                ConfigData.background = background
                val editor = ConfigData.prefs.edit()
                editor.putString(mPrefString, background.name)
                editor.apply()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}
