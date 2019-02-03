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

/** Interface of a target object, where the generated Java
 * source can be serialized to.
 */
public interface SerializationTarget {
	/** Called to write the given object (typically a string)
	 * to the target.
	 * @param pObject The object being written.
	 */
	void write(@Nonnull Object pObject);
	/** Called to terminate the line, which is currently being
	 * written.
	 */
	void newLine();
	/** Closes the target, which may not be used thereafter.
	 */
	void close();
}
