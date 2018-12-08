package com.github.jochenw.jsgen.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.util.AbstractBuilder;


/** This object represents an arbitrary class, which is being generated.
 * @param <T> The actual object type. Used to specify the return type for builder methods.
 */
public abstract class JSGClass<T extends JSGClass<T>> extends AbstractBuilder<T> implements IAnnotatable, IProtectable<T>, ICommentOwner {
	private final JQName type;
	private final List<Object> content = new ArrayList<>();
	private final AnnotationSet annotations = new AnnotationSet();
	private Protection protection;
	private final List<JQName> extendedClasses = new ArrayList<>();
	private final List<JQName> implementedInterfaces = new ArrayList<>();
	private boolean isInterface;
	private Comment comment;

	protected JSGClass(JQName pType) {
		type = pType;
	}

	/** Returns the classes type.
	 * @return The classes fully qualified name.
	 */
	public JQName getType() {
		return type;
	}

	@Override
	public AnnotationSet getAnnotations() {
		return annotations;
	}

	@Nonnull public T protection(@Nonnull IProtectable.Protection pProtection) {
		assertMutable();
		protection = pProtection;
		return self();
	}

	@Nonnull public T makePublic() {
		return protection(Protection.PUBLIC);
	}

	@Nonnull public T makeProtected() {
		return protection(Protection.PROTECTED);
	}

	@Nonnull public T makePackagePrivate() {
		return protection(Protection.PACKAGE);
	}

	@Nonnull public T makePrivate() {
		return protection(Protection.PRIVATE);
	}

	@Override
	@Nonnull public IProtectable.Protection getProtection() {
		return protection;
	}

	/** Creates a new member field with the given type, and name, and
	 * "package private" protection. Equivalent to
	 * {@code newField(pType, pName, Protection.PACKAGE)}.
	 * @param pType The fields type.
	 * @param pName The fields name.
	 * @return The created member field, a builder object, which can be
	 *   used for further configuration.
	 * @see #newField(Class, String)
	 * @see #newField(String, String)
	 * @see #newField(JQName, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 * @see #newField(Class, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 * @see #newField(String, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 */
	@Nonnull public Field newField(@Nonnull JQName pType, @Nonnull String pName) {
		return newField(pType, pName, Protection.PACKAGE);
	}

	/** Creates a new member field with the given type, and name, and
	 * "package private" protection. Equivalent to
	 * {@code newField(pType, pName, Protection.PACKAGE)}.
	 * @param pType The fields type.
	 * @param pName The fields name.
	 * @return The created member field, a builder object, which can be
	 *   used for further configuration.
	 * @see #newField(JQName, String)
	 * @see #newField(String, String)
	 * @see #newField(JQName, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 * @see #newField(Class, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 * @see #newField(String, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 */
	@Nonnull public Field newField(@Nonnull Class<?> pType, @Nonnull String pName) {
		return newField(JQName.valueOf(pType), pName, Protection.PACKAGE);
	}

	/** Creates a new member field with the given type, and name, and
	 * "package private" protection. Equivalent to
	 * {@code newField(pType, pName, Protection.PACKAGE)}.
	 * @param pType The fields type.
	 * @param pName The fields name.
	 * @return The created member field, a builder object, which can be
	 *   used for further configuration.
	 * @see #newField(JQName, String)
	 * @see #newField(Class, String)
	 * @see #newField(JQName, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 * @see #newField(Class, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 * @see #newField(String, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 */
	@Nonnull public Field newField(@Nonnull String pType, @Nonnull String pName) {
		return newField(JQName.valueOf(pType), pName, Protection.PACKAGE);
	}

	/** Creates a new member field with the given type, name, and protection.
	 * @param pType The fields type.
	 * @param pName The fields name.
	 * @param pProtection The fields protection.
	 * @return The created member field, a builder object, which can be
	 *   used for further configuration.
	 * @see #newField(JQName, String)
	 * @see #newField(Class, String)
	 * @see #newField(String, String)
	 * @see #newField(JQName, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 * @see #newField(Class, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 */
	@Nonnull public Field newField(@Nonnull String pType, @Nonnull String pName, @Nonnull Protection pProtection) {
		return newField(JQName.valueOf(pType), pName, pProtection);
	}

