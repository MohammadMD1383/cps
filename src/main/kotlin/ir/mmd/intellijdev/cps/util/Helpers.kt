package ir.mmd.intellijdev.cps.util

import java.io.File

operator fun String?.plus(string: String) = if (this == null) null else this + string

operator fun String.invoke(directory: File) = ProcessBuilder(split(' ')).directory(directory).start()!!
