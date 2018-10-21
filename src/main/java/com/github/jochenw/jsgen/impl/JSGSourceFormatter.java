package com.github.jochenw.jsgen.impl;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.api.Source;

public interface JSGSourceFormatter {
	public void write(@Nonnull Source pSource, @Nonnull JSGSourceTarget pTarget);
}
