package com.github.jochenw.jsgen.api;

/** Interface of objects, that can be class members.
 * @param <T> The actual object type. Used to specify the return type for builder methods.
 */
public interface IVolatilable<T extends IVolatilable<T>> {
	/** Returns, whether this object is volatile (true), or not (false).
	 * @return True, if this object is volatile, otherwise false.
	 */
	boolean isVolatile();
	
	/** Sets, whether this object is volatile (true), or not (false).
	 * @param pVolatile True, if this object should be volatile, otherwise false.
	 * @return This builder.
	 */
	T makeVolatile(boolean pVolatile);
	/** Makes this object volatile. Equivalent to {@code makeVolatile(true)}.
	 * @return This builder.
	 */
	T makeVolatile();
}
