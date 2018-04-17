package zir.teq.wearable.watchface.config.select.main.wave

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
import zir.teq.wearable.watchface.model.data.settings.wave.Wave
import zir.teq.wearable.watchface.util.ViewHelper


class WaveViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.list_item_main) as Button
        view.setOnClickListener { super.handleClick(view, WaveSelectionActivity.EXTRA) }
    }
}

class WaveSelectionActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: WaveSelectionAdapter
    private lateinit var mManager: WearableLinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        val sharedWaveName = intent.getStringExtra(EXTRA)
        mAdapter = WaveSelectionAdapter(sharedWaveName, Wave.options())
        mView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mView, mAdapter, mManager)
    }

    override fun onStart() {
        super.onStart()
        val index = Wave.all.indexOfFirst { it.name.equals(ConfigData.wave.name) }
        mView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_WAVE"
    }
}

class WaveSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Wave>) : RecAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WaveViewHolder(inflater.inflate(R.layout.list_item_circle_text, parent, false))
    }

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val wave = mOptions[position]
        val waveViewHolder = vh as WaveViewHolder
        waveViewHolder.bindWave(wave)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class WaveViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle: CircularProgressLayout = view.findViewById(R.id.list_item_cicle_layout)
        val mText: TextView = view.findViewById(R.id.list_item_text)

        init {
            mView.setOnClickListener(this)
        }

        fun bindWave(wave: Wave) {
            mCircle.setBackgroundResource(wave.iconId)
            mText.text = wave.name
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val wave: Wave = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                ConfigData.wave = wave
                val editor = ConfigData.prefs.edit()
                editor.putString(mPrefString, wave.name)
                editor.apply()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}
