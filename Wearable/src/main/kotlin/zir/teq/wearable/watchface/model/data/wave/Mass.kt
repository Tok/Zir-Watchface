import zir.teq.wearable.watchface.util.CalcUtil
import zir.teq.wearable.watchface.util.CalcUtil.PHI

enum class Mass(val value: Float) {
    DEFAULT(1F),
    LIGHT(1F / PHI),
    LIGHTER(1F / (PHI * PHI)),
    HEAVY(CalcUtil.PHI),
    HEAVIER(PHI * PHI);
}
