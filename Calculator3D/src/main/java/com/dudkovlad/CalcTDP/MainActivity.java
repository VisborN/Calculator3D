package com.dudkovlad.CalcTDP;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by vlad on 28.01.14.
 */
public class MainActivity extends ActionBarActivity
{
    Context context;
    FragmentManager fm;


    Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ThreadForCalculating.LOG_TEXT:
                    if (debugview != null)
                        debugview.setText(msg.obj.toString());
                    break;
                case ThreadForCalculating.SHOW_RESULT:



                    debugview.setText("");
                    if (mainFragment != null && debugview != null && mainFragment.result_view != null) {
                        String result = msg.obj.toString();
                        if (!result.isEmpty() && result.charAt(0) == '$') {
                            debugview.setText(result);
                            result = "";
                        }
                        mainFragment.result_view.setText(result);

                        Data.HistoryChange(new HistoryItem(mainFragment.equation_view.getText().toString(), result, 10, true));
                    }
                    ArrayList<HistoryItem> temp = (ArrayList<HistoryItem>)Data.history_items.clone();
                    histAdapter.clear();

                    histAdapter.addAll(temp);
                    break;

                default:
                    debugview.setText("unusual message " + msg.obj.toString());
            }
        }
    };
    public ThreadForCalculating threadFC = new ThreadForCalculating(mHandler);;


    TextView debugview;
    MyClickListener myClickListener;
    EnteringFragment mainFragment;
    GraphicsFragment graphicsFragment;
    DrawerLayout drawerlayout;
    ListView historylist;
    HistoryListAdapter histAdapter;
    private int where;

	@Override
	public void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activ_main_drawer);

        debugview = (TextView)findViewById(R.id.debugview);

        debugview.setTextSize(10);
        //debugview.setBackgroundColor(Data.colors[Data.color_theme][4]);
        debugview.setTextColor(0x99ffffff);
        Data.Load_Data (getSharedPreferences("main_prefs", 0), this);
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.setTheme();
        }*/
        myClickListener = new MyClickListener(this);
        fm = getSupportFragmentManager();
        context = this;




        CreateNavDrawer();


        mainFragment = EnteringFragment.newInstance(this);
        graphicsFragment = GraphicsFragment.newInstance(this);
        setMainFragment(mainFragment);
        where = 0;


        Toast.makeText(this, "Hello World!", Toast.LENGTH_SHORT).show();




	}

    void CreateNavDrawer ()
    {

        drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        historylist = (ListView)findViewById(R.id.left_drawer);


        histAdapter = new HistoryListAdapter(this, R.layout.history_item, Data.history_items);

        historylist.setAdapter(histAdapter);


    }

    void setMainFragment (Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).commit();

    }

    public void go_to_graphics_fragment()
    {
        if (graphicsFragment == null)
            graphicsFragment = GraphicsFragment.newInstance(this);
        setMainFragment(graphicsFragment);
        where = 1;
        mainFragment.onDestroy();
        mainFragment = null;
    }


    @Override
    public void onPause()
    {
        Data.Save();
        super.onPause();

    }


    @Override
    public void onBackPressed()
    {


        if (where == 1) {
            mainFragment = EnteringFragment.newInstance(this);
            setMainFragment(mainFragment);
            graphicsFragment = null;
            where = 0;
        }
        else
            if(where == 0)
                where = -1;
        else
            super.onBackPressed();



    }

    protected void onDestroy() {
        threadFC.interrupt();
        super.onDestroy();
    }




}