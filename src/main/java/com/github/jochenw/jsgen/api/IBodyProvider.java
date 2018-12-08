package com.github.jochenw.jsgen.api;

/** Interface of an object with a body of code.
 */
public interface IBodyProvider {
	/** Returns the objects body.
	 * @return The objects body.
	 */
	Block<?> body();
}
