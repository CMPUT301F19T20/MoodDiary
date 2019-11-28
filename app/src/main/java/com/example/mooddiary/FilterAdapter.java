package com.example.mooddiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an adapter for displaying choices of mood in the filter.
 */
public class FilterAdapter extends ArrayAdapter<MoodBean> {

    private int resourceId;
    private ArrayList<MoodBean> dataList;
    private Boolean[] selectedItems = {false, false, false, false, false, false};

    public FilterAdapter(Context context, int textViewResourceId, List<MoodBean> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        dataList = (ArrayList<MoodBean>) objects;
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
            viewHolder.iconImage = view.findViewById(R.id.home_filter_icon);
            viewHolder.moodText = view.findViewById(R.id.home_filter_mood);
            viewHolder.selectMoodCheckBox = view.findViewById(R.id.home_filter_check);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (FilterAdapter.ViewHolder) view.getTag();
        }
        viewHolder.iconImage.setImageResource(moodBean.getIcon());
        viewHolder.moodText.setText(moodBean.getName());

        if (selectedItems[position]) {
            viewHolder.selectMoodCheckBox.setImageResource(R.drawable.redheart);
        } else {
            viewHolder.selectMoodCheckBox.setImageResource(R.drawable.ic_remove);
        }

        return view;
    }

    public ArrayList<String> getSelectedItems() {
        ArrayList<String> selectedType = new ArrayList<>();
        for (int i = 0; i < selectedItems.length; i++) {
            if (selectedItems[i]) {
                selectedType.add(dataList.get(i).getName());
            }
        }
        return selectedType;
    }

    public void setSelectedItems(ArrayList<String> selectedType) {
        for (MoodBean moodBean: dataList) {
            if (selectedType.contains(moodBean.getName())) {
                selectedItems[moodBean.getIndex()] = true;
            } else {
                selectedItems[moodBean.getIndex()] = false;
            }
        }
    }

    /**
     * This is a private class used to store views for reusing.
     */
    class ViewHolder {

        ImageView iconImage;

        TextView moodText;

        ImageView selectMoodCheckBox;

    }


}