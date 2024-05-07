import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class PlayList {
	
	
	public PlayList() {
	}

	public PlayList(String m3uPathname) {

	}
	
	//Audiofile Liste + dazugehoerige Methoden

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
		return null;
		
	}
	
	public void nextSong() {
		
	}
	
	public void loadFromM3U() {
		
	}
	
	public void saveFromM3U() {}
	public List<AudioFile> getList(){
		return audioFiles;
	}
	public int getCurrent() {
		return 0;
	}
	public void setCurrent(int current) {
		
	}
}
