package com.itbox.forgetprice.adapter;

import com.itbox.forgetprice.R;
import com.itbox.forgetprice.bean.Goods;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GoodsAdapter extends CursorAdapter {
	private Context mContext;
	
	public GoodsAdapter(Context context, Cursor c) {
		super(context, c, true);
		mContext = context;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        Goods goods = new Goods();
        goods.loadFromCursor(cursor);
        holder.mGoodsName.setText(goods.getName());
        holder.mGoodsPrice.setText(goods.getPrice());
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.layout_item_goods, null);
		new ViewHolder(view);
		return view;
	}
	
	class ViewHolder {
		private TextView mGoodsPrice;
		private TextView mGoodsName;

		public ViewHolder(View view) {
			mGoodsName = (TextView) view.findViewById(R.id.goods_name);
			mGoodsPrice = (TextView) view.findViewById(R.id.goods_price);
			view.setTag(this);
		}
		
	}

}
