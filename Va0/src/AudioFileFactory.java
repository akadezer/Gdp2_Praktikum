

import javax.management.RuntimeErrorException;

public class AudioFileFactory {
	public static AudioFile createAudioFile(String path) {
		
	String ext = path.substring(path.lastIndexOf("."));
	 if( ext.equalsIgnoreCase(".wav")) {
		 return new WavFile(path);
	 } else if (ext.equalsIgnoreCase(".ogg") || ext.equalsIgnoreCase(".mp3")) {
		 return new TaggedFile(path);
		
	}
	throw new RuntimeException("Unknown suffix for AudioFile " +'"' + path + '"')	;
	}
	}

