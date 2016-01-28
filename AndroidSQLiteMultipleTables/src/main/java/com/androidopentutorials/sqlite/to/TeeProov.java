package com.androidopentutorials.sqlite.to;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class TeeProov implements Parcelable {

	private int id;
	private String name;
	private Date prooviKp;
	private double prooviNr;
	private String prVotuKoht;
	private String prMaterjal;
	private String prTooteKirjeldus;
	private int prKasPlaanKoostati;


	private TeeObjekt teeObjekt;

	public TeeProov() {
		super();
	}

	private TeeProov(Parcel in) {
		super();
		this.id = in.readInt();
		this.name = in.readString();
		this.prooviKp = new Date(in.readLong());
		this.prooviNr = in.readDouble();
		this.prVotuKoht = in.readString();
		this.prMaterjal = in.readString();
		this.prTooteKirjeldus = in.readString();
		//// TODO: 27.01.2016
		// booleani peab uurima
		//this.prKasPlaanKoostati =
		this.prKasPlaanKoostati = in.readInt();

		this.teeObjekt = in.readParcelable(TeeObjekt.class.getClassLoader());
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

	public Date getProoviKp() {
		return prooviKp;
	}

	public void setProoviKp(Date prooviKp) {
		this.prooviKp = prooviKp;
	}

	public double getProoviNr() {
		return prooviNr;
	}

	public void setProoviNr(double prooviNr) {
		this.prooviNr = prooviNr;
	}

	public String getPrVotuKoht() {
		return prVotuKoht;
	}

	public void setPrVotuKoht(String prVotuKoht) {
		this.prVotuKoht = prVotuKoht;
	}

	public String getPrMaterjal() {
		return prMaterjal;
	}

	public void setPrMaterjal(String prMaterjal) {
		this.prMaterjal = prMaterjal;
	}

	public String getPrTooteKirjeldus() {
		return prTooteKirjeldus;
	}

	public int getPrKasPlaanKoostati() {
		return prKasPlaanKoostati;
	}

	public void setPrKasPlaanKoostati(int prKasPlaanKoostati) {
		this.prKasPlaanKoostati = prKasPlaanKoostati;
	}

	public void setPrTooteKirjeldus(String prTooteKirjeldus) {
		this.prTooteKirjeldus = prTooteKirjeldus;
	}

	public TeeObjekt getTeeObjekt() {
		return teeObjekt;
	}

	public void setTeeObjekt(TeeObjekt teeObjekt) {
		this.teeObjekt = teeObjekt;
	}

	@Override
	public String toString() {
		return "TeeProov{" +
				"id=" + id +
				", name='" + name + '\'' +
				", prooviKp=" + prooviKp +
				", prooviNr=" + prooviNr +
				", prVotuKoht='" + prVotuKoht + '\'' +
				", prMaterjal='" + prMaterjal + '\'' +
				", prTooteKirjeldus='" + prTooteKirjeldus + '\'' +
				", prKasPlaanKoostati=" + prKasPlaanKoostati +
				", teeObjekt=" + teeObjekt +
				'}';
	}

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
		TeeProov other = (TeeProov) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(getId());
		parcel.writeString(getName());
		parcel.writeLong(getProoviKp().getTime());
		parcel.writeDouble(getProoviNr());
		parcel.writeString(getPrVotuKoht());
		parcel.writeString(getPrMaterjal());
		parcel.writeString(getPrTooteKirjeldus());
		parcel.writeInt(getPrKasPlaanKoostati());

		parcel.writeParcelable(getTeeObjekt(), flags);
	}

	public static final Parcelable.Creator<TeeProov> CREATOR = new Parcelable.Creator<TeeProov>() {
		public TeeProov createFromParcel(Parcel in) {
			return new TeeProov(in);
		}

		public TeeProov[] newArray(int size) {
			return new TeeProov[size];
		}
	};

}
