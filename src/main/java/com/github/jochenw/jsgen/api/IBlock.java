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

/**
 * Interface of a an arbitrary block of Java code. This may be a named code block, like a method, or a
 * constructor, or it may be unnamed, like a static class initializer, a "for" loop, an
 * "if" condition.
 * @param <T> The actual type. This is used by builder methods to determine the result type.
 */
public interface IBlock<T extends IBlock<T>> {
	/**
	 * Creates a new line with the given elements. The actual line will be created by concatenating
	 * the elements. No statement separator will be added.
	 * @param pObjects The lines elements, without any line terminator.
	 * @return This block.
	 * @see #line(Iterable)
	 * @see #tline(Object...)
	 */
	public T line(Iterable<Object> pObjects);
	/**
	 * Creates a new line with the given elements. The actual line will be created by concatenating
	 * the elements. No statement separator will be added.
	 * @param pObjects The lines elements, without any line terminator.
	 * @return This block.
	 * @see #line(Iterable)
	 * @see #tline(Object...)
	 */
	public T line(Object... pObjects);
	/**
	 * Creates a new line with the given elements. The actual line will be created by concatenating
	 * the elements. A statement separator will be added.
	 * @param pObjects The lines elements, without any line terminator.
	 * @return This block.
	 * @see #line(Iterable)
	 * @see #tline(Object...)
	 */
	public T tline(Iterable<Object> pObjects);
	/**
	 * Creates a new line with the given elements. The actual line will be created by concatenating
	 * the elements. A statement separator will be added.
	 * @param pObjects The lines elements, without any line terminator.
	 * @return This block.
	 * @see #line(Iterable)
	 * @see #tline(Object...)
	 */
	public T tline(Object... pObjects);
	/**
	 * Creates a new Java field with the given name, and type. The fields scope is the current
	 * code block.
	 * @param pType The fields type. Will be converted to an instance of {@link JQName} via
	 *   {@link JQName#valueOf(String)}.
	 * @param pName The fields name.
	 * @return The created field, which has been added to the current code block.
	 * @see #newField(Class, String)
	 * @see #newField(JQName, String)
	 */
	public LocalField newField(String pType, String pName);

	/**
	 * Creates a new Java field with the given name, and type. The fields scope is the current
	 * code block.
	 * @param pType The fields type. Will be converted to an instance of {@link JQName} via
	 *   {@link JQName#valueOf(String)}.
	 * @param pName The fields name.
	 * @return The created field, which has been added to the current code block.
	 * @see #newField(String, String)
	 * @see #newField(JQName, String)
	 */
	public LocalField newField(Class<?> pType, String pName);

	/**
	 * Creates a new Java field with the given name, and type. The fields scope is the current
	 * code block.
	 * @param pType The fields type.
	 * @param pName The fields name.
	 * @return The created field, which has been added to the current code block.
	 * @see #newField(Class, String)
	 * @see #newField(JQName, String)
	 */
	public LocalField newField(JQName pType, String pName);

	/**
	 * Creates a new, nested, "if" block, with the given condition, and adds it to the current block.
	 * @param pCondition The condition elements. The actual line will be created by concatenating
	 *   the condition elements.
	 * @return The created block, which has been added to the current code block.
	 */
	@Nonnull public IfBlock newIf(@Nonnull Object... pCondition);

	/**
	 * Creates a new, nested, "if" block, with the given condition, and adds it to the current block.
	 * @param pCondition The condition elements. The actual line will be created by concatenating
	 *   the condition elements.
	 * @return The created block, which has been added to the current code block.
	 */
	@Nonnull public IfBlock newIf(@Nonnull Iterable<?> pCondition);

	/** Creates a new, nested "do... while" block, with the given condition, and adds it to the
	 * current block.
	 * @param pCondition The condition elements. The actual line will be created by concatenating
	 *   the condition elements.
	 * @return The created block, which has been added to the current code block.
	 */
	@Nonnull public DoWhileBlock newDoWhile(@Nonnull Object... pCondition);

	/** Creates a new, nested "do... while" block, with the given condition, and adds it to the
	 * current block.
	 * @param pCondition The condition elements. The actual line will be created by concatenating
	 *   the condition elements.
	 * @return The created block, which has been added to the current code block.
	 */
	@Nonnull public DoWhileBlock newDoWhile(@Nonnull Iterable<?> pCondition);

	/**
	 * Creates a new, nested, "for" block, with the given condition, and adds it to the current block.
	 * @param pCondition The condition elements. The actual line will be created by concatenating
	 *   the condition elements.
	 * @return The created block, which has been added to the current code block.
	 */
	@Nonnull public ForBlock newFor(@Nonnull Object... pCondition);

	/**
	 * Creates a new, nested, "for" block, with the given condition, and adds it to the current block.
	 * @param pCondition The condition elements. The actual line will be created by concatenating
	 *   the condition elements.
	 * @return The created block, which has been added to the current code block.
	 */
	@Nonnull public ForBlock newFor(@Nonnull Iterable<?> pCondition);

	/** Creates a non-public, comment, and adds it to the current code block.
	 * The created comments type (single-line, or
	 * multi-line) is being determined by the number of elements in the array.
	 * @return This code block.
	 * @param pText The comments text. The created comments type (single-line, or
	 * multi-line) is being determined by the number of elements in the array.
	 */
	@Nonnull T comment(String... pText);

	/** Creates a non-public, comment, and adds it to the current code block.
	 * The created comments type (single-line, or
	 * multi-line) is being determined by the number of elements in the array.
	 * @return This code block.
	 * @param pText The comments text. The created comments type (single-line, or
	 * multi-line) is being determined by the number of elements in the array.
	 */
	@Nonnull T comment(Iterable<String> pText);
}
