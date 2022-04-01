package com.fischer.telegramSpotifyBot;

import java.io.IOException;
import java.net.URI;

import org.apache.hc.core5.http.ParseException;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.special.SearchResult;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import se.michaelthelin.spotify.requests.data.search.SearchItemRequest;

public class SpotifyBot {
	private static final String playlistId = "5n4TgUA9zled2qmtS5CH3F";
	private static final String clientId = "8f1c407b38904e20bdfe66f952f3fcc4";
	private static final String clientSecret = "b6ecfb2fe5274909938961ebd903c100";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("https://google.com/");
	private static String code = "";
	private static SpotifyApi spotifyApi = new SpotifyApi.Builder()
			.setClientId(clientId)
			.setClientSecret(clientSecret)
			.setRedirectUri(redirectUri)
			.build();
			
	public static void init(String authCode){

		code = authCode;
		
		AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
				.build();
		
		try {
		    
			final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

			// Set access and refresh token for further "spotifyApi" object usage
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
			//	      System.out.println(authorizationCodeCredentials.getAccessToken());
			spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			System.out.println("Error1: " + e.getMessage());
		}
	}
	
	public static void refresh(){

		 AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
				    .build();

				    try {
				      final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

				      // Set access and refresh token for further "spotifyApi" object usage
				      spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

//			System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			System.out.println("Error2: " + e.getMessage());
		}
	}
	
	public static String addSongToPlaylist(String searchPhrase) {

		refresh();
		
			SearchItemRequest searchItemRequest = spotifyApi.searchItem(searchPhrase, ModelObjectType.TRACK.getType()).build();
	      SearchResult searchResult;
		try {
			searchResult = searchItemRequest.execute();
			if (searchResult.getTracks().getTotal() == 0) return "Track not found on Spotify";
			else {
				AddItemsToPlaylistRequest addItemsToPlaylistRequest = spotifyApi
				.addItemsToPlaylist(playlistId, new String[]{"spotify:track:" + searchResult.getTracks().getItems()[0].getId()})
				//	          .position(0)
				.build();
				addItemsToPlaylistRequest.execute();
				return "Found a track on Spotify, let's hope it's the right one! (" + "https://open.spotify.com/playlist/5n4TgUA9zled2qmtS5CH3F?si=ff0f698642744fc3" + ")";
			}
						
		} catch (ParseException | SpotifyWebApiException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "Found a track on Spotify, let's hope it's the right one! (" + "https://open.spotify.com/playlist/5n4TgUA9zled2qmtS5CH3F?si=ff0f698642744fc3" + ")";
		}

	}	  

}
