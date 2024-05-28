package studiplayer.ui;

import java.awt.Button;
import java.awt.Label;
import java.awt.TextField;
import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import studiplayer.audio.PlayList;

public class Player extends Application {

	public static String DEFAULT_PLAYLIST = "playList.cert.m3u";
	private static String PLAYLIST_DIRECTORY; 
	private static String INITIAL_PLAY_TIME_LABEL; 
	private static String NO_CURRENT_SONG; 
	private PlayList playList;
	private boolean useCertPlayList = false;
	
	
	private Button playButton = new Button();
	private Button pauseButton = new Button();
	private Button stopButton = new Button();
	private Button nextButton = new Button();
	
	private Label playListLabel = new Label();
	private Label playTimeLabel = new Label();
	private Label currentSongLabel = new Label();
	
	private ChoiceBox sortChoiceBox = new ChoiceBox();
	
	private TextField searchTextField = new TextField();
	
	private Button filterButton = new Button();

	public Player() {
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane mainPane = new BorderPane();
		mainPane.setCenter(new SongTable(playList));
		stage.setTitle("in Scene");
		Scene scene = new Scene(mainPane, 600, 400);
		stage.setScene(scene);
		stage.show();

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("m3u files", "*.m3u"));
		if (useCertPlayList) {
			fileChooser.showOpenDialog(stage);

		} else {

		}
	}

	public static void main(String[] args) {
		launch();
	}

	public void setUseCertPlayList(boolean value) {
		this.useCertPlayList = value;
	}

	public void loadPlayList(String pathname) {
		if (pathname == null || pathname.isEmpty()) {
			playList = new PlayList(pathname);
		}else {
		playList = new PlayList(DEFAULT_PLAYLIST);
		}
	}

}
