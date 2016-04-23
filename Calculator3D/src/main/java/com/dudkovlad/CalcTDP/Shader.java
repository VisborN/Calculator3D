package com.dudkovlad.CalcTDP;

import android.opengl.GLES20;

import java.nio.FloatBuffer;

/**
 * Created by vlad on 04.11.2014.
 */
public class Shader {
//будем хранить ссылку на шейдерную программу
    //внутри класса как локальное поле
    private int program_Handle;

    //при создании объекта класса передаем в конструктор
//строки кода вершинного и фрагментного шейдера
    public Shader(String vertexShaderCode, String fragmentShaderCode){
        //вызываем метод, создающий шейдерную программу
        //при этом заполняется поле program_Handle
        createProgram(vertexShaderCode, fragmentShaderCode);
    }
    // метод, который создает шейдерную программу, вызывается в конструкторе
    private void createProgram(String vertexShaderCode, String fragmentShaderCode){
        //получаем ссылку на вершинный шейдер
        int vertexShader_Handle =
                GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        //присоединяем к вершинному шейдеру его код
        GLES20.glShaderSource(vertexShader_Handle, vertexShaderCode);
        //компилируем вершинный шейдер
        GLES20.glCompileShader(vertexShader_Handle);
        //получаем ссылку на фрагментный шейдер
        int fragmentShader_Handle =
                GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        //присоединяем к фрагментному шейдеру его код
        GLES20.glShaderSource(fragmentShader_Handle, fragmentShaderCode);
        //компилируем фрагментный шейдер
        GLES20.glCompileShader(fragmentShader_Handle);
        //получаем ссылку на шейдерную программу
        program_Handle = GLES20.glCreateProgram();
        //присоединяем к шейдерной программе вершинный шейдер
        GLES20.glAttachShader(program_Handle, vertexShader_Handle);
        //присоединяем к шейдерной программе фрагментный шейдер
        GLES20.glAttachShader(program_Handle, fragmentShader_Handle);
        //компилируем шейдерную программу
        GLES20.glLinkProgram(program_Handle);
    }

    //метод, который связывает
//буфер координат вершин vertexBuffer с атрибутом a_vertex
    public void linkVertexBuffer(FloatBuffer vertexBuffer){
        //устанавливаем активную программу
        GLES20.glUseProgram(program_Handle);
        //получаем ссылку на атрибут a_vertex
        int a_vertex_Handle = GLES20.glGetAttribLocation(program_Handle, "a_vertex");
    //включаем использование атрибута a_vertex
    GLES20.glEnableVertexAttribArray(a_vertex_Handle);
    //связываем буфер координат вершин vertexBuffer с атрибутом a_vertex
    GLES20.glVertexAttribPointer(
    a_vertex_Handle, 3, GLES20.GL_FLOAT, false, 0,vertexBuffer);
    }

    //метод, который связывает
//буфер координат векторов нормалей normalBuffer с атрибутом a_normal
    public void linkNormalBuffer(FloatBuffer normalBuffer){
        //устанавливаем активную программу
        GLES20.glUseProgram(program_Handle);
        //получаем ссылку на атрибут a_normal
        int a_normal_Handle = GLES20.glGetAttribLocation(program_Handle, "a_normal");
        //включаем использование атрибута a_normal
        GLES20.glEnableVertexAttribArray(a_normal_Handle);
        //связываем буфер нормалей normalBuffer с атрибутом a_normal
        GLES20.glVertexAttribPointer(
                a_normal_Handle, 3, GLES20.GL_FLOAT, false, 0,normalBuffer);
    }

    //метод, который связывает
//буфер цветов вершин colorBuffer с атрибутом a_color
    public void linkColorBuffer(FloatBuffer colorBuffer){
        //устанавливаем активную программу
        GLES20.glUseProgram(program_Handle);
        //получаем ссылку на атрибут a_color
        int a_color_Handle = GLES20.glGetAttribLocation(program_Handle, "a_color");
        //включаем использование атрибута a_color
        GLES20.glEnableVertexAttribArray(a_color_Handle);
        //связываем буфер нормалей colorBuffer с атрибутом a_color
        GLES20.glVertexAttribPointer(
                a_color_Handle, 4, GLES20.GL_FLOAT, false, 0, colorBuffer);
    }

