package com.example.mooddiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class FollowAdapter extends ArrayAdapter<String> {
    private int resourceId;

    public FollowAdapter(Context context, int textViewResourceId, List<String> objects) {
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
        FollowAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view =
                    LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new FollowAdapter.ViewHolder();
            viewHolder.followUsernameText = view.findViewById(R.id.share_follow_username_text);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (FollowAdapter.ViewHolder) view.getTag();
        }

        viewHolder.followUsernameText.setText(followUsername);

        return view;
    }

    /**
     * This is a private class used to store views for reusing.
     */
    class ViewHolder {

        TextView followUsernameText;

    }

}

