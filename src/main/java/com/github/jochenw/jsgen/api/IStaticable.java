package com.github.jochenw.jsgen.api;

/** Interface of objects, that can be class members.
 * @param <T> The actual object type. Used to specify the return type for builder methods.
 */
public interface IStaticable<T extends IStaticable<T>> {
	/** Returns, whether this object is static (true), or not (false).
	 * @return True, if this object is static (a class member), or not
	 *   (an instance member).
	 */
	boolean isStatic();
}
