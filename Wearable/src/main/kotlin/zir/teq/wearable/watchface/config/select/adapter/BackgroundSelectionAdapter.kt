package zir.teq.wearable.watchface.config.select.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Background

class BackgroundSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Background>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            BackgroundViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_background, parent, false))

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val bg = mOptions[position]
        val colorViewHolder = vh as BackgroundViewHolder
        colorViewHolder.bindBackground(bg)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class BackgroundViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle = view.findViewById(R.id.list_item_background_cirlce) as CircledImageView
        val mText = view.findViewById(R.id.list_item_background_text) as TextView
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