	/** Creates a new member field with the given type, name, and protection.
	 * @param pType The fields type.
	 * @param pName The fields name.
	 * @param pProtection The fields protection.
	 * @return The created member field, a builder object, which can be
	 *   used for further configuration.
	 * @see #newField(JQName, String)
	 * @see #newField(Class, String)
	 * @see #newField(String, String)
	 * @see #newField(JQName, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 * @see #newField(String, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 */
	@Nonnull public Field newField(@Nonnull Class<?> pType, @Nonnull String pName, @Nonnull Protection pProtection) {
		return newField(JQName.valueOf(pType), pName, pProtection);
	}

	/** Creates a new member field with the given type, name, and protection.
	 * @param pType The fields type.
	 * @param pName The fields name.
	 * @param pProtection The fields protection.
	 * @return The created member field, a builder object, which can be
	 *   used for further configuration.
	 * @see #newField(JQName, String)
	 * @see #newField(Class, String)
	 * @see #newField(String, String)
	 * @see #newField(Class, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 * @see #newField(String, String, com.github.jochenw.jsgen.api.IProtectable.Protection)
	 */
	@Nonnull public Field newField(@Nonnull JQName pType, @Nonnull String pName, @Nonnull Protection pProtection) {
		final Field f = new Field().type(pType).name(pName).protection(pProtection);
		content.add(f);
		return f;
	}

	/** Creates a new method with the given name, the given return
	 * type, and "package private" protection.
	 * Equivalent to {@code newMethod(Protection.PACKAGE, pType, pName)}.
	 * @param pName The methods name.
	 * @param pType The methods return type.
	 * @return The created method, a builder object, which can be
	 *   used for further configuration, and for adding code to the method.
	 */
	@Nonnull public Method newMethod(@Nonnull JQName pType, @Nonnull String pName) {
		return newMethod(IProtectable.Protection.PACKAGE, pType, pName);
	}

	/** Creates a new void method with the given name, and "package private"
	 * protection. Equivalent to {@code newMethod(Protection.PACKAGE,
	 * JQName.VOID_TYPE, pName)}.
	 * @param pName The methods name.
	 * @return The created method, a builder object, which can be
	 *   used for further configuration, and for adding code to the method.
	 */
	@Nonnull public Method newMethod(@Nonnull String pName) {
		return newMethod(IProtectable.Protection.PACKAGE, JQName.VOID_TYPE, pName);
	}

	/** Creates a new method with the given name, the given return
	 * type, and "package private" protection.
	 * Equivalent to {@code newMethod(Protection.PACKAGE, pType, pName)}.
	 * The created method takes no arguments, unless {@link Method#parameter(JQName, String)}
	 * is used to add parameters.
	 * @param pName The methods name.
	 * @param pType The methods return type.
	 * @return The created method, a builder object, which can be
	 *   used for further configuration, and for adding code to the method.
	 */
	@Nonnull public Method newMethod(@Nonnull Class<?> pType, @Nonnull String pName) {
		return newMethod(IProtectable.Protection.PACKAGE, pType, pName);
	}

	/** Creates a new constructor with "public" protection, and no arguments.
	 * (A default constructor). Use {@link Constructor#parameter(JQName, String)}
	 * to add parameters.
	 * @return The created constructor, a builder object, which can be
	 *   used for further configuration, and for adding code to the method.
	 * @see #newConstructor(com.github.jochenw.jsgen.api.IProtectable.Protection)
	 */
	@Nonnull public Constructor newConstructor() {
		return newConstructor(IProtectable.Protection.PUBLIC);
	}

