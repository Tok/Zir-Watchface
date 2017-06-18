package config.select.adapter

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
import zir.teq.wearable.watchface.model.data.settings.Stack

class StackSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Stack>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            StackViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_stack, parent, false))

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val stack = mOptions[position]
        val stackViewHolder = vh as StackViewHolder
        stackViewHolder.bindStack(stack)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class StackViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle = view.findViewById(R.id.list_item_stack_circle) as CircledImageView
        val mText = view.findViewById(R.id.list_item_stack_text) as TextView
        init {
            mView.setOnClickListener(this)
        }

        fun bindStack(stack: Stack) {
            mCircle.setImageResource(stack.iconId)
            mText.text = stack.name
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val stack: Stack = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                ConfigData.stack = stack
                val editor = ConfigData.prefs.edit()
                editor.putString(mPrefString, stack.name)
                editor.commit()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}