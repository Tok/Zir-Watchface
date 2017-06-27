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
import zir.teq.wearable.watchface.config.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.manager.ScalingLayoutManager
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.wave.Wave
import zir.teq.wearable.watchface.util.ViewHelper


class WaveViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.config_list_item) as Button
        view.setOnClickListener { super.handleClick(view, WaveSelectionActivity.EXTRA) }
    }
}

class WaveSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: WaveSelectionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection)
        val sharedWaveName = intent.getStringExtra(EXTRA)
        mAdapter = WaveSelectionAdapter(sharedWaveName, Wave.options())
        mConfigView = findViewById<View>(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val index = Wave.all.indexOfFirst { it.name.equals(ConfigData.wave.name) }
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_WAVE"
    }
}

class WaveSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Wave>) : RecAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            WaveViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

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
        val mCircle = view.findViewById<View>(R.id.list_item_cirlce) as CircledImageView
        val mText = view.findViewById<View>(R.id.list_item_text) as TextView
        init {
            mView.setOnClickListener(this)
        }

        fun bindWave(wave: Wave) {
            mCircle.setImageResource(wave.iconId)
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
                editor.commit()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}
