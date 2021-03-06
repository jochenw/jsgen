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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.util.AbstractBuilder;
import com.github.jochenw.jsgen.util.Objects;


/** This object holds the data for one comment in then Java sorce file.
 * A comment is built from one, or more lines of text (as returned by
 * {@link #getText()}, plus a boolean flag, which makes the comment
 * either public (Starts with "/**"), or non-public (starts with "/*").
 */
public class Comment extends AbstractBuilder<Comment> {
	private final List<String> text = new ArrayList<String>();
	private boolean isPublic;
	private boolean terse;

	/**
	 * Adds the given text lines (one per element in the array {@code pText} to the
	 * comments text.
	 * @param pText An array of text lines, one per entry in the array.
	 * @return This comment.
	 */
	@Nonnull public Comment text(@Nonnull String... pText) {
		Objects.requireAllNonNull(pText, "Text Line");
		assertMutable();
		text.clear();
		text.addAll(Arrays.asList(pText));
		return self();
	}

	/**
	 * Adds the given text lines (one per element in the {@link Iterable}
	 * {@code pText} to the comments text.
	 * @param pText An {@link Iterable} of text lines, one per entry in the
	 *   sequence.
	 * @return This comment.
	 */
	@Nonnull public Comment text(Iterable<String> pText) {
		Objects.requireAllNonNull(pText, "Text Line");
		assertMutable();
		text.clear();
		for (String s : pText) {
			text.add(s);
		}
		return self();
	}

	/** Returns the list of text lines. This list has been built by calls to
	 * {@link #text(String...)}, or {@link #text(Iterable)}.
	 * @return The list of text lines.
	 */
	@Nonnull public List<String> getText() {
		return text;
	}
	
	@Override
	protected Comment self() {
		return this;
	}

	/** Returns, whether this is a public comment. A public comment is
	 * introduced with {@code /**}. A non-public comment is introduced
	 * with {@code /*}.
	 * @return True, if this is a public comment. Otherwise false.
	 */
	public boolean isPublic() {
		return isPublic;
	}

	/** Makes this comment public. A public comment is
	 * introduced with {@code /**}. A non-public comment is introduced
	 * with {@code /*}.
	 * @return This comment.
	 */
	@Nonnull public Comment makePublic() {
		return makePublic(true);
	}

	/** Sets, whether this is a public comment. A public comment is
	 * introduced with {@code /**}. A non-public comment is introduced
	 * with {@code /*}.
	 * @param pPublic True, if this is a public comment. False otherwise.
	 * @return This comment.
	 */
	@Nonnull public Comment makePublic(boolean pPublic) {
		assertMutable();
		isPublic = pPublic;
		return this;
	}

	/** Returns, whether this comment should use terse formatting. A
	 * terse comment will use the {@code //} syntax.
	 * @return True, if this comment will use terse formatting, otherwise
	 * false.
	 */
	public boolean isTerse() {
		return isPublic;
	}

	/** Sets, whether this comment should use terse formatting. A
	 * terse comment will use the {@code //} syntax.
	 * @return True, if this comment will use terse formatting, otherwise
	 * false.
	 */
	@Nonnull public Comment terse() {
		return terse(true);
	}

	/** Sets, whether this comment should use terse formatting. A
	 * terse comment will use the {@code //} syntax.
	 * @param pTerse True, if this comment will use terse formatting, otherwise
	 * false.
	 */
	@Nonnull public Comment terse(boolean pTerse) {
		assertMutable();
		terse = pTerse;
		return this;
	}
}
