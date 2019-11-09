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

import static com.github.jochenw.jsgen.api.Source.q;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

import com.github.jochenw.jsgen.api.JSGFactory;
import com.github.jochenw.jsgen.api.Method;
import com.github.jochenw.jsgen.api.JQName;
import com.github.jochenw.jsgen.api.Source;
import com.github.jochenw.jsgen.api.JSGFactory.NamedResource;
import com.github.jochenw.jsgen.impl.AbstractSourceWriter;
import com.github.jochenw.jsgen.impl.SourceSerializer;


/**
 * Test for creating a simple "Hello, world!" application.
 */
public class IfElseIfElseTest {
	private static final String IF_ELSE_IF_ELSE_DEFAULT = "package com.foo.myapp;\n" + 
			"\n" + 
			"import java.lang.IllegalArgumentException;\n" + 
			"import java.lang.String;\n" + 
			"import java.lang.System;\n" + 
			"\n" + 
			"public class Main {\n" + 
			"    public static void main(String[] pArgs) {\n" + 
			"        final int i = pArgs.length;\n" +
			"        if (i == 0) {\n" +
			"            throw new IllegalArgumentException(\"No arguments given.\");\n" +
			"        } else if (i == 1) {\n" +
			"            System.out.println(\"One argument given.\");\n" +
			"        } else if (i == 2) {\n" +
			"            System.out.println(\"Two arguments given.\");\n" +
			"        } else {\n" +
			"            System.out.println(\"Number of arguments: \" + i);\n" +
			"        }\n" +
			"    }\n" + 
			"}\n";

	private static final String IF_ELSE_IF_ELSE_MAVEN = "package com.foo.myapp;\n" + 
			"\n" + 
			"import java.lang.IllegalArgumentException;\n" + 
			"import java.lang.String;\n" + 
			"import java.lang.System;\n" + 
			"\n" + 
			"public class Main\n" +
			"{\n" +
			"    public static void main( String[] pArgs ) \n" +
			"    {\n" +
			"        final int i = pArgs.length;\n" +
			"        if ( i == 0 )\n" +
			"        {\n" +
			"            throw new IllegalArgumentException( \"No arguments given.\" );\n" +
			"        }\n" +
			"        else if ( i == 1 )\n" +
			"        {\n" +
			"            System.out.println(\"One argument given.\");\n" +
			"        }\n" +
			"        else if ( i == 2 )\n" +
			"        {\n" +
			"            System.out.println(\"Two arguments given.\");\n" +
			"        }\n" +
			"        else\n" +
			"        {\n" +
			"            System.out.println(\"Number of arguments: \" + i);\n" +
			"        }\n" +
			"    }\n" + 
			"}\n";

	/**
	 * Tests creating the class {@link #IF_ELSE_IF_ELSE_DEFAULT}.
	 * <pre>
	 *   package com.foo.myapp;
	 *
	 *   public class Main {
	 *       public static void main(String[] pArgs) throws Exception {
	 *           System.out.println("Hello, world!");
	 *       }
	 *   }
	 * </pre>
	 * @throws Exception The test failed.
	 */
	@Test
	public void testHelloWorldJavaDefault() throws Exception {
		final String got = generateHelloWorld(AbstractSourceWriter.DEFAULT_FORMATTER);
		Assert.assertEquals(IF_ELSE_IF_ELSE_DEFAULT, got);
	}

	/**
	 * Tests creating the class {@link #IF_ELSE_IF_ELSE_MAVEN}.
	 * <pre>
	 *   package com.foo.myapp;
	 *
	 *   public class Main {
	 *       public static void main(String[] pArgs) throws Exception {
	 *           System.out.println("Hello, world!");
	 *       }
	 *   }
	 * </pre>
	 * @throws Exception The test failed.
	 */
	@Test
	public void testHelloWorldJavaMaven() throws Exception {
		final String got = generateHelloWorld(AbstractSourceWriter.MAVEN_FORMATTER);
		Assert.assertEquals(IF_ELSE_IF_ELSE_MAVEN, got);
	}

	private String generateHelloWorld(SourceSerializer pFormatter) throws UnsupportedEncodingException {
		final JSGFactory factory = JSGFactory.create();
		final Source jsb = factory.newSource("com.foo.myapp.Main").makePublic();
		final Method mainMethod = jsb.newMethod("main").makePublic().makeStatic();
		mainMethod.parameter(JQName.STRING_ARRAY, "pArgs");
		final Block<?> body = mainMethod.body();
		body
		    .newField(JQName.INT_TYPE, "i").makeFinal()
		    .assign("pArgs.length");
		body.newIf("i == 0")
		    .addThrowNew(IllegalArgumentException.class, q("No arguments given."))
		.elseIf("i == 1")
		    .tline(System.class, ".out.println(", q("One argument given."), ")")
		.elseIf("i == 2")
	        .tline(System.class, ".out.println(", q("Two arguments given."), ")")
	    .otherwise()
            .tline(System.class, ".out.println(", q("Number of arguments: "), " + i)");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final AbstractSourceWriter jsgw = new AbstractSourceWriter() {
			@Override
			protected OutputStream open(NamedResource pResource) throws IOException {
				return baos;
			}
		};
		jsgw.setFormatter(pFormatter);
		jsgw.write(factory);
		final String got = baos.toString(StandardCharsets.UTF_8.name());
		return got;
	}
}
