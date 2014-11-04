package com.dudkovlad.Calc3d;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by vlad on 31.10.2014.
 */
public class Triangle {


    private FloatBuffer vertexBuffer;
    private FloatBuffer normalBuffer;
    private FloatBuffer colorBuffer;
    private Shader mShader;


    static float [] normalArray = {   // in counterclockwise order:
            0.0f, 1.0f, 0.0f, // top
            0.0f, 1.0f, 0.0f, // bottom left
            0.0f, 1.0f, 0.0f  // bottom right
    };

    float colorArray [] = {
            1, 0, 0, 1,
            0, 1, 0, 1,
            0, 0, 1, 1,
    };


    static float [] triangleCoords = {   // in counterclockwise order:
            -2f,  0f, -2f, // top
            -2f, 0f, 2f, // bottom left
            2f, 0f, 0f  // bottom right
    };

    private final String vertexShaderCode =
            "uniform mat4 u_modelViewProjectionMatrix;"+
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
            "    gl_Position = u_modelViewProjectionMatrix * vec4(a_vertex,1.0);"+
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
                "float specular = k_specular * pow( max(dot(lookvector,reflectvector),0.0), 40.0 );"+
                "gl_FragColor = (ambient+diffuse+specular)*v_color;"+
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

        mShader=new Shader(vertexShaderCode, fragmentShaderCode);
        //свяжем буфер вершин с атрибутом a_vertex в вершинном шейдере
        mShader.linkVertexBuffer(vertexBuffer);
        //свяжем буфер нормалей с атрибутом a_normal в вершинном шейдере
        mShader.linkNormalBuffer(normalBuffer);
        //свяжем буфер цветов с атрибутом a_color в вершинном шейдере
        mShader.linkColorBuffer(colorBuffer);
    }

    public void draw(float[] mvpMatrix) {
        mShader.linkModelViewProjectionMatrix(mvpMatrix);
        //передаем в шейдерный объект координаты камеры
        mShader.linkCamera(0.6f, 5.4f, 3f);
        //передаем в шейдерный объект координаты источника света
        mShader.linkLightSource(0, 0.6f, 0);
        //делаем шейдерную программу активной
        mShader.useProgram();
        //рисуем квадрат
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);



    }
}