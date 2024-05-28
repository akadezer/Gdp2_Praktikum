package studiplayer.audio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ControllablePlayListIterator implements Iterator<AudioFile> {
	private int index = 0;
	List<AudioFile> list = new LinkedList<AudioFile>();

	public ControllablePlayListIterator(List<AudioFile> list) {
		this.list = list;
	}

	public ControllablePlayListIterator(List<AudioFile> list, String search, SortCriterion sortCriterion) {
		if (search == null || search.isEmpty()) {
			for (AudioFile audioFile : list) {
				this.list.add(audioFile);
			}
		} else {
			for (AudioFile audioFile : list) {
				if (audioFile.getAuthor().contains(search) || audioFile.getTitle().contains(search)) {
					this.list.add(audioFile);

				}

				if (audioFile instanceof TaggedFile) {
					TaggedFile t1 = (TaggedFile) audioFile;
					if (t1.getAlbum() != null) {
						
						if (t1.getAlbum().contains(search)) {
							this.list.add(t1);
					}
					}
				}
			}
		}
		switch (sortCriterion) {
		case ALBUM:
			this.list.sort(new AlbumComparator());
			break;

		case DURATION:
			this.list.sort(new DurationComparator());

			break;
		case TITLE:
			this.list.sort(new TitleComparator());
			break;
		case AUTHOR:
			this.list.sort(new AuthorComparator());
			break;

		default:
			break;
		}
	}

	@Override
	public boolean hasNext() {
		return index < list.size();
	}

	@Override
	public AudioFile next() {
		if (hasNext()) {
			return list.get(index++);
		} else {
			return null;
		}
	}

	public AudioFile jumpToAudioFile(AudioFile file) {
		if (list.contains(file)) {
			return file;
		} else {
			return null;
		}
	}

}
