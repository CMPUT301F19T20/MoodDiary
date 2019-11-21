package com.example.mooddiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an adapter for displaying each friend in the request list.
 */
public class RequestAdapter extends ArrayAdapter<Request> {

    private int resourceId;
    private ArrayList<Request> requestList;

    public RequestAdapter(Context context, int textViewResourceId, List<Request> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        requestList = (ArrayList<Request>) objects;
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
        Request request = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view =
                    LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.friendUsernameText = (TextView) view.findViewById(R.id.share_friend_username_text);
            viewHolder.agreeButton = (Button) view.findViewById(R.id.share_agree_button);
            viewHolder.declineButton = (Button) view.findViewById(R.id.share_decline_button);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.friendUsernameText.setText(request.getSender());
        viewHolder.agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.setConfirmed(true);
                requestList.remove(request);
                Database.getRequest(request.getSender()+request.getReceiver()).set(request);
                notifyDataSetChanged();
            }
        });

        viewHolder.declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestList.remove(request);
                Database.getRequest(request.getSender()+request.getReceiver()).delete();
                notifyDataSetChanged();
            }
        });

        return view;
    }

    /**
     * This is a private class used to store views for reusing.
     */
    class ViewHolder {

        TextView friendUsernameText;

        Button agreeButton;

        Button declineButton;

    }


}
