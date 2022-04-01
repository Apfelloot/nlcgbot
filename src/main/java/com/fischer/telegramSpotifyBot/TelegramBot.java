package com.fischer.telegramSpotifyBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			
			String videoTitle = "";
			
			if(update.getMessage().getText().contains("youtube.com") || update.getMessage().getText().contains("youtu.be")) {

				for(String word : update.getMessage().getText().split(" ")) {

					if (word.contains("youtube.com") || word.contains("youtu.be")){

						videoTitle = YoutubeBot.getVideoTitle(word.split("/")[3].replace("watch?v=", ""));
						videoTitle = videoTitle.replace("-", " ");
						videoTitle = videoTitle.replace("&", " ");
						videoTitle = videoTitle.replace("feat.", " ");
						videoTitle = videoTitle.replace("ft.", " ");
						videoTitle = videoTitle.replace("Ft.", " ");

					}

				}

				SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
				message.setChatId(update.getMessage().getChatId().toString());
				message.setText( SpotifyBot.addSongToPlaylist(videoTitle));
				try {
					execute(message); // Call method to send the message
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	@Override
	public String getBotUsername() {
		return "NLCgBot";
	}

	@Override
	public String getBotToken() {
		return "5178076066:AAGxwcB2zJY_UuYHnKbr-ybvA_4aq1uLtTg";
	}
}