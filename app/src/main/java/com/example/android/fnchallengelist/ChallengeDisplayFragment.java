package com.example.android.fnchallengelist;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbolatto on 6/2/2018.
 */
public class ChallengeDisplayFragment extends Fragment {

    WeekViewModel mWeekViewModel;
    ExpandableListView expandableListView;
    WeekExpandableListAdapter mExpandableListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.challenge_display_fragment, container, false);
        final TextView titleView = rootView.findViewById(R.id.season_title);
        final LinearLayout errorInfoContainer = rootView.findViewById(R.id.error_info_container);
        final TextView errorInfoTextView = errorInfoContainer.findViewById(R.id.error_info_text);

        mWeekViewModel = ViewModelProviders.of(this).get(WeekViewModel.class);

        expandableListView = (ExpandableListView) rootView.findViewById(R.id.week_list);
        expandableListView.setDividerHeight(0);

        mExpandableListAdapter = new WeekExpandableListAdapter(getActivity());


        // TODO: figure out the livedatas in the modelview to see if we can get
        // the livedatas info all at once in a single observer. (MediatorLiveData?)
        // then we can check if all are not null and do the errorInfoContainer visibility correctly

        // gets latest season name from database and updates the view. if no database entry,
        // show the errorInfoContainer
        mWeekViewModel.getLatestSeasonName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String latestSeasonName) {
                if (latestSeasonName != null ){
                    titleView.setText(latestSeasonName);
                    errorInfoContainer.setVisibility(View.GONE);
                } else {
                    errorInfoTextView.setText(R.string.database_error);
                    errorInfoContainer.setVisibility(View.VISIBLE);
                }
            }
        });


        /*
         * TODO: figure out how to only trigger these when both weeks and challenges livedata
         * is not null at the same time and remove the nullcheck in
         * WeekExpandableListAdapter.setChallenges()
         * setChallenges requires Weeks to be populated in the adapter. sometimes crashes since
         * weeks isn't updated before challenges. temporary fix in
         * WeekExpandableListAdapter.setChallenges() with a nullcheck
        */
        mWeekViewModel.getAllWeeks().observe(this, new Observer<List<Week>>() {
            @Override
            public void onChanged(@Nullable final List<Week> weeks) {
                if (weeks != null) {
                    mExpandableListAdapter.setWeeks(weeks);
                }
            }
        });

        mWeekViewModel.getAllChallenges().observe(this, new Observer<List<Challenge>>() {
            @Override
            public void onChanged(@Nullable List<Challenge> challenges) {
                if (challenges != null) {
                    mExpandableListAdapter.setChallenges(challenges);
                }
            }
        });

        expandableListView.setAdapter(mExpandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int childPos, long id) {

                Challenge challenge = (Challenge) mExpandableListAdapter.getChild(groupPos, childPos);
                if (!challenge.getCompletion()) {
                    challenge.setCompletion(true);
                } else {
                    challenge.setCompletion(false);
                }
                mWeekViewModel.updateChallenge(challenge);
                return false;
            }
        });
        return rootView;
    }
}
