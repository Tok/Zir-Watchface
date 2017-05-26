package zir.teq.wearable.watchface.model

import android.content.Context
import zir.teq.wearable.watchface.R

data class Col(val name: String, val darkId: Int, val lightId: Int) {
    companion object {
        val WHITE = zir.teq.wearable.watchface.model.Col("White", R.color.white_dark, R.color.white_light)
        val RED = zir.teq.wearable.watchface.model.Col("Red", R.color.red_dark, R.color.red_light)
        val ORANGE = zir.teq.wearable.watchface.model.Col("Orange", R.color.orange_dark, R.color.orange)
        val YELLOW = zir.teq.wearable.watchface.model.Col("Yellow", R.color.yellow_dark, R.color.yellow_light)
        val GREEN = zir.teq.wearable.watchface.model.Col("Green", R.color.green_dark, R.color.green_light)
        val BLUE = zir.teq.wearable.watchface.model.Col("Blue", R.color.blue_dark, R.color.blue_light)
        val PURPLE = zir.teq.wearable.watchface.model.Col("Purple", R.color.purple_dark, R.color.purple_light)
        val PINK = zir.teq.wearable.watchface.model.Col("Pink", R.color.pink_dark, R.color.pink_light)
        val defaultColor = zir.teq.wearable.watchface.model.Col.Companion.WHITE
        val ALL_COLORS = listOf(zir.teq.wearable.watchface.model.Col.Companion.WHITE, zir.teq.wearable.watchface.model.Col.Companion.RED, zir.teq.wearable.watchface.model.Col.Companion.ORANGE, zir.teq.wearable.watchface.model.Col.Companion.YELLOW, zir.teq.wearable.watchface.model.Col.Companion.GREEN, zir.teq.wearable.watchface.model.Col.Companion.BLUE, zir.teq.wearable.watchface.model.Col.Companion.PURPLE, zir.teq.wearable.watchface.model.Col.Companion.PINK)
        fun getColorOptions() = zir.teq.wearable.watchface.model.Col.Companion.ALL_COLORS.toCollection(ArrayList())
        fun getColorByName(name: String): zir.teq.wearable.watchface.model.Col = zir.teq.wearable.watchface.model.Col.Companion.ALL_COLORS.find { it.name.equals(name) } ?: zir.teq.wearable.watchface.model.Col.Companion.defaultColor
        fun prep(color: Int): android.graphics.Paint {
            val paint = zir.teq.wearable.watchface.model.Col.Companion.prep()
            paint.color = color
            return paint
        }

        fun createPaint(ctx: Context, type: zir.watchface.Config.PaintType, col: zir.teq.wearable.watchface.model.Col, stroke: Stroke): android.graphics.Paint {
            val paint = when (type) {
                zir.watchface.Config.PaintType.HAND -> zir.teq.wearable.watchface.model.Col.Companion.prepareLinePaint(ctx, col.lightId)
                zir.watchface.Config.PaintType.HAND_AMB -> zir.teq.wearable.watchface.model.Col.Companion.prepareLinePaint(ctx, col.darkId)
                zir.watchface.Config.PaintType.SHAPE -> zir.teq.wearable.watchface.model.Col.Companion.prepareShapePaint(ctx, col.lightId)
                zir.watchface.Config.PaintType.SHAPE_AMB -> zir.teq.wearable.watchface.model.Col.Companion.prepareShapePaint(ctx, col.darkId)
                zir.watchface.Config.PaintType.CIRCLE -> zir.teq.wearable.watchface.model.Col.Companion.prepareCirclePaint(ctx, col.darkId)
                zir.watchface.Config.PaintType.CIRCLE_AMB -> zir.teq.wearable.watchface.model.Col.Companion.prepareCirclePaint(ctx, col.lightId)
                zir.watchface.Config.PaintType.TEXT -> zir.teq.wearable.watchface.model.Col.Companion.prepareTextPaint(ctx, R.color.text)
                zir.watchface.Config.PaintType.POINT -> zir.teq.wearable.watchface.model.Col.Companion.preparePointPaint(ctx, R.color.points)
                else -> {
                    val msg = "Ignoring paintType: " + type
                    throw IllegalArgumentException(msg)
                }
            }
            paint.strokeWidth = stroke.dim
            return paint
        }

        fun prep(): android.graphics.Paint {
            val paint = android.graphics.Paint()
            paint.isAntiAlias = true
            return paint
        }

        fun prepareLinePaint(ctx: Context, colorId: Int): android.graphics.Paint {
            val paint = zir.teq.wearable.watchface.model.Col.Companion.prep()
            paint.strokeCap = android.graphics.Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }

        fun prepareShapePaint(ctx: Context, colorId: Int): android.graphics.Paint {
            val paint = zir.teq.wearable.watchface.model.Col.Companion.prep()
            paint.strokeCap = android.graphics.Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }

        fun prepareCirclePaint(ctx: Context, colorId: Int): android.graphics.Paint {
            val paint = zir.teq.wearable.watchface.model.Col.Companion.prep()
            paint.style = android.graphics.Paint.Style.STROKE
            paint.strokeCap = android.graphics.Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }

        fun prepareTextPaint(ctx: Context, colorId: Int): android.graphics.Paint {
            val paint = zir.teq.wearable.watchface.model.Col.Companion.prep()
            paint.typeface = zir.watchface.Config.Companion.NORMAL_TYPEFACE
            paint.isFakeBoldText = true
            paint.color = ctx.getColor(colorId)
            return paint
        }

        fun preparePointPaint(ctx: Context, colorId: Int): android.graphics.Paint {
            val paint = zir.teq.wearable.watchface.model.Col.Companion.prep()
            paint.style = android.graphics.Paint.Style.STROKE
            paint.strokeCap = android.graphics.Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }
    }
}
