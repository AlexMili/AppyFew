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
 * Merge h264 and aac file to a single mp4
 */
public class JoinVideo extends Thread{

    private ListenerVideo finishListener;
    private String srcFile = "";
    private String dstFile = "";
    private String audioAac = "";
    private FfmpegController ffmpegCtrl = null;

    /**
     * Initiate the variables and the thread.
     *
     * Use {@link #JoinVideo(org.ffmpeg.android.FfmpegController, String, String, String, com.superone.superone.listeners.ListenerVideo)} to initiate the variables.
     *
     * @param ctrlo     controller to run ffmpeg's process functions
     * @param src       file to output
     * @param dest      file to output in
     * @param audio     audio file to add
     * @param listener  listener on the end of the thread
     */
    public JoinVideo(FfmpegController ctrlo, String src,String dest,String audio,ListenerVideo listener) {
        this.ffmpegCtrl = ctrlo;
        this.srcFile = src;
        this.dstFile = dest;
        this.audioAac = audio;
        this.finishListener = listener;
    }

    public void run() {
        String outputmp4 = dstFile;
        String inputh264 = srcFile;

        final ShellUtils.ShellCallback _ShellCallback = new ShellUtils.ShellCallback() {
            @Override
            public void shellOut(String shellLine) {  }
            @Override
            public void processComplete(int exitValue) {
                if (finishListener != null) {
                    finishListener.onThreadEnd();
                }
            }
        };

        try { ffmpegCtrl.joinVideoAndAudio(inputh264, audioAac, outputmp4, _ShellCallback); }
        catch (Exception e) { e.printStackTrace(); }
    }
}