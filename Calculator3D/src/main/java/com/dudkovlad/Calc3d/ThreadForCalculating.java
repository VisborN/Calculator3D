package com.dudkovlad.Calc3d;

import android.os.Handler;
import android.os.Message;

import com.dudkovlad.Calc3d.Parser.Parser;

/**
 * Created by vlad on 22.11.2014.
 */
public class ThreadForCalculating extends Thread{

    public static final int LOG_TEXT = 100;
    public static final int SHOW_RESULT = 101;

    private boolean busy, busygraphic, result_requested, graphic_requested, graphic_updated = false;

    private short       []      tempIndex = new short [4];
    short               []      poligon_indicies = new short [4];
    int                 [][]    poligon_vertices = new int [3][3];

    public float        []      Vertexarr;
    public float        []      Normalarr;
    public float        []      Colorarr;
    public short        []      Indexarr;
    private short     [][]      triangles_indicies;
    private short     [][]      Pointsshort;
    private float   [][][][]    PointsDist;
    private boolean [][][][]    PointsRights;
    float xmin, xmax, ymin, ymax, zmin, zmax, step;
    private String equation, result;
    private Handler mHandler;
    private Parser equation_parse;
    private Message msg;


    ThreadForCalculating (Handler h) {
        // Создаём новый второй поток

        super("ThreadForCalculating");

        equation_parse = new Parser();
        mHandler = h;


        start(); // Запускаем поток
    }

