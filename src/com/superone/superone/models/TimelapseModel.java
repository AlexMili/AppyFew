package com.superone.superone.models;

public class TimelapseModel {

	public String getName() { return timelapsename; }
	public void setName(String name) { this.timelapsename = name; }

	public String getDescription() { return timelapsedescription; }
	public void setDescription(String description) { this.timelapsedescription = description; }
	
	public String getDate() { return timelapsedate; }
	public void setDate(String date) { this.timelapsedate = date; }

	public String getDuration() { return timelapseduration; }
	public void setDuration(String duration) { this.timelapseduration = duration; }
	
	public String getDelay() { return timelapsedelay; }
	public void setDelay(String delay) { this.timelapsedelay = delay; }
	
	public String getMainUrl() { return timelapseurlmain; }
	public void setMainUrl(String url) { this.timelapseurlmain = url; }
	
	public String getSlaveUrl() { return timelapseurlslave; }
	public void setSlaveUrl(String url) { this.timelapseurlslave = url; }
	
	public String getThumbnail() { return timelapseurlthumbnail; }
	public void setThumbnail(String thumb) { this.timelapseurlthumbnail = thumb; }
	
	public String getAlarm() { return timelapsealarm; }
	public void setAlarm(String alarm) { this.timelapsealarm = alarm; }
	
	public String getDisplayDate() { return timelapsedisplaydate; }
    public void setDisplayDate(String displaydate) { this.timelapsedisplaydate = displaydate; }

    public String getDisplayTime() { return timelapsedisplaytime; }
    public void setDisplayTime(String displaytime) { this.timelapsedisplaytime = displaytime; }

    public String getPublish() { return timelapsepublish; }
    public void setPublish(String publish) { this.timelapsepublish = publish; }

    public String getSoundStatus() { return timelapsesoundstatus; }
    public void setSoundStatus(String sound) { this.timelapsesoundstatus = sound; }

    public String getSample() { return timelapsesample; }
    public void setSample(String sample) { this.timelapsesample = sample; }

	public int id = -1;
	public String 
			idno = "", 
			timelapsename="",
			timelapsedescription="",
			timelapsedate="",
			timelapseduration="",
			timelapsedelay="",
			timelapseurlmain="",
			timelapseurlslave="",
			timelapseurlthumbnail="",
			timelapsealarm="",
			timelapsedisplaydate="",
			timelapsedisplaytime="",
			timelapsepublish="",
            timelapsesample="",
            timelapsesoundstatus=""
			;

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
