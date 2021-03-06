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

import java.util.Comparator;

import com.github.jochenw.jsgen.impl.DefaultImportSorter;

/** Interface of an object, which is used to sort the import list.
 * Basically, the sorter is used like this: First, all imported types
 * are being devided by {@link #getCategory(JQName)} into groups.
 * For every non-empty group, the groups elements are then sorted,
 * using the {@link #compare(JQName, JQName)} comparator.
 */
public interface IImportSorter {
	/**
	 * Returns the imported types category. The {@link DefaultImportSorter default implementation}
	 * provides the following categories:
	 * <ul>
	 *   <li>0 = java.*</li>
	 *   <li>1 = javax.*</li>
	 *   <li>2 = com.*</li>
	 *   <li>3 = org.*</li>
	 *   <li>4 = Everything else</li>
	 * </ul>
	 * @param pName The type being categorized.
	 * @return The types category.
	 */
	int getCategory(JQName pName);
	/** Compares the given types, following the rules of a
	 * {@link Comparator}. The {@link DefaultImportSorter default implementation}
	 * sorts types by alphabetically comparing the fully, qualified names, ignoring
	 * case.
	 * @param pName1 The first type, being compared.
	 * @param pName2 The second type, being compared.
	 * @return Either of the values -1 (First name is assumed lower),
	 * 0 (Both names are equal), and 1 (Second name is assumed lower).
	 */
	int compare(JQName pName1, JQName pName2);
}
