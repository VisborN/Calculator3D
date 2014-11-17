package com.dudkovlad.Calc3d;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Created by vlad on 28.01.14.
 */
public class MainActivity extends Activity
{

    public static Context context;
    public static MyCalc data_del;


    EnteringFragment mainFragment;
    GraphicsFragment graphicsFragment;
    DrawerLayout drawerlayout;
    ListView historylist;
    public static FragmentManager fm;
    private int where;

	@Override
	public void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.setTheme();
        }*/
        fm = getFragmentManager();
        context = this;


        mainFragment = EnteringFragment.newInstance(this);
        CreateNavDrawer();
        //graphicsFragment = GraphicsFragment.newInstance(this);
        setMainFragment(mainFragment);
        where = 0;

        setContentView(R.layout.main_activ_main_drawer);
        Toast.makeText(this, "Hello World!", Toast.LENGTH_SHORT).show();
	}

    public static void AddToEquation(String input)
    {
        data_del.AddToEquation(input);
    }

    void CreateNavDrawer ()
    {
        drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        historylist = (ListView) findViewById(R.id.left_drawer);//todo fill list view
    }

    void setMainFragment (Fragment fragmen)
    {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragmen).commit();

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




}