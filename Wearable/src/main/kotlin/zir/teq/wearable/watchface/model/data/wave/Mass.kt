import zir.teq.wearable.watchface.util.CalcUtil.PHI

enum class Mass(val value: Float) {
    LIGHTEST(1F / (PHI * PHI * PHI)),
    LIGHTER(1F / (PHI * PHI)),
    LIGHT(1F / PHI),
    DEFAULT(1F),
    HEAVY(PHI),
    HEAVIER(PHI * PHI),
    HEAVIEST(PHI * PHI * PHI);
}
