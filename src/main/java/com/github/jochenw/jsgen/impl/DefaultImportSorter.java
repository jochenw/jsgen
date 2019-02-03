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

import com.github.jochenw.jsgen.api.IImportSorter;
import com.github.jochenw.jsgen.api.JQName;


/** Default implementation of {@link IImportSorter}.
 */
public class DefaultImportSorter implements IImportSorter {
	/** The comparator for sorting imported names within a single
	 * category. This implementation compares the fully qualified
	 * class names, ignoring case.
	 */
	@Override
	public int compare(@Nonnull JQName o1, @Nonnull JQName o2) {
		return o1.getQName().compareToIgnoreCase(o2.getQName());
	}

	/** The categorizer, which devides imported names into categories.
	 * The default implementation is using the following categories:
	 * <ul>
	 *   <li>0=java.*</li>
	 *   <li>1=javax.*</li>
	 *   <li>2=com.*</li>
	 *   <li>3=org.*</li>
	 *   <li>4=Everything else</li>
	 * </ul>
	 */
	@Override
	public int getCategory(JQName pName) {
		if (pName.getPackageName().startsWith("java.")) {
			return 0;
		} else if (pName.getPackageName().startsWith("javax.")) {
			return 1;
		} else if (pName.getPackageName().startsWith("com.")) {
			return 2;
		} else if (pName.getPackageName().startsWith("org.")) {
			return 3;
		} else {
			return 4;
		}
	}
}
