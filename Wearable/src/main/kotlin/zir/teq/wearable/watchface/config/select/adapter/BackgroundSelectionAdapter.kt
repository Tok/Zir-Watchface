package zir.teq.wearable.watchface.config.select.adapter

import android.support.v7.widget.RecyclerView
import zir.teq.wearable.watchface.model.data.Background

class BackgroundSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: java.util.ArrayList<Background>) : android.support.v7.widget.RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int) =
            BackgroundViewHolder(android.view.LayoutInflater.from(parent.context).inflate(zir.teq.wearable.watchface.R.layout.list_item_background, parent, false))

    override fun onBindViewHolder(vh: android.support.v7.widget.RecyclerView.ViewHolder, position: Int) {
        val bg = mOptions[position]
        val colorViewHolder = vh as zir.teq.wearable.watchface.config.select.adapter.BackgroundSelectionAdapter.BackgroundViewHolder
        colorViewHolder.bindBackground(bg)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class BackgroundViewHolder(view: android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(view), android.view.View.OnClickListener {
        val mView = view as android.widget.LinearLayout
        val mCircle = view.findViewById(zir.teq.wearable.watchface.R.id.list_item_background_cirlce) as android.support.wearable.view.CircledImageView
        val mText = view.findViewById(zir.teq.wearable.watchface.R.id.list_item_background_text) as android.widget.TextView
        init {
            mView.setOnClickListener(this)
        }

        fun bindBackground(bg: zir.teq.wearable.watchface.model.data.Background) {
            val ctx = mView.context
            mCircle.setCircleColor(ctx.getColor(bg.id))
            mText.text = bg.name
        }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            val background: zir.teq.wearable.watchface.model.data.Background = mOptions[position]
            val activity = view.context as android.app.Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                val editor = zir.teq.wearable.watchface.model.ConfigData.prefs(view.context).edit()
                editor.putString(mPrefString, background.name)
                editor.commit()
                activity.setResult(android.app.Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}