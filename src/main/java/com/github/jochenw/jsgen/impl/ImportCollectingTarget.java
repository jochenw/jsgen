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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.api.JQName;

/** Ths implementation of {@link SerializationTarget} collects class
 * names, which are being used, and counts the usage. The collected
 * data is used for building the import lists.
 */
public class ImportCollectingTarget implements SerializationTarget {
	/** The collected data: Basically a class name, and a usage
	 * counter.
	 */
	public static class CountedName {
		private int count;
		private final JQName name;
		private final CountedName next;

		/**
		 * Creates a new usage counter for the given class name.
		 * @param pName The class name being counted.
		 * @param pNext The next element in a linked list. (Null,
		 *   if this is the last element in the list.)
		 */
		public CountedName(JQName pName, CountedName pNext) {
			name = pName;
			next = pNext;
		}
	}

	private final Map<String,CountedName> countedNames = new HashMap<>();
	private Set<JQName> importedNames;
	private Function<Object,Object> filter;
	private JQName importingClass;

	/**
	 * Returns the class, which is currently being generated.
	 * (And thus, the importing class.)
	 * @return The class, which is currently being generated.
	 * (And thus, the importing class.)
	 */
	public JQName getImportingClass() {
		return importingClass;
	}

	/**
	 * Sets the class, which is currently being generated.
	 * (And thus, the importing class.)
	 * @param pImportingClass The class, which is currently being generated.
	 * (And thus, the importing class.)
	 */
	public void setImportingClass(JQName pImportingClass) {
		importingClass = pImportingClass;
	}

	@Override
	public void write(@Nonnull Object pObject) {
		final Object o;
		if (filter == null) {
			o = pObject;
		} else {
			o = filter.apply(pObject);
		}
		if (o instanceof JQName) {
			if (importedNames != null) {
				throw new IllegalStateException("Object is already closed.");
			}
			@Nonnull final JQName name = (JQName) o;
			if (isImportable(name)) {
				CountedName counter = findCountedName(name);
				counter.count++;
			}
		} else {
			// Do nothing
		}
	}

	protected boolean isImportable(JQName pName) {
		if (pName.isPseudoClass()) {
			return false;
		}
		if (pName.isPrimitive()) {
			return false;
		}
		if (pName.getSimpleClassName().equals(importingClass.getSimpleClassName())) {
			return false;
		}
		if (pName.equals(importingClass)) {
			return false;
		}
		if (pName.isInnerClass()) {
			return false;
		}
		return true;
	}
	
	@Nonnull protected CountedName findCountedName(@Nonnull JQName pName) {
		final CountedName firstCn = countedNames.get(pName.getSimpleClassName());
		if (firstCn != null) {
			for (CountedName cn = firstCn;  cn != null; cn = cn.next) {
				if (cn.name.equals(pName)) {
					return cn;
				}
			}
		}
		final CountedName newCn = new CountedName(pName, firstCn);
		countedNames.put(pName.getSimpleClassName(), newCn);
		return newCn;
	}
	
	@Override
	public void newLine() {
		// Do nothing
	}

	@Override
	public void close() {
		if (importedNames == null) {
			importedNames = new HashSet<>();
			for(CountedName cn : countedNames.values()) {
				final JQName name = findMaxCount(cn);
				importedNames.add(name);
			}
		}
	}

	protected JQName findMaxCount(@Nonnull CountedName pCn) {
		JQName name = null;
		int max = 0;
		for (CountedName cn = pCn;  cn != null;  cn = cn.next) {
			if (name == null  ||  cn.count > max) {
				max = cn.count;
				name = cn.name;
			}
		}
		return name;
	}

	/** Returns the list of names, which can be imported.
	 * @return The list of names, which can be imported.
	 */
	public List<JQName> getImportedNames() {
		close();
		return new ArrayList<>(importedNames);
	}
}
