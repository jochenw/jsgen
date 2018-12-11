package com.github.jochenw.jsgen.api;

/** Interface of objects, that can be abstract.
 * @param <T> The actual object type. Used to specify the return type for builder methods.
 */
public interface IAbstractable<T extends IAbstractable<T>> {
	/** Returns, whether this object is abstract (true), or not (false).
	 * @return True, if this object is abstract, otherwise false.
	 * @see #makeAbstract()
	 * @see #makeAbstract(boolean)
	 */
	boolean isAbstract();
	/** Makes this object abstract. Equivalent to {@code makeAbstract(true)}.
	 * @return This builder.
	 * @see #makeAbstract(boolean)
	 * @see #isAbstract()
	 */
	T makeAbstract();
	/** Sets, whether this object is abstract.
	 * @param pAbstract True, if this object should be abstract, otherwise false.
	 * @return This builder.
	 * @see #makeAbstract()
	 * @see #isAbstract()
	 */
	T makeAbstract(boolean pAbstract);
}
