package com.example.android.smartchef;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class ConfigActivity extends Activity implements View.OnClickListener {

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private static int mPosition ;

    public static int getmPosition() {
        return mPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        assignAppWidgetId();
        findViewById(R.id.widgetStartButton).setOnClickListener(this);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        //if no radio button is clicked mPosition will be 0 by default
        switch(view.getId()) {
            case R.id.nutella_pie:
                if (checked)
                    mPosition = 0;
                    break;
            case R.id.brownies:
                if (checked)
                    mPosition = 1;
                    break;
            case R.id.yellow_cake:
                if (checked)
                    mPosition = 2;
                    break;
            case R.id.cheesecake:
                if (checked)
                    mPosition = 3;
                    break;
        }
    }

    /**
     * Widget configuration activity,always receives appwidget Id appWidget Id =
     * unique id that identifies your widget analogy : same as setting view id
     * via @+id/viewname on layout but appwidget id is assigned by the system
     * itself
     */
    private void assignAppWidgetId() {
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.widgetStartButton)
            startWidget();
    }

    /**
     * This method right now displays the widget and starts a Service to fetch
     * remote data from Server
     */
    private void startWidget() {

        // this intent is essential to show the widget

        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(Activity.RESULT_OK, intent);

        // start your service
        // to fetch data from web
        Intent serviceIntent = new Intent(this, RemoteFetchService.class);
        serviceIntent
                .putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        startService(serviceIntent);

        // finish this activity
        this.finish();

    }

}