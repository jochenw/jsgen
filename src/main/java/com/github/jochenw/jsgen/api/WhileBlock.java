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

import com.github.jochenw.jsgen.util.Objects;

/**
 * Representation of a while(...) block.
 */
public class WhileBlock extends Block<WhileBlock> {
	private Object condition;

	/** Creates a new while block with the given condition.
	 * @param pValues The condition, an array of elements, which are being concatenated
	 *   to build the actual condition.
	 * @return The while block, a builder, which can be used for further configuration.
	 */
	public WhileBlock condition(@Nonnull Object... pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (condition != null) {
			throw new IllegalStateException("A condition has already been assigned.");
		}
		condition = pValues;
		return this;
	}

	/** Creates a new while block with the given condition.
	 * @param pValues The condition, an {@link Iterable} of elements, which are being concatenated
	 *   to build the actual condition.
	 * @return The while block, a builder, which can be used for further configuration.
	 */
	public WhileBlock condition(@Nonnull Iterable<Object> pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (condition != null) {
			throw new IllegalStateException("A condition has already been assigned.");
		}
		condition = pValues;
		return this;
	}

	/** Returns the while blocks condition.
	 * @return The condition; an array, or {@link Iterable} of elements, which
	 *   are being concatenated.
	 */
	@Nonnull public Object getCondition() {
		return condition;
	}
}
