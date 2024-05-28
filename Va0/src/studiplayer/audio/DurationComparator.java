package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile> {

	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		if (o1 == null || o2 == null) {

			throw new RuntimeException();
		}
		if (o1 instanceof SampledFile && !(o2 instanceof SampledFile)) {
			
			return 1;
		} else if (!(o1 instanceof SampledFile) && o2 instanceof SampledFile) {
			return -1; 
		
		} else if (o1 instanceof SampledFile && o2 instanceof SampledFile) {
			SampledFile s1 = (SampledFile) o1;
			SampledFile s2 = (SampledFile) o2;
			return (int) (s1.getDuration() - s2.getDuration());
		}else return 0 ;
		
		
		
	}

}
