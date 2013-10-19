/**
 * @author alexandre
 * @since 21/09/13
 */
package com.superone.superone.threads;

/* ANDROID IMPORT */

/* LOCAL IMPORT */
import com.superone.superone.listeners.ListenerVideo;
import org.ffmpeg.android.FfmpegController;

/* STANDARD IMPORT */
import java.io.File;

/**
 * Concatenate two h264 files, then two aac files.
 */
public class ConcatVideo extends Thread{

    private ListenerVideo finishListener;
    private String srcFile = "";
    private String dstFile = "";
    private FfmpegController ffmpegCtrl = null;

    /**
     * Initiate the variables and the thread.
     *
     * Use {@link #ConcatVideo(org.ffmpeg.android.FfmpegController, String, String, com.superone.superone.listeners.ListenerVideo)} to initiate the variables.
     *
     * @param ctrlo     controller to run ffmpeg's process functions
     * @param src       file to concatenate
     * @param dest      file to concatenate in
     * @param listener  listener on the end of the thread
     */
    public ConcatVideo(FfmpegController ctrlo, String src, String dest, ListenerVideo listener) {
        this.ffmpegCtrl = ctrlo;
        this.srcFile = src;
        this.dstFile = dest;
        this.finishListener = listener;
    }

    public void run() {
        String inputh264 = srcFile+".h264";
        String inputaac = srcFile+".aac";
        String outputh264 = dstFile+".h264";
        String outputaac = dstFile+".aac";

        try { ffmpegCtrl.concatFiles(inputh264, outputh264) ; }
        catch (Exception e) { e.printStackTrace(); }

        try { ffmpegCtrl.concatFiles(inputaac, outputaac) ; }
        catch (Exception e) { e.printStackTrace(); }

        new File(inputaac).delete();
        new File(inputh264).delete();

        if (finishListener != null) { finishListener.onThreadEnd(); }
    }
}