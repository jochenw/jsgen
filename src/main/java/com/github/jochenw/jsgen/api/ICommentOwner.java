package com.github.jochenw.jsgen.api;

import javax.annotation.Nullable;


/** Interface of an object, which may be annotated with a comment.
 */
public interface ICommentOwner {
	/** Returns the owners comment, if any. Otherwise returns null.
	 * @return The owners comment, if any, or null.
	 */
	@Nullable Comment getComment();
}
