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

/** Common interface of a field. (Class members, local variables, and parameters.)
 * @param <T> The actual object type. Used to specify the return type for builder methods.
 */
public interface IField<T extends IField<T>> extends IAnnotatable<T> {
	/** Returns the fields name.
	 * @return The fields name.
	 */
	@Nonnull String getName();
	/** Returns the fields type.
	 * @return The fields type.
	 */
	@Nonnull JQName getType();
	/** Returns, whether the field is final.
	 * @return True, if this field is final, otherwise false.
	 */
	boolean isFinal();
	/** Returns the fields assigned value.
	 * @return The fields assigned value.
	 */
	@Nullable Object getValue();
}
