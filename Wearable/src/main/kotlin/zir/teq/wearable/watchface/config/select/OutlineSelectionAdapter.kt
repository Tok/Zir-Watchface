package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Outline

class OutlineSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Outline>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        android.util.Log.d(TAG, "onCreateViewHolder(): viewType: " + viewType)
        val viewHolder = OutlineViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_outline, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        android.util.Log.d(TAG, "onBindViewHolder() Element $position set.")
        //val outline = mOptions[position]
        //val outlineViewHolder = viewHolder as OutlineSelectionAdapter.OutlineViewHolder
        //outlineViewHolder.setOutline(outline) //TODO set outline
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class OutlineViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val mView: CircledImageView
        init {
            mView = view.findViewById(R.id.outline) as CircledImageView
            view.setOnClickListener(this)
        }

        /*
        fun setOutline(outlineWidth: Float) {
            mView.setImageResource(R.)
        }*/

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            val outline: Outline = mOptions[position]
            Log.d(TAG, "Outline: $outline onClick() position: $position sharedPrefString: $mPrefString")
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                updateSavedValue(view.context, outline)
                activity.setResult(android.app.Activity.RESULT_OK) //triggers config activity
            }
            activity.finish()
        }
    }

    fun updateSavedValue(ctx: Context, outline: Outline) {
        val editor = ConfigData.prefs(ctx).edit()
        editor.putString(mPrefString, outline.name)
        editor.commit()
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}