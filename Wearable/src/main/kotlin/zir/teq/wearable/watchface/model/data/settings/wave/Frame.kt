package zir.teq.wearable.watchface.model.data.settings.wave

import android.graphics.Point
import zir.teq.wearable.watchface.model.data.types.Complex

typealias Key = Point
class Frame(val map: MutableMap<Key, Complex> = mutableMapOf()) {
    fun get(k: Key) = map.get(k)
    fun put(k: Key, v: Complex) = map.put(k, v)
}
