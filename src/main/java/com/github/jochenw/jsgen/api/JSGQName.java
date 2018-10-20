package com.github.jochenw.jsgen.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.util.Objects;


public class JSGQName implements JSGLocation {
	@Nonnull private final String packageName;
	@Nonnull private final String qName;
	@Nonnull private final String className;
	@Nonnull private final List<JSGQName> qualifiers;
	private final boolean primitive, array, pseudo;
	@Nullable private final JSGQName outerClassName;
	@Nonnull private final String simpleClassName;


	JSGQName(@Nonnull String pPackageName, @Nonnull String pClassName, @Nonnull String pQName, @Nonnull List<JSGQName> pQualifiers,
			 boolean pPrimitive, boolean pArray, @Nullable JSGQName pOuterClassName, boolean pPseudo) {
		packageName = Objects.requireNonNull(pPackageName, "Package Name");
		className = Objects.requireNonNull(pClassName, "Class Name");
		qName = Objects.requireNonNull(pQName, "Qualified Name");
		qualifiers = pQualifiers;
		primitive = pPrimitive;
		array = pArray;
		outerClassName = pOuterClassName;
		final int offset = className.lastIndexOf('.');
		if (offset == -1) {
			simpleClassName = className;
		} else {
			simpleClassName = className.substring(offset+1);
		}
		pseudo = pPseudo;
	}