    public void linkVertex_ALLINONE_Buffer(FloatBuffer Buffer) {
        GLES20.glUseProgram(program_Handle);

        int a_vertex_Handle = GLES20.glGetAttribLocation(program_Handle, "a_vertex");
        //включаем использование атрибута a_vertex
        GLES20.glEnableVertexAttribArray(a_vertex_Handle);
        //связываем буфер координат вершин vertexBuffer с атрибутом a_vertex
        GLES20.glVertexAttribPointer(
                a_vertex_Handle, 3, GLES20.GL_FLOAT, false, 2,Buffer);

        int a_normal_Handle = GLES20.glGetAttribLocation(program_Handle, "a_normal");
        //включаем использование атрибута a_normal
        GLES20.glEnableVertexAttribArray(a_normal_Handle);
        //связываем буфер нормалей normalBuffer с атрибутом a_normal
        GLES20.glVertexAttribPointer(
                a_normal_Handle, 3, GLES20.GL_FLOAT, false, 2,Buffer);

        //получаем ссылку на атрибут a_color
        int a_color_Handle = GLES20.glGetAttribLocation(program_Handle, "a_color");
        //включаем использование атрибута a_color
        GLES20.glEnableVertexAttribArray(a_color_Handle);
        //связываем буфер нормалей colorBuffer с атрибутом a_color
        GLES20.glVertexAttribPointer(
                a_color_Handle, 4, GLES20.GL_FLOAT, false, 3, Buffer);
    }



    public void linkArrayAsUniform(float [] array, String uniformName, int type){

        GLES20.glUseProgram(program_Handle);
        int uniform_handle =
                GLES20.glGetUniformLocation(program_Handle, uniformName);
        switch (type) {
            case 16:
                GLES20.glUniformMatrix4fv(
                    uniform_handle, 1, false, array, 0);break;
            case 3:
                GLES20.glUniform3fv(uniform_handle, 1, array, 0);


        }
    }
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shaderHandle = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shaderHandle, shaderCode);
        GLES20.glCompileShader(shaderHandle);

        return shaderHandle;
    }
//
//    public void linkModelMatrix(float [] modelMatrix){
//        //устанавливаем активную программу
//        GLES20.glUseProgram(program_Handle);
//        //получаем ссылку на униформу u_modelViewProjectionMatrix
//        int u_modelViewProjectionMatrix_Handle =
//                GLES20.glGetUniformLocation(program_Handle, "u_modelMatrix");
//        //связываем массив modelViewProjectionMatrix
//        //с униформой u_modelViewProjectionMatrix
//        GLES20.glUniformMatrix4fv(
//                u_modelViewProjectionMatrix_Handle, 1, false, modelMatrix, 0);
//    }
//
//    //метод, который связывает матрицу модели-вида-проекции
//// modelViewProjectionMatrix с униформой u_modelViewProjectionMatrix
//    public void linkViewProjectionMatrix(float [] ViewProjectionMatrix){
//        //устанавливаем активную программу
//        GLES20.glUseProgram(program_Handle);
//        //получаем ссылку на униформу u_modelViewProjectionMatrix
//        int u_modelViewProjectionMatrix_Handle =
//                GLES20.glGetUniformLocation(program_Handle, "u_ViewProjectionMatrix");
//        //связываем массив modelViewProjectionMatrix
//        //с униформой u_modelViewProjectionMatrix
//        GLES20.glUniformMatrix4fv(
//                u_modelViewProjectionMatrix_Handle, 1, false, ViewProjectionMatrix, 0);
//    }
//
//    // метод, который связывает координаты камеры с униформой u_camera
//    public void linkCamera (float xCamera, float yCamera, float zCamera){
//        //устанавливаем активную программу
//        GLES20.glUseProgram(program_Handle);
//        //получаем ссылку на униформу u_camera
//        int u_camera_Handle=GLES20.glGetUniformLocation(program_Handle, "u_camera");
//        // связываем координаты камеры с униформой u_camera
//        GLES20.glUniform3f(u_camera_Handle, xCamera, yCamera, zCamera);
//    }
//
//    // метод, который связывает координаты источника света
//// с униформой u_lightPosition
//    public void linkLightSource (float xLightPosition, float yLightPosition, float zLightPosition){
//        //устанавливаем активную программу
//        GLES20.glUseProgram(program_Handle);
//        //получаем ссылку на униформу u_lightPosition
//        int u_lightPosition_Handle=GLES20.glGetUniformLocation(program_Handle, "u_lightPosition");
//        // связываем координаты источника света с униформой u_lightPosition
//        GLES20.glUniform3f(u_lightPosition_Handle, xLightPosition, yLightPosition, zLightPosition);
//    }

    // метод, который делает шейдерную программу данного класса активной
    public void useProgram(){
        GLES20.glUseProgram(program_Handle);
    }
// конец класса
}
