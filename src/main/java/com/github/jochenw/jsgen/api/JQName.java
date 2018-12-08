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


/** <p>This object represents a Java types name. The object doesn't provide any additional
 * information about the class. For example, it doesn't know about inheritance (super,
 * or sub classes), implemented interfaces, and the like. Mainly, it serves as a means
 * to manage automatic imports.</p>
 * <p>To create an instance, use {@link #valueOf(Class)}, or {@link #valueOf(String)}.
 * (The former is recommended, because it established a compile time dependency between
 * the source code generator, and the respective class. This will be helpfull, if you
 * are in maintenance mode, and are looking for pieces of code, which are related to the
 * class.</p>
 */
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

	/** Returns the Java types package name. The package name is the fully qualified
	 * class name, minus the simple class name. For example, in the case of
	 * {@code java.util.Map.Entry}, the package name would be "java.util".
	 * @return The package name.
	 */
	public String getPackageName() {
		return packageName;
	}

	/** Returns the innermost component of the class name. For example, in the case
	 * of {@code java.util.Map.Entry}, the simple class name would be "Entry".
	 * @return The simple class name.
	 */
	public String getSimpleClassName() {
		return simpleClassName;
	}

	/** Returns the fully qualified class name. For example, in the case
	 * of {@code java.util.Map.Entry}, the fully qualified class name would be
	 * "java.util.Map.Entry".
	 * @return The fully qualified class name.
	 */
	@Override
	public String getQName() {
		return qName;
	}

	/** Returns the class name, excluding the package name. For example, in the case
	 * of {@code java.util.Map.Entry}, the class name would be
	 * "Map.Entry".
	 * @return The class name.
	 */
	public String getClassName() {
		return className;
	}

	/** Returns, whether the current type is primitive.
	 * @return True, if this type is primitive, otherwise false.
	 */
	public boolean isPrimitive() {
		return primitive;
	}

	/** Returns, whether the current type is an array type.
	 * @return True, if this type is an array type, otherwise false.
	 */
	public boolean isArray() {
		return array;
	}

	/** Returns, whether the current type is an inner class.
	 * @return True, if this type is an inner class, otherwise false.
	 */
	public boolean isInnerClass() {
		return outerClassName != null;
	}
	
	/** Returns, whether the current type is a pseudo class.
	 * Examples for pseudo classes, are "?" (as in List<?>), or "O extends Object".
	 * @return True, if this type is a pseudo class, otherwise false.
	 */
	public boolean isPseudoClass() {
		return pseudo;
	}
	
	/** True, if this type has qualifiers. Or, in other words, if this type
	 * is a generic type, qualified by another type.
	 * Examples:
	 * <ul>
	 *   <li>"java.util.List" has no qualifiers.</li>
	 *   <li>"java.util.List<java.lang.Object> has a single qualifier
	 *     ("java.lang.Object")</li>
	 *   <li>"java.util.Map<java.lang.String,java.lang.Object>" has two
	 *     qualifiers ("java.lang.String", and "java.lang.Object", respectively)</li>
	 *   <li>"java.util.List<?>" has a single qualifier (the pseudo class "?")</li>
	 *   <li>"java.util.List<? extends Map>" has a single qualifier (the pseudo
	 *     class "? extends Map")</li>
	 * </ul>
	 * @return True, if this type has qualifiers, otherwise false.
	 */
	public boolean hasQualifiers() {
		return !qualifiers.isEmpty();
	}

	/** Returns this types outer type. For example, in the case of "java.util.Map.Entry",
	 * the outer type would be "java.util.Map".
	 * @return The outer type, if this is an inner class, otherwise null.
	 */
	@Nullable public JQName getOuterClass() {
		return outerClassName;
	}
	
	/** Returns this types qualifiers.
	 * Examples:
	 * <ul>
	 *   <li>"java.util.List" has no qualifiers (empty list returned)</li>
	 *   <li>"java.util.List<java.lang.Object> has a single qualifier
	 *     (singleton list "java.lang.Object" returned)</li>
	 *   <li>"java.util.Map<java.lang.String,java.lang.Object>" has two
	 *     qualifiers (list of "java.lang.String", and "java.lang.Object", returned)</li>
	 *   <li>"java.util.List<?>" has a single qualifier (the pseudo class "?")</li>
	 *   <li>"java.util.List<? extends Map>" has a single qualifier (the pseudo
	 *     class "? extends Map")</li>
	 * </ul>
	 * @return List of qualifiers, if this type has any, otherwise an empty
	 *   list. Never null.
	 */
	@Nonnull public List<JQName> getQualifiers() {
		return qualifiers;
	}
	/** Returns a type, which represents the given Java class.
	 * @param pType The Java class being converted into a JQName.
	 * @return An instance of {@link JQName}, representing the given Java class.
	 */
	public static JQName valueOf(@Nonnull Class<?> pType) {
		return valueOf(Objects.requireNonNull(pType, "Type").getName());
	}

	/** Returns a type, which represents the given Java class, with the given
	 * qualifiers.
	 * @param pType The Java class being converted into a JQName, and the given
	 *   qualifiers applied.
	 * @param pQualifiers List of qualifiers, that the returned 
	 * @return An instance of {@link JQName}, representing the given Java class,
	 *   with the given qualifiers applied.
	 */
	public static JQName valueOf(@Nonnull Class<?> pType, Object... pQualifiers) {
		return genericValueOf(Objects.requireNonNull(pType, "Type").getName(), pQualifiers);
	}

	/** Returns a type, which represents the Java class, given by its fully
	 *   qualified class name {@code pQName}.
	 * @param pQName The Java class being converted into a JQName.
	 * @return An instance of {@link JQName}, representing the given Java class.
	 */
	public static JQName valueOf(@Nonnull String pQName) {
		return valueOf(pQName, Collections.emptyList());
	}

	/** Returns a type, which represents the given Java class, with the given
	 * pseudo types as qualifiers.
	 * @param pType The Java class being converted into a JQName, and the given
	 *   qualifiers applied.
	 * @param pPseudoQualifiers List of qualifiers, that the returned type will have. 
	 * @return An instance of {@link JQName}, representing the given Java class,
	 *   with the given qualifiers applied.
	 */
	@Nonnull public static JQName genericValueOf(@Nonnull JQName pType, @Nonnull String... pPseudoQualifiers) {
		Objects.requireAllNonNull(pPseudoQualifiers, "Pseudo Qualifier");
		if (pPseudoQualifiers == null  ||  pPseudoQualifiers.length == 0) {
			return pType;
		}
		final List<JQName> qualifiers = new ArrayList<>(pPseudoQualifiers.length);
		for (int i = 0;  i < pPseudoQualifiers.length;  i++) {
			final String s = pPseudoQualifiers[i];
			final JQName qName = new JQName("", s, s, Collections.emptyList(), false, false, null, true);
			qualifiers.add(qName);
		}
		return new JQName(pType.getPackageName(), pType.getClassName(), pType.getQName(),
				          qualifiers, false, pType.isArray(), null, false);
	}

	/** Returns a type, which represents the given Java class, with the given
	 * types as qualifiers.
	 * @param pType The Java class being converted into a JQName, and the given
	 *   qualifiers applied.
	 * @param pQualifiers List of qualifiers, that the returned type will have. 
	 * @return An instance of {@link JQName}, representing the given Java class,
	 *   with the given qualifiers applied.
	 */
	@Nonnull public static JQName genericValueOf(@Nonnull String pType, @Nonnull Object... pQualifiers) {
		if (pQualifiers == null  ||  pQualifiers.length == 0) {
			return valueOf(pType);
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
		return valueOf(pType, qualifiers);
	}

	/** Returns the current types name, as used by ClassLoaders. For example,
	 * the type "java.util.Map.Entry" would yield "java.util.Map$Entry".
	 * @return The classloader version of the current types fully qualified
	 *   name.
	 */
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

	/** Returns a type, which represents an inner class of the given outer type.
	 * @param pOuterType The type, for which an inner class type is being constructed.
	 * @param pInnerName The new inner types simple class name.
	 * @return  A new type with the following properties:
	 * <ul>
	 *   <li>The {@link #getPackageName() package name} is the outer types package name.</li>
	 *   <li>The {@link #getSimpleClassName() simple name} is {@code pInnerName}.</li>
	 *   <li>The {@link #getClassName() complete class name} is the outer types complete
	 *     class name, with ".", and {@code pInnerName} appended.</li>
	 *   <li>The {@link #getOuterClass() outer type} is {@code pOuterType}.</li>
	 *   <li>The method {@link #isInnerClass()} returns true.</li>
	 * </ul>
	 */
	@Nonnull public static JQName valueOf(@Nonnull JQName pOuterType, @Nonnull String pInnerName) {
		return new JQName(pOuterType.getPackageName(), pOuterType.getClassName() + "." + pInnerName,
				            pOuterType.getQName() + "." + pInnerName,
				            Collections.emptyList(), false, false, pOuterType);
				            
	}

	/** Returns a type, which is an array. The array elements are instances of the current type.
	 * @return A type representing the array class of the current types class.
	 */
	@Nonnull public JQName arrayOf() {
		return new JQName(packageName, className, qName, Collections.emptyList(), primitive, true, null);
	}

	/** Returns a type, which is the current type, with the given qualifiers applied.
	 * Example 1: If the current type is "java.util.Map", and the qualifiers are
	 * "java.lang.String", and "java.lang.Object", then the result will be
	 * "java.util.Map<java.lang.String,java.lang.Object>".
	 * Example 2: If the current type is "java.util.Map<java.lang.String>", and the
	 * list of qualifiers has a single element "java.lang.Object", then the result
	 * will also be "java.util.Map<java.lang.String,java.lang.Object>". (Of course,
	 * there is no valid Java type "java.util.Map<java.lang.String>", but the
	 * representation is possible nevertheless.
	 * @param pQualifiers The list of qualifiers, being added to the result
	 *   types list.
	 * @return A new type, with the same base type, and a list of qualifiers,
	 *   which is built from the current types list, and the given list
	 *   appended.
	 */
	@Nonnull public JQName qualifiedBy(Object... pQualifiers) {
		final List<JQName> qualifiers = new ArrayList<>();
		qualifiers.addAll(getQualifiers());
		if (pQualifiers != null) {
			Objects.requireAllNonNull(pQualifiers, "Qualifier");
			for (int i = 0;  i < pQualifiers.length;  i++) {
				final Object o = pQualifiers[i];
				if (o instanceof JQName) {
					qualifiers.add((JQName) o);
				} else if (o instanceof Class) {
					final Class<?> cl = (Class<?>) o;
					qualifiers.add(JQName.valueOf(cl));
				} else if (o instanceof String) {
					final String s = (String) o;
					qualifiers.add(new JQName("", s, s, Collections.emptyList(), false, false, null, true));
				} else {
					throw new IllegalArgumentException("Invalid qualifier type: " + o.getClass().getName());
				}
			}
		}
		return new JQName(packageName, className, qName, qualifiers, false, isArray(), null);
	}

	private JQName(String pName) {
		this("", pName, pName, Collections.emptyList(), true, false, null);
	}

	/** Representation of the type {@link ArrayList}.
	 */
	public static final JQName ARRAYLIST = valueOf(ArrayList.class);
	/** Representation of the type {@link Boolean}.
	 * @see #BOOLEAN_TYPE
	 */
	public static final JQName BOOLEAN_OBJ = valueOf(Boolean.class);
	/** Representation of the primitive type "boolean".
	 * @see #BOOLEAN_OBJ
	 */
	public static final JQName BOOLEAN_TYPE = new JQName("boolean");
	/** Representation of the type {@link Byte}.
	 * @see #BYTE_TYPE
	 */
	public static final JQName BYTE_OBJ = valueOf(Byte.class);
	/** Representation of the primitive type "byte".
	 * @see #BYTE_OBJ
	 */
	public static final JQName BYTE_TYPE = new JQName("byte");
	/** Representation of the type {@link Character}.
	 * @see #CHAR_TYPE
	 */
	public static final JQName CHAR_OBJ = valueOf(Character.class);
	/** Representation of the primitive type "char".
	 * @see #CHAR_OBJ
	 */
	public static final JQName CHAR_TYPE = new JQName("char");
	/** Representation of the type {@link Collection}.
	 */
	public static final JQName COLLECTION = valueOf(Collection.class);
	/** Representation of the type {@link Double}.
	 * @see #DOUBLE_TYPE
	 */
	public static final JQName DOUBLE_OBJ = valueOf(Double.class);
	/** Representation of the primitive type "double".
	 * @see #DOUBLE_OBJ
	 */
	public static final JQName DOUBLE_TYPE = new JQName("double");
	/** Representation of the type {@link Float}.
	 * @see #FLOAT_TYPE
	 */
	public static final JQName FLOAT_OBJ = valueOf(Float.class);
	/** Representation of the primitive type "float".
	 * @see #FLOAT_OBJ
	 */
	public static final JQName FLOAT_TYPE = new JQName("float");
	/** Representation of the type {@link HashMap}.
	 */
	public static final JQName HASHMAP = valueOf(HashMap.class);
	/** Representation of the type {@link Integer}.
	 * @see #INT_TYPE
	 */
	public static final JQName INT_OBJ = valueOf(Integer.class);
	/** Representation of the primitive type "int".
	 * @see #INT_OBJ
	 */
	public static final JQName INT_TYPE = new JQName("int");
	/** Representation of the type {@link Map}.
	 */
	public static final JQName MAP = valueOf(Map.class);
	/** Representation of the type {@link List}.
	 */
	public static final JQName LIST = valueOf(List.class);
	/** Representation of the type {@link Long}.
	 * @see #LONG_TYPE
	 */
	public static final JQName LONG_OBJ = valueOf(Long.class);
	/** Representation of the primitive type "long".
	 * @see #LONG_OBJ
	 */
	public static final JQName LONG_TYPE = new JQName("long");
	/** Representation of the type {@link Object}.
	 * @see #OBJECT_ARRAY
	 */
	public static final JQName OBJECT = valueOf(Object.class);
	/** Representation of the type {@link Object}[].
	 */
	public static final JQName OBJECT_ARRAY = OBJECT.arrayOf();
	/** Representation of the type {@link Set}.
	 */
	public static final JQName SET = valueOf(Set.class);
	/** Representation of the type {@link Short}.
	 * @see #SHORT_TYPE
	 */
	public static final JQName SHORT_OBJ = valueOf(Short.class);
	/** Representation of the primitive type "short".
	 * @see #SHORT_OBJ
	 */
	public static final JQName SHORT_TYPE = new JQName("short");
	/** Representation of the type {@link String}.
	 * @see #STRING_ARRAY
	 */
	public static final JQName STRING = valueOf(String.class);
	/** Representation of the type {@link String}[].
	 */
	public static final JQName STRING_ARRAY = STRING.arrayOf();
	/** Representation of the type {@link Void}.
	 * @see #VOID_TYPE
	 */
	public static final JQName VOID_OBJ = valueOf(Void.class);
	/** Representation of the primitive type "void".
	 * @see #VOID_OBJ
	 */
	public static final JQName VOID_TYPE = new JQName("void");
}
