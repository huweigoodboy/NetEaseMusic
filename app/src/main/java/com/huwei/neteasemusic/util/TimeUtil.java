package com.huwei.neteasemusic.util;

/**
 * @author jerry
 * @date 2016/05/20
 */
public class TimeUtil {

    /**
     * 获取dd hh mm ss
     *
     * @return
     */
    public static int[] getTimeArray(long second) {
        int[] t = new int[4];
        for (int i = 3; i >= 2; i--) {
            t[i] = (int) (second % 60);
            second /= 60;
        }

        t[1] = (int) (second % 24);
        t[0] = (int) (second /= 24);

        return t;
    }

    /**
     *
     * @param duration  单位为s
     * @return
     */
    public static String getDuration(int duration) {
        int[] time = getTimeArray(duration);

        String m = String.valueOf(time[2]);
        String s = String.valueOf(time[3]);

        if (time[2] < 10) {
            m = "0" + time[2];
        }

        if (time[3] < 10) {
            s = "0" + time[3];
        }

        return m + ":" + s;
    }

    public static int getLrcMillTime(String time){
        int millTime=0;
        time=time.replace(".", ":");



        String timedata[]=time.split(":");

        //Log.i("min,second,mill", timedata[0]+","+timedata[1]+","+timedata[2]);
        int min=0;
        int second=0;
        int mill=0;
        try {
            min = Integer.parseInt(timedata[0]);
            second = Integer.parseInt(timedata[1]);
            mill = Integer.parseInt(timedata[2]);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();


            return -1;

        }


        millTime=(min*60+second)*1000+mill*10;
        return millTime;
    }
}
