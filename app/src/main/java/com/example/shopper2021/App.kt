package com.example.shopper2021;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

/**
 * This class creates a Notification Channel for Shopper.  Notification Channels
 * are necessary starting with Android Oreo (API 26) to be able to show notifications.
 * The Notification Channel for Shopper will be created one time as soon as the
 * application starts.
 */
public class App extends Application {

    // declare and initialize a Channel Id (shortcut psfs)
    public static final String CHANNEL_SHOPPER_ID = "channelshopper";

    // override onCreate method (shortcut oncr)
    @Override
    public void onCreate() {
        super.onCreate();

        // call method that creates Notification Channel for Shopper
        createNotificationChannels();
    }

    /**
     * This method creates the Notification Channel for Shopper.
     */
    private void createNotificationChannels() {
        // check if on Android Oreo or higher b/c NotificationChannel class not
        // available on lower versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // initialize NotificationChannel - must pass Id, name, and importance level
            NotificationChannel channelshopper = new NotificationChannel(
                    CHANNEL_SHOPPER_ID,
                    "Channel Shopper",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            // customize channel - set its description
            channelshopper.setDescription("This is the Shopper Channel.");

            // create NotificationManager
            NotificationManager manager = getSystemService(NotificationManager.class);

            // create NotificationChannel
            manager.createNotificationChannel(channelshopper);
        }
    }
}
