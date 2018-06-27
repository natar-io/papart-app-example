package tech.lity.rea.example;

import processing.core.*;
import fr.inria.papart.procam.*;
import processing.data.JSONArray;
import processing.data.JSONObject;
import redis.clients.jedis.Jedis;

/**
 *
 * @author Jeremy Laviole, <laviole@rea.lity.tech>
 */
@SuppressWarnings("serial")
public class SendPose extends PApplet {

    Papart papart;

    @Override
    public void setup() {
        papart = Papart.seeThrough(this);
        new PoseAPP();
        papart.startTracking();
    }

    @Override
    public void settings() {
        // the application will be rendered in full screen, and using a 3Dengine.
        size(640, 480, P3D);
    }

    @Override
    public void draw() {
    }

    /**
     * @param passedArgs the command line arguments
     */
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{tech.lity.rea.example.SendPose.class.getName()};
//        if (passedArgs != null) {
//            PApplet.main(concat(appletArgs, passedArgs));
//        } else {
        PApplet.main(appletArgs);
//        }
    }
}

class PoseAPP extends PaperScreen {

    public static final int REDIS_PORT = 6379;
    public static final String REDIS_HOST = "localhost";

    @Override
    public void settings() {
        // the size of the draw area is 297mm x 210mm.
        setDrawingSize(297, 210);
        // loads the marker that are actually printed and tracked by the camera.
//        loadMarkerBoard(Papart.markerFolder + "A4-default.svg", 297, 210);
        loadMarkerBoard(Papart.markerFolder + "chili1.svg", 297, 210);

        // the application will render drawings and shapes only on the surface of the sheet of paper.
        setDrawOnPaper();
    }
    
    
    String output = "camera0:pose";
    boolean set = false;
    
    @Override
    public void setup() {
        connectRedis();
    }

    @Override
    public void drawOnPaper() {
        // setLocation(63, 45, 0);

        // background: blue
        background(0, 0, 200, 100);

        // fill the next shapes with green
        fill(0, 100, 0, 100);

        noStroke();
        // draw a green rectangle
        rect(98.7f, 140, 101, 12);
        
        JSONObject j = new JSONObject();
        JSONArray poseJson = PMatrixToJSON(getLocation());
        
        // Not used yet.
        j.setJSONArray("pose", poseJson);
        if (set) {
            redisSend.set(output, poseJson.toString());
//            System.out.println("Pose set to " + output);
        } else {
            redisSend.publish(output, poseJson.toString());
//            System.out.println("Pose updated to " + output);
        }
//        System.out.println("Pose");
        getLocation().print();

    }

    public static JSONArray PMatrixToJSON(PMatrix3D mat) {
        JSONArray output = new JSONArray();
        output.append(mat.m00);
        output.append(mat.m01);
        output.append(mat.m02);
        output.append(mat.m03);

        output.append(mat.m10);
        output.append(mat.m11);
        output.append(mat.m12);
        output.append(mat.m13);

        output.append(mat.m20);
        output.append(mat.m21);
        output.append(mat.m22);
        output.append(mat.m23);

        output.append(mat.m30);
        output.append(mat.m31);
        output.append(mat.m32);
        output.append(mat.m33);
        return output;
    }

    Jedis redis, redisSend;

    private void connectRedis() {
        try {
            redis = new Jedis(REDIS_HOST, REDIS_PORT);
            redisSend = new Jedis(REDIS_HOST, REDIS_PORT);
            if (redis == null) {
                throw new Exception("Cannot connect to server. ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        // redis.auth("156;2Asatu:AUI?S2T51235AUEAIU");
    }

}
