package com.github.jochenw.jsgen.util;


/** Abstract base class for builders, which are embedded into
 * outer builders, and can return the outer builder.
 * @param <T> The builders actual class, for use as
 *   the result type in builder methods.
 * @param <F> The outer builders class, for use as the
 *   result type of {@link #end()}.
 */
public abstract class AbstractTerminableBuilder<T extends AbstractBuilder<T>, F extends AbstractTerminableBuilder<T,F>> extends AbstractBuilder<T> {
	/**
	 * Terminates use of the current builder by returning the
	 *   outer builder.
	 * @return The outer builder.
	 */
	public abstract F end();
}
