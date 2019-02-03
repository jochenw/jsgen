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

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;


/** Default implementation of {@link SerializationTarget}, which writes
 * to an {@link Appendable} (typically a {@link Writer}).
 */
public class DefaultSerializationTarget implements SerializationTarget {
	private final Appendable appendable;
	private String lineTerminator = "\n";

	/** Creates a new instance, which writes to the given {@link Appendable}.
	 * @param pAppendable The actual target, to which generated code is being written.
	 */
	public DefaultSerializationTarget(Appendable pAppendable) {
		appendable = pAppendable;
	}

	/** Returns the {@link Appendable}, to which generated code is actually being
	 * written.
	 * @return The {@link Appendable}, to which generated code is actually being
	 * written.
	 */
	public Appendable getAppendable() {
		return appendable;
	}


	@Override
	public void write(Object pObject) {
		try {
			appendable.append(pObject.toString());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void newLine() {
		try {
			appendable.append(lineTerminator);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void close() {
		if (appendable instanceof Closeable) {
			try {
				((Closeable) appendable).close();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}
}
