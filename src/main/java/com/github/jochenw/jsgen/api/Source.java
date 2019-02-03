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

import com.github.jochenw.jsgen.impl.Quoter;

/** Representation of a Java source file.
 */
public class Source extends ClassBase<Source> {
	private Comment packageComment;

	/** Creates a new instance with the given type.
	 * @param pType The Java source files fully qualified type, including the package name.
	 */
	public Source(JQName pType) {
		super(pType);
	}

	/** Sets the Java source files package comment. (A comment, which precedes the
	 * package statement. This is typically used for license headers, or the like.
	 * @param pText The comments text, one string for every line.
	 * @return This builder.
	 */
	@Nonnull public Source packageComment(@Nonnull String... pText) {
		assertMutable();
		if (packageComment == null) {
			packageComment = new Comment().makePublic();
		}
		packageComment.text(pText);
		return this;
	}

	/** Sets the Java source files package comment. (A comment, which precedes the
	 * package statement. This is typically used for license headers, or the like.
	 * @param pText The comments text, one string for every line.
	 * @return This builder.
	 */
	@Nonnull public Source comment(@Nonnull Iterable<String> pText) {
		assertMutable();
		if (packageComment == null) {
			packageComment = new Comment().makePublic();
		}
		packageComment.text(pText);
		return self();
	}

	/** Returns the Java source files package comment. (A comment, which precedes the
	 * package statement. This is typically used for license headers, or the like.
	 * @return The Java source files package comment, if any, or null.
	 */
	@Nullable
	public Comment getPackageComment() {
		return packageComment;
	}

	/** Creates a quoted string. For example, the value {@code Hello, World!}
	 * would be mapped to {@code "Hello, World!"}.
	 * @param pValue The value to convert into a quoted string.
	 * @return The quoted version of the given value, properly escaped.
	 */
	public static String q(String pValue) {
		return Quoter.valueOf(pValue);
	}
}
