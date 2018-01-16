package com.example.android.smartchef;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.example.android.smartchef.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class RemoteFetchService extends Service {

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private String remoteJsonUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static ArrayList<String> listItemList;
    private String[] mIngredients = null ;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID))
            appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

        new FetchIngredientsTask().execute(remoteJsonUrl);

        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * Method which sends broadcast to WidgetProvider
     * so that widget is notified to do necessary action
     * and here action == WidgetProvider.DATA_FETCHED
     */
    private void populateWidget() {

        Intent widgetUpdateIntent = new Intent();
        widgetUpdateIntent.setAction(ChefWidgetProvider.DATA_FETCHED);
        widgetUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
        sendBroadcast(widgetUpdateIntent);

        this.stopSelf();
    }

    public class FetchIngredientsTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            try {

                URL url = new URL(remoteJsonUrl);

                String jsonRecipeResponse = NetworkUtils.getResponseFromHttpUrl(url);

                String[] simpleJsonIngredients = NetworkUtils.getSimpleIngredientsFromJson(jsonRecipeResponse, ConfigActivity.getmPosition());

                return simpleJsonIngredients;

            } catch (Exception e) {

                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] ingredients) {
            if (ingredients != null) {
                mIngredients = ingredients;
                listItemList = new ArrayList<String>(Arrays.asList(mIngredients));
                populateWidget();
            }
        }
    }
}