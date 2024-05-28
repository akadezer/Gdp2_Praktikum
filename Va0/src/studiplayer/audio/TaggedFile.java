package studiplayer.audio;

import java.awt.RenderingHints.Key;
import java.io.File;
import java.util.IllegalFormatCodePointException;
import java.util.Map;

import javax.management.ValueExp;

import studiplayer.basic.TagReader;

public class TaggedFile extends SampledFile {

	protected String album;

	public TaggedFile() throws NotPlayableException {
		super();

	}

	public TaggedFile(String path) throws NotPlayableException {
		super(path);

		readAndStoreTags();

	}

	public String getAlbum() {

			
			return album;
		
	}

	public void readAndStoreTags() throws NotPlayableException {
		try {

			Map<String, Object> tagMap = TagReader.readTags(getPathname());
			this.duration = (long) tagMap.get("duration");
			if (tagMap.get("album") != null) {
				this.album = ((String) tagMap.get("album")).trim();
			}
			if (tagMap.get("title") != null)
				this.title = ((String) tagMap.get("title")).trim();
			if (tagMap.get("author") != null)
				this.author = ((String) tagMap.get("author")).trim();
		} catch (Exception e) {
			throw new NotPlayableException();
		}
	}

	public String toString() {
		if (this.getAlbum() != null) {
			return super.toString() + " - " + this.getAlbum() + " - " + formatDuration();
		} else {
			return super.toString() + " - " + formatDuration();
		}
	}
}
