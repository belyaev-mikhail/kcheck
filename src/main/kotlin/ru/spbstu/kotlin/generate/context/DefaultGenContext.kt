package ru.spbstu.kotlin.generate.context

import ru.spbstu.kotlin.generate.cases.*
import ru.spbstu.kotlin.generate.combinators.Gen
import ru.spbstu.kotlin.generate.combinators.gen

open class DefaultGenContext : GenContext() {
    fun defaultForInt() = priorities(
            1 to constant(0),
            1 to constant(1),
            1 to constant(Int.MAX_VALUE),
            1 to constant(Int.MIN_VALUE),
            3 to anyInt()
    )

    fun defaultForLong() = priorities(
            1 to constant(0L),
            1 to constant(1L),
            1 to constant(Long.MAX_VALUE),
            1 to constant(Long.MIN_VALUE),
            3 to anyLong()
    )

    fun defaultForShort() = priorities(
            1 to constant(0.toShort()),
            1 to constant(1.toShort()),
            1 to constant(Short.MAX_VALUE),
            1 to constant(Short.MIN_VALUE),
            3 to anyShort()
    )

    fun defaultForByte() = priorities(
            1 to constant(0.toByte()),
            1 to constant(1.toByte()),
            1 to constant(Byte.MAX_VALUE),
            1 to constant(Byte.MIN_VALUE),
            3 to anyByte()
    )

    fun defaultForBoolean() = anyBool()

    fun defaultForFloat() = priorities(
            1 to constant(0.0f),
            1 to constant(Float.MAX_VALUE),
            1 to constant(-Float.MAX_VALUE),
            1 to constant(Float.NEGATIVE_INFINITY),
            1 to constant(Float.POSITIVE_INFINITY),
            1 to constant(Float.NaN),
            1 to constant(Math.ulp(0.0f)),
            1 to constant(Math.ulp(1.0f)),
            1 to constant(-Math.ulp(0.0f)),
            1 to constant(-Math.ulp(1.0f)),
            10 to anyFloat()
    )

    fun defaultForDouble() = priorities(
            1 to constant(0.0),
            1 to constant(Double.MAX_VALUE),
            1 to constant(-Double.MAX_VALUE),
            1 to constant(Double.NEGATIVE_INFINITY),
            1 to constant(Double.POSITIVE_INFINITY),
            1 to constant(Double.NaN),
            1 to constant(Math.ulp(0.0)),
            1 to constant(Math.ulp(1.0)),
            1 to constant(-Math.ulp(0.0)),
            1 to constant(-Math.ulp(1.0)),
            10 to anyDouble()
    )

    fun defaultForNumber() =
            priorities(
                    1 to defaultForInt(),
                    1 to defaultForLong(),
                    1 to defaultForShort(),
                    1 to defaultForDouble(),
                    1 to defaultForFloat()
            )

    fun defaultForChar() = priorities(
            2 to anyCommonChar(),
            1 to anyChar()
    )

    fun defaultForString() = priorities(
            2 to anyReadableString(),
            1 to anyString()
    )

    fun <T> defaultForNullable(pure: Gen<T>) = anyNullable(pure)

    fun <T> defaultForList(elGen: Gen<T>): Gen<List<T>> =
            anyList(elGen)
    fun <T> defaultForSet(elGen: Gen<T>): Gen<Set<T>> =
            anySet(elGen)
    fun <K, V> defaultForMap(kGen: Gen<K>, vGen: Gen<V>): Gen<Map<K, V>> =
            anyMap(kGen, vGen)
    fun <A, B> defaultForPair(aGen: Gen<A>, bGen: Gen<B>): Gen<Pair<A, B>> =
            anyPair(aGen, bGen)
    fun <A, B, C> defaultForTriple(aGen: Gen<A>, bGen: Gen<B>, cGen: Gen<C>): Gen<Triple<A, B, C>> =
            anyTriple(aGen, bGen, cGen)


    init {
        install { -> defaultForBoolean() }
        install { -> defaultForDouble() }
        install { -> defaultForFloat() }
        install { -> defaultForByte() }
        install { -> defaultForShort() }
        install { -> defaultForInt() }
        install { -> defaultForLong() }
        install { -> defaultForChar() }
        install { -> defaultForString() }

        install { -> defaultForNumber() }

        install { arg -> defaultForList(arg) }
        install { arg -> defaultForSet(arg) }
        install { arg1, arg2 -> defaultForMap(arg1, arg2) }

        install { arg1, arg2 -> defaultForPair(arg1, arg2) }
        install { arg1, arg2, arg3 -> defaultForTriple(arg1, arg2, arg3) }

        install { -> defaultForString() as Gen<CharSequence> }
        install { arg -> defaultForList(arg) as Gen<Collection<*>> }
        install { arg -> defaultForList(arg) as Gen<Iterable<*>> }
    }
}