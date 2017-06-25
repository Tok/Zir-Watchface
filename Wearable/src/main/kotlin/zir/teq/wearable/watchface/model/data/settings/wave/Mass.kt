import zir.watchface.DrawUtil

data class Mass(val name: String, val value: Double) {
    companion object {
        val DEFAULT = Mass("Default", 1.0)
        val LIGHT = Mass("Light", 1.0 / DrawUtil.PHI.toDouble())
        val LIGHTER = Mass("Lighter", 1.0 / (DrawUtil.PHI.toDouble() * DrawUtil.PHI.toDouble()))
        val HEAVY = Mass("Heavy", DrawUtil.PHI.toDouble())
        val HEAVIER = Mass("Heavier", DrawUtil.PHI.toDouble() * DrawUtil.PHI.toDouble())
    }
}
