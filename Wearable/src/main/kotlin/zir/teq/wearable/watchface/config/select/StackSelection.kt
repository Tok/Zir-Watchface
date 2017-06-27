package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.CircledImageView
import android.support.wearable.view.WearableRecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.manager.ScalingLayoutManager
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.Stack
import zir.teq.wearable.watchface.util.ViewHelper


class StackViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.config_list_item) as Button
        view.setOnClickListener { super.handleClick(view, StackSelectionActivity.EXTRA) }
    }
}

class StackSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: StackSelectionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_stack)
        val sharedStackName = intent.getStringExtra(EXTRA)
        mAdapter = StackSelectionAdapter(sharedStackName, Stack.options())
        mConfigView = findViewById<View>(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val index = Stack.all.indexOfFirst { it.name.equals(ConfigData.stack.name) }
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_STACK"
    }
}

class StackSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Stack>) : RecAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            StackViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_stack, parent, false))

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val stack = mOptions[position]
        val stackViewHolder = vh as StackViewHolder
        stackViewHolder.bindStack(stack)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class StackViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mCircle = view.findViewById<View>(R.id.list_item_stack_circle) as CircledImageView
        val mText = view.findViewById<View>(R.id.list_item_stack_text) as TextView
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
