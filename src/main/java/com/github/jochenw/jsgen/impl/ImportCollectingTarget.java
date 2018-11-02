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

public class ImportCollectingTarget implements JSGSourceTarget {
	public static class CountedName {
		private int count;
		private final JQName name;
		private final CountedName next;

		public CountedName(JQName pName, CountedName pNext) {
			name = pName;
			next = pNext;
		}
	}

	private final Map<String,CountedName> countedNames = new HashMap<>();
	private Set<JQName> importedNames;
	private Function<Object,Object> filter;
	private JQName importingClass;

	public JQName getImportingClass() {
		return importingClass;
	}

	public void setImportingClass(JQName importingClass) {
		this.importingClass = importingClass;
	}

	public Function<Object, Object> getFilter() {
		return filter;
	}

	public void setFilter(Function<Object, Object> filter) {
		this.filter = filter;
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

	public List<JQName> getImportedNames() {
		close();
		return new ArrayList<>(importedNames);
	}
}
