package com.itbox.forgetprice.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;

@Table(name = "goods", id = Goods.ID)
public class Goods extends Model implements Parcelable {

	public static final String ID = "_id";
	public static final String GOODS_CODE = "_scancode";
	public static final String GOODS_NAME = "_goodsname";
	public static final String GOODS_PRICE = "_goodsprice";

	@Column(name = Goods.GOODS_CODE)
	private String scancode;
	@Column(name = Goods.GOODS_NAME)
	private String name;
	@Column(name = Goods.GOODS_PRICE)
	private String price;
  
	public String getScancode() {
		return scancode;
	}

	public void setScancode(String scancode) {
		this.scancode = scancode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.scancode);
		dest.writeString(this.name);
		dest.writeString(this.price);
	}

	public static final Parcelable.Creator<Goods> CREATOR = new Creator<Goods>() {

		@Override
		public Goods[] newArray(int size) {
			return null;
		}

		@Override
		public Goods createFromParcel(Parcel source) {
			Goods goods = new Goods();
			goods.setScancode(source.readString());
			goods.setName(source.readString());
			goods.setPrice(source.readString());
			return goods;
		}
	};
}
