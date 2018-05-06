package zir.teq.wearable.watchface.util

import android.graphics.PointF
import zir.teq.wearable.watchface.model.data.meta.*
import zir.teq.wearable.watchface.util.CalcUtil.HALF_PI

//See http://varun.ca/metaballs/
object MetaBallUtil {
    fun calcVector(center: PointF, angle: Float, r: Float): PointF {
        val x = r * Math.cos(angle.toDouble()).toFloat() / 2
        val y = r * Math.sin(angle.toDouble()).toFloat() / 2
        return PointF(center.x + x, center.y + y)
    }

    fun calcMeta(centers: MetaCents, rads: MetaRads): Meta {
        val angleBetweenCenters = CalcUtil.calcAngle(centers.c1, centers.c2)
        val distance = CalcUtil.calcDistance(centers.c1, centers.c2)
        val maxSpread = Math.acos((rads.r1 - rads.r2) / distance.toDouble()).toFloat()
        val angles = calcMetaAngles(angleBetweenCenters, maxSpread)
        val points = calcMetaPoints(centers, angles, rads)
        val handles = calcMetaHandlePoints(points, angles, rads)
        return Meta(points, handles)
    }

    val v = 0.5F //TODO tune
    val sizeFactor = 3F //TODO tune
    fun calcMetaAngles(angleBetweenCenters: Float, maxSpread: Float): MetaAngles {
        val a1 = angleBetweenCenters + maxSpread * v
        val a2 = angleBetweenCenters - maxSpread * v
        val a3 = angleBetweenCenters + (Math.PI - (Math.PI - maxSpread) * v).toFloat()
        val a4 = angleBetweenCenters - (Math.PI - (Math.PI - maxSpread) * v).toFloat()
        return MetaAngles(a1, a2, a3, a4)
    }

    fun calcMetaPoints(centers: MetaCents, angles: MetaAngles, rads: MetaRads): MetaPoints {
        val p1 = calcVector(centers.c1, angles.a1, rads.r1)
        val p2 = calcVector(centers.c1, angles.a2, rads.r1)
        val p3 = calcVector(centers.c2, angles.a3, rads.r2)
        val p4 = calcVector(centers.c2, angles.a4, rads.r2)
        return MetaPoints(p1, p2, p3, p4)
    }

    fun calcMetaHandlePoints(points: MetaPoints, angles: MetaAngles, rads: MetaRads): MetaHandlePoints {
        val handleScale = rads.total * 100F //TODO tune (viscosity)
        val d2 = Math.min(v * handleScale, CalcUtil.calcDistance(points.p1, points.p3) / rads.total)
        val weightedRads = MetaRads(rads.r1 * d2, rads.r2 * d2)
        val h1 = calcVector(points.p1, angles.a1 - HALF_PI, weightedRads.r1)
        val h2 = calcVector(points.p2, angles.a2 + HALF_PI, weightedRads.r1)
        val h3 = calcVector(points.p3, angles.a3 + HALF_PI, weightedRads.r2)
        val h4 = calcVector(points.p4, angles.a4 - HALF_PI, weightedRads.r2)
        return MetaHandlePoints(h1, h2, h3, h4)
    }
}
