package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Common interface of a field. (Class members, local variables, and parameters.)
 */
public interface IField extends IAnnotatable {
	/** Returns the fields name.
	 * @return The fields name.
	 */
	@Nonnull String getName();
	/** Returns the fields type.
	 * @return The fields type.
	 */
	@Nonnull JQName getType();
	/** Returns, whether the field is final.
	 * @return True, if this field is final, otherwise false.
	 */
	boolean isFinal();
	/** Returns the fields assigned value.
	 * @return The fields assigned value.
	 */
	@Nullable Object getValue();
}
