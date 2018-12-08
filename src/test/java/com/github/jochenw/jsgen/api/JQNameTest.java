package com.github.jochenw.jsgen.api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;

import com.github.jochenw.jsgen.api.JQName;

public class JQNameTest {
	@Test
	public void testInstanceFromClass() {
		final JQName name = JQName.valueOf(JQNameTest.class);
		assertNotNull(name);
		assertEquals("com.github.jochenw.jsgen.api", name.getPackageName());
		assertEquals("JQNameTest", name.getClassName());
		assertEquals(JQNameTest.class.getName(), name.getQName());
	}

	@Test
	public void testInstanceFromString() {
		final JQName name = JQName.valueOf(JQNameTest.class.getName());
		assertNotNull(name);
		assertEquals("com.github.jochenw.jsgen.api", name.getPackageName());
		assertEquals("JQNameTest", name.getClassName());
		assertEquals(JQNameTest.class.getName(), name.getQName());
	}

	@Test
	public void testPrimitiveClasses() {
		assertPrimitiveType(JQName.BOOLEAN_TYPE, Boolean.TYPE);
		assertPrimitiveType(JQName.BYTE_TYPE, Byte.TYPE);
		assertPrimitiveType(JQName.CHAR_TYPE, Character.TYPE);
		assertPrimitiveType(JQName.DOUBLE_TYPE, Double.TYPE);
		assertPrimitiveType(JQName.FLOAT_TYPE, Float.TYPE);
		assertPrimitiveType(JQName.INT_TYPE, Integer.TYPE);
		assertPrimitiveType(JQName.LONG_TYPE, Long.TYPE);
		assertPrimitiveType(JQName.SHORT_TYPE, Short.TYPE);
	}

	@Test
	public void testObjectClasses() {
		assertObjectType(JQName.BOOLEAN_OBJ, Boolean.class);
		assertObjectType(JQName.BYTE_OBJ, Byte.class);
		assertObjectType(JQName.CHAR_OBJ, Character.class);
		assertObjectType(JQName.DOUBLE_OBJ, Double.class);
		assertObjectType(JQName.FLOAT_OBJ, Float.class);
		assertObjectType(JQName.INT_OBJ, Integer.class);
		assertObjectType(JQName.LONG_OBJ, Long.class);
		assertObjectType(JQName.SHORT_OBJ, Short.class);
		assertObjectType(JQName.OBJECT, Object.class);
		assertObjectType(JQName.STRING, String.class);
		assertObjectType(JQName.COLLECTION, Collection.class);
		assertObjectType(JQName.SET, Set.class);
		assertObjectType(JQName.LIST, List.class);
		assertObjectType(JQName.MAP, Map.class);
		assertObjectType(JQName.ARRAYLIST, ArrayList.class);
		assertObjectType(JQName.HASHMAP, HashMap.class);
	}

	public static class InnerClass {
	}

	@Test
	public void testInnerClass() {
		final JQName n0 = JQName.valueOf("com.foo.myapp.Main");
		final JQName n1 = JQName.valueOf(n0, "Data");
		assertEquals("com.foo.myapp", n1.getPackageName());
		assertEquals("com.foo.myapp.Main.Data", n1.getQName());
		assertEquals("Main.Data", n1.getClassName());
		assertTrue(n1.isInnerClass());
		assertEquals(n0, n1.getOuterClass());
		final JQName n2 = JQName.valueOf(InnerClass.class);
		assertEquals("com.github.jochenw.jsgen.api", n2.getPackageName());
		assertEquals("JQNameTest.InnerClass", n2.getClassName());
		assertEquals("com.github.jochenw.jsgen.api.JQNameTest.InnerClass", n2.getQName());
		assertFalse(n2.isArray());
		assertFalse(n2.isPrimitive());
		assertTrue(n2.isInnerClass());
		assertEquals(JQName.valueOf(getClass()), n2.getOuterClass());
	}

