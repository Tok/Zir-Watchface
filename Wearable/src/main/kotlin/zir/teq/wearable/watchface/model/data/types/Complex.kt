package zir.teq.wearable.watchface.model.data.types

/**
 * Recreated by Zir on 16.06.2017.
 * Transpiled and rearranged from: https://github.com/Tok/Erwin/tree/master/src/main/java/erwin
 */
data class Complex(val re: Float, val im: Float = 0F) {
    constructor(real: Int, imaginary: Int = 0) : this(real.toFloat(), imaginary.toFloat())

    val magnitude: Float = Math.sqrt(addSquares(re, im).toDouble()).toFloat()
    val mag: Float = magnitude
    val phase = Math.atan2(im.toDouble(), re.toDouble()).toFloat()

    fun negate() = Complex(-re, -im)
    operator fun not() = negate()

    fun conjugate() = Complex(re, -im)
    fun reverse() = Complex(-re, im)

    operator fun plus(c: Complex) = Complex(re + c.re, im + c.im)
    operator fun plus(d: Float) = Complex(re + d, im)

    operator fun minus(c: Complex) = Complex(re - c.re, im - c.im)
    operator fun minus(d: Float) = Complex(re - d, im)

    operator fun times(c: Complex) = Complex(re * c.re - im * c.im, re * c.im + im * c.re)

    operator fun div(c: Complex): Complex {
        if (c.re == 0F) throw IllegalArgumentException("Real part is 0.")
        if (c.im == 0F) throw IllegalArgumentException("Imaginary part is 0.")
        val d = addSquares(c.re, c.im)
        return Complex((re * c.re + im * c.im) / d, (im * c.re - re * c.im) / d)
    }

    private fun addSquares(re: Float, im: Float): Float = (re * re) + (im * im)

    override fun toString(): String {
        return when (this) {
            I -> "i"
            Complex(re) -> re.toString()
            Complex(im) -> im.toString() + "*i"
            else -> {
                val imString = if (im < 0F) "-" + -im else "+" + im
                return re.toString() + imString + "*i"
            }
        }
    }

    companion object {
        val I = Complex(0F, 1F)
        val ZERO = Complex(0F, 0F)
        val ONE = Complex(1F, 0F)

        fun selectStronger(first: Complex, second: Complex) = if (first.mag < second.mag) first else second

        fun valueOf(magnitude: Float, phase: Float) = fromMagnitudeAndPhase(magnitude, phase)
        fun fromImaginary(imaginary: Float) = Complex(0F, imaginary)
        fun fromImaginaryInt(imaginary: Int) = Complex(0F, imaginary.toFloat())
        fun fromMagnitudeAndPhase(magnitude: Float, phase: Float) =
                Complex(magnitude * Math.cos(phase.toDouble()).toFloat(),
                        magnitude * Math.sin(phase.toDouble()).toFloat())

        fun addAll(list: List<Complex>) = list.fold(Complex.ZERO) { total, next -> total + next }
        fun multiplyAll(list: List<Complex>) = list.fold(Complex.ONE) { total, next -> total * next }
    }
}
