package zir.teq.wearable.watchface.draw.complex

/**
 * Recreated by Zir on 16.06.2017.
 * Transpiled and rearranged from: https://github.com/Tok/Erwin/tree/master/src/main/java/erwin
 */
data class Complex(val re: Double, val im: Double = 0.0) {
    private constructor(real: Int, imaginary: Int) : this(real.toDouble(), imaginary.toDouble())
    private constructor(real: Int) : this(real.toDouble(), 0.0)

    val magnitude = Math.sqrt(addSquares(re, im))
    val magnitude2 = addSquares(re, im)
    val phase = Math.atan2(im, re)
    val modulus = magnitude

    fun negate() = valueOf(-re, -im)
    fun conjugate() = valueOf(re, -im)
    fun reverse() = valueOf(-re, im)
    fun add(c: Complex) = valueOf(re + c.re, im + c.im)
    fun add(d: Double) = valueOf(re + d, im)
    fun subtract(c: Complex) = valueOf(re - c.re, im - c.im)
    fun subtract(d: Double) = valueOf(re - d, im)
    fun multiply(c: Complex) = valueOf(re * c.re - im * c.im, re * c.im + im * c.re)
    fun divide(c: Complex): Complex {
        if (c.re == 0.0) throw IllegalArgumentException("Real part is 0.")
        if (c.im == 0.0) throw IllegalArgumentException("Imaginary part is 0.")
        val d = addSquares(c.re, c.im)
        return valueOf((re * c.re + im * c.im) / d, (im * c.re - re * c.im) / d)
    }

    private fun addSquares(re: Double, im: Double) = re * re + im * im

    override fun toString(): String {
        return when (this) {
            I -> "i"
            valueOf(re) -> re.toString()
            valueOf(im) -> im.toString() + "*i"
            else -> re.toString() + (if (im < 0.0) "-" + -im else "+" + im) + "*i"
        }
    }

    companion object {
        val I = valueOf(0.0, 1.0)
        val ZERO = valueOf(0.0, 0.0)
        val ONE = valueOf(1.0, 0.0)
        fun valueOf(real: Double, imaginary: Double) = Complex(real, imaginary)
        fun valueOf(real: Double) = valueOf(real, 0.0)
        fun valueOfIm(imaginary: Double) = valueOf(0.0, imaginary)
        fun fromMagAndPhase(magnitude: Double, phase: Double) =
                valueOf(magnitude * Math.cos(phase), magnitude * Math.sin(phase))
    }
}
