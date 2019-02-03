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
 * Representation of an if(...) block.
 */
public class IfBlock extends Block<IfBlock> {
	private Object condition;

	/** Specifies the if blocks condition. The array elements will be concatenated.
	 * @param pValues The if blocks condition, as an array of elements. 
	 * @return This builder.
	 */
	public IfBlock condition(@Nonnull Object... pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (condition != null) {
			throw new IllegalStateException("A condition has already been assigned.");
		}
		condition = pValues;
		return this;
	}

	/** Specifies the if blocks condition. The {@link Iterable iterable} will be concatenated.
	 * @param pValues The if blocks condition, as an {@link Iterable iterable} of elements. 
	 * @return This builder.
	 */
	public IfBlock condition(@Nonnull Iterable<Object> pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (condition != null) {
			throw new IllegalStateException("A condition has already been assigned.");
		}
		condition = pValues;
		return this;
	}

	/** Returns the blocks condition.
	 * @return The blocks condition, an array, or an iterable of elements.
	 */
	@Nonnull public Object getCondition() {
		return condition;
	}
}