	/** Creates a new constructor with the given protection, and no arguments.
	 * (A default constructor). Use {@link Constructor#parameter(JQName, String)}
	 * to add parameters.
	 * @param pProtection The constructors protection.
	 * @return The created constructor, a builder object, which can be
	 *   used for further configuration, and for adding code to the method.
	 * @see #newConstructor(com.github.jochenw.jsgen.api.IProtectable.Protection)
	 */
	@Nonnull public Constructor newConstructor(IProtectable.Protection pProtection) {
		assertMutable();
		final Constructor jmc = new Constructor().protection(pProtection).sourceClass(this);
		content.add(jmc);
		return jmc;
	}
	
	/** Creates a new method with the given name, the given return
	 * type, and the given protection.
	 * The created method takes no arguments, unless {@link Method#parameter(JQName, String)}
	 * is used to add parameters.
	 * @param pName The methods name.
	 * @param pType The methods return type.
	 * @param pProtection The methods protection.
	 * @return The created method, a builder object, which can be
	 *   used for further configuration, and for adding code to the method.
	 */
	@Nonnull public Method newMethod(@Nonnull IProtectable.Protection pProtection, @Nonnull JQName pType, @Nonnull String pName) {
		final Method jmb = new Method().protection(pProtection).returnType(pType).name(pName).sourceClass(this);
		content.add(jmb);
		return jmb;
	}

	/** Creates a new method with the given name, the given return
	 * type, and the given protection.
	 * The created method takes no arguments, unless {@link Method#parameter(JQName, String)}
	 * is used to add parameters.
	 * @param pName The methods name.
	 * @param pType The methods return type.
	 * @param pProtection The methods protection.
	 * @return The created method, a builder object, which can be
	 *   used for further configuration, and for adding code to the method.
	 */
	@Nonnull public Method newMethod(@Nonnull IProtectable.Protection pProtection, @Nonnull Class<?> pType, @Nonnull String pName) {
		return newMethod(pProtection, JQName.valueOf(pType), pName);
	}

	/** Creates a new static initializer (a code of block, which is being executed at
	 * the time, the class is being created).
	 * @return The created initializer, a builder object, which can be used to
	 * add code to the initializer.
	 */
	@Nonnull public StaticInitializer newInitializer() {
		final StaticInitializer jsbib = new StaticInitializer().sourceClass(this);
		content.add(jsbib);
		return jsbib;
	}

	/** Returns the classes contents: A list with all the fields, methods, static
	 * initializers, and constructors, that have been added by invoking the
	 * respective newFoo(...) methods, in the order of invocation.
	 * @return A list with all the fields, methods, static
	 * initializers, and constructors, that have been added by invoking the
	 * respective newFoo(...) methods, in the order of invocation.
	 */
	@Nonnull public List<Object> getContent() { return content; }

	/** Declares the classes super class. For interfaces, this may be invoked
	 * more than once.
	 * @param pType The super type.
	 * @return This builder.
	 * @see #extending(Class)
	 * @see #extending(JQName)
	 * @see #getExtendedClasses()
	 * @see #implementing(String)
	 */
	@Nonnull
	public T extending(String pType) {
		return extending(JQName.valueOf(pType));
	}

	/** Declares the classes super class. For interfaces, this may be invoked
	 * more than once.
	 * @param pType The super type.
	 * @return This builder.
	 * @see #extending(String)
	 * @see #extending(JQName)
	 * @see #getExtendedClasses()
	 * @see #implementing(Class)
	 */
	@Nonnull
	public T extending(Class<?> pType) {
		return extending(JQName.valueOf(pType));
	}

	/** Declares the classes super class. For interfaces, this may be invoked
	 * more than once.
	 * @param pType The super type.
	 * @return This builder.
	 * @see #extending(String)
	 * @see #extending(Class)
	 * @see #getExtendedClasses()
	 * @see #implementing(JQName)
	 */
	@Nonnull
	public T extending(JQName pType) {
		assertMutable();
		extendedClasses.add(pType);
		return self();
	}

	/** Returns the list of super classes.
	 * @return The list of extended types.
	 * @see #extending(JQName)
	 * @see #extending(Class)
	 * @see #extending(String)
	 */
	@Nonnull
	public List<JQName> getExtendedClasses() {
		return extendedClasses;
	}

