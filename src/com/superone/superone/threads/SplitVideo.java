/**
 * @author alexandre
 * @since 21/09/13
 */
package com.superone.superone.threads;

/* ANDROID IMPORT */

/* LOCAL IMPORT */
import com.superone.superone.listeners.ListenerVideo;
import org.ffmpeg.android.FfmpegController;
import org.ffmpeg.android.ShellUtils;

/* STANDARD IMPORT */


/**
 * Convert mp4 and wav containers in h264 and aac files.
 */
public class SplitVideo extends Thread{

    private ListenerVideo finishListener;
    private String srcFile = "";
    private FfmpegController ffmpegCtrl = null;

    /**
     * Initiate the variables and the thread.
     *
     * Use {@link #SplitVideo(org.ffmpeg.android.FfmpegController, String, com.superone.superone.listeners.ListenerVideo)} to initiate the variables.
     *
     * @param ctrlo     controller to run ffmpeg's process functions
     * @param src       file to output
     * @param listener  listener on the end of the thread
     */
    public SplitVideo(FfmpegController ctrlo, String src,ListenerVideo listener) {
        this.ffmpegCtrl = ctrlo;
        this.srcFile = src;
        this.finishListener = listener;
    }



    public void run() {
        final String outputh264 = srcFile+".h264";
        final String outputaac = srcFile+".aac";

        final ShellUtils.ShellCallback _ShellCallback = new ShellUtils.ShellCallback() {
            @Override
            public void shellOut(String shellLine) {  }
            @Override
            public void processComplete(int exitValue) {
                if (finishListener != null) { finishListener.onThreadEnd(); }
            }
        };

        ShellUtils.ShellCallback _ShellCallback1 = new ShellUtils.ShellCallback() {
            @Override
            public void shellOut(String shellLine) {  }
            @Override
            public void processComplete(int exitValue) {
                try { ffmpegCtrl.convertToAacStream(srcFile, outputaac, _ShellCallback); }
                catch (Exception e) { e.printStackTrace(); }
            }
        };


        try { ffmpegCtrl.convertToh264Stream(srcFile, outputh264, _ShellCallback1); }
        catch (Exception e) { e.printStackTrace(); }
    }
}