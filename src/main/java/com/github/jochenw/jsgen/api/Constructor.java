package com.github.jochenw.jsgen.api;


/** Object, which defines a constructor, that is being generated.
 * Use {@link JSGClass#newConstructor()}, or {@link JSGClass#newConstructor(com.github.jochenw.jsgen.api.IProtectable.Protection)},
 * to create an instance.
 */
public class Constructor extends Subroutine<Constructor> {
	@Override
	protected Constructor self() {
		return this;
	}
}
