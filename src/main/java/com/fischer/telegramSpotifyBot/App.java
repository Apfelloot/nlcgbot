package com.fischer.telegramSpotifyBot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	 try {
    		 System.out.println(args[0]);
    		 SpotifyBot.init(args[0]);
             TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
             botsApi.registerBot(new TelegramBot());
         } catch (TelegramApiException e) {
             e.printStackTrace();
         }
    }
}
