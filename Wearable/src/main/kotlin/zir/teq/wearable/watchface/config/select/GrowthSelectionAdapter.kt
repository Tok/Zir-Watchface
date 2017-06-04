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
import zir.teq.wearable.watchface.model.data.Growth

class GrowthSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Growth>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        android.util.Log.d(TAG, "onCreateViewHolder(): viewType: " + viewType)
        val viewHolder = GrowthViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_growth, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        android.util.Log.d(TAG, "onBindViewHolder() Element $position set.")
        //val growth = mOptions[position]
        //val growthViewHolder = viewHolder as GrowthSelectionAdapter.GrowthViewHolder
        //growthViewHolder.setIcon(growth)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class GrowthViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val mView: CircledImageView
        init {
            mView = view.findViewById(R.id.growth) as CircledImageView
            view.setOnClickListener(this)
        }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            val growth: Growth = mOptions[position]
            Log.d(TAG, "growth: $growth onClick() position: $position sharedPrefString: $mPrefString")
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                updateSavedValue(view.context, growth)
                activity.setResult(android.app.Activity.RESULT_OK) //triggers config activity
            }
            activity.finish()
        }
    }

    fun updateSavedValue(ctx: Context, growth: Growth) {
        val editor = ConfigData.prefs(ctx).edit()
        editor.putString(mPrefString, growth.name)
        editor.commit()
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}