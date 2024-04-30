import java.io.File;

abstract public class AudioFile {

	private String pathname; // Pfadname
	private String filename; // Dateiname
	protected String author; // Autor
	protected String title; // Titel

	public AudioFile() {

	}

	public AudioFile(String path) {
		parsePathname(path);
		parseFilename(filename);
		File file = new File(getPathname());
		if (!file.canRead()) {
			throw new RuntimeException();
		}

	}

	public void parsePathname(String path) {
		String sep = System.getProperty("file.separator");

		// Laufwerkskorrektur
		if (!isWindows()) {

			if (path.contains(":")) {

				path = sep + path.substring(0, path.indexOf(":")) + path.substring(path.indexOf(":") + 1);
			}

		}
		// falsche separatoren

		if (!isWindows()) {

			path = path.replace("\\", sep);
		}

		if (isWindows()) {

			path = path.replace("/", sep);

		}

		while (path.contains(sep + sep)) {
			path = path.replace(sep + sep, sep);

		}
		path = path.trim();
		pathname = path;
		if (path.lastIndexOf(sep) == path.length() - 1) {
			filename = "";
		} else {
			path = path.substring(path.lastIndexOf(sep) + 1);
			path = path.trim();
			filename = path;
		}
	}

	public void parseFilename(String filename) {

		if (filename.contains(".")) {

			filename = filename.substring(0, filename.lastIndexOf("."));

		}
		if (filename.equals(" - ")) {
			author = "";
			title = "";

		} else if (filename.contains(" - ")) {
			// Trennung zwischen Autor und Titel
			String[] fileparts = filename.split(" - ");
			author = fileparts[0].trim();
			title = fileparts[1].trim();
		} else {
			filename = filename.trim();
			author = "";
			title = filename;
		}
		if (filename.equals("-")) {
			author = "";
			title = "-";
		}

	}

	// abstract methods
	public abstract void play();

	public abstract void togglePause();

	public abstract void stop();

	public abstract String formatDuration();

	public abstract String formatPosition();

	// Getter-Methoden
	public String getPathname() {

		return pathname;

	}

	public String getFilename() {
		return filename;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		if (getAuthor().equals("")) {
			return getTitle();
		} else {
			return getAuthor() + " - " + getTitle();
		}
	}

	// Systemabfrage
	private boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
	}

}
