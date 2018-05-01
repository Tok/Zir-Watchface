package zir.teq.wearable.watchface.config.select.wave.activity

import android.app.Activity
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.RecAdapter


abstract class WavePropsActivity : Activity() {
    lateinit var view: WearableRecyclerView
    lateinit var adapter: RecAdapter
    lateinit var manager: WearableLinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        view = findViewById(R.id.zir_list_view)
    }
}
