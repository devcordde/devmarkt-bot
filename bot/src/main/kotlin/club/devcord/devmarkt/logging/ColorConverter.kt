package club.devcord.devmarkt.logging

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.pattern.CompositeConverter

class ColorConverter : CompositeConverter<ILoggingEvent>() {
	override fun transform(iLoggingEvent: ILoggingEvent, input: String): String {
		val colorName = firstOption
		val ansiColor = AnsiColor.fromName(colorName)
		if (ansiColor != null) {
			return ansiColor.colorize(input)
		}
		val level = iLoggingEvent.level.toInt()
		val levelColor: AnsiColor = LEVEL_COLORS.getOrDefault(level, AnsiColor.WHITE)
		return levelColor.colorize(input)
	}
}

private var LEVEL_COLORS: Map<Int, AnsiColor> = mapOf(
	Level.INFO_INT to AnsiColor.GREEN,
	Level.WARN_INT to AnsiColor.YELLOW,
	Level.ERROR_INT to AnsiColor.RED,
	Level.DEBUG_INT to AnsiColor.BLUE,
	Level.TRACE_INT to AnsiColor.BLACK
)
