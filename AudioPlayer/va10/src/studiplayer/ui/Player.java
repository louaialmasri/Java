package studiplayer.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import studiplayer.audio.AudioFile;
import studiplayer.audio.NotPlayableException;
import studiplayer.audio.PlayList;

import java.net.URL;
import java.util.List;

public class Player extends Application {
	private Stage myStage;
	private Button playButton;
    private Button pauseButton;
    private Button stopButton;
    private Button nextButton;
    private Button editorButton;
	private Label songDescription = new Label("no current Song");
	private Label playTime = new Label("--:--");
	private PlayList playList = new PlayList();
	private PlayListEditor playListEditor = new PlayListEditor(this, this.playList);
	private String playListPathname;
	private boolean stopped = true;
	private boolean editorVisible;

	private static final String initPlayTime = "00:00";
	public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
	private static final String titlePrefix = "Current song:";
	   
	private Button createButton(String iconfile) {
		Button button = null;
		try {
			URL url = getClass().getResource("/icons/" + iconfile);
			Image icon = new Image(url.toString());
			ImageView imageView = new ImageView(icon);
			imageView.setFitHeight(48);
			imageView.setFitWidth(48);
			button = new Button("", imageView);
			button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		} catch (Exception e) {
			System.out.println("Image " + "icons/" + iconfile + " not found!");
			System.exit(-1);
		}
		return button;
	}
	
	public void start(Stage primaryStage) throws Exception {
		BorderPane mainPane = new BorderPane();
		playButton = createButton("play.png");
        pauseButton = createButton("pause.png");
        stopButton = createButton("stop.png");
        nextButton = createButton("next.png");
        editorButton = createButton("pl_editor.png");
		List<String> parameters = getParameters().getRaw();
		this.playList = new PlayList();
		if (!parameters.isEmpty()) {
			playList.loadFromM3U(parameters.get(0));
			playListPathname = parameters.get(0);
			songDescription.setText(playList.getCurrentAudioFile().toString());
			primaryStage.setTitle(titlePrefix + songDescription.getText());
			playTime.setText(initPlayTime);
		} else {
			playList.loadFromM3U(DEFAULT_PLAYLIST);
			playListPathname = DEFAULT_PLAYLIST;
			if (playList.getCurrentAudioFile() != null) {
			    primaryStage.setTitle(playList.getCurrentAudioFile().toString());
			    songDescription.setText(playList.getCurrentAudioFile().toString());
			} else {
			    primaryStage.setTitle(songDescription.getText());
			}
		}
		myStage = primaryStage;
		Button pauseButton = this.pauseButton;
		Button playButton = this.playButton;
		Button editorButton = this.editorButton;
		Button stopButton = this.stopButton;
		Button nextButton = this.nextButton;
		HBox container = new HBox(8);
		Scene scene = new Scene(mainPane, 700, 90);
		editorVisible = false;

		primaryStage.setScene(scene);
		primaryStage.show();
		mainPane.setTop(songDescription);
		mainPane.setCenter(container);
		container.getChildren().addAll(playTime, playButton, pauseButton, stopButton, nextButton, editorButton);

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
			nextSong();
			primaryStage.setTitle(songDescription.getText());
		});
		editorButton.setOnAction(e -> {
			if (editorVisible) {
				setEditorVisible(false);
				playListEditor.hide();
			} else {
				setEditorVisible(true);
				playListEditor.show();
			}
		});
	}

	public void setEditorVisible(boolean visible) {
        editorVisible = visible;
        if (!visible) {
            playListEditor.hide();
        }
    }

	   public void playCurrentSong() {
	      this.setButtonStates(true, false, false, false, false);
	      this.stopped = false;
	      AudioFile af = this.playList.getCurrentAudioFile();
	      this.updateSongInfo(af);
	      if(af != null) {
	         
	         (new TimerThread()).start();
	         (new PlayerThread()).start();
	      }
	   }
	   
	   private void pauseCurrentSong() {
		    if (!playList.isEmpty()) {
		        if (this.stopped == false) {
		            setButtonStates(true, false, false, false, false);
		            this.stopped = true;
		            playList.getCurrentAudioFile().togglePause();
		        } else {
		            setButtonStates(true, false, false, false, false);
		            this.stopped = false;
		            playList.getCurrentAudioFile().togglePause();
		        }
		    }
		}
	   
	   private void stopCurrentSong() {
			setButtonStates(false, true, false, true, false);
			this.stopped = true;
			if (!playList.isEmpty()) {
				playList.getCurrentAudioFile().stop();
			}
			playTime.setText("00:00");
		}
	   
	   private void nextSong() {
	      this.setButtonStates(true, false, false, false, false);
	      if(!this.stopped) {
	         this.stopCurrentSong();

	      }
	      this.playList.changeCurrent();
	      this.updateSongInfo(this.playList.getCurrentAudioFile());
	      this.playCurrentSong();
	   }
	   
	   private void setButtonStates(boolean playButtonState, boolean stopButtonState, boolean nextButtonState, boolean pauseButtonState, boolean editButtonState) {
			playButton.setDisable(playButtonState);
			stopButton.setDisable(stopButtonState);
			nextButton.setDisable(nextButtonState);
			pauseButton.setDisable(pauseButtonState);
			editorButton.setDisable(editButtonState);
		}

	   private void updateSongInfo(AudioFile af) {
		    if (!playList.isEmpty() && af != null) {
		        songDescription.setText(af.toString());
		        playTime.setText(af.getFormattedPosition());
		        Stage primaryStage = (Stage) songDescription.getScene().getWindow();
		        primaryStage.setTitle("Current song: " + af.toString());
		    } else {
		        songDescription.setText("no current Song");
		        playTime.setText("--:--");
		        Stage primaryStage = (Stage) songDescription.getScene().getWindow();
		        primaryStage.setTitle("no current song");
		    }
		}

		public String getPlayListPathname() {
			return playListPathname;
		}

		private void refreshUI() {
			Platform.runLater(() -> {
				if (playList != null && playList.size() > 0) {
					updateSongInfo(playList.getCurrentAudioFile());
					if (stopped) {
						setButtonStates(false, true, false, true, false);
					}

				} else {
					updateSongInfo(null);
					setButtonStates(true, true, true, true, false);
				}
			});
		}

		public void setPlayList(String pl) {
			if (pl != null) {
				playList.loadFromM3U(pl);
				if (!playList.isEmpty()) {
					playList.setCurrent(0);
				}
				refreshUI();
			} else {
				setPlayList(DEFAULT_PLAYLIST);
			}
			refreshUI();
		}

		
		private class TimerThread extends Thread {
	        public void run() {
	            while (!stopped && !playList.isEmpty()) {
	                playTime.setText(playList.getCurrentAudioFile().getFormattedPosition());
	                try {
	                    Thread.sleep(100);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
		
		private class PlayerThread extends Thread {
	        public void run() {
	            while (!stopped && !playList.isEmpty()) {
	                try {
	                	playList.getCurrentAudioFile().play();
	                } catch (NotPlayableException e) {
	                    e.printStackTrace();
	                }
	                if (!stopped) {
	                    playList.changeCurrent();
	                    updateSongInfo(playList.getCurrentAudioFile());
	                }
	            }
	        }
	    }
		
		public static void main(String[] args) {
			launch(args);
		}
	}