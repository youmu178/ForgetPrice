package com.itbox.forgetprice.dialog;

import com.itbox.forgetprice.EditGoodsActivity;
import com.itbox.forgetprice.R;
import com.itbox.forgetprice.bean.Goods;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowDetailDialog extends DialogFragment {
	
	private LinearLayout ll;
	private LinearLayout mLinearLayoutView;

	public static ShowDetailDialog newIntance(Goods goods) {
		ShowDetailDialog dialog = new ShowDetailDialog();
		Bundle args = new Bundle();
		args.putParcelable("goods", goods);
		dialog.setArguments(args);
		return dialog;
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(getActivity(), R.style.dialog);
		View view = View.inflate(getActivity(), R.layout.dialog_detail, null);
		dialog.setContentView(view);
		final Goods goods = getArguments().getParcelable("goods");
		ll = (LinearLayout) view.findViewById(R.id.main);
		mLinearLayoutView=(LinearLayout)view.findViewById(R.id.parentPanel);
		TextView mGoodsName = (TextView) view.findViewById(R.id.dialog_goods_name);
		TextView mGoodsPrice = (TextView) view.findViewById(R.id.dialog_goods_price);
		TextView mCancel = (TextView) view.findViewById(R.id.dialog_cancel);
		TextView mEdit = (TextView) view.findViewById(R.id.dialog_edit);
		mGoodsName.setText(goods.getName());
		mGoodsPrice.setText(goods.getPrice());
		dialog.setOnShowListener(new OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				// TODO Auto-generated method stub
//				mLinearLayoutView.setVisibility(View.VISIBLE);
				start(Effectstype.Sidefill);
			}
		});
		mCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		mEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), EditGoodsActivity.class);
				intent.putExtra("goodsBean", goods);
				intent.putExtra("isEdit", true);
				startActivity(intent);
				dialog.dismiss();
			}
		});
		return dialog;
	}
	
	  private void start(Effectstype type){
	        BaseEffects animator = type.getAnimator();
	        animator.setDuration(Math.abs(500));
	        animator.start(ll);
	    }
}
