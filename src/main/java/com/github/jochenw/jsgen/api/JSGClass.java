package com.github.jochenw.jsgen.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.util.AbstractBuilder;


public abstract class JSGClass<T extends JSGClass<T>> extends AbstractBuilder<T> implements IAnnotatable, IProtectable, ICommentOwner {
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

	@Nonnull public T makePackageProtected() {
		return protection(Protection.PACKAGE);
	}

	@Nonnull public T makePrivate() {
		return protection(Protection.PRIVATE);
	}

	@Override
	@Nonnull public IProtectable.Protection getProtection() {
		return protection;
	}

	@Nonnull public Field newField(@Nonnull JQName pType, @Nonnull String pName) {
		return newField(pType, pName, Protection.PACKAGE);
	}

	@Nonnull public Field newField(@Nonnull Class<?> pType, @Nonnull String pName) {
		return newField(JQName.valueOf(pType), pName, Protection.PACKAGE);
	}

	@Nonnull public Field newField(@Nonnull String pType, @Nonnull String pName) {
		return newField(JQName.valueOf(pType), pName, Protection.PACKAGE);
	}

	@Nonnull public Field newField(@Nonnull String pType, @Nonnull String pName, @Nonnull Protection pProtection) {
		return newField(JQName.valueOf(pType), pName, pProtection);
	}

	@Nonnull public Field newField(@Nonnull Class<?> pType, @Nonnull String pName, @Nonnull Protection pProtection) {
		return newField(JQName.valueOf(pType), pName, pProtection);
	}

	@Nonnull public Field newField(@Nonnull JQName pType, @Nonnull String pName, @Nonnull Protection pProtection) {
		final Field f = new Field().type(pType).name(pName).protection(pProtection);
		content.add(f);
		return f;
	}

	@Nonnull public Method newVoidMethod(@Nonnull String pName) {
		return newVoidMethod(pName, IProtectable.Protection.PACKAGE);
	}

	@Nonnull public Method newVoidMethod(@Nonnull String pName, Protection pProtection) {
		return newMethod(pProtection, JQName.VOID_TYPE, pName);
	}

	@Nonnull public Method newMethod(@Nonnull JQName pType, @Nonnull String pName) {
		return newMethod(IProtectable.Protection.PACKAGE, pType, pName);
	}

	@Nonnull public Method newMethod(@Nonnull String pName) {
		return newMethod(IProtectable.Protection.PACKAGE, JQName.VOID_TYPE, pName);
	}

	@Nonnull public Method newMethod(@Nonnull Class<?> pType, @Nonnull String pName) {
		return newMethod(IProtectable.Protection.PACKAGE, pType, pName);
	}

	@Nonnull public Constructor newConstructor() {
		return newConstructor(IProtectable.Protection.PUBLIC);
	}

	@Nonnull public Constructor newConstructor(IProtectable.Protection pProtection) {
		assertMutable();
		final Constructor jmc = new Constructor().protection(pProtection).sourceClass(this);
		content.add(jmc);
		return jmc;
	}
	
	@Nonnull public Method newMethod(@Nonnull IProtectable.Protection pProtection, @Nonnull JQName pType, @Nonnull String pName) {
		final Method jmb = new Method().protection(pProtection).returnType(pType).name(pName).sourceClass(this);
		content.add(jmb);
		return jmb;
	}

	@Nonnull public Method newMethod(@Nonnull IProtectable.Protection pProtection, @Nonnull Class<?> pType, @Nonnull String pName) {
		return newMethod(pProtection, JQName.valueOf(pType), pName);
	}

	@Nonnull public StaticInitializer newInitializer() {
		final StaticInitializer jsbib = new StaticInitializer().sourceClass(this);
		content.add(jsbib);
		return jsbib;
	}

	@Nonnull public List<Object> getContent() { return content; }

	@Nonnull
	public T extending(String pType) {
		return extending(JQName.valueOf(pType));
	}

	@Nonnull
	public T extending(Class<?> pType) {
		return extending(JQName.valueOf(pType));
	}

	@Nonnull
	public T extending(JQName pType) {
		assertMutable();
		extendedClasses.add(pType);
		return self();
	}

	@Nonnull
	public List<JQName> getExtendedClasses() {
		return extendedClasses;
	}

	@Nonnull
	public T implementing(String pType) {
		return implementing(JQName.valueOf(pType));
	}

	@Nonnull
	public T implementing(Class<?> pType) {
		return implementing(JQName.valueOf(pType));
	}

	@Nonnull
	public T implementing(JQName pType) {
		assertMutable();
		implementedInterfaces.add(pType);
		return self();
	}

	@Nonnull
	public List<JQName> getImplementedInterfaces() {
		return implementedInterfaces;
	}

	@Nonnull
	public T makeInterface() {
		return makeInterface(true);
	}

	@Nonnull
	public T makeInterface(boolean pInterface) {
		assertMutable();
		isInterface = pInterface;
		return self();
	}

	@Nonnull
	public boolean isInterface() {
		return isInterface;
	}

	@Nonnull public T comment(@Nonnull String... pText) {
		assertMutable();
		comment = new Comment().text(pText);
		return self();
	}

	@Nonnull public T comment(@Nonnull Iterable<String> pText) {
		assertMutable();
		comment = new Comment().text(pText);
		return self();
	}

	@Override
	@Nullable
	public Comment getComment() {
		return comment;
	}

	@Nonnull public InnerClass newInnerClass(String pClassName) {
		return newInnerClass(pClassName, Protection.PACKAGE);
	}

	@Nonnull public InnerClass newInnerClass(String pClassName, Protection pProtection) {
		assertMutable();
		final JQName type = JQName.valueOf(getType(), pClassName);
		final InnerClass clazz = new InnerClass(type).protection(pProtection);
		content.add(clazz);
		return clazz;
	}
}
