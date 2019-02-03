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

/** Interface of objects, that can be abstract.
 * @param <T> The actual object type. Used to specify the return type for builder methods.
 */
public interface IAbstractable<T extends IAbstractable<T>> {
	/** Returns, whether this object is abstract (true), or not (false).
	 * @return True, if this object is abstract, otherwise false.
	 * @see #makeAbstract()
	 * @see #makeAbstract(boolean)
	 */
	boolean isAbstract();
	/** Makes this object abstract. Equivalent to {@code makeAbstract(true)}.
	 * @return This builder.
	 * @see #makeAbstract(boolean)
	 * @see #isAbstract()
	 */
	T makeAbstract();
	/** Sets, whether this object is abstract.
	 * @param pAbstract True, if this object should be abstract, otherwise false.
	 * @return This builder.
	 * @see #makeAbstract()
	 * @see #isAbstract()
	 */
	T makeAbstract(boolean pAbstract);
}
