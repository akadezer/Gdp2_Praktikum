import studiplayer.basic.BasicPlayer;

abstract public class SampledFile extends AudioFile {

	public SampledFile() {
	super();
	}

	public SampledFile(String path) {
	super(path);
	}

	public void play() {
		BasicPlayer.play(getFilename());
	}

	public void togglePause() {
		BasicPlayer.togglePause();
	}

	public void stop() {
		BasicPlayer.stop();
	}

	public String formatDuration() {

		return null;
	}

	public String formatPositon() {

		return null;
	}

	public static String timeFormatter(long timeInMicroSeconds) {
		return null;
	}

	public long getDuration() {
		return 0;

	}
}