	/** Declares an implemented interface. This may be invoked
	 * more than once, to specify multiple implemented interfaces.
	 * @param pType The implemented type.
	 * @return This builder.
	 * @see #implementing(Class)
	 * @see #implementing(JQName)
	 * @see #getImplementedInterfaces()
	 * @see #extending(String)
	 */
	@Nonnull
	public T implementing(String pType) {
		return implementing(JQName.valueOf(pType));
	}

	/** Declares an implemented interface. This may be invoked
	 * more than once, to specify multiple implemented interfaces.
	 * @param pType The implemented type.
	 * @return This builder.
	 * @see #implementing(String)
	 * @see #implementing(JQName)
	 * @see #getImplementedInterfaces()
	 * @see #extending(String)
	 */
	@Nonnull
	public T implementing(Class<?> pType) {
		return implementing(JQName.valueOf(pType));
	}

	/** Declares an implemented interface. This may be invoked
	 * more than once, to specify multiple implemented interfaces.
	 * @param pType The implemented type.
	 * @return This builder.
	 * @see #implementing(Class)
	 * @see #implementing(String)
	 * @see #getImplementedInterfaces()
	 * @see #extending(String)
	 */
	@Nonnull
	public T implementing(JQName pType) {
		assertMutable();
		implementedInterfaces.add(pType);
		return self();
	}

	/** Returns the list of implemented interfaces.
	 * @see #implementing(JQName)
	 * @see #implementing(Class)
	 * @see #implementing(String)
	 * @return The list of implemented interfaces.
	 */
	@Nonnull
	public List<JQName> getImplementedInterfaces() {
		return implementedInterfaces;
	}

	/**
	 * Makes this class an interface. Equivalent to
	 * {@code makeInterface(true)}.
	 * @return This builder.
	 */
	@Nonnull
	public T makeInterface() {
		return makeInterface(true);
	}

	/**
	 * Sets, whether this class is an interface.
	 * @param pInterface True, if this generated class should be an
	 *   interface, otherwise false.
	 * @return This builder.
	 */
	@Nonnull
	public T makeInterface(boolean pInterface) {
		assertMutable();
		isInterface = pInterface;
		return self();
	}

	/** Returns, whether this generated class is an interface.
	 * @return True, if the generated class is an interface, otherwise
	 *   false.
	 */
	@Nonnull
	public boolean isInterface() {
		return isInterface;
	}

	/**
	 * Sets the classes comment.
	 * @param pText The comments text, one string for every line.
	 * @return The comment builder.
	 */
	@Nonnull public T comment(@Nonnull String... pText) {
		assertMutable();
		comment = new Comment().text(pText);
		return self();
	}

	/**
	 * Sets the classes comment.
	 * @param pText The comments text, one string for every line.
	 * @return The comment builder.
	 */
	@Nonnull public T comment(@Nonnull Iterable<String> pText) {
		assertMutable();
		comment = new Comment().text(pText);
		return self();
	}

	/** Returns the classes comment.
	 * @return The classes comment, if any, or null.
	 */
	@Override
	@Nullable
	public Comment getComment() {
		return comment;
	}

	/**
	 * Creates a new inner class with the given simple name, and "package private"
	 * protection. Equivalent to {@code newInnerClass(pClassName, Protection.PACKAGE)}.
	 * @param pClassName The inner classes name.
	 * @return The inner classes builder.
	 */
	@Nonnull public InnerClass newInnerClass(String pClassName) {
		return newInnerClass(pClassName, Protection.PACKAGE);
	}

	/**
	 * Creates a new inner class with the given simple name, and the given
	 * protection.
	 * @param pClassName The inner classes name.
	 * @param pProtection The inner classes protection.
	 * @return The inner classes builder.
	 */
	@Nonnull public InnerClass newInnerClass(String pClassName, Protection pProtection) {
		assertMutable();
		final JQName type = JQName.valueOf(getType(), pClassName);
		final InnerClass clazz = new InnerClass(type).protection(pProtection);
		content.add(clazz);
		return clazz;
	}
}
