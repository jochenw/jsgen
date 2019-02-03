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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.github.jochenw.jsgen.api.JSGFactory.NamedResource;
import com.github.jochenw.jsgen.api.Subroutine.Parameter;
import com.github.jochenw.jsgen.impl.AbstractSourceWriter;
import com.github.jochenw.jsgen.impl.SourceSerializer;


/** Test for creating a "for" block.
 */
public class JQForBlockTest {
	private static final String FOR_BLOCK_JAVA_DEFAULT =
		    "package com.foo.myapp;\n" +
			"\n" +
		    "import java.lang.Exception;\n" + 
		    "import java.lang.String;\n" + 
		    "import java.util.ArrayList;\n" +
		    "import java.util.List;\n" +
		    "\n" +
		    "public class ForBlockDemo {\n" +
			"    public static void main(String[] pArgs) throws Exception {\n" +
		    "        final List<String> list = new ArrayList<>();\n" +
			"        for (int i = 0;  i < pArgs.length;  i++) {\n" +
		    "            list.add(pArgs[i]);\n" +
			"        }\n" +
		    "    }\n" +
			"}\n";

	private static final String FOR_BLOCK_JAVA_MAVEN =
		    "package com.foo.myapp;\n" +
			"\n" +
		    "import java.lang.Exception;\n" + 
		    "import java.lang.String;\n" + 
		    "import java.util.ArrayList;\n" +
		    "import java.util.List;\n" +
		    "\n" +
		    "public class ForBlockDemo\n" +
		    "{\n" +
			"    public static void main( String[] pArgs ) throws Exception \n" +
		    "    {\n" +
		    "        final List<String> list = new ArrayList<>();\n" +
			"        for ( int i = 0;  i < pArgs.length;  i++ )\n" +
		    "        {\n" +
		    "            list.add(pArgs[i]);\n" +
			"        }\n" +
		    "    }\n" +
			"}\n";

	/** Test using the default formatter.
	 * @throws Exception The test failed.
	 */
	@Test
	public void testForBlockJavaDefault() throws Exception {
		final JSGFactory factory = JSGFactory.create();
		generateForBlockJava(factory);
		final String gotDefault = asString(factory, AbstractSourceWriter.DEFAULT_FORMATTER);
		Assert.assertEquals(FOR_BLOCK_JAVA_DEFAULT, gotDefault);
	}

	/** Test using the Maven formatter.
	 * @throws Exception The test failed.
	 */
	@Test
	public void testForBlockJavaMaven() throws Exception {
		final JSGFactory factory = JSGFactory.create();
		generateForBlockJava(factory);
		final String gotDefault = asString(factory, AbstractSourceWriter.MAVEN_FORMATTER);
		Assert.assertEquals(FOR_BLOCK_JAVA_MAVEN, gotDefault);
	}

	private void generateForBlockJava(JSGFactory pFactory) {
		final Source source = pFactory.newSource("com.foo.myapp.ForBlockDemo").makePublic();
		final Method mainMethod = source.newMethod("main").makeStatic().makePublic().exception(Exception.class);
		final Parameter args = mainMethod.parameter(JQName.STRING_ARRAY, "pArgs");
		final LocalField listField = mainMethod.newField(JQName.LIST.qualifiedBy(JQName.STRING), "list").makeFinal()
				.assign("new ", JQName.valueOf(ArrayList.class).qualifiedBy(""), "()");
		mainMethod.body().newFor("int i = 0;  i < ", args, ".length;  i++")
			.line(listField, ".add(", args, "[i]);");
	}

	private String asString(JSGFactory pFactory, SourceSerializer pFormatter) throws UnsupportedEncodingException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final AbstractSourceWriter jsgw = new AbstractSourceWriter() {
			@Override
			protected OutputStream open(NamedResource pResource) throws IOException {
				return baos;
			}
		};
		jsgw.setFormatter(pFormatter);
		jsgw.write(pFactory);
		return baos.toString(StandardCharsets.UTF_8.name());
	}
	

}
