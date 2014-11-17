package com.dudkovlad.Calc3d;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by vlad on 31.10.2014.
 */
public class Triangle {


    private FloatBuffer vertexBuffer;
    private FloatBuffer normalBuffer;
    private FloatBuffer colorBuffer;
    private Shader mShader;
    private float [] verticesDATA;
    private short [] verticesINDEX;



    static float [] normalArray = {

            0f, 0f, 1f,
            0f, 0f, 1f,
            0f, 0f, 1f,

            0f, 0f, 1f,
            0f, 0f, 1f,
            0f, 0f, 1f,

            0f, 1f, 0f,
            0f, 1f, 0f,
            0f, 1f, 0f,

            0f, 1f, 0f,
            0f, 1f, 0f,
            0f, 1f, 0f,

            0f, 0f, -1f,
            0f, 0f, -1f,
            0f, 0f, -1f,

            0f, 0f, -1f,
            0f, 0f, -1f,
            0f, 0f, -1f,

            0f, -1f, 0f,
            0f, -1f, 0f,
            0f, -1f, 0f,

            0f, -1f, 0f,
            0f, -1f, 0f,
            0f, -1f, 0f,

            1f, 0f, 0f,
            1f, 0f, 0f,
            1f, 0f, 0f,

            1f, 0f, 0f,
            1f, 0f, 0f,
            1f, 0f, 0f,

            -1f, 0f, 0f,
            -1f, 0f, 0f,
            -1f, 0f, 0f,

            -1f, 0f, 0f,
            -1f, 0f, 0f,
            -1f, 0f, 0f

    };


float colorArray [] = {
            0f, 0f, 1f, 1,
            1f, 0f, 1f, 1,
            0f, 1f, 1f, 1,
            0f, 1f, 1f, 1,
            1f, 0f, 1f, 1,
            1f, 1f, 1f, 1,
            0f, 1f, 1f, 1,
            1f, 1f, 1f, 1,
            0f, 1f, 0f, 1,
            0f, 1f, 0f, 1,
            1f, 1f, 1f, 1,
            1f, 1f, 0f, 1,
            0f, 1f, 0f, 1,
            1f, 1f, 0f, 1,
            0f, 0f, 0f, 1,
            0f, 0f, 0f, 1,
            1f, 1f, 0f, 1,
            1f, 0f, 0f, 1,
            0f, 0f, 0f, 1,
            1f, 0f, 0f, 1,
            0f, 0f, 1f, 1,
            0f, 0f, 1f, 1,
            1f, 0f, 0f, 1,
            1f, 0f, 1f, 1,
            1f, 0f, 1f, 1,
            1f, 0f, 0f, 1,
            1f, 1f, 1f, 1,
            1f, 1f, 1f, 1,
            1f, 0f, 0f, 1,
            1f, 1f, 0f, 1,
            0f, 0f, 1f, 1,
            0f, 1f, 1f, 1,
            0f, 0f, 0f, 1,
            0f, 0f, 0f, 1,
            0f, 1f, 1f, 1,
            0f, 1f, 0f, 1
    };
/*
    float colorArray [] = {
            0f, 0f, 1f, 1,
            0f, 0f, 1f, 1,
            0f, 0f, 1f, 1,
            0f, 0f, 1f, 1,
            0f, 0f, 1f, 1,
            0f, 0f, 1f, 1,
            0f, 1f, 0f, 1,
            0f, 1f, 0f, 1,
            0f, 1f, 0f, 1,
            0f, 1f, 0f, 1,
            0f, 1f, 0f, 1,
            0f, 1f, 0f, 1,
            1f, 0f, 0f, 1,
            1f, 0f, 0f, 1,
            1f, 0f, 0f, 1,
            1f, 0f, 0f, 1,
            1f, 0f, 0f, 1,
            1f, 0f, 0f, 1,
            1f, 1f, 0f, 1,
            1f, 1f, 0f, 1,
            1f, 1f, 0f, 1,
            1f, 1f, 0f, 1,
            1f, 1f, 0f, 1,
            1f, 1f, 0f, 1,
            1f, 0f, 1f, 1,
            1f, 0f, 1f, 1,
            1f, 0f, 1f, 1,
            1f, 0f, 1f, 1,
            1f, 0f, 1f, 1,
            1f, 0f, 1f, 1,
            0f, 1f, 1f, 1,
            0f, 1f, 1f, 1,
            0f, 1f, 1f, 1,
            0f, 1f, 1f, 1,
            0f, 1f, 1f, 1,
            0f, 1f, 1f, 1
    };


    static float [] triangleCoords = {   // in counterclockwise order:
            -1f, -1f, 1f,
            1f, -1f, 1f,
            -1f, 1f, 1f,
            1f, 1f, 1f,
            -1f, 1f, -1f,
            1f, 1f, -1f,
            -1f, -1f, -1f,
            1f, -1f, -1f,
            -1f, -1f, 1f,
            1f, -1f, 1f,
            1f, -1f, 1f,
            1f, -1f, -1f,
            1f, 1f, 1f,
            1f, 1f, -1f,
            -1f, -1f, 1f,
            -1f, 1f, 1f,
            -1f, -1f, -1f,
            -1f, 1f, -1f
    };
*/


