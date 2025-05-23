package studiplayer.audio;

import java.io.File;

import javax.management.RuntimeErrorException;

import studiplayer.basic.BasicPlayer;

abstract public class SampledFile extends AudioFile {

	protected long duration;

	public SampledFile() throws NotPlayableException {
		super();
	}

	public SampledFile(String path) throws NotPlayableException {
		super(path);
	}

	public void play() throws NotPlayableException {
		try {

			BasicPlayer.play(getPathname());
		} catch (Exception e) {
			throw new NotPlayableException();
		}
	}

	public void togglePause() {
		BasicPlayer.togglePause();
	}

	public void stop() {
		BasicPlayer.stop();
	}

	public String formatDuration() {

		return timeFormatter(getDuration());
	}

	public String formatPosition() {

		return timeFormatter(BasicPlayer.getPosition());
	}

	public static String timeFormatter(long timeInMicroSeconds) {
		if (timeInMicroSeconds >= 0 && timeInMicroSeconds < (6000000000L)) {

//		Long timeInMicroSecondsLong = Math.divideExact(timeInMicroSeconds, 1000L);
			long minuten = timeInMicroSeconds / (1000 * 1000 * 60);
			long sekunden = (timeInMicroSeconds / (1000 * 1000)) % 60;

			return String.format("%02d:%02d", minuten, sekunden);
		} else {
			throw new RuntimeException();

		}

	}

	public long getDuration() {
		return this.duration;

	}
}
