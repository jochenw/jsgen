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

import com.github.jochenw.jsgen.api.JSGQName;

public class JSGQNameTest {
	@Test
	public void testInstanceFromClass() {
		final JSGQName name = JSGQName.valueOf(JSGQNameTest.class);
		assertNotNull(name);
		assertEquals("com.github.jochenw.jsgen.api", name.getPackageName());
		assertEquals("JSGQNameTest", name.getClassName());
		assertEquals(JSGQNameTest.class.getName(), name.getQName());
	}

	@Test
	public void testInstanceFromString() {
		final JSGQName name = JSGQName.valueOf(JSGQNameTest.class.getName());
		assertNotNull(name);
		assertEquals("com.github.jochenw.jsgen.api", name.getPackageName());
		assertEquals("JSGQNameTest", name.getClassName());
		assertEquals(JSGQNameTest.class.getName(), name.getQName());
	}

	@Test
	public void testPrimitiveClasses() {
		assertPrimitiveType(JSGQName.BOOLEAN_TYPE, Boolean.TYPE);
		assertPrimitiveType(JSGQName.BYTE_TYPE, Byte.TYPE);
		assertPrimitiveType(JSGQName.CHAR_TYPE, Character.TYPE);
		assertPrimitiveType(JSGQName.DOUBLE_TYPE, Double.TYPE);
		assertPrimitiveType(JSGQName.FLOAT_TYPE, Float.TYPE);
		assertPrimitiveType(JSGQName.INT_TYPE, Integer.TYPE);
		assertPrimitiveType(JSGQName.LONG_TYPE, Long.TYPE);
		assertPrimitiveType(JSGQName.SHORT_TYPE, Short.TYPE);
	}

	@Test
	public void testObjectClasses() {
		assertObjectType(JSGQName.BOOLEAN_OBJ, Boolean.class);
		assertObjectType(JSGQName.BYTE_OBJ, Byte.class);
		assertObjectType(JSGQName.CHAR_OBJ, Character.class);
		assertObjectType(JSGQName.DOUBLE_OBJ, Double.class);
		assertObjectType(JSGQName.FLOAT_OBJ, Float.class);
		assertObjectType(JSGQName.INT_OBJ, Integer.class);
		assertObjectType(JSGQName.LONG_OBJ, Long.class);
		assertObjectType(JSGQName.SHORT_OBJ, Short.class);
		assertObjectType(JSGQName.OBJECT, Object.class);
		assertObjectType(JSGQName.STRING, String.class);
		assertObjectType(JSGQName.COLLECTION, Collection.class);
		assertObjectType(JSGQName.SET, Set.class);
		assertObjectType(JSGQName.LIST, List.class);
		assertObjectType(JSGQName.MAP, Map.class);
		assertObjectType(JSGQName.ARRAYLIST, ArrayList.class);
		assertObjectType(JSGQName.HASHMAP, HashMap.class);
	}

	public static class InnerClass {
	}

	@Test
	public void testInnerClass() {
		final JSGQName n0 = JSGQName.valueOf("com.foo.myapp.Main");
		final JSGQName n1 = JSGQName.valueOf(n0, "Data");
		assertEquals("com.foo.myapp", n1.getPackageName());
		assertEquals("com.foo.myapp.Main.Data", n1.getQName());
		assertEquals("Main.Data", n1.getClassName());
		assertTrue(n1.isInnerClass());
		assertEquals(n0, n1.getOuterClass());
		final JSGQName n2 = JSGQName.valueOf(InnerClass.class);
		assertEquals("com.github.jochenw.jsgen.api", n2.getPackageName());
		assertEquals("JSGQNameTest.InnerClass", n2.getClassName());
		assertEquals("com.github.jochenw.jsgen.api.JSGQNameTest.InnerClass", n2.getQName());
		assertFalse(n2.isArray());
		assertFalse(n2.isPrimitive());
		assertTrue(n2.isInnerClass());
		assertEquals(JSGQName.valueOf(getClass()), n2.getOuterClass());
	}

	@Test
	public void testPseudoClass() {
		final JSGQName listClass = JSGQName.valueOf(List.class);
		final JSGQName genericListClass = JSGQName.genericValueOf(JSGQName.valueOf(List.class), "?");
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
		final JSGQName name = genericListClass.getQualifiers().get(0);
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

	private void assertObjectType(@Nonnull JSGQName pType, @Nonnull Class<?> pClass) {
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
		assertEquals(pType, JSGQName.valueOf(pClass));
	}

	private void assertPrimitiveType(@Nonnull JSGQName pType, @Nonnull Class<?> pClass) {
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
		assertSame(JSGQName.valueOf(pClass), pType);
	}

	
	@Test
	public void testErrors() {
		try {
			JSGQName.valueOf((String) null);
			fail("Expected Exception");
		} catch (NullPointerException e) {
			assertEquals("Qualified Name", e.getMessage());
		}

		try {
			JSGQName.valueOf((Class<?>) null);
			fail("Expected Exception");
		} catch (NullPointerException e) {
			assertEquals("Type", e.getMessage());
		}

		try {
			JSGQName.valueOf("MyType");
			fail("Expected Exception");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid class name (Missing package): MyType", e.getMessage());
		}
	}
}
