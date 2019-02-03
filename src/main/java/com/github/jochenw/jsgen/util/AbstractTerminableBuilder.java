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
package com.github.jochenw.jsgen.util;


/** Abstract base class for builders, which are embedded into
 * outer builders, and can return the outer builder.
 * @param <T> The builders actual class, for use as
 *   the result type in builder methods.
 * @param <F> The outer builders class, for use as the
 *   result type of {@link #end()}.
 */
public abstract class AbstractTerminableBuilder<T extends AbstractBuilder<T>, F extends AbstractTerminableBuilder<T,F>> extends AbstractBuilder<T> {
	/**
	 * Terminates use of the current builder by returning the
	 *   outer builder.
	 * @return The outer builder.
	 */
	public abstract F end();
}
