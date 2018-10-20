package com.github.jochenw.jsgen.api;

public interface JSGImportSorter {
	int getCategory(JSGQName pName);
	int compare(JSGQName pName1, JSGQName pName2);
}
