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


public class JQName implements ILocation {
	@Nonnull private final String packageName;
	@Nonnull private final String qName;
	@Nonnull private final String className;
	@Nonnull private final List<JQName> qualifiers;
	private final boolean primitive, array, pseudo;
	@Nullable private final JQName outerClassName;
	@Nonnull private final String simpleClassName;


	JQName(@Nonnull String pPackageName, @Nonnull String pClassName, @Nonnull String pQName, @Nonnull List<JQName> pQualifiers,
			 boolean pPrimitive, boolean pArray, @Nullable JQName pOuterClassName, boolean pPseudo) {
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

	JQName(@Nonnull String pPackageName, @Nonnull String pClassName, @Nonnull String pQName, @Nonnull List<JQName> pQualifiers,
			 boolean pPrimitive, boolean pArray, @Nullable JQName pOuterClassName) {
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
		JQName other = (JQName) obj;
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
			@Nonnull final JQName ocn = Objects.requireNonNull(outerClassName, "Outer Class Name"); 
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

	@Nullable public JQName getOuterClass() {
		return outerClassName;
	}
	
	public List<JQName> getQualifiers() {
		return qualifiers;
	}
	public static JQName valueOf(@Nonnull Class<?> pType) {
		return valueOf(Objects.requireNonNull(pType, "Type").getName());
	}

	public static JQName valueOf(@Nonnull Class<?> pType, Object... pQualifiers) {
		return genericValueOf(Objects.requireNonNull(pType, "Type").getName(), pQualifiers);
	}

	public static JQName valueOf(@Nonnull String pQName) {
		return valueOf(pQName, Collections.emptyList());
	}

	@Nonnull public static JQName genericValueOf(@Nonnull JQName pQName, @Nonnull String... pPseudoQualifiers) {
		Objects.requireAllNonNull(pPseudoQualifiers, "Pseudo Qualifier");
		if (pPseudoQualifiers == null  ||  pPseudoQualifiers.length == 0) {
			return pQName;
		}
		final List<JQName> qualifiers = new ArrayList<>(pPseudoQualifiers.length);
		for (int i = 0;  i < pPseudoQualifiers.length;  i++) {
			final String s = pPseudoQualifiers[i];
			final JQName qName = new JQName("", s, s, Collections.emptyList(), false, false, null, true);
			qualifiers.add(qName);
		}
		return new JQName(pQName.getPackageName(), pQName.getClassName(), pQName.getQName(), qualifiers, false, pQName.isArray(), null, false);
	}
	
	@Nonnull public static JQName genericValueOf(@Nonnull String pQName, @Nonnull Object... pQualifiers) {
		if (pQualifiers == null  ||  pQualifiers.length == 0) {
			return valueOf(pQName);
		}
		final List<JQName> qualifiers = new ArrayList<>(pQualifiers.length);
		for (int i = 0;  i < pQualifiers.length;  i++) {
			final Object o = pQualifiers[i];
			if (o == null) {
				throw new NullPointerException("Qualifier[" + i+ "] must not be null.");
			}
			if (o instanceof JQName) {
				qualifiers.add((JQName) o);
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

	public String getClassLoaderName() {
		if (isInnerClass()) {
			return getPackageName() + "." + getClassName().replace('.', '$');
		} else {
			return getQName();
		}
	}

	
	private static final JQName valueOf(@Nonnull String pQName, List<JQName> pQualifiers) {
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
					return new JQName(packageName, className, qName, pQualifiers, false, false, null);
				} else {
					final String innerClassName = qName.substring(offset2+1);
					final String outerClassName = qName.substring(0, offset2);
					return valueOf(valueOf(outerClassName), innerClassName);
				}
			}
		}
	}

	@Nonnull public static JQName valueOf(@Nonnull JQName pParentClassName, @Nonnull String pInnerName) {
		return new JQName(pParentClassName.getPackageName(), pParentClassName.getClassName() + "." + pInnerName,
				            pParentClassName.getQName() + "." + pInnerName,
				            Collections.emptyList(), false, false, pParentClassName);
				            
	}

	@Nonnull public JQName arrayOf() {
		return new JQName(packageName, className, qName, Collections.emptyList(), primitive, true, null);
	}
	
	private JQName(String pName) {
		this("", pName, pName, Collections.emptyList(), true, false, null);
	}

	public static final JQName ARRAYLIST = valueOf(ArrayList.class);
	public static final JQName BOOLEAN_OBJ = valueOf(Boolean.class);
	public static final JQName BOOLEAN_TYPE = new JQName("boolean");
	public static final JQName BYTE_OBJ = valueOf(Byte.class);
	public static final JQName BYTE_TYPE = new JQName("byte");
	public static final JQName CHAR_OBJ = valueOf(Character.class);
	public static final JQName CHAR_TYPE = new JQName("char");
	public static final JQName COLLECTION = valueOf(Collection.class);
	public static final JQName DOUBLE_OBJ = valueOf(Double.class);
	public static final JQName DOUBLE_TYPE = new JQName("double");
	public static final JQName FLOAT_OBJ = valueOf(Float.class);
	public static final JQName FLOAT_TYPE = new JQName("float");
	public static final JQName HASHMAP = valueOf(HashMap.class);
	public static final JQName INT_OBJ = valueOf(Integer.class);
	public static final JQName INT_TYPE = new JQName("int");
	public static final JQName MAP = valueOf(Map.class);
	public static final JQName LIST = valueOf(List.class);
	public static final JQName LONG_OBJ = valueOf(Long.class);
	public static final JQName LONG_TYPE = new JQName("long");
	public static final JQName OBJECT = valueOf(Object.class);
	public static final JQName SET = valueOf(Set.class);
	public static final JQName SHORT_OBJ = valueOf(Short.class);
	public static final JQName SHORT_TYPE = new JQName("short");
	public static final JQName STRING = valueOf(String.class);
	public static final JQName STRING_ARRAY = STRING.arrayOf();
	public static final JQName VOID_TYPE = new JQName("void");
}
