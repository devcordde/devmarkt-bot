package club.devcord.devmarkt.logging

import ch.qos.logback.classic.pattern.ExtendedThrowableProxyConverter
import ch.qos.logback.classic.spi.IThrowableProxy
import ch.qos.logback.core.CoreConstants


class ThrowableSpacer : ExtendedThrowableProxyConverter() {
	override fun throwableProxyToString(tp: IThrowableProxy): String {
		return super.throwableProxyToString(tp) + CoreConstants.LINE_SEPARATOR
	}
}

