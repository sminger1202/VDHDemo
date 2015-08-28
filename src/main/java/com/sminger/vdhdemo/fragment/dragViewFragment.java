package com.sminger.vdhdemo.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sminger.vdhdemo.MainActivity;
import com.sminger.vdhdemo.R;

/**
 * Created by sminger on 2015/8/28.
 */
public class dragViewFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    public static dragViewFragment newInstance(int sectionNumber) {

        dragViewFragment fragment = new dragViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public dragViewFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle mBundle = getArguments();
        int mCate = mBundle.getInt(ARG_SECTION_NUMBER, 1);
        View rootView;
        switch (mCate) {
            case 1:
                rootView = inflater.inflate(R.layout.fragment_simple, container, false);
                break;
            case 2:
                rootView = inflater.inflate(R.layout.fragment_edge, container, false);
                break;
            default:
                rootView = inflater.inflate(R.layout.fragment_youtube, container, false);
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}

