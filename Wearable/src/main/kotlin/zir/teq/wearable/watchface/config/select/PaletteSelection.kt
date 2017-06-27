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
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.manager.ScalingLayoutManager
import zir.teq.wearable.watchface.config.select.main.MainConfigActivity
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.Palette
import zir.teq.wearable.watchface.util.ViewHelper


class PaletteViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.config_list_item) as Button
        view.setOnClickListener { super.handleClick(view, PaletteSelectionActivity.EXTRA, MainConfigActivity.PALETTE.code) }
    }
}

class PaletteSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: PaletteSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_palette)
        val sharedColorName = intent.getStringExtra(EXTRA)
        mAdapter = PaletteSelectionAdapter(sharedColorName, Palette.options())
        mConfigView = findViewById<View>(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val index = Palette.selectable.indexOfFirst { it.equals(ConfigData.palette) } + 1
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_COLOR"
    }
}

class PaletteSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: java.util.ArrayList<Palette>) : RecAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ColorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_palette, parent, false))

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val pal = mOptions[position]
        val colorViewHolder = vh as ColorViewHolder
        colorViewHolder.bindPalette(pal)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class ColorViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mFirst = view.findViewById<View>(R.id.list_item_palette_first_cirlce) as CircledImageView
        val mSecond = view.findViewById<View>(R.id.list_item_palette_second_circle) as CircledImageView
        val mThird = view.findViewById<View>(R.id.list_item_palette_third_circle) as CircledImageView
        init {
            mView.setOnClickListener(this)
        }

        fun bindPalette(pal: Palette) {
            val oDim = Math.max(1F, ConfigData.outline.dim)
            with (mFirst) {
                setCircleColor(pal.dark())
                setCircleBorderWidth(oDim)
            }
            with (mSecond) {
                setCircleColor(pal.half())
                setCircleBorderWidth(oDim)
            }
            with (mThird) {
                setCircleColor(pal.light())
                setCircleBorderWidth(oDim)
            }
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val palette = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                ConfigData.palette = palette
                val editor = ConfigData.prefs.edit()
                editor.putString(mPrefString, palette.name)
                editor.commit()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}
