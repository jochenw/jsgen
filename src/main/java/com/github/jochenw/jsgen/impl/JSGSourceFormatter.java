package com.github.jochenw.jsgen.impl;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.api.JSGSource;

public interface JSGSourceFormatter {
	public void write(@Nonnull JSGSource pSource, @Nonnull JSGSourceTarget pTarget);
}
