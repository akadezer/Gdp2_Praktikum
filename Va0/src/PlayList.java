import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PlayList {
	private int current;

	public PlayList() {
	}

	public PlayList(String m3uPathname) {
		loadFromM3U(m3uPathname);
	}

	// Audiofile Liste + dazugehoerige Methoden

	LinkedList<AudioFile> audioFiles = new LinkedList<AudioFile>();

	public void add(AudioFile file) {
		audioFiles.add(file);
	}

	public void remove(AudioFile file) {
		audioFiles.remove(file);
	}

	public int size() {
		return audioFiles.size();

	}

	public AudioFile currentAudioFile() {
		if (current < audioFiles.size() && !(audioFiles.isEmpty())) {
			return audioFiles.get(current);
		}
		return null;

	}

	public void nextSong() {

		if (current >= (audioFiles.size() - 1)) {
			current = 0;
		} else {
			current++;
		}
	}

	public void loadFromM3U(String pathname) {
		audioFiles.clear();
		current = 0;

		List<String> lines = new ArrayList<>();
		Scanner scanner = null;

		try {
			// open the file for reading
			scanner = new Scanner(new File(pathname));

			while (scanner.hasNextLine()) {
				
				String line = scanner.nextLine();
				if ((line.indexOf("#") != 0) && !(line.isBlank())) {
					
					lines.add(line);
				}
		
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {

				scanner.close();
			} catch (Exception e) {
			}
		}

		
		for (String string : lines) {
			audioFiles.add(AudioFileFactory.createAudioFile(string));
		}
		
	}

	public void saveAsM3U(String pathname) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(pathname);
			for (AudioFile audioFile : audioFiles) {
				writer.write(audioFile.getPathname() + "\n");
			}

		} catch (Exception e) {
			throw new RuntimeException("Unable to write file " + pathname + "!");
		}
		try {

			writer.close();
		} catch (Exception e) {
		}

	}

	public List<AudioFile> getList() {
		return audioFiles;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}
}
