import zir.watchface.DrawUtil

enum class Mass(val value: Float) {
    DEFAULT(1F),
    LIGHT(1F / DrawUtil.PHI),
    LIGHTER(1F / (DrawUtil.PHI * DrawUtil.PHI)),
    HEAVY(DrawUtil.PHI),
    HEAVIER(DrawUtil.PHI * DrawUtil.PHI);
}
