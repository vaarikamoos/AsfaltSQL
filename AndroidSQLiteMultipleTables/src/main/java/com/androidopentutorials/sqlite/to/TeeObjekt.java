package com.androidopentutorials.sqlite.to;

import android.os.Parcel;
import android.os.Parcelable;

public class TeeObjekt implements Parcelable {
	private int id;
	private String name;
	private String objektiNimetus;
	private String teeNimetus;
	private double teeNr;
	private double algusKm;
	private double loppKm;


	public TeeObjekt() {
		super();
	}

	public TeeObjekt(int id, String name) {
		super();
		this.id = id;
		this.name = name;

	}

	public TeeObjekt(String name) {
		this.name = name;
	}

	private TeeObjekt(Parcel in) {
		super();
		this.id = in.readInt();
		this.name = in.readString();
		this.objektiNimetus = in.readString();
		this.teeNimetus = in.readString();
		this.teeNr = in.readDouble();
		this.algusKm = in.readDouble();
		this.loppKm = in.readDouble();

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjektiNimetus() {
		return objektiNimetus;
	}

	public void setObjektiNimetus(String objektiNimetus) {
		this.objektiNimetus = objektiNimetus;
	}

	public String getTeeNimetus() {
		return teeNimetus;
	}

	public void setTeeNimetus(String teeNimetus) {
		this.teeNimetus = teeNimetus;
	}

	public double getTeeNr() {
		return teeNr;
	}

	public void setTeeNr(double teeNr) {
		this.teeNr = teeNr;
	}

	public double getAlgusKm() {
		return algusKm;
	}

	public void setAlgusKm(double algusKm) {
		this.algusKm = algusKm;
	}

	public double getLoppKm() {
		return loppKm;
	}

	public void setLoppKm(double loppKm) {
		this.loppKm = loppKm;
	}

	@Override
	public String toString() {
		return "TeeObjekt{" +
				"id=" + id +
				", name='" + name + '\'' +
				", objektiNimetus='" + objektiNimetus + '\'' +
				", teeNimetus='" + teeNimetus + '\'' +
				", teeNr=" + teeNr +
				", algusKm=" + algusKm +
				", loppKm=" + loppKm +
				'}';
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(getId());
		parcel.writeString(getName());
		parcel.writeString(getObjektiNimetus());
		parcel.writeString(getTeeNimetus());
		parcel.writeDouble(getTeeNr());
		parcel.writeDouble(getAlgusKm());
		parcel.writeDouble(getLoppKm());
	}

	public static final Parcelable.Creator<TeeObjekt> CREATOR = new Parcelable.Creator<TeeObjekt>() {
		public TeeObjekt createFromParcel(Parcel in) {
			return new TeeObjekt(in);
		}

		public TeeObjekt[] newArray(int size) {
			return new TeeObjekt[size];
		}
	};

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeeObjekt other = (TeeObjekt) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
