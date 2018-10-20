package com.github.jochenw.jsgen.impl;

import javax.annotation.Nonnull;

public interface JSGSourceTarget {
	void write(@Nonnull Object pObject);
	void newLine();
	void close();
}
