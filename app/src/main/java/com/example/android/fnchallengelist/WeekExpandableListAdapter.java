package com.example.android.fnchallengelist;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gbolatto on 5/28/2018.
 */
public class WeekExpandableListAdapter extends BaseExpandableListAdapter {

    private final LayoutInflater mInflater;
    private Map<String, List<Challenge>> mChallengeHashMap = new HashMap<String, List<Challenge>>();
    private ArrayList<String> mHeaders = new ArrayList<String>();
    private List<Week> mWeeks;
    private List<Challenge> mChallenges;

    public WeekExpandableListAdapter(Activity context){
        mInflater = LayoutInflater.from(context);
    }

    /*
     * Takes in a list of Week objects and creates the mHeaders ArrayList for group titles and
     * mChallengeHashMap for each week
     */
    public void setWeeks(List<Week> weeks) {

        if (mWeeks == null) {
            mWeeks = weeks;
        } else {
            mWeeks.clear();
            mHeaders.clear();
            mWeeks = weeks;
        }

        for (int n = 0; n < mWeeks.size(); n++) {
            String weekName = mWeeks.get(n).getWeekName();
            mHeaders.add(weekName);
        }

        notifyDataSetChanged();
    }

    // sometimes mWeeks here isn't populated yet before setChallenges runs.
    // TODO: change the mWeeks null check to something else?
    public void setChallenges(List<Challenge> challenges) {
        if (mWeeks != null) {     // null check to make sure mWeeks is populated before continuing
            if (mChallenges == null) {
                mChallenges = challenges;
            } else {
                mChallenges.clear();
                mChallengeHashMap.clear();
            }

            for (int n = 0; n < mWeeks.size(); n++) {

                int weekId = mWeeks.get(n).getId();
                String weekName = mWeeks.get(n).getWeekName();

                List<Challenge> challengesByWeek = new ArrayList<>();

                for (int i = 0; i < challenges.size(); i++) {
                    if (challenges.get(i).getWeekId() == weekId){
                        challengesByWeek.add(challenges.get(i));
                    }
                }

                mChallengeHashMap.put(weekName, challengesByWeek);
            }

            notifyDataSetChanged();
        }
    }


    public class ChildViewHolder {
        TextView challengeTextView;
        ImageView completionIconImageView;
    }

    public class GroupViewHolder {
        TextView weekNameTextView;
        ImageView indicatorImageView;
    }


    @Override
    public Object getChild(int groupPos, int childPos) {
        return mChallengeHashMap.get(mHeaders.get(groupPos)).get(childPos);
    }

    @Override
    public long getChildId(int groupPos, int childPos) {
        return childPos;
    }

    @Override
    public View getChildView(final int groupPos, final int childPos, boolean isLastChild, View view, ViewGroup parent) {

        ChildViewHolder holder = new ChildViewHolder();

        if (view == null ) {
            view = mInflater.inflate(R.layout.week_list_item, parent, false);
            holder.challengeTextView = view.findViewById(R.id.challenge_item);
            holder.completionIconImageView = view.findViewById(R.id.item_icon);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }

        Challenge challenge = (Challenge) getChild(groupPos, childPos);
        holder.challengeTextView.setText(challenge.getChallengeString());

        if (challenge.getCompletion()) {
            holder.completionIconImageView.setImageResource(R.drawable.check_icon);
        } else {
            holder.completionIconImageView.setImageResource(R.drawable.unchecked_icon);
        }

        /* TODO: make this onClick performItemClick work so we can just click the check box
         * and not the entire line item

        final ViewGroup par = parent;
        holder.completionIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ExpandableListView) par).performItemClick(view, childPos, 0);
            }
        });
        */

        return view;
    }

    @Override
    public int getChildrenCount(int groupPos) {

        // just in case data is not ready
        if (mChallengeHashMap != null && mHeaders != null) {
            return mChallengeHashMap.get(mHeaders.get(groupPos)).size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPos) {
        return mHeaders.get(groupPos);
    }

    @Override
    public int getGroupCount() {

        // just in case data is not ready
        if (mHeaders != null) {
            return mHeaders.size();
        } else {
            return 0;
        }
    }

    @Override
    public long getGroupId(int groupPos) {
        return groupPos;
    }

    @Override
    public View getGroupView(int groupPos, boolean isExpanded, View view, ViewGroup parent) {

        GroupViewHolder holder = new GroupViewHolder();
        if (view == null ) {
            view = mInflater.inflate(R.layout.week_list_header, parent, false);
            holder.weekNameTextView = view.findViewById(R.id.week_header);
            holder.indicatorImageView = view.findViewById(R.id.indicator);
            view.setTag(holder);
        } else {
            holder = (GroupViewHolder) view.getTag();
        }

        String weekName = (String) getGroup(groupPos);

        // the extra space is needed since with the current font selection, part of the last letter
        // gets cut off. perhaps a way to fix with different padding/margins?
        holder.weekNameTextView.setText(weekName + " ");

        if (isExpanded) {
            holder.indicatorImageView.setImageResource(R.drawable.up_arrow);
        } else {
            holder.indicatorImageView.setImageResource(R.drawable.down_arrow);
        }

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPos, int childPos) {
        return true;
    }
}
