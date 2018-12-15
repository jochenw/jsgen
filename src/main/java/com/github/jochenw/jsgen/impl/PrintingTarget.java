package com.github.jochenw.jsgen.impl;

import java.io.IOException;
import java.io.UncheckedIOException;


/** Implementation of a target, which logs its invocations.
 * This is mainly usefull for debugging JSGen itself.
 */
public class PrintingTarget implements SerializationTarget {
	private final Appendable appendable;
	private final int maxLines;
	private int lines;

	/** Creates a new instance, which logs to the given
	 * {@link Appendable}.
	 * @param pAppendable The {@link Appendable}, to which
	 *   method calls are being logged.
	 * @param pMaxLines The maximum number of calls to log.
	 *   An {@link IllegalStateException} is being thrown,
	 *   if this number would be exceeded. Use -1 for unlimited.
	 * @see #PrintingTarget(Appendable)
	 */
	public PrintingTarget(Appendable pAppendable, int pMaxLines) {
		appendable = pAppendable;
		maxLines = pMaxLines;
	}

	/** Creates a new instance, which logs to the given
	 * {@link Appendable}. The number of lines being logged,
	 * is unlimited.
	 * @param pAppendable The {@link Appendable}, to which
	 *   method calls are being logged.
	 * @see #PrintingTarget(Appendable, int)
	 */
	public PrintingTarget(Appendable pAppendable) {
		this(pAppendable,-1);
	}
	
	@Override
	public void write(Object pObject) {
		try {
			appendable.append("write: '" + pObject + "'\r\n");
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		count();
	}

	@Override
	public void newLine() {
		try {
			appendable.append("newLine:\r\n");
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		count();
	}

	@Override
	public void close() {
		try {
			appendable.append("close:\r\n");
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		count();
	}

	protected void count() {
		if (maxLines != -1) {
			if (++lines == maxLines) {
				throw new IllegalStateException("Maximum of lines reached.");
			}
		}
	}
}
