package com.github.jochenw.jsgen.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.api.ISourceWriter;
import com.github.jochenw.jsgen.api.JSGFactory.NamedResource;


/** Default implementation of {@link ISourceWriter}, which persists to
 * files in a given base directory.
 */
public class FileJavaSourceWriter extends AbstractSourceWriter {
	private final @Nonnull File javaSourceTargetDirectory, resourceFileDirectory;
	private boolean creatingTargetDirectoryForbidden;
	private boolean avoidingUpdates;

	/** Returns, whether this source writer is avoiding updates. If so,
	 * the source writer will not write files immediately. Instead, it
	 * collects the generated code into a byte array. Before actually
	 * writing the byte array to the file, the files contents are being
	 * compared with the byte array. If they are identical (in other
	 * words: If the file hasn't changed since the last generator run),
	 * then nothing is written to the file. 
	 * @return True, if the source writer is avoiding updates, otherwise
	 *   false.
	 */
	public boolean isAvoidingUpdates() {
		return avoidingUpdates;
	}

	/** Sets, whether this source writer is avoiding updates. If so,
	 * the source writer will not write files immediately. Instead, it
	 * collects the generated code into a byte array. Before actually
	 * writing the byte array to the file, the files contents are being
	 * compared with the byte array. If they are identical (in other
	 * words: If the file hasn't changed since the last generator run),
	 * then nothing is written to the file. 
	 * @param pAvoidingUpdates True, if the source writer is avoiding updates, otherwise
	 *   false.
	 */
	public void setAvoidingUpdates(boolean pAvoidingUpdates) {
		avoidingUpdates = pAvoidingUpdates;
	}

	/** Returns, whether creating the target directory is forbidden.
	 * If so, the source writer will throw an exception, if the
	 * target directory does not yet exist.
	 * @return True, if creating the target directory is forbidden,
	 *   otherwise false.
	 */
	public boolean isCreatingTargetDirectoryForbidden() {
		return creatingTargetDirectoryForbidden;
	}

	/** Sets, whether creating the target directory is forbidden.
	 * If so, the source writer will throw an exception, if the
	 * target directory does not yet exist.
	 * @param pCreatingTargetDirectoryForbidden True, if creating the target directory is forbidden,
	 *   otherwise false.
	 */
	public void setCreatingTargetDirectoryForbidden(boolean pCreatingTargetDirectoryForbidden) {
		creatingTargetDirectoryForbidden = pCreatingTargetDirectoryForbidden;
	}

	/** Creates a new instance with the given target directory. The target directory is
	 * used for both Java source files, and resource files.
	 * @param pTargetDirectory The source writers target directory.
	 * @see #FileJavaSourceWriter(File, File)
	 */
	public FileJavaSourceWriter(@Nonnull File pTargetDirectory) {
		this(Objects.requireNonNull(pTargetDirectory, "Target Directory"), Objects.requireNonNull(pTargetDirectory, "Target Directory"));
	}

	/** Creates a new instance with the given target directory,
	 * and the given resource file directory.
	 * @param pJavaSourceTargetDirectory The source writers target directory for Java source files.
	 * @param pResourceFileTargetDirectory The source writers target directory for resource files.
	 * @see #FileJavaSourceWriter(File)
	 */
	public FileJavaSourceWriter(@Nonnull File pJavaSourceTargetDirectory, @Nonnull File pResourceFileTargetDirectory) {
		javaSourceTargetDirectory = Objects.requireNonNull(pJavaSourceTargetDirectory, "Java Source Target Directory");
		resourceFileDirectory = Objects.requireNonNull(pResourceFileTargetDirectory, "Resource File Target Directory");
	}

	protected OutputStream open(@Nonnull NamedResource pResource) throws IOException {
		final File targetDir;
		if (pResource.isJavaSource()) {
			targetDir = javaSourceTargetDirectory;
		} else {
			targetDir = resourceFileDirectory;
		}
		final File f = new File(targetDir, pResource.getName().getQName());
		final File dir = f.getParentFile();
		if (dir != null  &&  !dir.isDirectory()) {
			if (isCreatingTargetDirectoryForbidden()) {
				throw new IOException("Target directory does not exist: " + dir.getAbsolutePath());
			} else {
				if (!dir.mkdirs()) {
					throw new IOException("Unable to create target directory: " + dir.getAbsolutePath());
				}
			}
		}
		if (isAvoidingUpdates()) {
			return new ByteArrayOutputStream() {
				@Override
				public void close() throws IOException {
					FileJavaSourceWriter.this.write(f, toByteArray());
					super.close();
				}
			};
		} else {
			return new FileOutputStream(f);
		}
	}


	protected void write(File pFile, byte[] pBytes) throws IOException {
		if (isAvoidingUpdates()) {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos.write(pBytes);
			final byte[] bytes = baos.toByteArray();
			boolean needWrite = false;
			if (pFile.isFile()) {
				if (isAvoidingUpdates()) {
					try (InputStream is = new FileInputStream(pFile);
						 BufferedInputStream bis = new BufferedInputStream(is)) {
						for (int i = 0;  i < bytes.length;  i++) {
							int b1 = bis.read();
							int b2 = bytes[i];
							if (b1 != b2) {
								needWrite = true;
								break;
							}
						}
						if (bis.read() != -1) {
							needWrite = true;
						}
					}
				} else {
					needWrite = true;
				}
			} else {
				needWrite = true;
			}
			if (needWrite) {
				try (OutputStream os = new FileOutputStream(pFile)) {
					os.write(pBytes);
				}
			}
		}
	}
}
