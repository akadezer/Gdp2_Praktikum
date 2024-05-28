package studiplayer.audio;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PlayList implements Iterable<AudioFile> {
	private AudioFile currentFile;
	private String search;
	private SortCriterion sortCriterion = SortCriterion.DEFAULT;

	public PlayList() {
	}

	public PlayList(String m3uPathname) {
		loadFromM3U(m3uPathname);
	}

	// Audiofile Liste + dazugehoerige Methoden

	private LinkedList<AudioFile> audioFiles = new LinkedList<AudioFile>();

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
		if (currentFile == null) {
			currentFile = this.iterator().next();
			return currentFile;
		}
		for (AudioFile audioFile : audioFiles) {
			if (audioFile.equals(currentFile)) {
				return currentFile;
			}
		}
		return null;
	}

	public void nextSong() {
		Iterator<AudioFile> iterator = this.iterator();
		if (currentFile == null) {
			if (!audioFiles.isEmpty()) {

				currentFile = audioFiles.getFirst();
			}
		}
		while (iterator.hasNext()) {
			if (iterator.next().equals(currentFile)) {
				currentFile = iterator.next();
			}

		}

	}

	public void loadFromM3U(String pathname) {
		audioFiles.clear();

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

			try {
				for (String string : lines) {
					try {

						audioFiles.add(AudioFileFactory.createAudioFile(string));
					} catch (Exception e2) {
					}

				}
				currentFile = audioFiles.getFirst();
			} catch (Exception e) {
				e.printStackTrace();
			}
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

	public SortCriterion getSortCriterion() {
		return this.sortCriterion;
	}

	public void setSortCriterion(SortCriterion sort) {
		this.sortCriterion = sort;
		currentFile = null;
	}

	public String getSearch() {

		return this.search;
	}

	public void setSearch(String value) {
		this.search = value;
		currentFile = null;
	}

	@Override
	public Iterator<AudioFile> iterator() {
		return new ControllablePlayListIterator(audioFiles, this.search, this.sortCriterion);
	}

	public void jumpToAudioFile(AudioFile file) {
		ControllablePlayListIterator iterator = new ControllablePlayListIterator(audioFiles);
		currentFile = iterator.jumpToAudioFile(file);

	}

	@Override
	public String toString() {
		return audioFiles.toString();
	}

}
