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

/** Interface of objects, that can be volatile.
 * @param <T> The actual object type. Used to specify the return type for builder methods.
 */
public interface IVolatilable<T extends IVolatilable<T>> {
	/** Returns, whether this object is volatile (true), or not (false).
	 * @return True, if this object is volatile, otherwise false.
	 */
	boolean isVolatile();
	
	/** Sets, whether this object is volatile (true), or not (false).
	 * @param pVolatile True, if this object should be volatile, otherwise false.
	 * @return This builder.
	 */
	T makeVolatile(boolean pVolatile);
	/** Makes this object volatile. Equivalent to {@code makeVolatile(true)}.
	 * @return This builder.
	 */
	T makeVolatile();
}
