package org.esigelec.faceexpressionlbp;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileUtils {
    private static String TAG = FileUtils.class.getSimpleName();
    private static boolean loadFile(Context context, String cascadeName) {
        InputStream inp = null;
        OutputStream out = null;
        boolean completed = false;
        try {
            inp = context.getResources().getAssets().open(cascadeName);
            File outFile = new File(context.getCacheDir(), cascadeName);
            out = new FileOutputStream(outFile);

            byte[] buffer = new byte[4096];
            int bytesread;
            while((bytesread = inp.read(buffer)) != -1) {
                out.write(buffer, 0, bytesread);
            }

            completed = true;
            inp.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            Log.i(TAG, "Unable to load cascade file" + e);
        }
        return completed;
    }
    public static CascadeClassifier loadXMLS(Activity activity) {


        InputStream is = activity.getResources().openRawResource(R.raw.lbpcascade_frontalface);
        File cascadeDir = activity.getDir("cascade", Context.MODE_PRIVATE);
        File mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface_improved.xml");
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(mCascadeFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new CascadeClassifier(mCascadeFile.getAbsolutePath());
    }
    public static String loadTrained() {
        File file = new File(Environment.getExternalStorageDirectory(), "TrainedData/lbph_trained_data.xml");

        return file.toString();
    }
}
