package zir.teq.wearable.watchface.model.data.meta

//radius pairs for meta balls
data class MetaRads(val r1: Float, val r2: Float) {
    val total = r1 + r2
    val r1sq = r1 * r1
    val r2sq = r2 * r2
}
