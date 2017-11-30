package fileSystem;

public class MusicPlayer {
	public void playMusicAndVideo(String path) throws Exception {
		Runtime run = Runtime.getRuntime();
		String ss= String.format("C:\\Program Files\\Windows Media Player\\wmplayer.exe  %s", path);
		run.exec(ss);
	}
}
