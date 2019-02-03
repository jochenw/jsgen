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

/** Representation of a "throw new FooException()" statement.
 */
public class Throw {
	private final JQName type;
	private final Object constructorArgs;

	/** Creates a new instance with the given type, and the given
	 * constructor args.
	 * @param pType The exception class being instantiated, and thrown.
	 * @param constructorArgs The constructor arguments (an array, or an
	 *   {@link Iterable} of elements, which are being concatenated.
	 */
	public Throw(JQName pType, Object constructorArgs) {
		this.type = pType;
		this.constructorArgs = constructorArgs;
	}

	/** Returns the exception type.
	 * @return The exception type.
	 */
	public JQName getType() {
		return type;
	}

	/** Returns the constructor arguments.
	 * @return The constructor arguments.
	 */
	public Object getConstructorArgs() {
		return constructorArgs;
	}
}
