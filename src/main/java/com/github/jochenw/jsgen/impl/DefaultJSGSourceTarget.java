package com.github.jochenw.jsgen.impl;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;


/** Default implementation of {@link JSGSourceTarget}, which writes
 * to an {@link Appendable} (typically a {@link Writer}).
 */
public class DefaultJSGSourceTarget implements JSGSourceTarget {
	private final Appendable appendable;
	private String lineTerminator = "\n";

	/** Creates a new instance, which writes to the given {@link Appendable}.
	 * @param pAppendable The actual target, to which generated code is being written.
	 */
	public DefaultJSGSourceTarget(Appendable pAppendable) {
		appendable = pAppendable;
	}

	/** Returns the {@link Appendable}, to which generated code is actually being
	 * written.
	 * @return The {@link Appendable}, to which generated code is actually being
	 * written.
	 */
	public Appendable getAppendable() {
		return appendable;
	}


	@Override
	public void write(Object pObject) {
		try {
			appendable.append(pObject.toString());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void newLine() {
		try {
			appendable.append(lineTerminator);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void close() {
		if (appendable instanceof Closeable) {
			try {
				((Closeable) appendable).close();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}
}
