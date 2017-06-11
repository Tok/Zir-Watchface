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
import zir.teq.wearable.watchface.model.data.Dim

class DimSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: java.util.ArrayList<Dim>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DimViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_dim, parent, false))

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val dim = mOptions[position]
        val dimViewHolder = vh as DimViewHolder
        dimViewHolder.bindDim(dim)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class DimViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle = view.findViewById(R.id.list_item_dim_cirlce) as CircledImageView
        val mText = view.findViewById(R.id.list_item_dim_text) as TextView
        init {
            mView.setOnClickListener(this)
        }

        fun bindDim(dim: Dim) {
            mCircle.setCircleColor(mView.context.getColor(R.color.black))
            mCircle.alpha = dim.value.toFloat()
            mText.text = dim.name
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val dim: Dim = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                val editor = ConfigData.prefs.edit()
                editor.putString(mPrefString, dim.name)
                editor.commit()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}