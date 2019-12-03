package Application.AdditionalClasses;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

public class DatasetMaker {

    public static ArrayList<Pair<double[], double[]>> createDataset(int size){
        var dataset = new ArrayList<Pair<double[], double[]>>();

        for (int i = 0; i < size; i++) {
            double[] rgb = {0.0,0.0,0.0};
            double[] hsv = {0.0,0.0,0.0};

            //generate random RGB color
            Random rd = new Random();
            rgb[0] = rd.nextDouble();
            rgb[1] = rd.nextDouble();
            rgb[2] = rd.nextDouble();

            //get hsv values (h normalized)
            hsv = RGBtoHSV(rgb[0], rgb[1], rgb[2]);

            dataset.add(new Pair<>(rgb, hsv));
        }

        return dataset;
    }

    /**
     * Converts RGB to normalized HSV values.
     * @param r
     * @param g
     * @param b
     * @return
     */
    public static double[] RGBtoHSV(double r, double g, double b){

        double h, s, v;
        double Cmin, Cmax, diff;

        Cmin = Math.min(Math.min(r, g), b);
        Cmax = Math.max(Math.max(r, g), b);

        v = Cmax;
        diff = Cmax - Cmin;

        if( Cmax != 0 )
            s = diff / Cmax;
        else {
            s = 0;
            h = -1;
            return new double[]{h,s,v};
        }

        if( r == Cmax )
            h = ( g - b ) / diff;
        else if( g == Cmax )
            h = 2 + ( b - r ) / diff;
        else
            h = 4 + ( r - g ) / diff;

        h *= 60;

        if( h < 0 )
            h += 360;

        //normalize h to 0-1
        h = h / 360;

        return new double[]{h,s,v};
    }
}
