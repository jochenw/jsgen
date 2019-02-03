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
import javax.annotation.Nullable;


/** Representation of a static initializer: An anonymous code block, which is being
 * executed, when the class is loaded. 
 */
public class StaticInitializer extends CodeBlock<StaticInitializer> implements ICommentOwner  {
	private Comment comment;

	@Override
	protected StaticInitializer self() {
		return this;
	}

	@Nonnull public StaticInitializer comment(@Nonnull String... pText) {
		assertMutable();
		if (comment == null) {
			comment = new Comment().makePublic(false);
		}
		comment.text(pText);
		return this;
	}

	@Nonnull public StaticInitializer comment(@Nonnull Iterable<String> pText) {
		assertMutable();
		if (comment == null) {
			comment = new Comment().makePublic(false);
		}
		comment.text(pText);
		return this;
	}

	@Override
	@Nullable public Comment getComment() {
		return comment;
	}
}