    // Точка входа второго потока
    public void run() {
        try {

            while(true)
            {
                if (busy) {
                    yield();
                    continue;
                }
                busy = true;

                if (result_requested)
                {

                    result = equation_parse.INIT(equation);
                    if (!result.equals("all is ok")) {
                        if(result.equals("equation is empty"))
                            result = "";
                    }else {
                        result = equation_parse.Result();
//                for (int i = 0; i < count; i++)
//                    equation_parse.forLoop(1, 1, 1, 1, 1);
                    }

                    showResult(result);
                    result_requested = false;
                }else
                if (graphic_requested&&!busygraphic)
                {
                    busygraphic = true;
                    graphic_requested = false;

                    short sizex = (short)(((Float)((xmax-xmin)/step)).shortValue() + 2);
                    short sizey = (short)(((Float)((ymax-ymin)/step)).shortValue() + 2);
                    short sizez = (short)(((Float)((zmax-zmin)/step)).shortValue() + 2);
                    short sizec = 1;
                    /*PointsDist      = new float     [sizex*sizey*sizez*sizec];
                    PointsRights    = new boolean   [sizex*sizey*sizez*sizec];
                    Pointsshort     = new short       [sizex*sizey*sizez*4];*/


                    PointsDist      = new float     [sizex][sizey][sizez][sizec];
                    PointsRights    = new boolean   [sizex][sizey][sizez][sizec];
                    Pointsshort     = new short       [sizex*sizey*sizez][4];
                    short indicesCount = 0;
                    for(short x = 0; x<sizex;x++)
                        for(short y = 0; y<sizey;y++)
                            for(short z = 0; z<sizez;z++)
                                for(short c = 0; c<sizec;c++)
                                    PointsDist[x][y][z][c] =   (xmin - step + step*x)*(xmin - step + step*x) +
                                                                (zmin - step + step*z)*(zmin - step + step*z) -
                                                                (ymin - step + step*y);
                                    /*
                                            equation_parse.forLoop( xmin - step + step*x,
                                                                    ymin - step + step*y,
                                                                    zmin - step + step*z, c, 0);*/
                    float temp;
                    for(short x = 1; x<sizex-1;x++)
                        for(short y = 1; y<sizey-1;y++)
                            for(short z = 1; z<sizez-1;z++) {
                                for (short c = 1; c < sizec - 1; c++) {

                                    temp = PointsDist[x][y][z][c];
                                    PointsRights[x][y][z][c] = true;
                                    for (short xt = -1; xt < 2; xt++)
                                        for (short yt = -1; yt < 2; yt++)
                                            for (short zt = -1; zt < 2; zt++)
                                                for (short ct = -1; ct < 2; ct++)
                                                    if (temp > PointsDist[x + xt][y + yt][z + zt][c + ct])
                                                        PointsRights[x][y][z][c] = false;

                                    if (PointsRights[x][y][z][c]) {
                                        indicesCount++;
                                        Pointsshort[indicesCount - 1][0] = x;
                                        Pointsshort[indicesCount - 1][1] = y;
                                        Pointsshort[indicesCount - 1][2] = z;
                                        Pointsshort[indicesCount - 1][3] = c;
                                        if (indicesCount==Short.MAX_VALUE) {
                                            LOG("too many points");
                                            x = sizex;
                                            y = sizey;
                                            z = sizez;
                                            c = sizec;
                                        }
                                        break;
                                    }
                                }
                            }
                    Vertexarr = new float[indicesCount*3];
                    Normalarr = new float[indicesCount*3];
                    Colorarr  = new float[indicesCount*4];
                    triangles_indicies = new short [indicesCount*3][3];
                    short trianglesCount = 0;
                    float dist1, dist2, dist3;
                    if (indicesCount>3) {
                        for (short i = 0; i < indicesCount; i++) {

                            poligon_indicies[0] = i;
                            dist1 = Float.POSITIVE_INFINITY;
                            dist2 = Float.POSITIVE_INFINITY;
                            dist3 = Float.POSITIVE_INFINITY;
                            for (short j = 0; j < indicesCount; j++) {
                                temp = (Pointsshort[i][0] - Pointsshort[j][0]) *
                                        (Pointsshort[i][0] - Pointsshort[j][0]) +
                                        (Pointsshort[i][1] - Pointsshort[j][1]) *
                                                (Pointsshort[i][1] - Pointsshort[j][1]) +
                                        (Pointsshort[i][2] - Pointsshort[j][2]) *
                                                (Pointsshort[i][2] - Pointsshort[j][2]);
                                if (temp < dist1) {
                                    poligon_indicies[3] = poligon_indicies[2];
                                    dist3 = dist2;
                                    poligon_indicies[2] = poligon_indicies[1];
                                    dist2 = dist1;
                                    poligon_indicies[1] = j;
                                    dist1 = temp;

                                } else if (temp < dist2) {
                                    poligon_indicies[3] = poligon_indicies[2];
                                    dist3 = dist2;
                                    poligon_indicies[2] = j;
                                    dist2 = temp;
                                } else if (temp < dist3) {
                                    poligon_indicies[3] = j;
                                    dist3 = temp;
                                }
                            }

                            for (int j = 0; j < 3; j++) {
                                poligon_vertices[j][0] = Pointsshort[poligon_indicies[j + 1]][0] -
                                        Pointsshort[poligon_indicies[0]][0];
                                poligon_vertices[j][1] = Pointsshort[poligon_indicies[j + 1]][1] -
                                        Pointsshort[poligon_indicies[0]][2];
                                poligon_vertices[j][2] = Pointsshort[poligon_indicies[j + 1]][1] -
                                        Pointsshort[poligon_indicies[0]][2];
                            }

                            Normalarr[i * 3] = (poligon_vertices[0][0] / dist1 +
                                    poligon_vertices[1][0] / dist2 +
                                    poligon_vertices[2][0] / dist3) / 3;
                            Normalarr[i * 3 + 1] = (poligon_vertices[0][1] / dist1 +
                                    poligon_vertices[1][1] / dist2 +
                                    poligon_vertices[2][1] / dist3) / 3;
                            Normalarr[i * 3 + 2] = (poligon_vertices[0][2] / dist1 +
                                    poligon_vertices[1][2] / dist2 +
                                    poligon_vertices[2][2] / dist3) / 3;

                            Vertexarr[i * 3] = xmin - step + step * Pointsshort[i][0];
                            Vertexarr[i * 3 + 1] = ymin - step + step * Pointsshort[i][1];
                            Vertexarr[i * 3 + 2] = zmin - step + step * Pointsshort[i][2];

                            Colorarr[i * 4] = 0;
                            Colorarr[i * 4 + 1] = 1;
                            Colorarr[i * 4 + 2] = Pointsshort[i][3] % 265 / 265f;
                            Colorarr[i * 4 + 3] = 1;

                            for (short k2 = 1; k2 < 4; k2++) {
                                trianglesCount++;
                                triangles_indicies[trianglesCount][0] = poligon_indicies[0];
                                triangles_indicies[trianglesCount][1] = poligon_indicies[k2];
                                triangles_indicies[trianglesCount][2] = poligon_indicies[k2 % 3 + 1];
                                for (short j = 0; j < trianglesCount - 1; j++)
                                    for (int k = 0; k < 3; k++)
                                        if (triangles_indicies[j][k] == poligon_indicies[0] ||
                                                triangles_indicies[j][k] == poligon_indicies[k2] ||
                                                triangles_indicies[j][k] == poligon_indicies[k2 % 3 + 1]) {
                                            trianglesCount--;
                                            break;
                                        }

                            }
                        }
                        Indexarr  = new short[trianglesCount*3];

                        for (short i = 0; i < trianglesCount;i++)
                        {
                            Indexarr[i*3] = triangles_indicies[i][0];
                            Indexarr[i*3+1] = triangles_indicies[i][1];
                            Indexarr[i*3+2] = triangles_indicies[i][2];
                        }
                    }
                    else
                    {

                        Indexarr  = new short[1];


                    }


                    graphic_updated = true;
                    busygraphic = false;


                    LOG("calculating ended");


                }



                busy = false;
                yield();

            }

        } catch (Exception e) {

            LOG("TFC exterminate "+e.toString()+"\n");
        }
    }



    void Result (String equation)
    {
        result_requested = true;
        this.equation = equation;
    }


    void setGraphic (float xmin, float xmax, float ymin, float ymax, float zmin, float zmax, float step, boolean byresolution, int resolution)
    {
        if (busygraphic)
            while (busygraphic)
                yield();
        busygraphic = true;
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
        this.zmin = zmin;
        this.zmax = zmax;
        if (byresolution)
        {
            this.step = (xmax - xmin)/resolution;
        }
        else
        {
            this.step = step;
        }
        graphic_requested = true;
        busygraphic = false;
    }

    boolean isGraphic_updated()
    {
        return graphic_updated;
    }

    public void setGraphic_updated(boolean graphic_updated) {
        this.graphic_updated = graphic_updated;
    }

    public void setBusygraphic(boolean busygraphic) {
        this.busygraphic = busygraphic;
    }

    public boolean isBusygraphic() {
        return busygraphic;
    }

    void showResult(String result)
    {
        msg = new Message();
        msg.what = SHOW_RESULT;
        msg.obj = result;
        mHandler.sendMessage(msg);
    }

    void LOG(String some)
    {
        msg = new Message();
        msg.what = LOG_TEXT;
        msg.obj = some;
        mHandler.sendMessage(msg);
    }
}
