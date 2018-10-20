package com.github.jochenw.jsgen.impl;

import java.io.IOException;
import java.io.UncheckedIOException;

public class PrintingTarget implements JSGSourceTarget {
	private final Appendable appendable;
	private final int maxLines;
	private int lines;

	public PrintingTarget(Appendable pAppendable, int pMaxLines) {
		appendable = pAppendable;
		maxLines = pMaxLines;
	}

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
