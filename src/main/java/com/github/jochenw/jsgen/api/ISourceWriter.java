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

import java.io.IOException;
import java.io.UncheckedIOException;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.api.JSGFactory.NamedResource;


/** Interface of an object, which persists the Java source representations.
 */
public interface ISourceWriter {
	/** Persists all objects, which have been created by the factory.
	 * @param pFactory The factory object being persisted.
	 */
	public default void write(@Nonnull JSGFactory pFactory) {
		pFactory.forEach((l,o) -> {
			try {
				if (o instanceof Source) {
					write((Source) o);
				} else if (o instanceof NamedResource) {
					write((NamedResource) o);
				} else {
					throw new IllegalStateException("Invalid object type: " + o.getClass().getName());
				}
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});
	}
	/** Persists the given Java source object.
	 * @param pSource The source object being persisted.
	 * @throws IOException An I/O error occurred while accessing external storage.
	 */
	void write(@Nonnull Source pSource) throws IOException;
	/** Persists the given Java resource file.
	 * @param pResource The source object being persisted.
	 * @throws IOException An I/O error occurred while accessing external storage.
	 */
	void write(@Nonnull NamedResource pResource) throws IOException;
}
