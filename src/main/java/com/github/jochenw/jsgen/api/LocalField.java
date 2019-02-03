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
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.util.AbstractBuilder;
import com.github.jochenw.jsgen.util.Objects;


/**
 * Representation of a local field: A variable, which has a code block as its scope.
 */
public class LocalField extends AbstractBuilder<LocalField> implements ICommentOwner, IField<LocalField> {
	private final AnnotationSet annotations = new AnnotationSet();
	private boolean isFinal;
	private JQName type;
	private String name;
	private Object value;
	@Nullable private Comment comment;

	@Override
	@Nonnull protected LocalField self() {
		return this;
	}

	public boolean isFinal() {
		return isFinal;
	}

	@Nonnull public JQName getType() {
		return type;
	}

	@Nonnull public String getName() {
		return name;
	}

	/**
	 * Sets the fields name.
	 * @param pName The fields name.
	 * @return This builder.
	 */
	@Nonnull public LocalField name(@Nonnull String pName) {
		assertMutable();
		name = pName;
		return self();
	}

	/**
	 * Sets the fields type.
	 * @param pType The fields type.
	 * @return This builder.
	 */
	@Nonnull public LocalField type(@Nonnull String pType) {
		return type(JQName.valueOf(pType));
	}

	/**
	 * Sets the fields type.
	 * @param pType The fields type.
	 * @return This builder.
	 */
	@Nonnull public LocalField type(@Nonnull Class<?> pType) {
		return type(JQName.valueOf(pType));
	}

	/**
	 * Sets the fields type.
	 * @param pType The fields type.
	 * @return This builder.
	 */
	@Nonnull public LocalField type(@Nonnull JQName pType) {
		assertMutable();
		type = pType;
		return self();
	}

	/** Makes the field final. Equivalent to {@code makeFinal(true)}.
	 * @return This builder.
	 */
	@Nonnull public LocalField makeFinal() {
		return makeFinal(true);
	}

	/** Sets, whether the field is final.
	 * @param pFinal True, to make the field final, otherwise false.
	 * @return This builder.
	 */
	@Nonnull public LocalField makeFinal(boolean pFinal) {
		assertMutable();
		isFinal = pFinal;
		return self();
	}

	@Override
	public AnnotationSet getAnnotations() {
		return annotations;
	}

	/** Sets the fields value.
	 * @param pValues The fields value, as an array of elements. The actual value
	 *   is generated by concatenating the elements.
	 * @return This builder.
	 */
	@Nonnull public LocalField assign(@Nonnull Object... pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (value != null) {
			throw new IllegalStateException("A value has already been assigned to this field.");
		}
		value = pValues;
		return this;
	}

	/** Sets the fields value.
	 * @param pValues The fields value, as an {@link Iterable} of elements. The actual value
	 *   is generated by concatenating the elements.
	 * @return This builder.
	 */
	@Nonnull public LocalField assign(@Nonnull Iterable<Object> pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (value != null) {
			throw new IllegalStateException("A value has already been assigned to this field.");
		}
		value = pValues;
		return this;
	}

	@Nullable public Object getValue() {
		return value;
	}

	/** Sets the fields comment.
	 * @param pText The fields comment, as an array of strings, one per line.
	 * @return This builder.
	 */
	@Nonnull public LocalField comment(String... pText) {
		assertMutable();
		comment = new Comment().text(pText);
		return this;
	}

	/** Sets the fields comment.
	 * @param pText The fields comment, as an {@link Iterable} of strings, one per line.
	 * @return This builder.
	 */
	@Nonnull public LocalField comment(Iterable<String> pText) {
		assertMutable();
		comment = new Comment().text(pText);
		return this;
	}

	@Nullable public Comment getComment() {
		return comment;
	}
}
