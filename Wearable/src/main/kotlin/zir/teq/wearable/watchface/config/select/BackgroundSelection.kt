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
import zir.teq.wearable.watchface.config.manager.ScalingLayoutManager
import zir.teq.wearable.watchface.config.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.Background
import zir.teq.wearable.watchface.util.ViewHelper

class BackgroundPickerViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.config_list_item_background) as Button
        view.setOnClickListener { super.handleClick(view, BackgroundSelectionActivity.EXTRA) }
    }
}

class BackgroundSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: BackgroundSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_background)
        val sharedBackgroundId = intent.getStringExtra(EXTRA)
        mAdapter = BackgroundSelectionAdapter(sharedBackgroundId, Background.options())
        mConfigView = findViewById<View>(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
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
            BackgroundViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_background, parent, false))

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val bg = mOptions[position]
        val colorViewHolder = vh as BackgroundViewHolder
        colorViewHolder.bindBackground(bg)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class BackgroundViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle = view.findViewById<View>(R.id.list_item_background_cirlce) as CircledImageView
        val mText = view.findViewById<View>(R.id.list_item_background_text) as TextView
        init {
            mView.setOnClickListener(this)
        }

        fun bindBackground(background: Background) {
            val oDim = Math.max(1F, ConfigData.outline.dim)
            mCircle.setCircleBorderWidth(oDim)
            mCircle.setCircleColor(mCircle.context.getColor(background.id))
            mText.text = background.name
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val background: Background = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                ConfigData.background = background
                val editor = ConfigData.prefs.edit()
                editor.putString(mPrefString, background.name)
                editor.commit()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}
