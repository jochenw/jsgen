/**
 * Copyright 2018 Jochen Wiedmann
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;

/** Representation of an inner class.
 */
public class InnerClass extends ClassBase<InnerClass> implements IStaticable<InnerClass> {
	private boolean isStatic;

	/** Creates a new instance with the given type.
	 * @param pType The inner classes type. (Must have {@link JQName#isInnerClass()}=true.)
	 */
	public InnerClass(JQName pType) {
		super(pType);
	}

	/** Declares this inner class to be static. Equivalent to
	 * {@code makeStatic(true}.
	 * @return This builder.
	 */
	@Nonnull public InnerClass makeStatic() {
		return makeStatic(true);
	}

	/** Declares this inner class to be static, or not.
	 * @param pStatic True, if this inner class should be static, otherwise false.
	 * @return This builder.
	 */
	@Nonnull public InnerClass makeStatic(boolean pStatic) {
		assertMutable();
		isStatic = pStatic;
		return this;
	}

	/** Returns, whether this inner class is static.
	 * @return True, if this inner class is static, otherwise false.
	 */
	public boolean isStatic() {
		return isStatic;
	}
}
