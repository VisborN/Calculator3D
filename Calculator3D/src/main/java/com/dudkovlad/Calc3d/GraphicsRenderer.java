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

    Triangle mTriangle;
    float [] mProjectionMatrix = new float [16];
    float [] mViewMatrix = new float [16];
    float [] mVPMatrix = new float [16];
    float [] mViewLookVertex = new float []{0,0,-1,1};
    float [] mVLVRotated = new float []{-1,-1,-1,1};
    float [] mViewPosVertex = new float []{6,6,6,1};
    float [] mMMatrix = new float [16];
    float [] mMMatrix1 = new float [16];
    float [] mMMatrix2 = new float [16];
    public volatile float mAnglex = 45;
    public volatile float mAngley = -30;
    float ViewLookSensetivityK = 180.0f / 360 * 0.34f; //0.17
    float[] move = new float []{0,0,0,1};
    long timep = SystemClock.uptimeMillis();


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glClearColor(0.2f, 0.2f, 0.2f, 0.2f);

        mTriangle = new Triangle();


    }

    @Override
    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        float time = SystemClock.uptimeMillis();



        Matrix.setLookAtM(  mViewMatrix, 0, mViewPosVertex[0], mViewPosVertex[1], mViewPosVertex[2],
                            mViewPosVertex[0] + mVLVRotated[0],
                            mViewPosVertex[1] + mVLVRotated[1],
                            mViewPosVertex[2] + mVLVRotated[2],
                            0f, 1.0f, 0.0f);

        Matrix.multiplyMM(mVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);



        mTriangle.draw(mVPMatrix,  mViewPosVertex, new float [] {3f, 3f, 3f});
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;


        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, 0.055f*(-ratio), 0.055f*ratio, -0.055f, 0.055f, 0.1f, 1000);

    }


    public void addAngleAndAddMoveAcceleration(float dx1, float dy1, float dx2, float dy2 )
    {
        mAnglex += -dx2*ViewLookSensetivityK;
        mAngley += -dy2*ViewLookSensetivityK;

        if(mAngley < -89.999/*- Math.PI / 2*/)
            mAngley = - 89.999f;
        if(mAngley > 89.999f)
            mAngley = 89.999f;

        Matrix.setRotateM(mMMatrix1, 0, mAnglex, 0, 1, 0);
        Matrix.setRotateM(mMMatrix2, 0, mAngley, 1, 0, 0);
        Matrix.multiplyMM(mMMatrix, 0, mMMatrix1, 0, mMMatrix2, 0);

        Matrix.multiplyMV(mVLVRotated, 0, mMMatrix,0, mViewLookVertex, 0);

        mViewPosVertex[0] = mViewPosVertex[0] + mVLVRotated[0]*(-dy1)*0.01f;
        mViewPosVertex[1] = mViewPosVertex[1] + mVLVRotated[1]*(-dy1)*0.01f;
        mViewPosVertex[2] = mViewPosVertex[2] + mVLVRotated[2]*(-dy1)*0.01f;


    }




    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

}