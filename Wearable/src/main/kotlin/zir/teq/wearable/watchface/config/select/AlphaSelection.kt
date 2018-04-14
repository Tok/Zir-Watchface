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
import zir.teq.wearable.watchface.model.data.settings.style.Alpha
import zir.teq.wearable.watchface.util.ViewHelper
import java.util.*


class AlphaViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.list_item_main) as Button
        view.setOnClickListener { super.handleClick(view, AlphaSelectionActivity.EXTRA) }
    }
}

class AlphaSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: AlphaSelectionAdapter
    private lateinit var mManager: WearableLinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        val sharedAlphaId = intent.getStringExtra(EXTRA)
        mAdapter = AlphaSelectionAdapter(sharedAlphaId, Alpha.options())
        mConfigView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mConfigView, mAdapter, mManager)
    }

    override fun onStart() {
        super.onStart()
        val index = Alpha.all.indexOfFirst { it.name.equals(ConfigData.style.alpha.name) } + 1
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_ALPHA"
    }
}

class AlphaSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Alpha>) : RecAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            AlphaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_circle_text, parent, false))

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val alpha = mOptions[position]
        val alphaViewHolder = vh as AlphaViewHolder
        alphaViewHolder.bindAlpha(alpha)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class AlphaViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle: CircularProgressLayout = view.findViewById(R.id.list_item_cicle_layout)
        val mText: TextView = view.findViewById(R.id.list_item_text)

        init {
            mView.setOnClickListener(this)
        }

        fun bindAlpha(alpha: Alpha) {
            mCircle.foreground = mView.context.getDrawable(R.drawable.icon_dummy)
            mCircle.backgroundColor = ConfigData.palette.half()
            mCircle.alpha = 256 - alpha.value.toFloat() //Inverse of how it's used in draw util.
            mCircle.strokeWidth = 1F
            mText.text = alpha.name
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val alpha: Alpha = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                val editor = ConfigData.prefs.edit()
                editor.putString(mPrefString, alpha.name)
                editor.apply()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}
