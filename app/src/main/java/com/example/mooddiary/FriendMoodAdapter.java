package com.example.mooddiary;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * This is an adapter for displaying each MoodEvent in the friend list.
 */
public class FriendMoodAdapter extends ArrayAdapter<MoodEvent> {
    private int resourceId;

    public FriendMoodAdapter(Context context, int textViewResourceId, List<MoodEvent> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    /**
     * This gets a View that displays the data at the specified position in the data set.
     * @param position
     *      This is the position of the item within the adapter's data set of the item whose view we want.
     * @param convertView
     *      This is the old view to reuse, if possible.
     * @param parent
     *      This is the the parent that this view will eventually be attached to.
     * @return
     *      Return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MoodEvent moodEvent = getItem(position);
        View view;
        FriendMoodAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view =
                    LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new FriendMoodAdapter.ViewHolder();
            viewHolder.moodImage = view.findViewById(R.id.friend_mood_image);
            viewHolder.username = view.findViewById(R.id.friend_username_text);
            viewHolder.dateText = view.findViewById(R.id.friend_mood_date_text);
            viewHolder.timeText = view.findViewById(R.id.friend_mood_time_text);
            viewHolder.moodTypeText = view.findViewById(R.id.friend_mood_type_text);
            viewHolder.socialSituationText = view.findViewById(R.id.friend_social_situation_text);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (FriendMoodAdapter.ViewHolder) view.getTag();
        }
        viewHolder.moodImage.setImageResource(moodEvent.getMood().getMoodImage());
        viewHolder.username.setText(moodEvent.getUsername());
        viewHolder.dateText.setText(moodEvent.getDate());
        viewHolder.timeText.setText(moodEvent.getTime());
        viewHolder.moodTypeText.setText(moodEvent.getMood().getMood());
        viewHolder.moodTypeText.setTextColor(Color.parseColor(moodEvent.getMood().getColor()));
        viewHolder.socialSituationText.setText(moodEvent.getSocialSituation());
        return view;
    }


    /**
     * This is a private class used to store views for reusing.
     */
    class ViewHolder {
        ImageView moodImage;
        TextView dateText;
        TextView timeText;
        TextView moodTypeText;
        TextView socialSituationText;
        TextView username;
    }
}
