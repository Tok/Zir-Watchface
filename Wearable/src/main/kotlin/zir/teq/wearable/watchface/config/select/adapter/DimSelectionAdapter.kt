package zir.teq.wearable.watchface.config.select.adapter

import android.support.v7.widget.RecyclerView
import zir.teq.wearable.watchface.model.data.Dim

class DimSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: java.util.ArrayList<Dim>) : android.support.v7.widget.RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int) =
            DimViewHolder(android.view.LayoutInflater.from(parent.context).inflate(zir.teq.wearable.watchface.R.layout.list_item_dim, parent, false))

    override fun onBindViewHolder(vh: android.support.v7.widget.RecyclerView.ViewHolder, position: Int) {
        val dim = mOptions[position]
        val dimViewHolder = vh as zir.teq.wearable.watchface.config.select.adapter.DimSelectionAdapter.DimViewHolder
        dimViewHolder.bindDim(dim)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class DimViewHolder(view: android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(view), android.view.View.OnClickListener {
        val mView = view as android.widget.LinearLayout
        val mCircle = view.findViewById(zir.teq.wearable.watchface.R.id.list_item_dim_cirlce) as android.support.wearable.view.CircledImageView
        val mText = view.findViewById(zir.teq.wearable.watchface.R.id.list_item_dim_text) as android.widget.TextView
        init {
            mView.setOnClickListener(this)
        }

        fun bindDim(dim: zir.teq.wearable.watchface.model.data.Dim) {
            val ctx = mView.context
            mCircle.setCircleColor(ctx.getColor(zir.teq.wearable.watchface.R.color.black))
            mCircle.alpha = dim.value.toFloat()
            mText.text = dim.name
        }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            val dim: zir.teq.wearable.watchface.model.data.Dim = mOptions[position]
            val activity = view.context as android.app.Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                val editor = zir.teq.wearable.watchface.model.ConfigData.prefs(view.context).edit()
                editor.putString(mPrefString, dim.name)
                editor.commit()
                activity.setResult(android.app.Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}