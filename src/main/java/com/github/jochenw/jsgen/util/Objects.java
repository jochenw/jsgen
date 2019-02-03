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

import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/** Utility class for working with objects.
 */
public class Objects {
	/** Checks, that {@code pObject} isn't null. If so, the
	 * non-null object is being returned. Otherwise, a
	 * {@link NullPointerException} with the given message
	 * is being thrown.
	 * @param pObject The object being checked.
	 * @param pMessage Message of the exception, which is
	 *   being thrown, if the check fails.
	 * @return The non-null object, if the check succeeds.
	 * @throws NullPointerException The check has failed.
	 */
	@Nonnull public static <O> O requireNonNull(@Nullable O pObject, @Nullable String pMessage) {
		if (pObject == null) {
			throw new NullPointerException(pMessage == null ? "Object is null" : pMessage);
		} else {
			return pObject;
		}
	}

	/** Checks, that neither the array {@code pObjects}, nor any
	 * of its elements are null. If so, the array is returned.
	 * Otherwise, a {@link NullPointerException} with the
	 * given message is being thrown.
	 * @param pObjects The array being checked.
	 * @param pMessage Message of the exception, which is
	 *   being thrown, if the check fails.
	 * @return The non-null object, if the check succeeds.
	 * @throws NullPointerException The check has failed.
	 */
	@Nonnull public static <O> O[] requireAllNonNull(O[] pObjects, String pMessage) {
		@Nonnull final O[] objects = requireNonNull(pObjects, pMessage);
		for (int i = 0;  i < pObjects.length;  i++) {
			if (objects[i] == null) {
				throw new NullPointerException(pMessage + "[" + i + "]");
			}
		}
		return objects;
	}

	/** Checks, that neither the array {@code pObjects}, nor any
	 * of its elements are null. If so, the array is returned.
	 * Otherwise, a {@link NullPointerException} with the
	 * given message is being thrown.
	 * @param pObjects The array being checked.
	 * @param pMessage Message of the exception, which is
	 *   being thrown, if the check fails.
	 * @return The non-null object, if the check succeeds.
	 * @throws NullPointerException The check has failed.
	 */
	@Nonnull public static <O extends Iterable<?>> O requireAllNonNull(O pObjects, String pMessage) {
		@Nonnull final O objects = requireNonNull(pObjects, pMessage);
		final Iterator<?> iter = objects.iterator();
		int i = 0;
		while (iter.hasNext()) {
			Object o = iter.next();
			if (o == null) {
				throw new NullPointerException(pMessage + "[" + i + "]");
			}
			++i;
		}
		return objects;
	}

}
