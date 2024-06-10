package studiplayer.ui;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URL;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.xml.stream.events.StartDocument;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import studiplayer.audio.AudioFile;
import studiplayer.audio.NotPlayableException;
import studiplayer.audio.PlayList;
import studiplayer.audio.SortCriterion;

public class Player extends Application {

	public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
	private static final String PLAYLIST_DIRECTORY = "playlists/";
	private static final String INITIAL_PLAY_TIME_LABEL = "00:00";
	private static final String NO_CURRENT_SONG = "-";
	private PlayList playList;
	private boolean useCertPlayList = false;

	private Button playButton = createButton("play.jpg");
	private Button pauseButton = createButton("pause.jpg");
	private Button stopButton = createButton("stop.jpg");
	private Button nextButton = createButton("next.jpg");

	private Label playListLabel = new Label();
	private Label playTimeLabel = new Label();
	private Label currentSongLabel = new Label();

	private ChoiceBox<SortCriterion> sortChoiceBox = new ChoiceBox();

	private TextField searchTextField = new TextField();

	private Button filterButton = new Button("Anzeigen");

	TimerThread tThread = null;
	PlayerThread pThread = null;
	SongTable table;

	public Player() {
	}

	public void start(Stage stage) throws Exception {
		playButton.setText("playButton");
		stopButton.setText("stopButton");
		pauseButton.setText("pauseButton");
		nextButton.setText("nextButton");
		stopButton.setDisable(true);
		pauseButton.setDisable(true);

		sortChoiceBox.getItems().setAll(SortCriterion.values());
		sortChoiceBox.setValue(SortCriterion.DEFAULT);
		File file = null;

		if (useCertPlayList) {
			FileChooser fileChooser = new FileChooser();
			File playListDir = new File(PLAYLIST_DIRECTORY);
			fileChooser.setInitialDirectory(playListDir);
			fileChooser.setInitialFileName("playlists/playList.cert.m3u");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("m3u files", "*.m3u"));
//			file = fileChooser.showOpenDialog(stage);
			
			if (file != null) {
				
				playList = new PlayList(file.getPath());
			}else {
				playList = new PlayList(fileChooser.getInitialFileName()); 
			}

		} else {
			playList = new PlayList(DEFAULT_PLAYLIST);
		}

		// Choicebox

		sortChoiceBox.setPrefWidth(200);
		BorderPane mainPane = new BorderPane();

		// Center
		table = new SongTable(playList);
		mainPane.setCenter(table);

		// Top

		TitledPane filterPane = new TitledPane();
		GridPane filterGridPane = new GridPane();
		filterGridPane.add(new Label("Suchtext"), 0, 0);
		filterGridPane.add(new Label("Sortierung"), 0, 1);
		filterGridPane.add(searchTextField, 1, 0);
		filterGridPane.add(sortChoiceBox, 1, 1);

		// Filter-Button Event

		filterButton.setOnAction(e -> {
			playList.setSearch(searchTextField.getText());
			playList.setSortCriterion(sortChoiceBox.getValue());
			table.refreshSongs();
		});

		filterGridPane.add(filterButton, 2, 1);
		filterGridPane.setVgap(10);
		filterGridPane.setHgap(10);
		filterPane.setText("Filter");
		filterPane.setContent(filterGridPane);

		mainPane.setTop(filterPane);

