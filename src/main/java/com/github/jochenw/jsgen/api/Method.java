/**
 * Copyright 2018 Jochen Wiedmann
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;


/** Representation of a method, that is a member of a class.
 */
public class Method extends Subroutine<Method>
        implements IAbstractable<Method>, IProtectable<Method>, IStaticable<Method> {
	@Nonnull private JQName type;
	@Nonnull private IProtectable.Protection protection;
	@Nonnull private String name;
	private boolean isStatic;
	private boolean isAbstract;
	private boolean isFinal;
	private boolean isSynchronized;

	@Override
	protected Method self() { return this; }

	/** Sets the methods return type.
	 * @param pType The methods return type. "void" for
	 *   void methods.
	 * @return This builder.
	 */
	@Nonnull public Method returnType(@Nonnull JQName pType) {
		assertMutable();
		type = pType;
		return this;
	}

	/** Sets the methods return type.
	 * @param pType The methods return type. {@link Void#TYPE} for
	 *   void methods.
	 * @return This builder.
	 */
	@Nonnull public Method returnType(@Nonnull Class<?> pType) {
		return returnType(JQName.valueOf(pType));
	}

	/** Sets the methods return type.
	 * @param pType The methods return type. {@link JQName#VOID_TYPE} for
	 *   void methods.
	 * @return This builder.
	 */
	@Nonnull public Method returnType(@Nonnull String pType) {
		return returnType(JQName.valueOf(pType));
	}

	/** Returns the methods return type.
	 * @return The methods return type. {@link JQName#VOID_TYPE} for
	 *   void methods.
	 */
	@Nonnull public JQName getReturnType() {
		return type;
	}

	@Nonnull Method name(@Nonnull String pName) {
		assertMutable();
		name = pName;
		return this;
	}

	/** Returns the methods name.
	 * @return The methods name.
	 */
	@Nonnull public String getName() {
		return name;
	}

	
	@Nonnull public Method makeAbstract() {
		return makeAbstract(true);
	}
	
	@Nonnull public Method makeAbstract(boolean pAbstract) {
		assertMutable();
		isAbstract = pAbstract;
		return this;
	}

	/** Returns, whether this method is abstract.
	 * @return True, if this method is abstract, otherwise false.
	 */
	public boolean isAbstract() {
		return isAbstract;
	}


	/** Makes this method static. Equivalent to
	 * <pre>
	 *   makeStatic(true)
	 * </pre>
	 * @return This builder.
	 * @see #makeStatic(boolean)
	 * @see #isStatic()
	 */
	@Nonnull public Method makeStatic() {
		return makeStatic(true);
	}
	
	/** Sets, whether this method is static.
	 * @param pStatic True, if the method should be static, or false otherwise.
	 * @return This builder.
	 * @see #makeStatic()
	 * @see #isStatic()
	 */
	@Nonnull public Method makeStatic(boolean pStatic) {
		assertMutable();
		isStatic = pStatic;
		return this;
	}

	public boolean isStatic() {
		return isStatic;
	}

	/** Makes this method final. Equivalent to
	 * <pre>
	 *   makeFinal(true)
	 * </pre>
	 * @return This builder.
	 * @see #makeFinal(boolean)
	 * @see #isFinal()
	 */
	@Nonnull public Method makeFinal() {
		return makeFinal(true);
	}

	/** Sets, whether this method is final.
	 * @param pFinal True, if the method should be final, or false otherwise.
	 * @return This builder.
	 * @see #makeFinal()
	 * @see #isFinal()
	 */
	@Nonnull public Method makeFinal(boolean pFinal) {
		assertMutable();
		isFinal = pFinal;
		return this;
	}

	@Nonnull public Method makePublic() {
		return protection(Protection.PUBLIC);
	}
	
	@Nonnull public Method makeProtected() {
		return protection(Protection.PROTECTED);
	}

	@Nonnull public Method makePackagePrivate() {
		return protection(Protection.PACKAGE);
	}

	@Nonnull public Method makePrivate() {
		return protection(Protection.PUBLIC);
	}

	@Nonnull public Method protection(Protection pProtection) {
		protection = pProtection;
		return this;
	}

	@Nonnull public Protection getProtection() {
		return protection;
	}

	/** Returns, whether this method is final.
	 * @return True, if this method is final, otherwise false.
	 */
	public boolean isFinal() {
		return isFinal;
	}

	/** Makes this method synchronized. Equivalent to
	 * <pre>
	 *   makeSynchronized(true)
	 * </pre>
	 * @return This builder.
	 * @see #makeSynchronized(boolean)
	 * @see #isSynchronized()
	 */
	@Nonnull public Method makeSynchronized() {
		return makeSynchronized(true);
	}

	/** Sets, whether this method is synchronized.
	 * @param pSynchronized True, if the method should be synchronized, or false otherwise.
	 * @return This builder.
	 * @see #makeSynchronized()
	 * @see #isSynchronized()
	 */
	@Nonnull public Method makeSynchronized(boolean pSynchronized) {
		assertMutable();
		isSynchronized = pSynchronized;
		return this;
	}

	/** Returns, whether this method is synchronized.
	 * @return True, if this method is synchronized, otherwise false.
	 */
	public boolean isSynchronized() {
		return isSynchronized;
	}

	private static final JQName OVERRIDE = JQName.valueOf(Override.class);

	/** Specifies, that this method is being annotated with {@link Override}.
	 * @return This builder.
	 * @see #overriding(boolean)
	 * @see #isOverriding()
	 */
	@Nonnull
	public Method overriding() {
		return overriding(true);
	}

	/** Specifies, whether this method is being annotated with {@link Override}.
	 * @param pOverriding True, to annotate the method with {@code @Override},
	 *   or false, to remove the annotation.
	 * @return This builder.
	 * @see #overriding()
	 * @see #isOverriding()
	 */
	@Nonnull public Method overriding(boolean pOverriding) {
		assertMutable();
		if (pOverriding) {
			annotation(OVERRIDE);
		}
		return this;
	}

	/** Returns, whether this method is annotated with {@link Override}.
	 * @return True, if this method is annotated with {@link Override},
	 *   otherwise false.
	 */
	public boolean isOverriding() {
		return isAnnotatedWith(OVERRIDE);
	}
}
