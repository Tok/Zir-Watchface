package zir.teq.wearable.watchface.model.data.types

/**
 * Recreated by Zir on 16.06.2017.
 * Transpiled and rearranged from: https://github.com/Tok/Erwin/tree/master/src/main/java/erwin
 */
data class Complex(val re: Float, val im: Float = 0F) {
    private constructor(real: Int, imaginary: Int = 0) : this(real.toFloat(), imaginary.toFloat())

    val magnitude = Math.sqrt(addSquares(re, im))
    val magnitude2 = addSquares(re, im)
    val phase = Math.atan2(im.toDouble(), re.toDouble())
    val modulus = magnitude

    fun negate() = Complex(-re, -im)
    fun conjugate() = Complex(re, -im)
    fun reverse() = Complex(-re, im)
    operator fun not() = negate()
    operator fun plus(c: Complex) = Complex(re + c.re, im + c.im)
    operator fun plus(d: Float) = Complex(re + d, im)
    operator fun minus(c: Complex) = Complex(re - c.re, im - c.im)
    operator fun minus(d: Float) = Complex(re - d, im)
    operator fun times(c: Complex) = Complex(re * c.re - im * c.im, re * c.im + im * c.re)
    operator fun div(c: Complex): Complex {
        if (c.re == 0F) throw IllegalArgumentException("Real part is 0.")
        if (c.im == 0F) throw IllegalArgumentException("Imaginary part is 0.")
        val d = addSquares(c.re, c.im).toFloat()
        return Complex((re * c.re + im * c.im) / d, (im * c.re - re * c.im) / d)
    }

    private fun addSquares(re: Float, im: Float) = (re * re + im * im).toDouble()

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
        fun fromImaginary(imaginary: Float) = Complex(0F, imaginary)
        fun fromImaginaryInt(imaginary: Int) = Complex(0F, imaginary.toFloat())
        fun fromMagnitudeAndPhase(magnitude: Float, phase: Double) =
                Complex(magnitude * Math.cos(phase).toFloat(), magnitude * Math.sin(phase).toFloat())
    }
}
