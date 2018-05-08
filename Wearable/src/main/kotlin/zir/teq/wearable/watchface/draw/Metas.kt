package zir.teq.wearable.watchface.draw

import android.graphics.*
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.frame.ActiveFrame
import zir.teq.wearable.watchface.model.data.frame.AmbientFrame
import zir.teq.wearable.watchface.model.data.meta.Meta
import zir.teq.wearable.watchface.model.data.meta.MetaCents
import zir.teq.wearable.watchface.model.data.meta.MetaRads
import zir.teq.wearable.watchface.model.setting.color.Palette
import zir.teq.wearable.watchface.model.setting.component.Component
import zir.teq.wearable.watchface.model.setting.style.StyleGrowth
import zir.teq.wearable.watchface.model.setting.style.StyleOutline
import zir.teq.wearable.watchface.model.setting.style.StyleStroke
import zir.teq.wearable.watchface.model.types.PaintType
import zir.teq.wearable.watchface.model.types.State.ACTIVE
import zir.teq.wearable.watchface.model.types.State.AMBIENT
import zir.teq.wearable.watchface.util.MetaBallUtil


object Metas {
    val isDrawCenterBlobs = false //TODO
    val blobSizeFactor = 3.0F

    fun drawActive(can: Canvas, frame: ActiveFrame) {
        if (ConfigData.isOn(Component.METAS to ACTIVE)) {
            val p = Palette.createPaint(PaintType.META, ConfigData.palette().dark())
            val r = calcRadius()
            val secondR = r * frame.secondMass
            val hourR = r * frame.hourMass
            val minuteR = r * frame.minuteMass
            if (isDrawCenterBlobs) {
                val centerR = r * frame.secondMass
                makeMeta(can, MetaRads(hourR, centerR), MetaCents(frame.hr, frame.center), p)
                makeMeta(can, MetaRads(minuteR, centerR), MetaCents(frame.min, frame.center), p)
                makeMeta(can, MetaRads(secondR, centerR), MetaCents(frame.sec, frame.center), p)
            }
            makeMeta(can, MetaRads(minuteR, hourR), MetaCents(frame.min, frame.hr), p)
            makeMeta(can, MetaRads(secondR, hourR), MetaCents(frame.sec, frame.hr), p)
            makeMeta(can, MetaRads(secondR, minuteR), MetaCents(frame.sec, frame.min), p)
        }
    }

    fun drawAmbient(can: Canvas, frame: AmbientFrame) {
        if (ConfigData.isOn(Component.METAS to AMBIENT)) {
            val p = Palette.createPaint(PaintType.META, ConfigData.palette().dark())
            val r = calcRadius()
            val hourR = r * frame.hourMass
            val minuteR = r * frame.minuteMass
            if (isDrawCenterBlobs) {
                val centerR = r * frame.secondMass
                makeMeta(can, MetaRads(hourR, centerR), MetaCents(frame.hr, frame.center), p)
                makeMeta(can, MetaRads(minuteR, centerR), MetaCents(frame.min, frame.center), p)
            }
            makeMeta(can, MetaRads(minuteR, hourR), MetaCents(frame.min, frame.hr), p)
        }
    }

    private fun calcRadius(): Float {
        val pointR = StyleStroke.load().value + StyleGrowth.load().value + StyleOutline.load().value
        return blobSizeFactor * pointR
    }

    private fun makeMeta(can: Canvas, rads: MetaRads, centers: MetaCents, p: Paint) {
        val meta = MetaBallUtil.calcMeta(centers, rads)
        makeMetaPoint(can, centers.c1, p, rads.r1)
        makeMetaPoint(can, centers.c2, p, rads.r2)
        drawMeta(can, meta, p)
    }

    private fun makeMetaPoint(can: Canvas, center: PointF, p: Paint, r: Float) {
        val rh = r / 2F
        with(center) {
            val arc = RectF(x - rh, y - rh, x + rh, y + rh)
            can.drawArc(arc, 0F, 360F, true, p)
        }
    }

    fun drawMeta(can: Canvas, meta: Meta, p: Paint) {
        can.save()
        p.setAntiAlias(true)

        p.style = Paint.Style.FILL_AND_STROKE
        can.clipPath(meta.leftPath(), Region.Op.DIFFERENCE)
        can.clipPath(meta.rightPath(), Region.Op.DIFFERENCE)
        /***********
         * FIXME Alternative for deprecated call with API 26+
         * can.clipOutPath(meta.leftPath())
         * can.clipOutPath(meta.rightPath())
         ***********/

        p.style = Paint.Style.FILL
        can.drawPath(meta.blobPath(), p)
        can.restore()

        /*** DEBUG ***/
        val isDebug = false
        if (isDebug) {
            p.style = Paint.Style.STROKE
            with(meta) {
                p.color = Color.RED
                can.drawLine(points.p1.x, points.p1.y, handles.h1.x, handles.h1.y, p)
                can.drawLine(points.p2.x, points.p2.y, handles.h2.x, handles.h2.y, p)
                can.drawLine(points.p3.x, points.p3.y, handles.h3.x, handles.h3.y, p)
                can.drawLine(points.p4.x, points.p4.y, handles.h4.x, handles.h4.y, p)
                p.color = Color.GREEN
                can.drawPoint(points.p1.x, points.p1.y, p)
                can.drawPoint(points.p2.x, points.p2.y, p)
                can.drawPoint(points.p3.x, points.p3.y, p)
                can.drawPoint(points.p4.x, points.p4.y, p)
            }
        }
    }
}
