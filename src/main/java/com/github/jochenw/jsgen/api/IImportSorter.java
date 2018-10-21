package com.github.jochenw.jsgen.api;

public interface IImportSorter {
	int getCategory(JQName pName);
	int compare(JQName pName1, JQName pName2);
}
