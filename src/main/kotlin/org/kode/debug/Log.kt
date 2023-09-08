package org.kode.debug

import org.kode.util.Time.timestamp

object Log {

    var isDebugMode: Boolean = true
    var logLevel: Level = Level.TRACE

    fun log(level: Level, msg: Any?, caller: String = "Kode", linebreak: Boolean = true) {
        if (level == Level.DEBUG && !isDebugMode ||
            level > logLevel) return

        print("${level.color}[$timestamp][$level][$caller] $msg${if (linebreak) "\n" else ""}${ConsoleColor.RESET}")
    }

    fun fatal(msg: Any?, caller: String = "Kode", linebreak: Boolean = true) = log(Level.FATAL, msg, caller, linebreak)
    fun error(msg: Any?, caller: String = "Kode", linebreak: Boolean = true) = log(Level.ERROR, msg, caller, linebreak)
    fun warn(msg: Any?, caller: String = "Kode", linebreak: Boolean = true) = log(Level.WARN, msg, caller, linebreak)
    fun info(msg: Any?, caller: String = "Kode", linebreak: Boolean = true) = log(Level.INFO, msg, caller, linebreak)
    fun debug(msg: Any?, caller: String = "Kode", linebreak: Boolean = true) = log(Level.DEBUG, msg, caller, linebreak)
    fun trace(msg: Any?, caller: String = "Kode", linebreak: Boolean = true) = log(Level.TRACE, msg, caller, linebreak)

    enum class Level(val color: String) {
        FATAL(ConsoleColor.RED + ConsoleColor.BOLD),
        ERROR(ConsoleColor.LIGHT_RED + ConsoleColor.BOLD),
        WARN(ConsoleColor.YELLOW),
        INFO(ConsoleColor.BLUE),
        DEBUG(ConsoleColor.GREEN),
        TRACE(ConsoleColor.LIGHT_GRAY)
    }

}