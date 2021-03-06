package com.dudkovlad.CalcTDP;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by vlad on 20.02.14.
 */
public class PagesFragment extends Fragment {


    static final String ARGUMENT_PAGE_NUMBER = "section_number";



    Context context;
    ButtonsAdapter mButtonsAdapter;




    static PagesFragment newInstance(int page, Context _context) {
        PagesFragment pageFragment = new PagesFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        pageFragment.context = _context;



        return pageFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.grid_adapter, container, false);


        mButtonsAdapter = new ButtonsAdapter(context, getArguments().getInt(ARGUMENT_PAGE_NUMBER));

        ((GridView) view.findViewById(R.id.gridView)).setAdapter(mButtonsAdapter);

        return view;
    }

}