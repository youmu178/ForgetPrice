package com.itbox.forgetprice;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.itbox.forgetprice.bean.Goods;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditGoodsActivity extends FragmentActivity {

	private String code;
	private EditText mGoodsName;
	private EditText mGoodsPrice;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_edit_goods);
		Bundle extras = getIntent().getExtras();
		boolean isEdit = extras.getBoolean("isEdit");
		code = extras.getString("code");
		Goods goods = extras.getParcelable("goodsBean");
		TextView mEditTitle = (TextView) findViewById(R.id.edit_title);
		if (isEdit) {
			mEditTitle.setText("商品编辑");
		} else {
			mEditTitle.setText("商品录入");
		}
		TextView mSave = (TextView) findViewById(R.id.edit_save);
		mGoodsName = (EditText) findViewById(R.id.edit_name);
		mGoodsPrice = (EditText) findViewById(R.id.edit_price);
		if (null != goods) {
			if (!TextUtils.isEmpty(goods.getName())) {
				mGoodsName.setText(goods.getName());
				mGoodsName.setSelection(goods.getName().length());
			}
			if (!TextUtils.isEmpty(goods.getPrice())) {
				mGoodsPrice.setText(goods.getPrice());
				mGoodsPrice.setSelection(goods.getPrice().length());
			}
			if (!TextUtils.isEmpty(goods.getScancode())) {
				code = goods.getScancode();
			}
		}
		
		mSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = mGoodsName.getText().toString();
				String price = mGoodsPrice.getText().toString();
				if (TextUtils.isEmpty(name)) {
					Toast.makeText(EditGoodsActivity.this, "名称为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if (TextUtils.isEmpty(price)) {
					Toast.makeText(EditGoodsActivity.this, "价格为空", Toast.LENGTH_SHORT).show();
					return;
				}
				Goods goodsBean = new Select().from(Goods.class).where(Goods.GOODS_CODE + "= ?", code).executeSingle();
				if (goodsBean == null) {
					Goods goods = new Goods();
					goods.setScancode(code);
					goods.setName(name);
					goods.setPrice(price);
					goods.save();
				} else {
					new Update(Goods.class).set(Goods.GOODS_NAME +" = ?," + Goods.GOODS_PRICE + "= ?", name ,price).where(Goods.GOODS_CODE + "= ?", code).execute();
				
//					sendBroadcast(new Intent("updataGoods"));
				}
				finish();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
