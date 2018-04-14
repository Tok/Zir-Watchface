package zir.teq.wearable.watchface.watchface

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

internal class ZirHandler(service: ZirWatchFaceService.Engine) : Handler() {
    private val mService: WeakReference<ZirWatchFaceService.Engine>

    init {
        mService = WeakReference(service)
    }

    override fun handleMessage(msg: Message) {
        mService.get()?.handleMessage(msg)
    }
}
