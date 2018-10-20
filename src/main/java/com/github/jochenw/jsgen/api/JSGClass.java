package com.github.jochenw.jsgen.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.util.AbstractBuilder;


public abstract class JSGClass<T extends JSGClass<T>> extends AbstractBuilder<T> implements IAnnotatable, IProtectable, ICommentOwner {
	private final JSGQName type;
	private final List<Object> content = new ArrayList<>();
	private final AnnotationSet annotations = new AnnotationSet();
	private Protection protection;
	private final List<JSGQName> extendedClasses = new ArrayList<>();
	private final List<JSGQName> implementedInterfaces = new ArrayList<>();
	private boolean isInterface;
	private JSGComment comment;

	protected JSGClass(JSGQName pType) {
		type = pType;
	}

	public JSGQName getType() {
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

	@Nonnull public JSGField newField(@Nonnull JSGQName pType, @Nonnull String pName) {
		return newField(pType, pName, Protection.PACKAGE);
	}

	@Nonnull public JSGField newField(@Nonnull Class<?> pType, @Nonnull String pName) {
		return newField(JSGQName.valueOf(pType), pName, Protection.PACKAGE);
	}

	@Nonnull public JSGField newField(@Nonnull String pType, @Nonnull String pName) {
		return newField(JSGQName.valueOf(pType), pName, Protection.PACKAGE);
	}

	@Nonnull public JSGField newField(@Nonnull String pType, @Nonnull String pName, @Nonnull Protection pProtection) {
		return newField(JSGQName.valueOf(pType), pName, pProtection);
	}

	@Nonnull public JSGField newField(@Nonnull Class<?> pType, @Nonnull String pName, @Nonnull Protection pProtection) {
		return newField(JSGQName.valueOf(pType), pName, pProtection);
	}

	@Nonnull public JSGField newField(@Nonnull JSGQName pType, @Nonnull String pName, @Nonnull Protection pProtection) {
		final JSGField f = new JSGField().type(pType).name(pName).protection(pProtection);
		content.add(f);
		return f;
	}

	@Nonnull public JSGMethod newVoidMethod(@Nonnull String pName) {
		return newVoidMethod(pName, IProtectable.Protection.PACKAGE);
	}

	@Nonnull public JSGMethod newVoidMethod(@Nonnull String pName, Protection pProtection) {
		return newMethod(pProtection, JSGQName.VOID_TYPE, pName);
	}

	@Nonnull public JSGMethod newMethod(@Nonnull JSGQName pType, @Nonnull String pName) {
		return newMethod(IProtectable.Protection.PACKAGE, pType, pName);
	}

	@Nonnull public JSGMethod newMethod(@Nonnull String pName) {
		return newMethod(IProtectable.Protection.PACKAGE, JSGQName.VOID_TYPE, pName);
	}

	@Nonnull JSGMethod newMethod(@Nonnull Class<?> pType, @Nonnull String pName) {
		return newMethod(IProtectable.Protection.PACKAGE, pType, pName);
	}

	@Nonnull JSGConstructor newConstructor() {
		return newConstructor(IProtectable.Protection.PUBLIC);
	}

	@Nonnull JSGConstructor newConstructor(IProtectable.Protection pProtection) {
		assertMutable();
		final JSGConstructor jmc = new JSGConstructor().protection(pProtection).sourceClass(this);
		content.add(jmc);
		return jmc;
	}
	
	@Nonnull JSGMethod newMethod(@Nonnull IProtectable.Protection pProtection, @Nonnull JSGQName pType, @Nonnull String pName) {
		final JSGMethod jmb = new JSGMethod().protection(pProtection).returnType(pType).name(pName).sourceClass(this);
		content.add(jmb);
		return jmb;
	}

	@Nonnull JSGMethod newMethod(@Nonnull IProtectable.Protection pProtection, @Nonnull Class<?> pType, @Nonnull String pName) {
		return newMethod(pProtection, JSGQName.valueOf(pType), pName);
	}

	@Nonnull JSGStaticInitializer newInitializer() {
		final JSGStaticInitializer jsbib = new JSGStaticInitializer().sourceClass(this);
		content.add(jsbib);
		return jsbib;
	}

	@Nonnull public List<Object> getContent() { return content; }

	@Nonnull
	public T extending(String pType) {
		return extending(JSGQName.valueOf(pType));
	}

	@Nonnull
	public T extending(Class<?> pType) {
		return extending(JSGQName.valueOf(pType));
	}

	@Nonnull
	public T extending(JSGQName pType) {
		assertMutable();
		extendedClasses.add(pType);
		return self();
	}

	@Nonnull
	public List<JSGQName> getExtendedClasses() {
		return extendedClasses;
	}

	@Nonnull
	public T implementing(String pType) {
		return implementing(JSGQName.valueOf(pType));
	}

	@Nonnull
	public T implementing(Class<?> pType) {
		return implementing(JSGQName.valueOf(pType));
	}

	@Nonnull
	public T implementing(JSGQName pType) {
		assertMutable();
		implementedInterfaces.add(pType);
		return self();
	}

	@Nonnull
	public List<JSGQName> getImplementedInterfaces() {
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
		comment = new JSGComment().text(pText);
		return self();
	}

	@Nonnull public T comment(@Nonnull Iterable<String> pText) {
		assertMutable();
		comment = new JSGComment().text(pText);
		return self();
	}

	@Override
	@Nullable
	public JSGComment getComment() {
		return comment;
	}

	@Nonnull public JSGInnerClass newInnerClass(String pClassName) {
		return newInnerClass(pClassName, Protection.PACKAGE);
	}

	@Nonnull public JSGInnerClass newInnerClass(String pClassName, Protection pProtection) {
		assertMutable();
		final JSGQName type = JSGQName.valueOf(getType(), pClassName);
		final JSGInnerClass clazz = new JSGInnerClass(type).protection(pProtection);
		content.add(clazz);
		return clazz;
	}
}
