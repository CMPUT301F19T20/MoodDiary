package com.example.mooddiary;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This is an adapter for displaying each mood in the spinner.
 */
public abstract class MsAdapter<T> extends BaseAdapter {

    private ArrayList<T> mData;
    private int mLayoutRes;


    public MsAdapter() {
    }

    public MsAdapter(ArrayList<T> mData, int mLayoutRes) {
        this.mData = mData;
        this.mLayoutRes = mLayoutRes;
    }

    /**
     * This returns the size of the data set
     * @return
     *      Return the size of the data set
     */
    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    /**
     * This returns the data at the specified position in the data set
     * @param position
     *      This is the position of the item within the adapter's data set
     * @return
     *      Return the data at the specified position in the data set
     */
    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    /**
     *  This returns the id of the data at the specified position
     * @param position
     *      This is the position of the item within the adapter's data set
     * @return
     *      Return the id of the data at the specified position
     */
    @Override
    public long getItemId(int position) {
        return position;
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
     *      Return a View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.bind(parent.getContext(), convertView, parent, mLayoutRes
                , position);
        bindView(holder, getItem(position));
        return holder.getItemView();
    }

    public abstract void bindView(ViewHolder holder, T obj);

    /**
     * This is a private class used to store views for reusing.
     */
    public static class ViewHolder {

        private SparseArray<View> mViews;   //store the view of each item in listview
        private View item;                  //store convertView
        private int position;
        private Context context;


        private ViewHolder(Context context, ViewGroup parent, int layoutRes) {
            mViews = new SparseArray<>();
            this.context = context;
            View convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
            convertView.setTag(this);
            item = convertView;
        }

        /**
         * This binds ViewHolder to the adapter
         * @param context
         *
         * @param convertView
         *      This is the old view to reuse, if possible.
         * @param parent
         *      This is the the parent that this view will eventually be attached to.
         * @param layoutRes
         *      This is id of the layout of each item in the listView
         * @param position
         *      This is the position of the item within the adapter's data set of the item whose view we want.
         * @return
         *      Return the ViewHolder
         */
        public static ViewHolder bind(Context context, View convertView, ViewGroup parent,
                                      int layoutRes, int position) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder(context, parent, layoutRes);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.item = convertView;
            }
            holder.position = position;
            return holder;
        }

        /**
         * This gets a View that displays the data at the specified position in the data set.
         * @param id
         *      This is the position of the item within the adapter's data set of the item whose view we want.
         * @return
         *      Return a View corresponding to the data at the specified position.
         */
        public <T extends View> T getView(int id) {
            T t = (T) mViews.get(id);
            if (t == null) {
                t = (T) item.findViewById(id);
                mViews.put(id, t);
            }
            return t;
        }

        /**
         * This returns a View corresponding to the data at the specified position.
         * @return
         *      Return a View corresponding to the data at the specified position.
         */
        public View getItemView() {
            return item;
        }

        /**
         * This returns the position of the item within the adapter's data set of the item whose view we want.
         * @return
         *      Return the position of the item within the adapter's data set of the item whose view we want.
         */
        public int getItemPosition() {
            return position;
        }

        /**
         * This sets Text for TextView
         * @param id
         *      This is the position of the item within the adapter's data set of the item whose view we want.
         * @param text
         *      This is the text to be set
         * @return
         *      This returns the ViewHolder
         */
        public ViewHolder setText(int id, CharSequence text) {
            View view = getView(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }

        /**
         * This sets image resource for ImageView
         * @param id
         *      This is the position of the item within the adapter's data set of the item whose view we want.
         * @param drawableRes
         *      This is the image to be set
         * @return
         *      This returns the ViewHolder
         */
        public ViewHolder setImageResource(int id, int drawableRes) {
            View view = getView(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            } else {
                view.setBackgroundResource(drawableRes);
            }
            return this;
        }


    }

}

