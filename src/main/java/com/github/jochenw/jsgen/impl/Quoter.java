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
package com.github.jochenw.jsgen.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Helper class for creating escaped strings, and the like.
 */
public class Quoter {
	/** Creates a quoted representation of the given value.
	 * @param pValue The string being quoted.
	 * @return The given value, converted into a quoted string.
	 */
	@Nonnull public static final String valueOf(@Nullable String pValue) {
		if (pValue == null) {
			return "null";
		} else if (pValue.length() == 0) {
			return "\"\"";
		} else {
			final StringBuilder sb = new StringBuilder();
			sb.append('"');
			for (int i = 0;  i < pValue.length();  i++) {
				final char c = pValue.charAt(i);
				switch (c) {
				case '"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\t':
					sb.append("\\t");
					break;
				default:
					sb.append(c);
				}
			}
			sb.append('"');
			return sb.toString();
		}
	}
}
