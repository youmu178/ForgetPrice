package com.itbox.forgetprice;

import java.util.List;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.content.ContentProvider;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.itbox.forgetprice.adapter.GoodsAdapter;
import com.itbox.forgetprice.bean.Goods;
import com.itbox.forgetprice.dialog.ShowDetailDialog;
import com.itbox.forgetprice.dialog.ShowOperateDialog;
import com.itbox.forgetprice.widget.FloatingActionButton;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.zxing.activity.CaptureActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		UmengUpdateAgent.update(this);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
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

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	//
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	// if (id == R.id.action_settings) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnClickListener, OnItemLongClickListener, LoaderCallbacks<Cursor> {

		// private Fab mFab;
		private ListView mGoodsLV;
		private GoodsAdapter goodsAdapter;

		public PlaceholderFragment() {
		}
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//        	super.onCreate(savedInstanceState);
//        	IntentFilter filter = new IntentFilter();
//        	filter.addAction("updataGoods");
//        	AppContext.getInstance().registerReceiver(receiver, filter);
//        }
//        
//        private BroadcastReceiver receiver = new BroadcastReceiver() {
//			
//			@Override
//			public void onReceive(Context context, Intent intent) {
//				// TODO Auto-generated method stub
//			    getActivity().getSupportLoaderManager().restartLoader(0, null, PlaceholderFragment.this);
//			}
//		};
//		
//		@Override
//		public void onDestroy() {
//			super.onDestroy();
//			AppContext.getInstance().unregisterReceiver(receiver);
//		}
		
		@Override
		public void onResume() {
			super.onResume();
			MobclickAgent.onPageStart("MainScreen");
		}

		@Override
		public void onPause() {
			super.onPause();
			MobclickAgent.onPageEnd("MainScreen");
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			mGoodsLV = (ListView) rootView.findViewById(R.id.goods_lv);
			TextView mEmpty = (TextView) rootView.findViewById(R.id.empty);
			// mFab = (Fab) rootView.findViewById(R.id.scan);
			mGoodsLV.setEmptyView(mEmpty);
			goodsAdapter = new GoodsAdapter(getActivity(), null);
			mGoodsLV.setAdapter(goodsAdapter);
			// int holoPurple = getResources().getColor(R.color.holo_purple);
			// mFab.setFabColor(holoPurple);
			// mFab.setOnClickListener(this);
			FloatingActionButton floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.button_floating_action);
			floatingActionButton.attachToListView(mGoodsLV);
			floatingActionButton.setOnClickListener(this);
			mGoodsLV.setOnItemLongClickListener(this);
			getActivity().getSupportLoaderManager().initLoader(0, null, this);
			return rootView;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent openCameraIntent = new Intent(getActivity(), CaptureActivity.class);
			startActivityForResult(openCameraIntent, 0);
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			if (resultCode == RESULT_OK) {
				switch (requestCode) {
				case 0:
					Bundle bundle = data.getExtras();
					String scanResult = bundle.getString("result");
					if (TextUtils.isEmpty(scanResult)) {
						Toast.makeText(getActivity(), "扫码没有获取值", Toast.LENGTH_SHORT).show();
					} else {
						// Toast.makeText(getActivity(), scanResult,
						// Toast.LENGTH_SHORT).show();
						selectGoods(scanResult);
					}
					break;

				default:
					break;
				}
			}
			super.onActivityResult(requestCode, resultCode, data);
		}

		@Override
		public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
			return new CursorLoader(getActivity(), ContentProvider.createUri(Goods.class, null), null, null, null, null);
		}

		@Override
		public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
			Log.i("youzh", cursor + "");
			if (cursor != null) {
				goodsAdapter.swapCursor(cursor);
			}
		}

		@Override
		public void onLoaderReset(Loader<Cursor> arg0) {
			goodsAdapter.swapCursor(null);
		}

		/**
		 * 查询表中是否有这个商品，有的话直接显示，没有话填写编辑
		 * 
		 * @param code
		 */
		private void selectGoods(String code) {
			Goods goods = new Select().from(Goods.class).where(Goods.GOODS_CODE + "= ?", code).executeSingle();
			if (goods == null) {
				Intent intent = new Intent(getActivity(), EditGoodsActivity.class);
				intent.putExtra("code", code);
				intent.putExtra("idEdit", false);
				startActivity(intent);
			} else {
				ShowDetailDialog dialog = ShowDetailDialog.newIntance(goods);
				dialog.show(getFragmentManager(), "showdetail");
			}
		}

		public void saveData(List<Goods> list) {
			try {
				ActiveAndroid.beginTransaction();
				try {
					new Delete().from(Goods.class).execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (list != null) {
					for (Goods er : list) {
						er.save();
					}
				}
				ActiveAndroid.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				ActiveAndroid.endTransaction();
			}
		}

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			Object item = goodsAdapter.getItem(position);
			if (item != null) {
				Goods goods = new Goods();
				goods.loadFromCursor((Cursor) item);
				ShowOperateDialog dialog = ShowOperateDialog.newIntance(goods);
				dialog.show(getFragmentManager(), "showoperate");
			}
			return false;
		}
	}

}
