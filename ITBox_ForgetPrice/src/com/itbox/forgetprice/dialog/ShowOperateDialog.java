package com.itbox.forgetprice.dialog;

import com.activeandroid.query.Delete;
import com.itbox.forgetprice.EditGoodsActivity;
import com.itbox.forgetprice.R;
import com.itbox.forgetprice.bean.Goods;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnShowListener;
import android.graphics.BitmapShader;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowOperateDialog extends DialogFragment implements OnClickListener{
	
	private Dialog dialog;
	private LinearLayout ll;

	public static ShowOperateDialog newIntance(Goods goods) {
		ShowOperateDialog dialog = new ShowOperateDialog();
		Bundle args = new Bundle();
		args.putParcelable("goods", goods);
		dialog.setArguments(args);
		return dialog;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dialog = new Dialog(getActivity(), R.style.dialog);
		View view = View.inflate(getActivity(), R.layout.dialog_operate, null);
		dialog.setContentView(view);
		final Goods goods = getArguments().getParcelable("goods");
		ll = (LinearLayout) view.findViewById(R.id.opera);
		TextView mOperateEdit = (TextView) view.findViewById(R.id.dialog_operate_edit);
		TextView mOperateDel = (TextView) view.findViewById(R.id.dialog_operate_del);
	dialog.setOnShowListener(new OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				// TODO Auto-generated method stub
//				mLinearLayoutView.setVisibility(View.VISIBLE);
				start(Effectstype.Newspager);
			}
		});
		mOperateEdit.setOnClickListener(this);
		mOperateEdit.setTag(goods);
		mOperateDel.setOnClickListener(this);
		mOperateDel.setTag(goods);
		return dialog;
	}
	 private void start(Effectstype type){
	        BaseEffects animator = type.getAnimator();
	        animator.setDuration(Math.abs(500));
	        animator.start(ll);
	    }
	@Override
	public void onClick(View v) {
		Goods goods = (Goods) v.getTag();
		Intent intent = null;
		switch (v.getId()) {
		case R.id.dialog_operate_edit:
			intent = new Intent(getActivity(), EditGoodsActivity.class);
			intent.putExtra("goodsBean", goods);
			intent.putExtra("isEdit", true);
			startActivity(intent);
			dialog.dismiss();
			break;
		case R.id.dialog_operate_del:
			goods.delete();
//			new Delete().from(Goods.class).where(Goods.GOODS_CODE + "= ?", goods.getScancode()).execute();
			dialog.dismiss();
			break;

		default:
			break;
		}
	}

    
}
