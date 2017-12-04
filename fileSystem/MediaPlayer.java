package fileSystem;

public class MediaPlayer {
	public void playMusicAndVideo(String path) throws Exception {
		String[] strarr;
		String newPath = "";
		strarr = path.split("/");
		for (int i = 0; i < strarr.length; i++) {
			newPath += strarr[i] + "\\\\";
		}
		newPath=newPath.substring(0, newPath.length()-2);
		if (newPath == null) {
			System.out.println("경로명이 명확하지 않습니다(in MusicPlayer");
		} else {
			Runtime run = Runtime.getRuntime();
			String ss = String.format("rundll32 SHELL32.DLL,ShellExec_RunDLL  %s", newPath);
			run.exec(ss);
		}
	}
}
