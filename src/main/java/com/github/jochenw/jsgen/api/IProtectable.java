package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;


/** Interface of an object, which can be protected.
 * @param <T> The actual object type. Used to specify the return type for builder methods.
 */
public interface IProtectable<T extends IProtectable<T>> {
	/** The available protections.
	 */
	public enum Protection {
		/** "Public": Everyone may access this object.
		 */
		PUBLIC,
		/** "Protected": Only subclasses may access this object.
		 */
		PROTECTED,
		/** "Package protected": The object may be accessed within this package only.
		 */
		PACKAGE,
		/** "Private": The object may be accessed within the same code unit only.
		 */
		PRIVATE
	}

	/** Returns the objects protection.
	 * @return The objects protection.
	 */
	@Nonnull Protection getProtection();

	/** Sets the objects protection.
	 * @param pProtection The objects protection.
	 * @return This builder.
	 */
	T protection(Protection pProtection);

    /** Sets the objects protection to "public". Equivalent to
     * {@code protection(Protection.PUBLIC)}.
	 * @return This builder.
     */
	T makePublic();

    /** Sets the objects protection to "protected". Equivalent to
     * {@code protection(Protection.PROTECTED)}.
	 * @return This builder.
     */
	T makeProtected();

    /** Sets the objects protection to "package private". Equivalent to
     * {@code protection(Protection.PACKAGE)}.
	 * @return This builder.
     */
	T makePackagePrivate();

    /** Sets the objects protection to "private". Equivalent to
     * {@code protection(Protection.PRIVATE)}.
	 * @return This builder.
     */
	T makePrivate();
}
