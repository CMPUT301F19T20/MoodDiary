package com.example.mooddiary.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.mooddiary.R;
import com.example.mooddiary.RequestAdapter;

/**
 * This is ShareFragment showing a list of user's friends
 */
public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;
    private ListView shareFollowListView;
    private ListView shareRequestListView;
    private ListView shareFollowerListView;
    private RequestAdapter requestAdapter;
    private ArrayAdapter<String> followAdapter;
    private ArrayAdapter<String> followerAdapter;

    /**
     * This creates the view for the user's friend list.
     * @param inflater
     *      This is a LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container
     *      This can be null. If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState
     *      If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return
     *      Return the view for the fragment UI
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);

        shareRequestListView = root.findViewById(R.id.share_new_request_list_view);
        shareFollowListView = root.findViewById(R.id.share_follow_list_view);
        shareFollowerListView = root.findViewById(R.id.share_follower_list_view);

        followAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, shareViewModel.getFollowList());
        followerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, shareViewModel.getFollowerList());
        requestAdapter = new RequestAdapter(getActivity(), R.layout.request_list_item, shareViewModel.getRequestList());

        shareFollowListView.setAdapter(followAdapter);
        shareFollowerListView.setAdapter(followerAdapter);
        shareRequestListView.setAdapter(requestAdapter);

        setDynamicHeight(shareRequestListView);
        setDynamicHeight(shareFollowListView);
        setDynamicHeight(shareFollowerListView);

        return root;
    }

    /**
     * This sets the dynamic height for a list view
     * @param mListView
     *      This is the list view to set
     */
    private void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
}
