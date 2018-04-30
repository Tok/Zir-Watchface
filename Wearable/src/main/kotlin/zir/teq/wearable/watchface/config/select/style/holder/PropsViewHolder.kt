package zir.teq.wearable.watchface.config.select.style.holder

import android.view.View
import android.widget.Button
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.holder.RecSelectionViewHolder


class PropsViewHolder(view: View, val extra: String, val req: Int) :
        RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.list_item_main) as Button
        view.setOnClickListener { super.handleClick(view, extra, req) }
    }
}
