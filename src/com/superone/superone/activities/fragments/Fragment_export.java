/**
 * @author BadMojo, alexandre
 * @since 21/09/13
 */
package com.superone.superone.activities.fragments;

/* ANDROID IMPORT */
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

/* LOCAL IMPORT */
import com.google.android.youtube.player.YouTubeIntents;
import com.superone.superone.AppConfiguration;
import com.superone.superone.AppUtils;
import com.superone.superone.R;
import com.superone.superone.db.DatabaseHelperTimelapse;
import com.superone.superone.listeners.ListenerVideo;
import com.superone.superone.models.TimelapseModel;
import com.superone.superone.threads.JoinVideo;

/* STANDARD IMPORT */
import org.ffmpeg.android.FfmpegController;
import org.ffmpeg.android.ShellUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * Manage local and youtube export.
 */
public class Fragment_export extends Fragment implements AppConfiguration{

    private int indexCurrentTimelapse;

    private DatabaseHelperTimelapse db;
    private ArrayList<TimelapseModel> timelapseList;
    private TimelapseModel currentTimelapse;

    private ImageButton btnYoutubeExport;
    private ImageButton btnLocalExport;

    public Fragment_export(DatabaseHelperTimelapse database) {
        super();
        db = database;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_export, container, false);

        btnYoutubeExport = (ImageButton) v.findViewById(R.id.btn_youtube_export);
        btnYoutubeExport.setOnClickListener(youtubeExportListener);

        btnLocalExport = (ImageButton) v.findViewById(R.id.btn_local_export);
        btnLocalExport.setOnClickListener(localExportListener);

