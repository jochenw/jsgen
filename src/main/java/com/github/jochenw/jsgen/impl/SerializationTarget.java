package com.github.jochenw.jsgen.impl;

import javax.annotation.Nonnull;

/** Interface of a target object, where the generated Java
 * source can be serialized to.
 */
public interface SerializationTarget {
	/** Called to write the given object (typically a string)
	 * to the target.
	 * @param pObject The object being written.
	 */
	void write(@Nonnull Object pObject);
	/** Called to terminate the line, which is currently being
	 * written.
	 */
	void newLine();
	/** Closes the target, which may not be used thereafter.
	 */
	void close();
}
