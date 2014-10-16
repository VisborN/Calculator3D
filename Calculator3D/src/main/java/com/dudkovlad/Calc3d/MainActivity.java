package com.dudkovlad.Calc3d;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by vlad on 28.01.14.
 */
public class MainActivity extends Activity
{

    public static Context context;
    public static MyCalc data_del;


    EnteringFragment mainFragment;
    DrawerLayout drawerlayout;
    ListView historylist;
    public static FragmentManager fm;

	@Override
	public void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

        fm = getFragmentManager();
        context = this;


        mainFragment = new EnteringFragment();
        CreateNavDrawer();
        setMainLay(mainFragment);

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

    void setMainLay (Fragment fragmen)
    {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragmen).commit();
    }


    @Override
    public void onPause()
    {
        super.onPause();
        Data.Save();
    }







}