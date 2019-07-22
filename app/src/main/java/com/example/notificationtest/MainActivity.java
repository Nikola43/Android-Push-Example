package com.example.notificationtest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "PUSH";
    private String firebaseToken;
    private String oneSignalToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID firebaseToken
                        firebaseToken = Objects.requireNonNull(task.getResult()).getToken();
                        // Log and toast
                        String msg =  "Firebase Token: " + firebaseToken;
                        Log.d(TAG, msg + oneSignalToken);
                        Log.d(TAG, msg);

                        //TODO
                        // update firebase token
                    }
                });

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                // get onesignal token
                OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
                oneSignalToken = status.getSubscriptionStatus().getUserId();

                Log.d(TAG, "OneSignal userId " + oneSignalToken);
                //Toast.makeText(MainActivity.this, "onesignal userId " + oneSignalToken, Toast.LENGTH_LONG).show();

                //TODO
                // update oneSignal token
            }
        });
    }

}
