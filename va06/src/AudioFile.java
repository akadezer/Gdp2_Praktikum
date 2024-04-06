

public class AudioFile{


	private String pathname; // Pfadname
	private String filename; // Dateiname
	private String author; // Autor
	private String title; // Titel


	public AudioFile(){

	}
	public AudioFile(String path) {
		parsePathname(path);
		parseFilename(filename);

	}
	
	public void parsePathname(String path){
		char sep;
		if (isWindows()){
			 sep = Utils.emulateWindows();
		}else {
			sep = Utils.emulateLinux();
		}
		//Laufwerkskorrektur
		if (!isWindows()){

		if (path.contains(":")){

			path = sep + path.substring(0,path.indexOf(":")) + path.substring(path.indexOf(":")+1);
		}


		}



	// Testet ob der Pfadseperator öfter vorkommt
		for (int i = 0; i < path.length(); i++){

			if (path.charAt(i) == path.charAt(i + 1) && path.charAt(i) == sep){
				path = path.substring(0,(i+1)) + path.substring(i+2);
			}
		}


		pathname = path;
		if (path.lastIndexOf(sep)){

			filename =  path.substring(path.lastIndexOf(sep)+1);
		}

	}

	public void parseFilename(String filename){

		filename = 	filename.substring(0,filename.lastIndexOf("."));
		if (filename.contains(" - ")){
		// Trennung zwischen Autor und Titel
	String[] fileparts = filename.split(" - ");
	author = fileparts[0].trim();
	title = fileparts[1].trim();
		}else {
			title = filename;
		}
	if (filename.equals("-") ){
		author = "";
		title = "-";
	}


	}

	//Getter-Methoden
	public String getPathname(){
		
		return pathname;

	}
	
	public String getFilename() {
		return filename;
	}

	public String getAuthor(){
		return author;
	}

	public String getTitle(){
		return title;
	}

	@Override
	public String toString() {
		if (getAuthor().equals("")){
			return getTitle();
		}else{
		return getAuthor() + " - " + getTitle();}
	}

	// Systemabfrage
private boolean isWindows() {
	return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
}


}
