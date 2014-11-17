package com.dudkovlad.Calc3d;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by vlad on 31.10.2014.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    GraphicsRenderer mRenderer;
    float[][] mPreviousCoord = new float[2][2];
    float[] CoordOnStart = new float [2];
    int mPreviousCount = 0;
    int [] mPreviousID = new int [] {-1,-1};


    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView

        mRenderer = new GraphicsRenderer();
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        requestRender();
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int count = e.getPointerCount();


        int [] ID = new int [count];
        int [] index = new int []{-1,-1};
        for(int i =0; i < count; i++) {
            ID[i] = e.getPointerId(i);
            if (ID[i] == mPreviousID[0]) {
                index[0] = i;
            } else if (ID[i] == mPreviousID[1]){
                index[1] = i;
            }
        }

        if (mPreviousCount<count){
            if(index[0]==index[1])
            {
                if (e.getX(0)<300) {
                    index[0] = 0;
                    ID[0] = e.getPointerId(0);
                    CoordOnStart [0] = e.getX(0);
                    CoordOnStart [1] = e.getY(0);
                }else {
                    index[1] = 0;
                    ID[0] = e.getPointerId(0);
                }
            }else
            if (index[0]==-1)
            {
                if (index [1]==0) {
                    index[0] = 1;
                    ID[1] = e.getPointerId(1);
                    CoordOnStart [0] = e.getX(1);
                    CoordOnStart [1] = e.getY(1);

                }else
                if (index [1]==1) {
                    index[0] = 0;
                    ID[0] = e.getPointerId(0);
                    CoordOnStart [0] = e.getX(0);
                    CoordOnStart [1] = e.getY(0);
                }
            }else
            if (index[1]==-1)
            {
                if (index [0]==0) {
                    index[1] = 1;
                    ID[1] = e.getPointerId(1);
                }else
                if (index [0]==1) {
                    index[1] = 0;
                    ID[0] = e.getPointerId(0);
                }
            }
        }


        float[][] coord = new float [2][2];

        if (mPreviousCount>count){
            if (index[0]==-1)
            {
                coord[0][0] = CoordOnStart[0];
                coord[0][1] = CoordOnStart[1];
            }else
            if (index[1]==-1)
            {
            }
        }


        for(int i =0; i < index.length; i++)
        {
            if(index[i]==-1) {
                mPreviousID[i] = -1;
                mPreviousCoord [i][0] = Float.NaN;
                mPreviousCoord [i][1] = Float.NaN;

            }else {
                coord[i][0] = e.getX(index[i]);
                coord[i][1] = e.getY(index[i]);

                if (mPreviousID[i]== -1) {
                    mPreviousCoord[i][0] = coord[i][0];
                    mPreviousCoord[i][1] = coord[i][1];
                }
            }
        }




        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:



                if(index [0]!= -1&&index [1]!= -1)
                    mRenderer.addAngleAndAddMoveAcceleration(
                            coord[0][0] - CoordOnStart[0],
                            coord[0][1] - CoordOnStart[1],
                            coord[1][0] - mPreviousCoord[1][0],
                            coord[1][1] - mPreviousCoord[1][1]);
                else if (index [0] == -1&& index [1]!=-1)
                    mRenderer.addAngleAndAddMoveAcceleration(
                            0,
                            0,
                            coord[1][0] - mPreviousCoord[1][0],
                            coord[1][1] - mPreviousCoord[1][1]);
                else if (index [0] != -1&& index [1] ==-1)
                    mRenderer.addAngleAndAddMoveAcceleration(
                            coord[0][0] - CoordOnStart[0],
                            coord[0][1] - CoordOnStart[1],
                            0,
                            0);

                requestRender();
                //throw new IllegalArgumentException("" + (coord[1][0] - mPreviousCoord[1][0]) + "  " + (coord[1][1] - mPreviousCoord[1][1]) );

        }

        for(int i =0; i < 2; i++)
        {
            if (index[i]!= -1) {
                mPreviousID[i] = e.getPointerId(index[i]);
                mPreviousCoord[i][0] = coord[i][0];
                mPreviousCoord[i][1] = coord[i][1];
            }
            else {
                mPreviousID[i] = -1;
                mPreviousCoord[i][0] = 0;
                mPreviousCoord[i][1] = 0;
            }
        }
        mPreviousCount = count;
        return true;
    }
}