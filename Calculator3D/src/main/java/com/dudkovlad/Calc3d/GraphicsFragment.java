package com.dudkovlad.Calc3d;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vlad on 31.10.2014.
 */
public class GraphicsFragment extends Fragment {

    Context context;

    private GLSurfaceView graphicsView;

    static GraphicsFragment newInstance(Context _context) {
        GraphicsFragment graphicsfragment = new GraphicsFragment();

        graphicsfragment.context = _context;



        return graphicsfragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        graphicsView = new MyGLSurfaceView(context);


        return graphicsView;
    }

}
