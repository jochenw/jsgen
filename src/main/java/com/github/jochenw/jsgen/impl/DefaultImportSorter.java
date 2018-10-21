package com.github.jochenw.jsgen.impl;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.api.IImportSorter;
import com.github.jochenw.jsgen.api.JQName;

public class DefaultImportSorter implements IImportSorter {
	@Override
	public int compare(@Nonnull JQName o1, @Nonnull JQName o2) {
		return o1.getQName().compareToIgnoreCase(o2.getQName());
	}

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
