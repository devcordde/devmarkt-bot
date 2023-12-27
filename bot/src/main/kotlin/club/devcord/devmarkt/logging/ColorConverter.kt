package club.devcord.devmarkt.logging

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.pattern.CompositeConverter


/**
 * @author Paul Kindler
 * @since 19/12/2023
 */
abstract class ColorConverter : CompositeConverter<ILoggingEvent>() {

	private val levelColors = mapOf(
		Level.ERROR_INT to AnsiColor.RED,
		Level.WARN_INT to AnsiColor.YELLOW,
		Level.INFO_INT to AnsiColor.GREEN,
		Level.DEBUG_INT to AnsiColor.BLUE,
		Level.TRACE_INT to AnsiColor.BLACK
	)

	override fun transform(iLoggingEvent: ILoggingEvent, input: String): String {
		val colorName = firstOption
		val ansiColor = AnsiColor.fromName(colorName)
		if (ansiColor != null) {
			return ansiColor.colorize(input)
		}
		val level = iLoggingEvent.level.toInt()
		val levelColor = levelColors[level] ?: AnsiColor.WHITE
		return levelColor.colorize(input)
	}
}