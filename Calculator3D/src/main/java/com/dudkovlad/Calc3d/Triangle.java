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
    private ShortBuffer IndexDataBuffer;
    private Shader mShader;
    private float [] verticesCoord;
    private float [] verticesNormal;
    private float [] verticesColor;
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
            0.4f, 0.4f, 0.7f, 1,
            0.4f, 0.4f, 0.7f, 1,
            0.4f, 0.4f, 0.7f, 1,
            0.4f, 0.4f, 0.7f, 1,
            0.4f, 0.4f, 0.7f, 1,
            0.4f, 0.4f, 0.7f, 1,
                0.4f, 0.7f, 0.4f, 1,
                0.4f, 0.7f, 0.4f, 1,
                0.4f, 0.7f, 0.4f, 1,
                0.4f, 0.7f, 0.4f, 1,
                0.4f, 0.7f, 0.4f, 1,
                0.4f, 0.7f, 0.4f, 1,
            0.4f, 0.4f, 0.7f, 1,
            0.4f, 0.4f, 0.7f, 1,
            0.4f, 0.4f, 0.7f, 1,
            0.4f, 0.4f, 0.7f, 1,
            0.4f, 0.4f, 0.7f, 1,
            0.4f, 0.4f, 0.7f, 1,
                0.4f, 0.7f, 0.4f, 1,
                0.4f, 0.7f, 0.4f, 1,
                0.4f, 0.7f, 0.4f, 1,
                0.4f, 0.7f, 0.4f, 1,
                0.4f, 0.7f, 0.4f, 1,
                0.4f, 0.7f, 0.4f, 1,
            0.7f, 0.4f, 0.4f, 1,
            0.7f, 0.4f, 0.4f, 1,
            0.7f, 0.4f, 0.4f, 1,
            0.7f, 0.4f, 0.4f, 1,
            0.7f, 0.4f, 0.4f, 1,
            0.7f, 0.4f, 0.4f, 1,
                0.7f, 0.4f, 0.4f, 1,
                0.7f, 0.4f, 0.4f, 1,
                0.7f, 0.4f, 0.4f, 1,
                0.7f, 0.4f, 0.4f, 1,
                0.7f, 0.4f, 0.4f, 1,
                0.7f, 0.4f, 0.4f, 1
    };

