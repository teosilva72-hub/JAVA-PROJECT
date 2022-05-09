package br.com.tracevia.webapp.model.sat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Maintenance {
	
	private String data;
	private String hora;
	private int id;
	private int siteId;
	private int status;
	private int volume;
	private int lane;
	private int numberLanes;
	private int[][] laneZero;
	private int[][] laneFifteen;
	private int[][] laneThirty;
	private int[][] laneFortyFive;
	private int[] statusZero;
	private int[] statusFifteen;
	private int[] statusThirty;
	private int[] statusFortyFive;
				
	public Maintenance(String data, String hora, int id, int siteId, int status, int volume, int lane,
			int numberLanes, int[][] laneZero, int[][] laneFifteen, int[][] laneThirty, int[][] laneFortyFive,
			int[] statusZero, int[] statusFifteen, int[] statusThirty, int[] statusFortyFive) {
		
		this.data = data;
		this.hora = hora;
		this.id = id;
		this.siteId = siteId;
		this.status = status;
		this.volume = volume;
		this.lane = lane;
		this.numberLanes = numberLanes;
		this.laneZero = laneZero;
		this.laneFifteen = laneFifteen;
		this.laneThirty = laneThirty;
		this.laneFortyFive = laneFortyFive;
		this.statusZero = statusZero;
		this.statusFifteen = statusFifteen;
		this.statusThirty = statusThirty;
		this.statusFortyFive = statusFortyFive;
	}

	public Maintenance(){}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getLane() {
		return lane;
	}

	public void setLane(int lane) {
		this.lane = lane;
	}

	public int getNumberLanes() {
		return numberLanes;
	}

	public void setNumberLanes(int numberLanes) {
		this.numberLanes = numberLanes;
	}

	public int[][] getLaneZero() {
		return laneZero;
	}

	public void setLaneZero(int[][] laneZero) {
		this.laneZero = laneZero;
	}

	public int[][] getLaneFifteen() {
		return laneFifteen;
	}

	public void setLaneFifteen(int[][] laneFifteen) {
		this.laneFifteen = laneFifteen;
	}

	public int[][] getLaneThirty() {
		return laneThirty;
	}

	public void setLaneThirty(int[][] laneThirty) {
		this.laneThirty = laneThirty;
	}

	public int[][] getLaneFortyFive() {
		return laneFortyFive;
	}

	public void setLaneFortyFive(int[][] laneFortyFive) {
		this.laneFortyFive = laneFortyFive;
	}

	public int[] getStatusZero() {
		return statusZero;
	}

	public void setStatusZero(int[] statusZero) {
		this.statusZero = statusZero;
	}

	public int[] getStatusFifteen() {
		return statusFifteen;
	}

	public void setStatusFifteen(int[] statusFifteen) {
		this.statusFifteen = statusFifteen;
	}

	public int[] getStatusThirty() {
		return statusThirty;
	}

	public void setStatusThirty(int[] statusThirty) {
		this.statusThirty = statusThirty;
	}

	public int[] getStatusFortyFive() {
		return statusFortyFive;
	}

	public void setStatusFortyFive(int[] statusFortyFive) {
		this.statusFortyFive = statusFortyFive;
	}

	public boolean isOnline() {
		Calendar now = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		now.add(Calendar.MINUTE, -30);

		try {
			return now.getTime().before(date.parse(String.format("%s %s", data, hora)))
				&& (statusZero[23] | statusFifteen[23] | statusThirty[23] | statusFortyFive[22]) == 1;
		} catch (ParseException e) {
			return false;
		}
	}
		
}
