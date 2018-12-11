package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;



/** A resettable block acts as a possibility to add code, and reserve the
 * ability to revoke that code later on. Example of use:
 * <pre>
 *   Method m; // The method, to which we are adding code.
 *   Resettable Block rblock = null;
 *   try {
 *       rblock = new ResettableBlock(m.getBody();
 *       // Try adding code to rblock.
 *       rblock.commit();
 *   } catch (Throwable t) {
 *       // Adding code failed, rollback, and do something different.
 *       rblock.rollback();
 *   }
 * </pre>
 */
public class ResettableBlock extends Block<ResettableBlock> {
	private @Nonnull final Block<?> parent;

	/** Creates a new instance. The method {@link #commit()}
	 * will add code to the given parent.
	 * @param pParent The resettable blocks parent block.
	 *   Code, which is being added to the resettable block
	 *   will not be added to the parent, until the
	 *   {@link #commit()} method is being invoked, at which
	 *   point code will be transferred from the resettable
	 *   block to the parent block.
	 */
	public ResettableBlock(@Nonnull Block<?> pParent) {
		parent = pParent;
	}

	/** Called to move all code, which has been added to the
	 * resettable block, to the parent block.
	 * 
	 */
	public void commit() {
		parent.getContents().addAll(getContents());
	}

	/** Called to discard all code, which has been added to
	 * the resettable block.
	 */
	public void rollback() {
		getContents().clear();
	}
}
