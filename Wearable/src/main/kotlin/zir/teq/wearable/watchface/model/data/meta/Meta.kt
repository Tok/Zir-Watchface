package zir.teq.wearable.watchface.model.data.meta

import android.graphics.Path

data class Meta(val centers: MetaCents, val points: MetaPoints, val handles: MetaHandlePoints) {
    fun leftPath(): Path {
        val path = Path()
        path.moveTo(points.p1.x, points.p1.y)
        with(handles) { path.cubicTo(h1.x, h1.y, h3.x, h3.y, points.p3.x, points.p3.y) }
        path.isInverseFillType
        return path
    }

    fun rightPath(): Path {
        val path = Path()
        path.moveTo(points.p2.x, points.p2.y)
        with(handles) { path.cubicTo(h2.x, h2.y, h4.x, h4.y, points.p4.x, points.p4.y) }
        return path
    }

    fun blobPath(): Path {
        val path = Path()
        with(points) {
            path.moveTo(p1.x, p1.y)
            path.lineTo(p3.x, p3.y)
            path.lineTo(p4.x, p4.y)
            path.lineTo(p2.x, p2.y)
        }
        return path
    }
}