        return v;
    }

    @Override
    public void onResume()	{
        super.onResume();
        timelapseList = db.getTimelapse();
        indexCurrentTimelapse = getActivity().getIntent().getExtras().getInt("ModelPosition");
        currentTimelapse = timelapseList.get(indexCurrentTimelapse);
    }

    private View.OnClickListener youtubeExportListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //final String videoName = currentTimelapse.getMainUrl();
            timelapseList = db.getTimelapse();
            currentTimelapse = timelapseList.get(indexCurrentTimelapse);

            if(YouTubeIntents.isYouTubeInstalled(getActivity())) {
                if(YouTubeIntents.canResolveUploadIntent(getActivity())) {
                    if(currentTimelapse.getSlaveUrl().equals("")) {
                         FfmpegController ffmpegCtrl = new FfmpegController(getActivity(), null);
                        final String videoPathWithoutExt = rootDirectory+currentTimelapse.getName();

                        //No sample
                        if(currentTimelapse.getSample().equals("0")) {
                            String audioPath = videoPathWithoutExt+"_full.mp4.aac";

                            Thread joinVideoAndAudio = new JoinVideo(ffmpegCtrl, videoPathWithoutExt+"_full.mp4.h264", videoPathWithoutExt+"_export.mp4", audioPath,new ListenerVideo(){

                                @Override
                                public void onThreadEnd() {
                                    Log.d(debugTag, "END MERGE with " +rootDirectory+currentTimelapse.getName()+"_export.mp4");

                                    final String videoPath2 = videoPathWithoutExt +"_export.mp4";
                                    if(!videoPath2.isEmpty()) {
                                        dumpVideos(getActivity());
                                        String mediaUri = getVideoIdFromFilePath(videoPath2, getActivity().getContentResolver());
                                        Log.d(debugTag, "Uri : " +Uri.fromFile(new File(videoPath2)));
                                        Log.d(debugTag, "RETURN : "+mediaUri);
                                        Intent intent = YouTubeIntents.createUploadIntent(getActivity(), Uri.parse(mediaUri));//OLD method : Uri.parse("content://media"+ root)
                                        //content://media/external/video/media/10136
                                        startActivity(intent);
                                    }
                                    else
                                        Toast.makeText(getActivity(), "You must have a video recorded !", Toast.LENGTH_LONG).show();
                                }
                            });
                            joinVideoAndAudio.start();
                        }
                        else {
                            int sampleId = Integer.parseInt(currentTimelapse.getSample());
                            final String loopPath = samplesFiles[sampleId];
                            int loopId = samplesIds[sampleId];
                            final String normalLoopPath = rootDirectory+loopPath;
                            final String aacLoopPath = normalLoopPath+".aac";
                            final String loopLoop = loopPath+"_loop.aac";

                            try { installFromRaw(getActivity(), loopId, rootDirectory, loopPath); }
                            catch (IOException e) { e.printStackTrace(); }

                            if(new File(aacLoopPath).exists())
                                new File(aacLoopPath).delete();

                            try {
                                ffmpegCtrl.convertWavToAac(normalLoopPath,aacLoopPath, new ShellUtils.ShellCallback(){

                                    @Override
                                    public void shellOut(String shellLine) { }

                                    @Override
                                    public void processComplete(int exitValue) {
                                        {
                                            FfmpegController test = ffmpegCtrl;
                                        }
                                        new File(normalLoopPath).delete();

                                        try { ffmpegCtrl.repeatLoop(rootDirectory,loopPath+".aac",loopLoop,10); }
                                        catch (Exception e) { e.printStackTrace(); }

                                        new File(aacLoopPath).delete();

                                        Thread joinVideoAndAudio = new JoinVideo(ffmpegCtrl, videoPathWithoutExt+"_full.mp4.h264", videoPathWithoutExt+"_export.mp4", rootDirectory+loopLoop,new ListenerVideo(){

                                            @Override
                                            public void onThreadEnd() {
                                                new File(rootDirectory+loopLoop).delete();
                                                Log.d(debugTag, "END MERGE with " + videoPathWithoutExt + "_export.mp4");

                                                final String videoPath2 = videoPathWithoutExt +"_export.mp4";
                                                if(!videoPath2.isEmpty()) {
                                                    dumpVideos(getActivity());
                                                    String mediaUri = getVideoIdFromFilePath(videoPath2, getActivity().getContentResolver());
                                                    Log.d(debugTag, "Uri : " +Uri.fromFile(new File(videoPath2)));
                                                    Log.d(debugTag, "RETURN : "+mediaUri);
                                                    Intent intent = YouTubeIntents.createUploadIntent(getActivity(), Uri.parse(mediaUri));//OLD method : Uri.parse("content://media"+ root)
                                                    //content://media/external/video/media/10136
                                                    startActivity(intent);
                                                }
                                                else
                                                    Toast.makeText(getActivity(), "You must have a video recorded !", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        joinVideoAndAudio.start();
                                    }
                                });
                            }
                            catch (Exception e) {  e.printStackTrace(); }
                        }


                    }
                    else
                        Toast.makeText(getActivity(), "Encoding, please wait ...", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getActivity(), "Your version of Youtube doesn't support the upload !", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getActivity(), "Youtube is not installed !", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * Gets the MediaStore video ID of a given file on external storage
     * @param filePath The path (on external storage) of the file to resolve the ID of
     * @param contentResolver The content resolver to use to perform the query.
     * @return the video ID as a long
     */
    private String getVideoIdFromFilePath(String filePath, ContentResolver contentResolver) {
        long videoId=0;
        Log.d(debugTag,"Loading file " + filePath);

        // This returns us content://media/external/videos/media (or something like that)
        // I pass in "external" because that's the MediaStore's name for the external
        // storage on my device (the other possibility is "internal")
        Uri videosUri = MediaStore.Video.Media.getContentUri("external");

        Log.d(debugTag,"videosUri = " + videosUri.toString());

        String[] projection = {MediaStore.Video.VideoColumns._ID};

        Cursor cursor = contentResolver.query(videosUri, projection, MediaStore.Video.VideoColumns.DATA + " LIKE ?", new String[]{filePath}, null);

        dumpVideos(getActivity());

        if(cursor != null) {
            if(cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(projection[0]);
                videoId = cursor.getLong(columnIndex);

                Log.d(debugTag,"Video ID is " + videoId);
                cursor.close();
            }
        }
        return videosUri.toString()+"/"+videoId;
    }
    public static void dumpVideos(Context context) {
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Video.VideoColumns.DATA };
        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);
        int vidsCount = 0;
        if (c != null) {
            vidsCount = c.getCount();
            while (c.moveToNext()) {
                Log.d("VIDEO", c.getString(0));
            }
            c.close();
        }
        Log.d("VIDEO", "Total count of videos: " + vidsCount);
    }
    private View.OnClickListener localExportListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String audioPath="";
            timelapseList = db.getTimelapse();
            currentTimelapse = timelapseList.get(indexCurrentTimelapse);

            if(currentTimelapse.getSlaveUrl().equals("")) {
                final FfmpegController ffmpegCtrl = new FfmpegController(getActivity(), null);
                final String videoPathWithoutExt = rootDirectory+currentTimelapse.getName();

                //No sample
                if(currentTimelapse.getSample().equals("0")) {
                    audioPath = videoPathWithoutExt+"_full.mp4.aac";

                    Thread joinVideoAndAudio = new JoinVideo(ffmpegCtrl, videoPathWithoutExt+"_full.mp4.h264", videoPathWithoutExt+"_export.mp4", audioPath,new ListenerVideo(){

                        @Override
                        public void onThreadEnd() {
                            Log.d(debugTag, "END MERGE with " +rootDirectory+currentTimelapse.getName()+"_export.mp4");

                            try {
                                AppUtils.copy(new File(videoPathWithoutExt+"_export.mp4"), new File(videoPathWithoutExt + ".mp4"));
                                new File(videoPathWithoutExt+"_export.mp4").delete();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(getActivity(), "Your video has been saved in " +exportDirectory, Toast.LENGTH_LONG).show();
                        }
                    });
                    joinVideoAndAudio.start();
                }
                else {
                    int sampleId = Integer.parseInt(currentTimelapse.getSample());
                    final String loopPath = samplesFiles[sampleId];
                    int loopId = samplesIds[sampleId];
                    final String normalLoopPath = rootDirectory+loopPath;
                    final String aacLoopPath = normalLoopPath+".aac";
                    final String loopLoop = loopPath+"_loop.aac";

                    try { installFromRaw(getActivity(), loopId, rootDirectory, loopPath); }
                    catch (IOException e) { e.printStackTrace(); }

                    if(new File(aacLoopPath).exists())
                        new File(aacLoopPath).delete();

                    try {
                        ffmpegCtrl.convertWavToAac(normalLoopPath,aacLoopPath, new ShellUtils.ShellCallback(){

                            @Override
                            public void shellOut(String shellLine) { }

                            @Override
                            public void processComplete(int exitValue) {
                                new File(normalLoopPath).delete();

                                try { ffmpegCtrl.repeatLoop(rootDirectory,loopPath+".aac",loopLoop,10); }
                                catch (Exception e) { e.printStackTrace(); }

                                new File(aacLoopPath).delete();

                                Thread joinVideoAndAudio = new JoinVideo(ffmpegCtrl, videoPathWithoutExt+"_full.mp4.h264", videoPathWithoutExt+"_export.mp4", rootDirectory+loopLoop,new ListenerVideo(){

                                    @Override
                                    public void onThreadEnd() {
                                        new File(rootDirectory+loopLoop).delete();
                                        Log.d(debugTag, "END MERGE with " + videoPathWithoutExt + "_export.mp4");

                                        try {
                                            AppUtils.copy(new File(videoPathWithoutExt+"_export.mp4"), new File(videoPathWithoutExt + ".mp4"));
                                            new File(videoPathWithoutExt+"_export.mp4").delete();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                joinVideoAndAudio.start();
                            }
                        });
                    }
                    catch (Exception e) {  e.printStackTrace(); }
                }


            }
            else
                Toast.makeText(getActivity(), "Encoding, please wait ...", Toast.LENGTH_LONG).show();
        }
    };

    public boolean installFromRaw(Context context, int rawId, String folder, String filename) throws IOException, FileNotFoundException
    {
        InputStream is;
        File outFile;

        is = context.getResources().openRawResource(rawId);
        outFile = new File(folder, filename);
        streamToFile(is, outFile, false, false, "700");

		/*is = context.getResources().openRawResource(R.raw.sox);
		outFile = new File(installFolder, "sox");
		streamToFile(is, outFile, false, false, "700");*/

        return true;
    }

    private final static int FILE_WRITE_BUFFER_SIZE = 32256;
    /*
     * Write the inputstream contents to the file
     */
    private static boolean streamToFile(InputStream stm, File outFile, boolean append, boolean zip, String mode) throws IOException {
        byte[] buffer = new byte[FILE_WRITE_BUFFER_SIZE];
        int bytecount;

        OutputStream stmOut = new FileOutputStream(outFile, append);

        if (zip)  {
            ZipInputStream zis = new ZipInputStream(stm);
            ZipEntry ze = zis.getNextEntry();
            stm = zis;
        }

        while ((bytecount = stm.read(buffer)) > 0) {
            stmOut.write(buffer, 0, bytecount);

        }

        stmOut.close();
        stm.close();

        Runtime.getRuntime().exec("chmod "+mode+" "+outFile.getCanonicalPath());


        return true;

    }
}
