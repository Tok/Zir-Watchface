package zir.teq.wearable.watchface.config.select.adapter

import android.support.v7.widget.RecyclerView

class OutlineSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<zir.teq.wearable.watchface.model.data.Outline>) : android.support.v7.widget.RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int) =
            OutlineViewHolder(android.view.LayoutInflater.from(parent.context).inflate(zir.teq.wearable.watchface.R.layout.list_item_outline, parent, false))

    override fun onBindViewHolder(vh: android.support.v7.widget.RecyclerView.ViewHolder, position: Int) {
        val outline = mOptions[position]
        val outlineViewHolder = vh as zir.teq.wearable.watchface.config.select.adapter.OutlineSelectionAdapter.OutlineViewHolder
        zir.teq.wearable.watchface.util.ViewHelper.bindCircleColor(outlineViewHolder.mView)
        outlineViewHolder.setOutline(outline)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class OutlineViewHolder(view: android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(view), android.view.View.OnClickListener {
        val mView: android.support.wearable.view.CircledImageView
        init {
            mView = view.findViewById(zir.teq.wearable.watchface.R.id.list_item_outline) as android.support.wearable.view.CircledImageView
            view.setOnClickListener(this)
        }

        fun setOutline(outline: zir.teq.wearable.watchface.model.data.Outline) { mView.setCircleBorderWidth(outline.dim) }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            val outline: zir.teq.wearable.watchface.model.data.Outline = mOptions[position]
            val activity = view.context as android.app.Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                updateSavedValue(view.context, outline)
                activity.setResult(android.app.Activity.RESULT_OK)
            }
            activity.finish()
        }
    }

    fun updateSavedValue(ctx: android.content.Context, outline: zir.teq.wearable.watchface.model.data.Outline) {
        val editor = zir.teq.wearable.watchface.model.ConfigData.prefs(ctx).edit()
        editor.putString(mPrefString, outline.name)
        editor.commit()
    }
}