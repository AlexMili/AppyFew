/**
 * @author alexandre
 * @since 21/09/13
 */
package com.superone.superone.threads;

/* ANDROID IMPORT */

/* LOCAL IMPORT */
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.tracks.H264TrackImpl;
import com.superone.superone.listeners.ListenerVideo;
import org.ffmpeg.android.FfmpegController;
import org.ffmpeg.android.ShellUtils;

/* STANDARD IMPORT */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Add a video container and an audio container to input files.
 */
public class ContainerVideo extends Thread{

    private ListenerVideo finishListener;
    private String srcFile = "";
    private FfmpegController ffmpegCtrl = null;

    /**
     * Initiate the variables and the thread.
     *
     * Use {@link #ContainerVideo(org.ffmpeg.android.FfmpegController, String, com.superone.superone.listeners.ListenerVideo)} to initiate the variables.
     *
     * @param ctrlo     controller to run ffmpeg's process functions
     * @param src       file to output
     * @param listener  listener on the end of the thread
     */
    public ContainerVideo(FfmpegController ctrlo, String src,ListenerVideo listener) {
        this.ffmpegCtrl = ctrlo;
        this.srcFile = src;
        this.finishListener = listener;
    }


    public void run() {
        H264TrackImpl h264Track = null;
        Movie movie = null;
        String inputh264 = srcFile+".h264";
        String outputmp4 = srcFile;
        final String inputaac = srcFile+".aac";
        final String outputwav = srcFile+".wav";

        final ShellUtils.ShellCallback _ShellCallback = new ShellUtils.ShellCallback() {
            @Override
            public void shellOut(String shellLine) {  }
            @Override
            public void processComplete(int exitValue) {
                if (finishListener != null) { finishListener.onThreadEnd(); }
            }
        };

        try { h264Track = new H264TrackImpl(new FileInputStream(inputh264).getChannel()); }
        catch (IOException e) { e.printStackTrace(); }

        movie = new Movie();
        movie.addTrack(h264Track);

        try {
            Container out = new DefaultMp4Builder().build(movie);
            FileOutputStream fos = new FileOutputStream(new File(outputmp4));
            FileChannel fc = fos.getChannel();
            out.writeContainer(fc);
            fos.close();
        }
        catch(Exception e) { e.printStackTrace(); }

        try { ffmpegCtrl.convertToWav(inputaac, outputwav, _ShellCallback); }
        catch (Exception e) { e.printStackTrace(); }
    }
}