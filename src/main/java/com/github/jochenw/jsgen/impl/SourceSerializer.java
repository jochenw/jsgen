package com.github.jochenw.jsgen.impl;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.api.Source;

/** Interface of the actual generator, which converts
 * the object tree into text.
 */
public interface SourceSerializer {
	/**
	 * Called to serialize the given Java source object by
	 * writing it to the given target.
	 * @param pSource The Java source object to convert.
	 * @param pTarget The target to write to.
	 */
	public void write(@Nonnull Source pSource, @Nonnull SerializationTarget pTarget);
}
