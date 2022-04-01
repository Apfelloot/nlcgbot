package com.fischer.telegramSpotifyBot;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class YoutubeBot {

	private static String apiKey = "AIzaSyAT2-Xx0Qe8_LyMCj3kwKt_uJEDriN4oyU";
	
	public static String getVideoTitle(String videoId) {
		
		String content = null;
		URLConnection connection = null;
		try {
		  connection =  new URL("https://www.googleapis.com/youtube/v3/videos?id="+ videoId + "&key=" + apiKey +"&part=snippet").openConnection();
		  Scanner scanner = new Scanner(connection.getInputStream());
		  scanner.useDelimiter("\\Z");
		  content = scanner.next();
		  scanner.close();
		}catch ( Exception ex ) {
		    ex.printStackTrace();
		}
		
		return content.split("\"title\"")[1].split("\"")[1];
		
	}
	
}
