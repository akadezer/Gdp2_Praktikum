package studiplayer.audio;

import java.util.Comparator;


public class AlbumComparator implements Comparator<AudioFile> {

	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		if (o1 == null || o2 == null) {

			throw new RuntimeException();
		}
	if (o1 instanceof TaggedFile && !(o2 instanceof TaggedFile)) {
			
			return 1;
		} else if (!(o1 instanceof TaggedFile) && o2 instanceof TaggedFile) {
			return -1; 
		
		} else if (o1 instanceof TaggedFile && o2 instanceof TaggedFile) {
			TaggedFile s1 = (TaggedFile) o1;
			TaggedFile s2 = (TaggedFile) o2;
			if (s1.album == null || s2.album == null) {
				return 0;
			}	
				return  s1.getAlbum().compareTo(s2.getAlbum());
			
		}else return 0 ;
	}
	}


