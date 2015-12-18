package sbu.wireless.project.analysisofcellularconnectivity.util;

/**
 * Created by Mondrita on 23-11-2015.
 */
public class Utilities {

    public static String getNetworkType(int networkType) {
        String result = "";
        switch (networkType)

        {
            case 7:
                result = "1xRTT";
                break;
            case 4:
                result = "CDMA";
                break;
            case 2:
                result = "EDGE";
                break;
            case 14:
                result = "eHRPD";
                break;
            case 5:
                result = "EVDO rev. 0";
                break;
            case 6:
                result = "EVDO rev. A";
                break;
            case 12:
                result = "EVDO rev. B";
                break;
            case 1:
                result = "GPRS";
                break;
            case 8:
                result = "HSDPA";
                break;
            case 10:
                result = "HSPA";
                break;
            case 15:
                result = "HSPA+";
                break;
            case 9:
                result = "HSUPA";
                break;
            case 11:
                result = "iDen";
                break;
            case 13:
                result = "LTE";
                break;
            case 3:
                result = "UMTS";
                break;
            case 0:
                result = "Unknown";
                break;
            default:
                result = "default";
                break;
        }
        return result;
    }
}