	JSGQName(@Nonnull String pPackageName, @Nonnull String pClassName, @Nonnull String pQName, @Nonnull List<JSGQName> pQualifiers,
			 boolean pPrimitive, boolean pArray, @Nullable JSGQName pOuterClassName) {
		this(pPackageName, pClassName, pQName, pQualifiers, pPrimitive, pArray, pOuterClassName, false);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(className, packageName, qName, qualifiers, outerClassName);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JSGQName other = (JSGQName) obj;
		if (!className.equals(other.className)  ||  !packageName.equals(other.packageName)  ||  !qName.equals(other.qName)) {
			return false;
		}
		if (qualifiers.size() != other.qualifiers.size()) {
			return false;
		}
		if (outerClassName == null) {
			if (other.outerClassName != null) {
				return false;
			}
		} else {
			@Nonnull final JSGQName ocn = Objects.requireNonNull(outerClassName, "Outer Class Name"); 
			if (!ocn.equals(other.outerClassName)) {
				return false;
			}
		}
		for (int i = 0;  i < other.qualifiers.size();  i++) {
			if (!qualifiers.get(i).equals(other.qualifiers.get(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		final String s;
		if (qualifiers.isEmpty()) {
			s = qName;
		} else {
			final StringBuilder sb = new StringBuilder();
			sb.append(qName);
			sb.append('<');
			for (int i = 0;  i < qualifiers.size();  i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(qualifiers.get(i));
			}
			sb.append('>');
			s = sb.toString();
		}
		if (array) {
			return s + "[]";
		} else {
			return s;
		}
	}

	public String getPackageName() {
		return packageName;
	}

	public String getSimpleClassName() {
		return simpleClassName;
	}

	@Override
	public String getQName() {
		return qName;
	}

	public String getClassName() {
		return className;
	}

	public boolean isPrimitive() {
		return primitive;
	}

	public boolean isArray() {
		return array;
	}

	public boolean isInnerClass() {
		return outerClassName != null;
	}
	
	public boolean isPseudoClass() {
		return pseudo;
	}
	
	public boolean hasQualifiers() {
		return !qualifiers.isEmpty();
	}

	@Nullable public JSGQName getOuterClass() {
		return outerClassName;
	}
	
	public List<JSGQName> getQualifiers() {
		return qualifiers;
	}
	public static JSGQName valueOf(@Nonnull Class<?> pType) {
		return valueOf(Objects.requireNonNull(pType, "Type").getName());
	}

	public static JSGQName valueOf(@Nonnull Class<?> pType, Object... pQualifiers) {
		return genericValueOf(Objects.requireNonNull(pType, "Type").getName(), pQualifiers);
	}

	public static JSGQName valueOf(@Nonnull String pQName) {
		return valueOf(pQName, Collections.emptyList());
	}

	@Nonnull public static JSGQName genericValueOf(@Nonnull JSGQName pQName, @Nonnull String... pPseudoQualifiers) {
		Objects.requireAllNonNull(pPseudoQualifiers, "Pseudo Qualifier");
		if (pPseudoQualifiers == null  ||  pPseudoQualifiers.length == 0) {
			return pQName;
		}
		final List<JSGQName> qualifiers = new ArrayList<>(pPseudoQualifiers.length);
		for (int i = 0;  i < pPseudoQualifiers.length;  i++) {
			final String s = pPseudoQualifiers[i];
			final JSGQName qName = new JSGQName("", s, s, Collections.emptyList(), false, false, null, true);
			qualifiers.add(qName);
		}
		return new JSGQName(pQName.getPackageName(), pQName.getClassName(), pQName.getQName(), qualifiers, false, pQName.isArray(), null, false);
	}
	
	@Nonnull public static JSGQName genericValueOf(@Nonnull String pQName, @Nonnull Object... pQualifiers) {
		if (pQualifiers == null  ||  pQualifiers.length == 0) {
			return valueOf(pQName);
		}
		final List<JSGQName> qualifiers = new ArrayList<>(pQualifiers.length);
		for (int i = 0;  i < pQualifiers.length;  i++) {
			final Object o = pQualifiers[i];
			if (o == null) {
				throw new NullPointerException("Qualifier[" + i+ "] must not be null.");
			}
			if (o instanceof JSGQName) {
				qualifiers.add((JSGQName) o);
			} else if (o instanceof Class<?>) {
				qualifiers.add(valueOf((Class<?>) o));
			} else if (o instanceof String) {
				qualifiers.add(valueOf((String) o));
			} else {
				throw new IllegalArgumentException("Qualifier[" + i + "] has invalid type: " + o.getClass().getName());
			}
		}
		return valueOf(pQName, qualifiers);
	}

	private static final JSGQName valueOf(@Nonnull String pQName, List<JSGQName> pQualifiers) {
		if ("boolean".equals(pQName)) {
			return BOOLEAN_TYPE;
		} else if ("int".equals(pQName)) {
			return INT_TYPE;
		} else if ("long".equals(pQName)) {
			return LONG_TYPE;
		} else if ("float".equals(pQName)) {
			return FLOAT_TYPE;
		} else if ("double".equals(pQName)) {
			return DOUBLE_TYPE;
		} else if ("short".equals(pQName)) {
			return SHORT_TYPE;
		} else if ("byte".equals(pQName)) {
			return BYTE_TYPE;
		} else if ("char".equals(pQName)) {
			return CHAR_TYPE;
		} else if ("void".equals(pQName)) {
			return VOID_TYPE;
		} else {
			@Nonnull final String qName = Objects.requireNonNull(pQName, "Qualified Name");
			final int offset = qName.lastIndexOf('.');
			if (offset == -1) {
				throw new IllegalArgumentException("Invalid class name (Missing package): " + qName);
			} else {
				final String packageName = qName.substring(0, offset);
				final String className = qName.substring(offset+1);
				final int offset2 = qName.lastIndexOf('$');
				if (offset2 == -1) {
					return new JSGQName(packageName, className, qName, pQualifiers, false, false, null);
				} else {
					final String innerClassName = qName.substring(offset2+1);
					final String outerClassName = qName.substring(0, offset2);
					return valueOf(valueOf(outerClassName), innerClassName);
				}
			}
		}
	}

	@Nonnull public static JSGQName valueOf(@Nonnull JSGQName pParentClassName, @Nonnull String pInnerName) {
		return new JSGQName(pParentClassName.getPackageName(), pParentClassName.getClassName() + "." + pInnerName,
				            pParentClassName.getQName() + "." + pInnerName,
				            Collections.emptyList(), false, false, pParentClassName);
				            
	}

	@Nonnull public JSGQName arrayOf() {
		return new JSGQName(packageName, className, qName, Collections.emptyList(), primitive, true, null);
	}
	
	private JSGQName(String pName) {
		this("", pName, pName, Collections.emptyList(), true, false, null);
	}

	public static final JSGQName ARRAYLIST = valueOf(ArrayList.class);
	public static final JSGQName BOOLEAN_OBJ = valueOf(Boolean.class);
	public static final JSGQName BOOLEAN_TYPE = new JSGQName("boolean");
	public static final JSGQName BYTE_OBJ = valueOf(Byte.class);
	public static final JSGQName BYTE_TYPE = new JSGQName("byte");
	public static final JSGQName CHAR_OBJ = valueOf(Character.class);
	public static final JSGQName CHAR_TYPE = new JSGQName("char");
	public static final JSGQName COLLECTION = valueOf(Collection.class);
	public static final JSGQName DOUBLE_OBJ = valueOf(Double.class);
	public static final JSGQName DOUBLE_TYPE = new JSGQName("double");
	public static final JSGQName FLOAT_OBJ = valueOf(Float.class);
	public static final JSGQName FLOAT_TYPE = new JSGQName("float");
	public static final JSGQName HASHMAP = valueOf(HashMap.class);
	public static final JSGQName INT_OBJ = valueOf(Integer.class);
	public static final JSGQName INT_TYPE = new JSGQName("int");
	public static final JSGQName MAP = valueOf(Map.class);
	public static final JSGQName LIST = valueOf(List.class);
	public static final JSGQName LONG_OBJ = valueOf(Long.class);
	public static final JSGQName LONG_TYPE = new JSGQName("long");
	public static final JSGQName OBJECT = valueOf(Object.class);
	public static final JSGQName SET = valueOf(Set.class);
	public static final JSGQName SHORT_OBJ = valueOf(Short.class);
	public static final JSGQName SHORT_TYPE = new JSGQName("short");
	public static final JSGQName STRING = valueOf(String.class);
	public static final JSGQName STRING_ARRAY = STRING.arrayOf();
	public static final JSGQName VOID_TYPE = new JSGQName("void");
}
