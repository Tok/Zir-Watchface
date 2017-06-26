import zir.watchface.DrawUtil

enum class Mass(val value: Double) {
    DEFAULT(1.0),
    LIGHT(1.0 / DrawUtil.PHI.toDouble()),
    LIGHTER(1.0 / (DrawUtil.PHI.toDouble() * DrawUtil.PHI.toDouble())),
    HEAVY(DrawUtil.PHI.toDouble()),
    HEAVIER(DrawUtil.PHI.toDouble() * DrawUtil.PHI.toDouble());
}