		// Bottom

		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10));

		GridPane gridPane = new GridPane();
		ColumnConstraints col1 = new ColumnConstraints();
		gridPane.getColumnConstraints().addAll(new ColumnConstraints(50), col1);
		gridPane.setHgap(10);
		gridPane.add(new Label("Playlist"), 0, 0);

		// Ausgewaehlte Playlist
		if (useCertPlayList) {
			if (playList != null && file != null) {
				playListLabel.setText(file.getName());
				gridPane.add(playListLabel, 1, 0);
			}
		} else {

			playListLabel.setText(DEFAULT_PLAYLIST);
			gridPane.add(playListLabel, 1, 0);
		}

		// Aktueller Song

		gridPane.add(new Label("Aktuelles Lied"), 0, 1);

		if (playList.currentAudioFile() != null) {
			currentSongLabel.setText(playList.currentAudioFile().toString());
			gridPane.add(currentSongLabel, 1, 1);
		} else {

			gridPane.add(new Label(NO_CURRENT_SONG), 1, 1);
		}
		playTimeLabel.setText(INITIAL_PLAY_TIME_LABEL);
		gridPane.add(new Label("Abspielzeit"), 0, 2);
		gridPane.add(playTimeLabel, 1, 2);
		vBox.getChildren().add(gridPane);
		HBox controllButtonsBox = new HBox();

		// Liedkontroll-Buttons

		playButton.setOnAction(e -> {
			playCurrentSong();
		});

		pauseButton.setOnAction(e -> {
			pauseCurrentSong();
		});

		stopButton.setOnAction(e -> {
			stopCurrentSong();
		});

		nextButton.setOnAction(e -> {
			nextCurrentSong();
		});

		controllButtonsBox.getChildren().add(playButton);
		controllButtonsBox.getChildren().add(pauseButton);
		controllButtonsBox.getChildren().add(stopButton);
		controllButtonsBox.getChildren().add(nextButton);
		controllButtonsBox.setAlignment(Pos.CENTER);
		controllButtonsBox.setSpacing(5);

		vBox.getChildren().add(controllButtonsBox);

		mainPane.setBottom(vBox);
		stage.setTitle("APA Player");
		Scene scene = new Scene(mainPane, 600, 400);
		stage.setScene(scene);
		stage.show();

	}

	private void nextCurrentSong() {
		currentSongLabel.setText(playList.currentAudioFile().getFilename());
		stopButton.setDisable(false);
		pauseButton.setDisable(false);
		playButton.setDisable(true);

		System.out.println("Switching to next Song");
		System.out.println(playList.currentAudioFile().toString());
		playList.currentAudioFile().stop();
		terminateThreads(false);
		playList.nextSong();
		playList.currentAudioFile().stop();
		startThreads(false);
		playTimeLabel.setText(INITIAL_PLAY_TIME_LABEL);
	}

	private void stopCurrentSong() {

		System.out.println("*Pausing* " + playList.currentAudioFile().toString());
		System.out.println("Filename is " + playList.currentAudioFile().getFilename());
		System.out.println("");
		playButton.setDisable(false);
		stopButton.setDisable(true);
		pauseButton.setDisable(true);
		terminateThreads(false);
		playList.currentAudioFile().stop();
		playTimeLabel.setText(INITIAL_PLAY_TIME_LABEL);

	}

	private void pauseCurrentSong() {
		System.out.println("_Pausing_ " + playList.currentAudioFile().toString());
		System.out.println("Filename is " + playList.currentAudioFile().getFilename());
		System.out.println("");
		if (tThread != null) {
			terminateThreads(true);
			playList.currentAudioFile().togglePause();

		} else {
			startThreads(true);
			playList.currentAudioFile().togglePause();
		}

	}

	private void playCurrentSong() {
		playButton.setDisable(true);
		pauseButton.setDisable(false);
		stopButton.setDisable(false);
		playTimeLabel.setText(INITIAL_PLAY_TIME_LABEL);
		startThreads(false);

	}

	public static void main(String[] args) {
		launch();

	}

	public void setUseCertPlayList(boolean value) {
		this.useCertPlayList = value;
	}

	public void loadPlayList(String pathname) {
		if (pathname == null || pathname.isEmpty()) {
			playList = new PlayList(DEFAULT_PLAYLIST);
		} else {
			playList = new PlayList(pathname);
		}
	}

	private Button createButton(String iconfile) {
		Button button = null;
		try {
			URL url = getClass().getResource("/icons/" + iconfile);
			Image icon = new Image(url.toString());
			ImageView imageView = new ImageView(icon);
			imageView.setFitHeight(20);
			imageView.setFitWidth(20);
			button = new Button("", imageView);
			button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			button.setStyle("-fx-background-color: #fff;");
		} catch (Exception e) {
			System.out.println("Image " + "icons/" + iconfile + " not found!");
			System.exit(-1);
		}
		return button;
	}

	private void updateSongInfo(AudioFile af) {
		Platform.runLater(() -> {
			if (af == null) {
				currentSongLabel.setText(NO_CURRENT_SONG);
				playTimeLabel.setText(INITIAL_PLAY_TIME_LABEL);
			} else {
				playTimeLabel.setText(playList.currentAudioFile().formatPosition());
			}
		});

	}

	class PlayerThread extends Thread {
		private boolean stopped = false;

		@Override
		public void run() {
			while (!stopped) {
				try {

					table.selectSong(playList.currentAudioFile());
					playList.currentAudioFile().play();

					if (playTimeLabel.getText().equals(playList.currentAudioFile().formatDuration())) {
						playList.nextSong();
						currentSongLabel.setText(playList.currentAudioFile().toString());
					}

				} catch (NotPlayableException e) {
					e.printStackTrace();
				}
			}
		}

		public void terminate() {
			stopped = true;
		}

	}

	class TimerThread extends Thread {
		private boolean stopped = false;

		public void run() {
			while (!stopped) {
				try {
					updateSongInfo(playList.currentAudioFile());
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void terminate() {
			stopped = true;
		}

	}

	private void startThreads(boolean onlyTimer) {
		tThread = new TimerThread();
		tThread.start();
		if (!onlyTimer) {
			pThread = new PlayerThread();
			pThread.start();
		}
	}

	private void terminateThreads(boolean onlyTimer) {
		if (tThread != null) {
			tThread.terminate();
			tThread = null;
		}
		
		if (!onlyTimer) {
			if (pThread != null) {
				
			pThread.terminate();
			pThread = null;
			}

		}
	}
}
