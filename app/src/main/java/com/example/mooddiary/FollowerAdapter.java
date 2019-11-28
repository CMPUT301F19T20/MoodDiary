package com.example.mooddiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class FollowerAdapter extends ArrayAdapter<String> {
    private int resourceId;

    public FollowerAdapter(Context context, int textViewResourceId, List<String> objects) {
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
        String followUsername = getItem(position);
        View view;
        FollowerAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view =
                    LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new FollowerAdapter.ViewHolder();
            viewHolder.followerUsernameText = view.findViewById(R.id.share_follower_username_text);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (FollowerAdapter.ViewHolder) view.getTag();
        }

        viewHolder.followerUsernameText.setText(followUsername);
        return view;
    }

    /**
     * This is a private class used to store views for reusing.
     */
    class ViewHolder {

        TextView followerUsernameText;

    }

}

