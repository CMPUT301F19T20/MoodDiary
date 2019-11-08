package com.example.mooddiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * This is an adapter for displaying choices of mood in the filter.
 */
public class FilterAdapter extends ArrayAdapter<MoodBean> {

    private int resourceId;
    private int selectedItem;

    public FilterAdapter(Context context, int textViewResourceId, List<MoodBean> objects) {
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
        MoodBean moodBean = getItem(position);
        View view;
        FilterAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view =
                    LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new FilterAdapter.ViewHolder();
            viewHolder.iconImage = (ImageView) view.findViewById(R.id.home_filter_icon);
            viewHolder.moodText = (TextView) view.findViewById(R.id.home_filter_mood);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (FilterAdapter.ViewHolder) view.getTag();
        }
        viewHolder.iconImage.setImageResource(moodBean.getIcon());
        viewHolder.moodText.setText(moodBean.getName());
        if ( position == selectedItem)
        {
            view.setBackgroundResource(R.color.selectedColor);

        } else {
            view.setBackgroundResource(R.color.unselectedColor);
        }
        return view;
    }

    public void setSelectedItem(int i) {
        this.selectedItem = i;
    }

    /**
     * This is a private class used to store views for reusing.
     */
    class ViewHolder {

        ImageView iconImage;

        TextView moodText;

    }


}