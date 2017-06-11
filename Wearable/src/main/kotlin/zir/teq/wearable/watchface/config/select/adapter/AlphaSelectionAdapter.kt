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
import zir.teq.wearable.watchface.model.data.Alpha
import java.util.*

class AlphaSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Alpha>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            AlphaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_alpha, parent, false))

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val alpha = mOptions[position]
        val alphaViewHolder = vh as AlphaViewHolder
        alphaViewHolder.bindAlpha(alpha)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class AlphaViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle = view.findViewById(R.id.list_item_alpha_cirlce) as CircledImageView
        val mText = view.findViewById(R.id.list_item_alpha_text) as TextView
        init {
            mView.setOnClickListener(this)
        }

        fun bindAlpha(alpha: Alpha) {
            val oDim = Math.max(1F, ConfigData.outline.dim)
            mCircle.setCircleBorderWidth(oDim)
            mCircle.setCircleColor(ConfigData.palette.half())
            mCircle.alpha = 256 - alpha.value.toFloat() //Inverse of how it's used in draw util.
            mText.text = alpha.name
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val alpha: Alpha = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                ConfigData.alpha = alpha
                val editor = ConfigData.prefs.edit()
                editor.putString(mPrefString, alpha.name)
                editor.commit()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}