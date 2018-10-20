package com.github.jochenw.jsgen.impl;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;


public class DefaultJSGSourceTarget implements JSGSourceTarget {
	private final Appendable appendable;
	private String lineTerminator = "\n";

	public DefaultJSGSourceTarget(Appendable pAppendable) {
		appendable = pAppendable;
	}

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