    static float [] triangleCoords = {   // in counterclockwise order:
            -1f, -1f, 1f,
            1f, -1f, 1f,
            -1f, 1f, 1f,

            -1f, 1f, 1f,
            1f, -1f, 1f,
            1f, 1f, 1f,

            -1f, 1f, 1f,
            1f, 1f, 1f,
            -1f, 1f, -1f,

            -1f, 1f, -1f,
            1f, 1f, 1f,
            1f, 1f, -1f,

            -1f, 1f, -1f,
            1f, 1f, -1f,
            -1f, -1f, -1f,

            -1f, -1f, -1f,
            1f, 1f, -1f,
            1f, -1f, -1f,

            -1f, -1f, -1f,
            1f, -1f, -1f,
            -1f, -1f, 1f,

            -1f, -1f, 1f,
            1f, -1f, -1f,
            1f, -1f, 1f,

            1f, -1f, 1f,
            1f, -1f, -1f,
            1f, 1f, 1f,

            1f, 1f, 1f,
            1f, -1f, -1f,
            1f, 1f, -1f,

            -1f, -1f, 1f,
            -1f, 1f, 1f,
            -1f, -1f, -1f,

            -1f, -1f, -1f,
            -1f, 1f, 1f,
            -1f, 1f, -1f
    };

    private final String vertexShaderCode =
            "uniform mat4 u_ViewProjectionMatrix;"+
            "attribute vec3 a_vertex;"+
            "attribute vec3 a_normal;"+
            "attribute vec4 a_color;"+
            "varying vec3 v_vertex;"+
            "varying vec3 v_normal;"+
            "varying vec4 v_color;"+
            "void main() {"+
            "    v_vertex=a_vertex;"+
            "    vec3 n_normal=normalize(a_normal);"+
            "    v_normal=n_normal;"+
            "    v_color=a_color;"+
            "    gl_Position = u_ViewProjectionMatrix * vec4(a_vertex,1.0);"+
            "}";

    private final String fragmentShaderCode =
            "precision mediump float;"+
            "uniform vec3 u_camera;"+
            "uniform vec3 u_lightPosition;"+
            "varying vec3 v_vertex;"+
            "varying vec3 v_normal;"+
            "varying vec4 v_color;"+
            "void main() {"+
                "vec3 n_normal=normalize(v_normal);"+
                "vec3 lightvector = normalize(u_lightPosition - v_vertex);"+
                "vec3 lookvector = normalize(u_camera - v_vertex);"+
                "float ambient=0.2;"+
                "float k_diffuse=0.8;"+
                "float k_specular=0.4;"+
                "float diffuse = k_diffuse * max(dot(n_normal, lightvector), 0.0);"+
                "vec3 reflectvector = reflect(-lightvector, n_normal);"+
                "float specular = k_specular * pow( max(dot(lookvector,reflectvector),0.0), 10.0 );"+
                "gl_FragColor = (ambient+diffuse+specular)*v_color;"+//
            "}";


    public Triangle() {





        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.position(0);
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        ByteBuffer bnormal = ByteBuffer.allocateDirect(normalArray.length*4);
        bnormal.order(ByteOrder.nativeOrder());
        normalBuffer = bnormal.asFloatBuffer();
        normalBuffer.position(0);
        normalBuffer.put(normalArray);
        normalBuffer.position(0);

        ByteBuffer bcolor = ByteBuffer.allocateDirect(colorArray.length*4);
        bcolor.order(ByteOrder.nativeOrder());
        colorBuffer = bcolor.asFloatBuffer();
        colorBuffer.position(0);
        colorBuffer.put(colorArray);
        colorBuffer.position(0);


        verticesDATA = new float[triangleCoords.length/3*10];

        verticesINDEX = new short[triangleCoords.length/3];

        CreateCub(verticesDATA,verticesINDEX,new float[]{0,0,0},1);


        final FloatBuffer heightMapVertexDataBuffer = ByteBuffer
                .allocateDirect(verticesDATA.length * 4).order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        heightMapVertexDataBuffer.put(verticesDATA).position(0);

        final ShortBuffer heightMapIndexDataBuffer = ByteBuffer
                .allocateDirect(verticesINDEX.length * 2).order(ByteOrder.nativeOrder())
                .asShortBuffer();
        heightMapIndexDataBuffer.put(verticesINDEX).position(0);

        mShader=new Shader(vertexShaderCode, fragmentShaderCode);
        //свяжем буфер вершин с атрибутом a_vertex в вершинном шейдере
        mShader.linkVertexBuffer(vertexBuffer);
        //свяжем буфер нормалей с атрибутом a_normal в вершинном шейдере
        mShader.linkNormalBuffer(normalBuffer);
        //свяжем буфер цветов с атрибутом a_color в вершинном шейдере
        mShader.linkColorBuffer(colorBuffer);
    }

    public void draw(float[] vpMatrix, float[] cam, float[] lightsource) {
        mShader.linkArrayAsUniform(vpMatrix,"u_ViewProjectionMatrix",(byte)16);
        //передаем в шейдерный объект координаты камеры
        mShader.linkArrayAsUniform(cam, "u_camera", 3);
        //передаем в шейдерный объект координаты источника света
        mShader.linkArrayAsUniform(cam, "u_lightPosition", 3);
        //делаем шейдерную программу активной
        mShader.useProgram();
        //рисуем квадрат
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);

    }

    private void CreateCub(float [] vertices, short [] index_array,float[] coords_center, float size)
    {

        for (int i = 0, j = 0; i < triangleCoords.length/3;i++)
        {
            vertices[j++] = triangleCoords [i*3]*0.5f*size + coords_center[0];
            vertices[j++] = triangleCoords [i*3+1]*0.5f*size + coords_center[1];
            vertices[j++] = triangleCoords [i*3+2]*0.5f*size + coords_center[2];


            vertices[j++] = normalArray [i*3];
            vertices[j++] = normalArray [i*3+1];
            vertices[j++] = normalArray [i*3+2];

            vertices[j++] = colorArray [i*4];
            vertices[j++] = colorArray [i*4+1];
            vertices[j++] = colorArray [i*4+2];
            vertices[j++] = colorArray [i*4+3];

            index_array [i] = (short)i;
        }





    }


}