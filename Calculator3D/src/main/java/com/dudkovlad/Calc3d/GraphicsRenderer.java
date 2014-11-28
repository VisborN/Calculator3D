package com.dudkovlad.Calc3d;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by vlad on 31.10.2014.
 */
public class GraphicsRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";

    MainActivity mainactivity;
    Triangle mTriangle;
    float [] mProjectionMatrix = new float [16];
    float [] mViewMatrix = new float [16];
    float [] mVPMatrix = new float [16];
    float [] mMMatrix = new float [16];
    float [] mMMatrix1 = new float [16];
    float [] mMMatrix2 = new float [16];

    float [] mLightVertex = new float [4];
    float [] camLookVertex = new float []{0,0,-1,1};
    float [] camLoVerRotated = new float []{-1,-1,-1,1};
    float [] camPosVertex = new float []{6,6,6,1};
    float [] camAccelVertex = new float[]{0,0,0,1};
    float [] camSpeedVertex = new float[]{0,0,0,1};
    public volatile float camAnglex = 45;
    public volatile float camAngley = -35.2643897f;
    float camLookSensK = 180.0f / 360 * 0.22f; //0.17
    float[] move = new float []{0,0,0,1};
    float timep = SystemClock.uptimeMillis()/1000;
//    float m = 1;
//    float k = 1;

    GraphicsRenderer(MainActivity mainactivity)
    {
        this.mainactivity = mainactivity;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glClearColor(0.2f, 0.2f, 0.2f, 0.2f);

        mainactivity.threadFC.setGraphic(-5,5,-5,5,-5,5,1f,false,0);

        mTriangle = new Triangle();



    }

    @Override
    public void onDrawFrame(GL10 unused) {
        // Redraw background color

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        float time = SystemClock.uptimeMillis()/1000f- timep;



/*

        if (mainactivity.threadFC.isGraphic_updated()&&!mainactivity.threadFC.isBusygraphic()) {
            mainactivity.threadFC.setBusygraphic(true);
            mTriangle.UpdateData(mainactivity.threadFC.Vertexarr,
                                mainactivity.threadFC.Normalarr,
                                mainactivity.threadFC.Colorarr,
                                mainactivity.threadFC.Indexarr);
            mainactivity.threadFC.setBusygraphic(false);
            mainactivity.threadFC.setGraphic_updated(false);
        }

*/




        camPosVertex[0] += /*camSpeedVertex[0]*time + time**/time*camAccelVertex[0]/2;
        camPosVertex[1] += /*camSpeedVertex[1]*time + time**/time*camAccelVertex[1]/2;
        camPosVertex[2] += /*camSpeedVertex[2]*time + time**/time*camAccelVertex[2]/2;




/*
        camSpeedVertex[0] += camAccelVertex[0]*time;
        camSpeedVertex[1] += camAccelVertex[1]*time;
        camSpeedVertex[2] += camAccelVertex[2]*time;

        camAccelVertex[0] += -camSpeedVertex[0]*time*time*0.5f;
        camAccelVertex[1] += -camSpeedVertex[1]*time*time*0.5f;
        camAccelVertex[2] += -camSpeedVertex[2]*time*time*0.5f;*/



        timep += time;


        Matrix.setLookAtM(  mViewMatrix, 0, camPosVertex[0], camPosVertex[1], camPosVertex[2],
                            camPosVertex[0] + camLoVerRotated[0],
                            camPosVertex[1] + camLoVerRotated[1],
                            camPosVertex[2] + camLoVerRotated[2],
                            0f, 1.0f, 0.0f);

        Matrix.multiplyMM(mVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //Matrix.multiplyMV(mLightVertex, 0,mVPMatrix, 0, new float [] {0f, 0f, 0f, 1}, 0);


        mTriangle.draw(mVPMatrix,  camPosVertex, new float [] {6f, 6f, 6f, 1});
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float)height / width;



        Matrix.frustumM(mProjectionMatrix, 0, -0.036f, 0.036f, -0.036f*ratio, 0.036f*ratio, 0.1f, 1000);

        timep = SystemClock.uptimeMillis()/1000;
    }


    public void addAngleAndAddMoveAcceleration(float dx1, float dy1, float dx2, float dy2 )
    {
        if (dx2>1||dx2<-1)
            camAnglex += -dx2*camLookSensK;
        if (dy2>1||dy2<-1)
            camAngley += -dy2*camLookSensK;

        if(camAngley < -89.999/*- Math.PI / 2*/)
            camAngley = - 89.999f;
        if(camAngley > 89.999f)
            camAngley = 89.999f;

        Matrix.setRotateM(mMMatrix1, 0, camAnglex, 0, 1, 0);
        Matrix.setRotateM(mMMatrix2, 0, camAngley, 1, 0, 0);
        Matrix.multiplyMM(mMMatrix, 0, mMMatrix1, 0, mMMatrix2, 0);

        Matrix.multiplyMV(camLoVerRotated, 0, mMMatrix,0, camLookVertex, 0);

        camAccelVertex[0] = camLoVerRotated[0]*(-dy1)*0.1f;
        camAccelVertex[1] = camLoVerRotated[1]*(-dy1)*0.1f;
        camAccelVertex[2] = camLoVerRotated[2]*(-dy1)*0.1f;



    }




    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

}