/*float colorArray [] = {
            0f, 0f, 1f, 0.5f,
            1f, 0f, 1f, 0.5f,
            0f, 1f, 1f, 0.5f,
            0f, 1f, 1f, 0.5f,
            1f, 0f, 1f, 0.5f,
            1f, 1f, 1f, 0.5f,
            0f, 1f, 1f, 0.5f,
            1f, 1f, 1f, 0.5f,
            0f, 1f, 0f, 0.5f,
            0f, 1f, 0f, 0.5f,
            1f, 1f, 1f, 0.5f,
            1f, 1f, 0f, 0.5f,
            0f, 1f, 0f, 0.5f,
            1f, 1f, 0f, 0.5f,
            0f, 0f, 0f, 0.5f,
            0f, 0f, 0f, 0.5f,
            1f, 1f, 0f, 0.5f,
            1f, 0f, 0f, 0.5f,
            0f, 0f, 0f, 0.5f,
            1f, 0f, 0f, 0.5f,
            0f, 0f, 1f, 0.5f,
            0f, 0f, 1f, 0.5f,
            1f, 0f, 0f, 0.5f,
            1f, 0f, 1f, 0.5f,
            1f, 0f, 1f, 0.5f,
            1f, 0f, 0f, 0.5f,
            1f, 1f, 1f, 0.5f,
            1f, 1f, 1f, 0.5f,
            1f, 0f, 0f, 0.5f,
            1f, 1f, 0f, 0.5f,
            0f, 0f, 1f, 0.5f,
            0f, 1f, 1f, 0.5f,
            0f, 0f, 0f, 0.5f,
            0f, 0f, 0f, 0.5f,
            0f, 1f, 1f, 0.5f,
            0f, 1f, 0f, 0.5f
    };
    float colorArray [] = {
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1,
            0.7f, 0.7f, 0.7f, 1
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

        setMegaFigure();



        ByteBuffer bb = ByteBuffer.allocateDirect(verticesCoord.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.position(0);
        vertexBuffer.put(verticesCoord);
        vertexBuffer.position(0);

        ByteBuffer bnormal = ByteBuffer.allocateDirect(verticesNormal.length*4);
        bnormal.order(ByteOrder.nativeOrder());
        normalBuffer = bnormal.asFloatBuffer();
        normalBuffer.position(0);
        normalBuffer.put(verticesNormal);
        normalBuffer.position(0);


        ByteBuffer bcolor = ByteBuffer.allocateDirect(verticesColor.length*4);
        bcolor.order(ByteOrder.nativeOrder());
        colorBuffer = bcolor.asFloatBuffer();
        colorBuffer.position(0);
        colorBuffer.put(verticesColor);
        colorBuffer.position(0);




        IndexDataBuffer = ByteBuffer
                .allocateDirect(verticesINDEX.length * 2).order(ByteOrder.nativeOrder())
                .asShortBuffer();
        IndexDataBuffer.put(verticesINDEX).position(0);


        mShader=new Shader(vertexShaderCode, fragmentShaderCode);

        mShader.linkVertexBuffer(vertexBuffer);
        mShader.linkNormalBuffer(normalBuffer);
        mShader.linkColorBuffer(colorBuffer);
    }

    public void UpdateData( float [] vertices, float [] normals, float [] colors, short [] indecies)
    {

        if (indecies.length < 2)
            setMegaFigure();
        else {

            verticesCoord = vertices;
            verticesNormal = normals;
            verticesColor = colors;
            verticesINDEX = indecies;
        }

        vertexBuffer.put(verticesCoord).position(0);


        normalBuffer.put(verticesNormal).position(0);


        colorBuffer.put(verticesColor).position(0);


        IndexDataBuffer.put(verticesINDEX).position(0);

    }

    public void draw(float[] vpMatrix, float[] cam, float[] lightsource) {
        mShader.linkArrayAsUniform(vpMatrix,"u_ViewProjectionMatrix",(byte)16);

        mShader.linkArrayAsUniform(cam, "u_camera", 3);

        mShader.linkArrayAsUniform(lightsource, "u_lightPosition", 3);

        mShader.useProgram();


        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, verticesINDEX.length,
                GLES20.GL_UNSIGNED_SHORT, IndexDataBuffer);

    }

    private void setMegaFigure()
    {

        int numofcubs = 18*9 + 1;
        float spacing = 0.01f;
        float z = 4;


        verticesCoord = new float[triangleCoords.length*numofcubs];
        verticesNormal = new float[triangleCoords.length*numofcubs];
        verticesColor = new float[triangleCoords.length/3*4*numofcubs];

        verticesINDEX = new short[triangleCoords.length/3*numofcubs];

        AddHiperCub(verticesCoord,verticesNormal,verticesColor,verticesINDEX,new float[]{0,             0+z,            0}, 0);
        AddHiperCub(verticesCoord,verticesNormal,verticesColor,verticesINDEX,new float[]{3+spacing,     -3-spacing+z,   0}, 648);
        AddHiperCub(verticesCoord,verticesNormal,verticesColor,verticesINDEX,new float[]{0,             -3-spacing+z,   0}, 648*2);
        AddHiperCub(verticesCoord,verticesNormal,verticesColor,verticesINDEX,new float[]{-3-spacing,    -3 - spacing+z, 0}, 648*3);
        AddHiperCub(verticesCoord,verticesNormal,verticesColor,verticesINDEX,new float[]{3+spacing,     -6-spacing*2+z, 0}, 648*4);
        AddHiperCub(verticesCoord,verticesNormal,verticesColor,verticesINDEX,new float[]{0,             -6-spacing*2+z, 0}, 648*5);
        AddHiperCub(verticesCoord,verticesNormal,verticesColor,verticesINDEX,new float[]{-3-spacing,    -6-spacing*2+z, 0}, 648*6);
        AddHiperCub(verticesCoord,verticesNormal,verticesColor,verticesINDEX,new float[]{6+spacing*2,   -6-spacing*2+z, 0}, 648*7);
        AddHiperCub(verticesCoord,verticesNormal,verticesColor,verticesINDEX,new float[]{-6-spacing*2,  -6-spacing*2+z, 0}, 648*8);

        AddCub(verticesCoord,verticesNormal,verticesColor,verticesINDEX,new float[]{6,6,6},0.05f, 648*9);

    }

    private void AddCub(float [] vertices, float [] normal, float [] color, short [] index_array,float[] coords_center, float size, int index)
    {

        for (int i = index; i - index  < triangleCoords.length/3;i++)
            {
            vertices[i*3] = triangleCoords [(i-index)*3]*0.5f*size + coords_center[0];
            vertices[i*3+1] = triangleCoords [(i-index)*3+1]*0.5f*size + coords_center[1];
            vertices[i*3+2] = triangleCoords [(i-index)*3+2]*0.5f*size + coords_center[2];


            normal[i*3] = normalArray [(i-index)*3];
            normal[i*3+1] = normalArray [(i-index)*3+1];
            normal[i*3+2] = normalArray [(i-index)*3+2];

            color[i*4] = colorArray [(i-index)*4];
            color[i*4+1] = colorArray [(i-index)*4+1];
            color[i*4+2] = colorArray [(i-index)*4+2];
            color[i*4+3] = colorArray [(i-index)*4+3];


            index_array [i] = (short)i;
        }
    }



    private void AddHiperCub(float [] vertices, float [] normal, float [] color, short [] index_array,float[] coords_center, int index)
    {

        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] +-1,coords_center[1] + 0,coords_center[2] + 0},1, index);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] +-1,coords_center[1] + 0,coords_center[2] +-1},1, index + 36);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] +-1,coords_center[1] + 0,coords_center[2] + 1},1, index + 36*2);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] + 0,coords_center[1] + 0,coords_center[2] +-1},1, index + 36*3);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] + 0,coords_center[1] + 0,coords_center[2] + 1},1, index + 36*4);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] + 1,coords_center[1] + 0,coords_center[2] +-1},1, index + 36*5);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] + 1,coords_center[1] + 0,coords_center[2] + 0},1, index + 36*6);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] + 1,coords_center[1] + 0,coords_center[2] + 1},1, index + 36*7);

        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] + 0,coords_center[1] +-1,coords_center[2] + 0},1, index + 36*8);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] + 0,coords_center[1] +-1,coords_center[2] +-1},1, index + 36*9);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] + 0,coords_center[1] +-1,coords_center[2] + 1},1, index + 36*10);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] + 0,coords_center[1] + 1,coords_center[2] + 0},1, index + 36*11);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] + 0,coords_center[1] + 1,coords_center[2] +-1},1, index + 36*12);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] + 0,coords_center[1] + 1,coords_center[2] + 1},1, index + 36*13);

        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] + 1,coords_center[1] +-1,coords_center[2] + 0},1, index + 36*14);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] + 1,coords_center[1] + 1,coords_center[2] + 0},1, index + 36*15);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] +-1,coords_center[1] +-1,coords_center[2] + 0},1, index + 36*16);
        AddCub(vertices,normal,color,index_array,new float[]{coords_center[0] +-1,coords_center[1] + 1,coords_center[2] + 0},1, index + 36*17);



    }


}