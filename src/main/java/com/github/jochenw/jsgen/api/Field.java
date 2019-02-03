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
 * This class represents a field in a Java class, as opposed to a field, which is local to a
 * method, or a code block.
 */
public class Field extends AbstractBuilder<Field> implements IProtectable<Field>, ICommentOwner, IField<Field>, IStaticable<Field>, IVolatilable<Field> {
	private AnnotationSet annotations = new AnnotationSet();
	private @Nonnull Protection protection;
	private @Nonnull JQName type;
	private @Nonnull String name;
	private @Nullable Object value;
	private boolean isStatic, isVolatile, isFinal;
	private Comment comment;

	@Override
	@Nonnull public AnnotationSet getAnnotations() {
		return annotations;
	}

	@Nonnull public Field protection(@Nonnull Protection pProtection) {
		assertMutable();
		protection = pProtection;
		return this;
	}

	/**
	 * Sets this fields protection to "public".
	 * @return This builder.
	 */
	@Nonnull public Field makePublic() {
		return protection(Protection.PUBLIC);
	}
	
	/**
	 * Sets this fields protection to "protected".
	 * @return This builder.
	 */
	@Nonnull public Field makeProtected() {
		return protection(Protection.PROTECTED);
	}

	/**
	 * Sets this fields protection to "package private".
	 * @return This builder.
	 */
	@Nonnull public Field makePackagePrivate() {
		return protection(Protection.PACKAGE);
	}

	/**
	 * Sets this fields protection to "private".
	 * @return This builder.
	 */
	@Nonnull public Field makePrivate() {
		return protection(Protection.PRIVATE);
	}
	
	/**
	 * Returns this fields protection.
	 * @return This fields protection.
	 */
	@Override
	@Nonnull public Protection getProtection() {
		return protection;
	}

	/** Sets this fields type.
	 * @param pType The fields type.
	 * @return This builder.
	 */
	@Nonnull Field type(@Nonnull JQName pType) {
		assertMutable();
		type = pType;
		return this;
	}

	/** Returns this fields type.
	 * @return The fields type.
	 */
	@Nonnull public JQName getType() {
		return type;
	}

	/** Sets this fields name.
	 * @param pName The fields name.
	 * @return This builder.
	 */
	@Nonnull Field name(@Nonnull String pName) {
		assertMutable();
		name = pName;
		return this;
	}

	/** Sets this fields value. The array elements will be concatenated to build the
	 * actual value.
	 * @param pValues The fields value.
	 * @return This builder.
	 * @see #assign(Iterable)
	 * @see #getValue()
	 */
	@Nonnull public Field assign(@Nonnull Object... pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (value != null) {
			throw new IllegalStateException("A value has already been assigned to this field.");
		}
		value = pValues;
		return this;
	}

	/** Sets this fields value. The elements in the {@link Iterable} will be concatenated to build the
	 * actual value.
	 * @param pValues The fields value.
	 * @return This builder.
	 * @see #assign(Object...)
	 * @see #getValue()
	 */
	@Nonnull public Field assign(@Nonnull Iterable<Object> pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (value != null) {
			throw new IllegalStateException("A value has already been assigned to this field.");
		}
		value = pValues;
		return this;
	}

	/**
	 * Returns the fields value.
	 * @return Either an array, or an {@link Iterable}, depending on which version was used to set the
	 * value
	 * @see #assign(Object...)
	 * @see #assign(Iterable)
	 */
	@Nullable public Object getValue() {
		return value;
	}

	/** Returns the fields name.
	 */
	public String getName() {
		return name;
	}

	/** Sets the fields ownership to "static". Equivalent to
	 * {@code makeStatic(true)}.
	 * @return This builder.
	 */
	@Nonnull
	public Field makeStatic() {
		return makeStatic(true);
	}

	/** Sets the fields ownership to "static" (true), or "instance" (false).
	 * @param pStatic Whether the field should be "static" (true) or not (false).
	 * @return This builder.
	 */
	@Nonnull public Field makeStatic(boolean pStatic) {
		assertMutable();
		isStatic = pStatic;
		return this;
	}

	/** Returns, whether the field is static.
	 * @return True, if the field is static. Otherwise false.
	 */
	public boolean isStatic() {
		return isStatic;
	}

	/** Sets the field to "volatile". Equivalent to
	 * {@code makeVolatile(true)}.
	 * @return This builder.
	 */
	@Nonnull public Field makeVolatile() {
		return makeVolatile(true);
	}

	/** Sets the field to "volatile" (true), or not (false).
	 * @param pVolatile Whether the field is "volatile" (true), or not (false).
	 * @return This builder.
	 */
	@Nonnull public Field makeVolatile(boolean pVolatile) {
		assertMutable();
		isVolatile = pVolatile;
		return this;
	}

	/**
	 * Returns, whether the field is volatile (true), or not (false).
	 */
	public boolean isVolatile() {
		return isVolatile;
	}

	/** Sets the field to "final". Equivalent to
	 * {@code makeFinal(true)}.
	 * @return This builder.
	 */
	@Nonnull public Field makeFinal() {
		return makeFinal(true);
	}

	/** Sets the field to "final" (true), or not (false).
	 * @param pFinal Whether the field is "final" (true), or not (false).
	 * @return This builder.
	 */
	@Nonnull public Field makeFinal(boolean pFinal) {
		assertMutable();
		isFinal = pFinal;
		return this;
	}

	/**
	 * Returns, whether the field is "final" (true), or not (false).
	 * @return True, if the field is "final". Otherwise false.
	 */
	public boolean isFinal() {
		return isFinal;
	}

	@Override
	protected @Nonnull Field self() { return this; }

	/**
	 * Sets the fields Javadoc comment. Each element in the array is
	 * a single line.
	 * @param pText The fields Javadoc comment, as an array of lines.
	 * @return This builder.
	 */
	public @Nonnull Field comment(@Nonnull String... pText) {
		assertMutable();
		comment = new Comment().makePublic().text(pText);
		return this;
	}

	/**
	 * Sets the fields Javadoc comment. Each element in the {@link Iterable} is
	 * a single line.
	 * @param pText The fields Javadoc comment, as an {@link Iterable} of lines.
	 * @return This builder.
	 */
	public @Nonnull Field comment(@Nonnull Iterable<String> pText) {
		assertMutable();
		comment = new Comment().makePublic().text(pText);
		return this;
	}

	/**
	 * Returns the fields Javadoc comment.
	 * @return The fields comment, if any, or null.
	 */
	@Nullable public Comment getComment() {
		return comment;
	}
}