	@Test
	public void testPseudoClass() {
		final JQName listClass = JQName.valueOf(List.class);
		final JQName genericListClass = JQName.genericValueOf(JQName.valueOf(List.class), "?");
		assertEquals("java.util", listClass.getPackageName());
		assertEquals("java.util", genericListClass.getPackageName());
		assertEquals("List", listClass.getClassName());
		assertEquals("List", genericListClass.getClassName());
		assertEquals("java.util.List", listClass.getQName());
		assertEquals("java.util.List", genericListClass.getQName());
		assertFalse(listClass.isPrimitive());
		assertFalse(genericListClass.isPrimitive());
		assertFalse(listClass.isArray());
		assertFalse(genericListClass.isArray());
		assertFalse(listClass.isInnerClass());
		assertFalse(genericListClass.isInnerClass());
		assertNull(listClass.getOuterClass());
		assertNull(genericListClass.getOuterClass());
		assertFalse(listClass.isPseudoClass());
		assertFalse(genericListClass.isPseudoClass());
		assertFalse(listClass.hasQualifiers());
		assertTrue(genericListClass.hasQualifiers());
		assertTrue(listClass.getQualifiers().isEmpty());
		assertFalse(genericListClass.getQualifiers().isEmpty());
		assertEquals(1, genericListClass.getQualifiers().size());
		final JQName name = genericListClass.getQualifiers().get(0);
		assertNotNull(name);
		assertEquals("", name.getPackageName());
		assertEquals("?", name.getClassName());
		assertEquals("?", name.getSimpleClassName());
		assertTrue(name.isPseudoClass());
		assertFalse(name.isPrimitive());
		assertFalse(name.isArray());
		assertFalse(name.isInnerClass());
		assertTrue(name.getQualifiers().isEmpty());
		assertNull(name.getOuterClass());
	}

	private void assertObjectType(@Nonnull JQName pType, @Nonnull Class<?> pClass) {
		assertFalse(pClass.isPrimitive());
		assertFalse(pType.isPrimitive());
		assertFalse(pClass.isMemberClass());
		assertFalse(pType.isInnerClass());
		assertNull(pType.getOuterClass());
		assertFalse(pClass.isArray());
		assertFalse(pType.isArray());
		assertEquals(pClass.getName(), pType.getQName());
		assertEquals(pClass.getSimpleName(), pType.getClassName());
		assertEquals(pClass.getName(), pType.getPackageName() + "." + pType.getClassName());
		assertEquals(pClass.getName(), pType.toString());
		assertFalse(pType.hasQualifiers());
		assertEquals(0, pType.getQualifiers().size());
		assertEquals(pType, JQName.valueOf(pClass));
	}

	private void assertPrimitiveType(@Nonnull JQName pType, @Nonnull Class<?> pClass) {
		assertTrue(pClass.isPrimitive());
		assertTrue(pType.isPrimitive());
		assertFalse(pClass.isMemberClass());
		assertFalse(pType.isInnerClass());
		assertNull(pType.getOuterClass());
		assertFalse(pClass.isArray());
		assertFalse(pType.isArray());
		assertEquals("", pType.getPackageName());
		assertEquals(pClass.getName(), pType.getClassName());
		assertEquals(pClass.getName(), pType.getQName());
		assertEquals(pClass.getName(), pType.toString());
		assertFalse(pType.hasQualifiers());
		assertEquals(0, pType.getQualifiers().size());
		assertSame(JQName.valueOf(pClass), pType);
	}

	@Test
	public void testMapEntry() {
		final JQName entryType = JQName.valueOf(Map.Entry.class);
		assertEquals("java.util.Map.Entry", entryType.getQName());
		assertEquals("java.util.Map$Entry", entryType.getClassLoaderName());
		assertEquals("java.util", entryType.getPackageName());
		assertEquals("Map.Entry", entryType.getClassName());
		assertEquals("Entry", entryType.getSimpleClassName());
	}

	@Test
	public void testErrors() {
		try {
			JQName.valueOf((String) null);
			fail("Expected Exception");
		} catch (NullPointerException e) {
			assertEquals("Qualified Name", e.getMessage());
		}

		try {
			JQName.valueOf((Class<?>) null);
			fail("Expected Exception");
		} catch (NullPointerException e) {
			assertEquals("Type", e.getMessage());
		}

		try {
			JQName.valueOf("MyType");
			fail("Expected Exception");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid class name (Missing package): MyType", e.getMessage());
		}
	}

	@Test
	public void testGenericTypes() {
		final JQName listOfStrings = JQName.LIST.qualifiedBy(JQName.STRING);
		assertNotNull(listOfStrings);
		assertFalse(listOfStrings.isPrimitive());
		assertFalse(listOfStrings.isInnerClass());
		assertFalse(listOfStrings.isArray());
		assertFalse(listOfStrings.isPseudoClass());
		assertEquals("java.util", listOfStrings.getPackageName());
		assertEquals("java.util.List", listOfStrings.getQName());
		assertEquals("java.util.List", listOfStrings.getClassLoaderName());
		assertEquals("List", listOfStrings.getClassName());
		assertEquals("List", listOfStrings.getSimpleClassName());
		assertTrue(listOfStrings.hasQualifiers());
		assertEquals(1, listOfStrings.getQualifiers().size());
		assertSame(JQName.STRING, listOfStrings.getQualifiers().get(0));

	
	}
}
