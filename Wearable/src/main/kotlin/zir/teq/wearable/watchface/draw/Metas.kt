package zir.teq.wearable.watchface.draw

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.frame.ActiveFrame
import zir.teq.wearable.watchface.model.data.frame.AmbientFrame
import zir.teq.wearable.watchface.model.data.meta.MetaCents
import zir.teq.wearable.watchface.model.data.meta.MetaRads
import zir.teq.wearable.watchface.model.setting.color.Palette
import zir.teq.wearable.watchface.model.setting.component.Component
import zir.teq.wearable.watchface.model.setting.style.StyleOutline
import zir.teq.wearable.watchface.model.types.PaintType
import zir.teq.wearable.watchface.model.types.State.ACTIVE
import zir.teq.wearable.watchface.model.types.State.AMBIENT
import zir.teq.wearable.watchface.util.DrawUtil
import zir.teq.wearable.watchface.util.MetaBallUtil


object Metas {
    val isDrawCenterBlobs = false //TODO implement
    fun drawActive(can: Canvas, frame: ActiveFrame) {
        if (ConfigData.isOn(Component.METAS to ACTIVE)) {
            val p = Palette.createPaint(PaintType.META, Zir.color(R.color.white))
            val r = (p.strokeWidth * MetaBallUtil.sizeFactor) + StyleOutline.load().value
            val secondR = r * frame.secondMass
            val hourR = r * frame.hourMass
            val minuteR = r * frame.minuteMass
            if (isDrawCenterBlobs) {
                val centerR = r * frame.secondMass
                makeMeta(can, MetaRads(secondR, centerR), MetaCents(frame.sec, frame.center), p)
                makeMeta(can, MetaRads(minuteR, centerR), MetaCents(frame.min, frame.center), p)
                makeMeta(can, MetaRads(hourR, centerR), MetaCents(frame.hr, frame.center), p)
            }
            makeMeta(can, MetaRads(secondR, hourR), MetaCents(frame.sec, frame.hr), p)
            makeMeta(can, MetaRads(secondR, minuteR), MetaCents(frame.sec, frame.min), p)
            makeMeta(can, MetaRads(minuteR, hourR), MetaCents(frame.min, frame.hr), p)
        }
    }

    fun drawAmbient(can: Canvas, frame: AmbientFrame) {
        if (ConfigData.isOn(Component.METAS to AMBIENT)) {
            val p = Palette.createPaint(PaintType.META, Zir.color(R.color.white))
            val r = (p.strokeWidth * MetaBallUtil.sizeFactor) + StyleOutline.load().value
            val hourR = r * frame.hourMass
            val minuteR = r * frame.minuteMass
            if (isDrawCenterBlobs) {
                val centerR = r * frame.secondMass
                makeMeta(can, MetaRads(minuteR, centerR), MetaCents(frame.min, frame.center), p)
                makeMeta(can, MetaRads(hourR, centerR), MetaCents(frame.hr, frame.center), p)
            }
            makeMeta(can, MetaRads(minuteR, hourR), MetaCents(frame.min, frame.hr), p)
        }
    }

    private fun makeMeta(can: Canvas, rads: MetaRads, centers: MetaCents, p: Paint) {
        makeMetaPoint(can, centers.c1, p, rads.r1)
        makeMetaPoint(can, centers.c2, p, rads.r2)
        val meta = MetaBallUtil.calcMeta(centers, rads)
        DrawUtil.drawMeta(can, meta, p)
    }

    private fun makeMetaPoint(can: Canvas, point: PointF, p: Paint, r: Float) {
        p.strokeWidth = r
        p.color = Zir.color(R.color.white)
        can.saveLayer(0F, 0F, can.width.toFloat(), can.height.toFloat(), p)
        can.drawPoint(point.x, point.y, p)
    }
}
