package com.superone.superone;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

public class old_Video_DONT_DELETE extends Thread{
	
	private ListenerVideo listener;
	
	private ArrayList<String> fileList = new ArrayList<String>();
	private String outputFile = "";
	
	public old_Video_DONT_DELETE(ArrayList<String> files, String out, ListenerVideo listener) {
		this.fileList = files;
		this.outputFile = out;
		this.listener = listener;
	}
	
	/*public void add(String file) {
		this.fileList.add(file);
	}*/
	
	public void run() {
        ArrayList<Movie> inMovies = new ArrayList<Movie>();
        
        int size = fileList.size();
        
        for(int i=0;i<size;i++) {
        	try {
				inMovies.add(MovieCreator.build(new FileInputStream((String)fileList.get(i)).getChannel()));
			}
        	catch (FileNotFoundException e) { e.printStackTrace(); }
        	catch (IOException e) { e.printStackTrace(); }
        }
        

        List<Track> videoTracks = new LinkedList<Track>();
        List<Track> audioTracks = new LinkedList<Track>();
        
        size = inMovies.size();
        
        for(int i=0;i<size;i++) {
        	for (Track t : ((Movie) inMovies.get(i)).getTracks()) {
                if (t.getHandler().equals("soun")) {
                    audioTracks.add(t);
                }
                if (t.getHandler().equals("vide")) {
                    videoTracks.add(t);
                }
            }
        }
        
        Movie result = new Movie();

        if (audioTracks.size() > 0) {
            try {
				result.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
			} catch (IOException e) { e.printStackTrace(); }
        }
        if (videoTracks.size() > 0) {
            try {
				result.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
			} catch (IOException e) { e.printStackTrace(); }
        }

        Container out = new DefaultMp4Builder().build(result);

        FileChannel fc = null;
		try {
			fc = new RandomAccessFile(String.format(outputFile), "rw").getChannel();
		} catch (FileNotFoundException e) { e.printStackTrace(); }
        try {
			out.writeContainer(fc);
		} catch (IOException e) { e.printStackTrace(); }
        try {
			fc.close();
			if (listener != null) {
				listener.onThreadEnd();
			}
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public interface ListenerVideo {
		public void onThreadEnd();
	}
}
