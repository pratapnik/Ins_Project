package com.odroid.inspro.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.odroid.inspro.common.InsApp;

public class PreferenceUtils {
   private static final String PREF_FILE_NAME = "MoviePref";
   private static final String TOTAL_TRENDING_PAGES = "total_trending_pages";
   private static final String TOTAL_NOW_PLAYING_PAGES = "total_now_playing_pages";
   private static final String CURRENT_TRENDING_PAGE = "current_trending_page";
   private static final String CURRENT_NOW_PLAYING_PAGE = "current_now_playing_page";

   protected static SharedPreferences prefs;

   static {
      prefs = InsApp.getContext().getSharedPreferences(PREF_FILE_NAME,
              Context.MODE_PRIVATE);
   }

   public static void setTotalTrendingPages(int pages) {
      setIntPreference(TOTAL_TRENDING_PAGES, pages);
   }

   public static int getTotalTrendingPages() {
      return getIntPreference(TOTAL_TRENDING_PAGES, 0);
   }

   public static void setTotalNowPlayingPages(int pages) {
      setIntPreference(TOTAL_NOW_PLAYING_PAGES, pages);
   }

   public static int getTotalNowPlayingPages() {
      return getIntPreference(TOTAL_NOW_PLAYING_PAGES, 0);
   }

   public static void setCurrentTrendingPage(int page) {
      setIntPreference(CURRENT_TRENDING_PAGE, page);
   }

   public static int getCurrentTrendingPage() {
      return getIntPreference(CURRENT_TRENDING_PAGE, 0);
   }

   public static void setCurrentNowPlayingPage(int page) {
      setIntPreference(CURRENT_NOW_PLAYING_PAGE, page);
   }

   public static int getCurrentNowPlayingPage() {
      return getIntPreference(CURRENT_NOW_PLAYING_PAGE, 0);
   }

   public static int getIntPreference(String key, int defValue) {
      return prefs.getInt(key, defValue);
   }

   public static void setIntPreference(String key, int value) {
      prefs.edit().putInt(key, value).apply();
   }

}

