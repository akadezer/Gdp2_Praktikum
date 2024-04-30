import java.awt.RenderingHints.Key;
import java.util.IllegalFormatCodePointException;
import java.util.Map;

import javax.management.ValueExp;

import studiplayer.basic.TagReader;

public class TaggedFile extends SampledFile {

	protected String album;

	public TaggedFile() {
		super();
		readAndStoreTags();
	}

	public TaggedFile(String path) {
		super(path);
		readAndStoreTags();
		
	}

	public String getAlbum() {
		return album;
	}

	public void readAndStoreTags() {

		Map<String, Object> tagMap = TagReader.readTags(getPathname());
		for (String tagS : tagMap.keySet()) {
			if (tagS.equals("duration")) {
				this.duration = (long) tagMap.get(tagS);

			} else if (tagS.equals("album")) {
				this.album = (String) tagMap.get(tagS);
				this.album = this.album.trim();
			} else if (tagS.equals("title")) {
				this.title = (String) tagMap.get(tagS);
				this.title = this.title.trim();
				} else if (tagS.equals("author")) {
				this.author = (String) tagMap.get(tagS);
				this.author = this.author.trim();
			}

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
