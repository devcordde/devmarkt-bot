package club.devcord.devmarkt.logging


enum class AnsiColor(val ansi: String) {
	RESET("\u001B[0m"),
	BLACK("\u001B[30m"),
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[33m"),
	BLUE("\u001B[34m"),
	PURPLE("\u001B[35m"),
	CYAN("\u001B[36m"),
	WHITE("\u001B[37m"),
	DARK_PURPLE("\u001B[38m");

	/**
	 * Formats a string with the given ansi escape sequence
	 *
	 * @param stringToColorize the String to be colorized
	 * @return the colorized String
	 */
	fun colorize(stringToColorize: String): String {
		return ansi + stringToColorize + ansi
	}

	companion object {
		/**
		 * Strips all Ansi Escape Codes supported by this enum from the string
		 *
		 * @param coloredString the string to strip
		 * @return the string without ansi escape codes
		 */
		fun stripColors(coloredString: String): String {
			return coloredString.replace("\u001B\\[(0|3[0-7])m".toRegex(), "")
		}

		fun fromName(name: String?): AnsiColor? {
			for (color in entries) {
				if (color.name.equals(name, ignoreCase = true)) {
					return color
				}
			}
			return null
		}
	}
}
