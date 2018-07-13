package com.example.android.fnchallengelist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends FragmentActivity {

    //
    String BASE_URL = "http://192.168.50.168";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //checks to see if there is an internet connection
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);


        if (findViewById(R.id.fragment_container) != null ) {
            if (savedInstanceState != null) {
                return;
            }

            ChallengeDisplayFragment challengeDisplayFragment = new ChallengeDisplayFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, challengeDisplayFragment).commit();
        }
    }


    BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

            if (noConnectivity) {
                connectionLost();
            } else {
                connectionFound();
            }
        }
    };

    /*
     * shows the view that explains to user there is no internet connection
     */
    void connectionLost() {
        LinearLayout internetStatusContainer = findViewById(R.id.internet_status_container);
        internetStatusContainer.setVisibility(View.VISIBLE);
    }

    /*
     * hides the view that explains to user there is no internet connection
     */

    void connectionFound() {
        LinearLayout internetStatusContainer = findViewById(R.id.internet_status_container);
        internetStatusContainer.setVisibility(View.GONE);
    }

    /*
     * used to delete all the seasons. uncomment if needed in future...
     */
    /*
    public void deleteSeason(View view){
        WeekRoomDatabase database = WeekRoomDatabase.getWeekDatabase(this);
        database.seasonDao().deleteAllSeasons();
    }
    */
}