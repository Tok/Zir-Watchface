package zir.teq.wearable.watchface.model.data.types

/**
 * Recreated by Zir on 16.06.2017.
 * Transpiled and rearranged from: https://github.com/Tok/Erwin/tree/master/src/main/java/erwin
 */
data class Complex(val re: Double, val im: Double = 0.0) {
    private constructor(real: Int, imaginary: Int = 0) : this(real.toDouble(), imaginary.toDouble())
    private constructor(real: Double) : this(real, 0.0)
    private constructor(real: Int) : this(real.toDouble(), 0.0)

    val magnitude = Math.sqrt(addSquares(re, im))
    val magnitude2 = addSquares(re, im)
    val phase = Math.atan2(im, re)
    val modulus = magnitude

    fun negate() = Complex(-re, -im)
    fun conjugate() = Complex(re, -im)
    fun reverse() = Complex(-re, im)
    fun add(c: Complex) = Complex(re + c.re, im + c.im)
    fun add(d: Double) = Complex(re + d, im)
    fun subtract(c: Complex) = Complex(re - c.re, im - c.im)
    fun subtract(d: Double) = Complex(re - d, im)
    fun multiply(c: Complex) = Complex(re * c.re - im * c.im, re * c.im + im * c.re)
    fun divide(c: Complex): Complex {
        if (c.re == 0.0) throw IllegalArgumentException("Real part is 0.")
        if (c.im == 0.0) throw IllegalArgumentException("Imaginary part is 0.")
        val d = addSquares(c.re, c.im)
        return Complex((re * c.re + im * c.im) / d, (im * c.re - re * c.im) / d)
    }

    private fun addSquares(re: Double, im: Double) = re * re + im * im

    override fun toString(): String {
        return when (this) {
            I -> "i"
            Complex(re) -> re.toString()
            Complex(im) -> im.toString() + "*i"
            else -> {
                val imString = if (im < 0.0) "-" + -im else "+" + im
                return re.toString() + imString + "*i"
            }
        }
    }

    companion object {
        val I = Complex(0.0, 1.0)
        val ZERO = Complex(0.0, 0.0)
        val ONE = Complex(1.0, 0.0)
        fun fromImaginary(imaginary: Double) = Complex(0.0, imaginary)
        fun fromImaginaryInt(imaginary: Int) = Complex(0.0, imaginary.toDouble())
        fun fromMagnitudeAndPhase(magnitude: Double, phase: Double) =
                Complex(magnitude * Math.cos(phase), magnitude * Math.sin(phase))
    }
}
