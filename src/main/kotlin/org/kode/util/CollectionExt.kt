package org.kode.util

operator fun <T> Collection<T>.get(index: Int) = this.elementAt(index)

fun <T> Collection<T>.hasDuplicates(): Boolean = this.size != this.distinct().size
