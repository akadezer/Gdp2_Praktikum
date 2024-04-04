import java.awt.desktop.SystemSleepEvent;

public class AudioFile{


	private String pathname; 
	private String filename;
	
	public AudioFile() {
		
	}
	
	public void parsePathname(String path){
		char sep;
		if (isWindows()){
			 sep = Utils.emulateWindows();
		}else {
			sep = Utils.emulateLinux();
		}
		// Laufwerksangabe 

	// Testet ob der Pfadseperator öfter vorkommt
		for (int i = 0; i < path.length(); i++){

			if (path.charAt(i) == path.charAt(i + 1) && path.charAt(i) == sep){
				path = path.substring(0,(i+1)) + path.substring(i+2);
			}
		}
	}
	
	public String getPathname(){
		
		return pathname;
	}
	
	public String getFilename() {
		return filename;
	}
private boolean isWindows() {
	return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
}


}
