package config.select.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.Outline

class OutlineSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Outline>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            OutlineViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_outline, parent, false))

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val outline = mOptions[position]
        val outlineViewHolder = vh as OutlineViewHolder
        outlineViewHolder.bindOutline(outline)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class OutlineViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mView: CircledImageView
        init {
            mView = view.findViewById<View>(R.id.list_item_outline) as CircledImageView
            view.setOnClickListener(this)
        }

        fun bindOutline(outline: Outline) {
            val oDim = Math.max(1F, outline.dim)
            mView.setCircleBorderWidth(oDim)
            mView.setCircleColor(ConfigData.palette.half())
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val outline: Outline = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                updateSavedValue(outline)
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }

    fun updateSavedValue(outline: Outline) {
        ConfigData.outline = outline
        val editor = ConfigData.prefs.edit()
        editor.putString(mPrefString, outline.name)
        editor.commit()
    }
}