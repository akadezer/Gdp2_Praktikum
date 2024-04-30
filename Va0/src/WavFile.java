import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {
	public WavFile() {
		super();
	}

	public WavFile(String path) {
		super(path);

		readAndSetDurationFromFile();
	}

	public void readAndSetDurationFromFile() {
		WavParamReader.readParams(getPathname());
		this.duration = computeDuration(WavParamReader.getNumberOfFrames(), WavParamReader.getFrameRate());
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
