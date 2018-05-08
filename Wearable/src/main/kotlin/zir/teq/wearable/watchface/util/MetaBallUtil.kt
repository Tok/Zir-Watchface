package zir.teq.wearable.watchface.util

import android.graphics.PointF
import zir.teq.wearable.watchface.model.data.meta.*
import zir.teq.wearable.watchface.util.CalcUtil.HALF_PI
import kotlin.math.PI

//See http://varun.ca/metaballs/
object MetaBallUtil {
    val v = 0.5F //TODO tune
    val handleSizeFactor = 1F //TODO tune

    fun calcMeta(centers: MetaCents, rads: MetaRads): Meta {
        val angleBetweenCenters = CalcUtil.calcAngle(centers.c1, centers.c2)
        val distance = CalcUtil.calcDistance(centers.c1, centers.c2)
        val maxSpread = Math.acos((rads.r1 - rads.r2) / distance.toDouble()).toFloat()
        val angles = calcMetaAngles(angleBetweenCenters, maxSpread, distance, rads)
        val points = calcMetaPoints(centers, angles, rads)
        val handles = calcMetaHandlePoints(points, angles, distance, rads)
        return Meta(centers, points, handles)
    }

    fun calcMetaAngles(angleBetweenCenters: Float, maxSpread: Float, d: Float, rads: MetaRads): MetaAngles {
        val isCalcIn = true //FIXME
        if (isCalcIn) {
            val dSq = d * d
            val isOverlap = d < rads.r1 + rads.r1
            val u1 = if (isOverlap) Math.acos((rads.r1sq + dSq - rads.r2sq) / (2.0 * rads.r1 * d)).toFloat() else 0F
            val u2 = if (isOverlap) Math.acos((rads.r2sq + dSq - rads.r1sq) / (2.0 * rads.r2 * d)).toFloat() else 0F
            val a1 = angleBetweenCenters + u1 + (maxSpread - u1) * v
            val a2 = angleBetweenCenters - u1 - (maxSpread - u1) * v
            val a3 = angleBetweenCenters + PI - u2 - (PI - u2 - maxSpread) * v
            val a4 = angleBetweenCenters - PI + u2 + (PI - u2 - maxSpread) * v
            return MetaAngles(a1, a2, a3.toFloat(), a4.toFloat())
        } else {
            val firstOffset = maxSpread * v
            val a1 = angleBetweenCenters + firstOffset
            val a2 = angleBetweenCenters - firstOffset
            val secondOffset = (PI - (PI - maxSpread) * v).toFloat()
            val a3 = angleBetweenCenters + secondOffset
            val a4 = angleBetweenCenters - secondOffset
            return MetaAngles(a1, a2, a3, a4)
        }
    }

    fun calcMetaPoints(centers: MetaCents, angles: MetaAngles, rads: MetaRads): MetaPoints {
        val p1 = calcVector(centers.c1, angles.a1, rads.r1)
        val p2 = calcVector(centers.c1, angles.a2, rads.r1)
        val p3 = calcVector(centers.c2, angles.a3, rads.r2)
        val p4 = calcVector(centers.c2, angles.a4, rads.r2)
        return MetaPoints(p1, p2, p3, p4)
    }

    fun calcMetaHandlePoints(points: MetaPoints, angles: MetaAngles, d: Float, rads: MetaRads): MetaHandlePoints {
        val dist = CalcUtil.calcDistance(points.p1, points.p3)
        val d2Base = Math.min(v * handleSizeFactor, dist / rads.total)
        val d2 = d2Base * Math.min(1.0, d * 2.0 / (rads.total)).toFloat()
        val weightedRads = MetaRads(rads.r1 * d2, rads.r2 * d2)
        val h1 = calcVector(points.p1, angles.a1 - HALF_PI, weightedRads.r1)
        val h2 = calcVector(points.p2, angles.a2 + HALF_PI, weightedRads.r1)
        val h3 = calcVector(points.p3, angles.a3 + HALF_PI, weightedRads.r2)
        val h4 = calcVector(points.p4, angles.a4 - HALF_PI, weightedRads.r2)
        return MetaHandlePoints(h1, h2, h3, h4)
    }

    fun calcVector(center: PointF, angle: Float, r: Float): PointF {
        val x = r * Math.cos(angle.toDouble()).toFloat() / 2F
        val y = r * Math.sin(angle.toDouble()).toFloat() / 2F
        return PointF(center.x + x, center.y + y)
    }
}
