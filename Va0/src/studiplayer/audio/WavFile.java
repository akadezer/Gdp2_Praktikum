package studiplayer.audio;

import java.io.File;

import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {
	public WavFile() throws NotPlayableException {
		super();
	}

	public WavFile(String path) throws NotPlayableException {
		super(path);

		readAndSetDurationFromFile();
	}

	public void readAndSetDurationFromFile() throws NotPlayableException {

		try {
			WavParamReader.readParams(getPathname());
			this.duration = computeDuration(WavParamReader.getNumberOfFrames(), WavParamReader.getFrameRate());

		} catch (Exception e) {
			throw new NotPlayableException();
		}
	}

	public String toString() {

		return super.toString() + " - " + formatDuration();
	}

	public static long computeDuration(long numberOfFrames, float frameRate) {

		Float float1 = numberOfFrames / frameRate;
		float1 *= 1000000;
		return float1.longValue();
	}
}
