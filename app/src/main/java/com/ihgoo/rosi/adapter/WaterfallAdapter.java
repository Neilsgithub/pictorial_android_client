package com.ihgoo.rosi.adapter;


import java.io.File;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.ihgoo.rosi.MainActivity;
import com.ihgoo.rosi.R;
import com.ihgoo.rosi.bean.ImageBean;
import com.ihgoo.rosi.bean.ImageListBean;
import com.ihgoo.rosi.bean.SettingHelper;
import com.ihgoo.rosi.net.ImageLoaderHelper;
import com.ihgoo.rosi.ui.ContentFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.DiscCacheUtil;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.MemoryCacheUtil;


public class WaterfallAdapter extends ArrayAdapter<ImageBean> {
	private OnBottonListener mOnBottonListener;
	private List<ImageBean> list;
	private Context mContext;
	private Drawable drawable;
	private int mScreenWidth;
	private SparseArray<ImageListBean> mCache = new SparseArray();
	private boolean cacheFound;
	protected ImageLoader imageLoader = ImageLoader.getInstance(); 
	
	/**
	 * 监听是否到了底部
	 * @author KelvinHu
	 *
	 */
	public interface OnBottonListener {  
		void loadMore();  
	}  
	
	// 接口实例化
	public void setOnBottonListener(OnBottonListener onBottonListener){
		mOnBottonListener = onBottonListener;
	}
	
	public WaterfallAdapter(Context context, int resource, List<ImageBean> list) {
		super(context, resource, list);
		this.list = list;
		this.mContext = context;
		mScreenWidth=((MainActivity)mContext).screenWidth;
		drawable = context.getResources().getDrawable(R.drawable.load_default);
	}


	
	


	@Override
	public int getCount() {
		return list != null ? list.size() : 0;
	}


	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	
	
	@Override
	public View getView(final int position, View view, ViewGroup group) {
		final Holder holder;
		
		// 下拉刷新回调接口监听事件
		if (mOnBottonListener != null) {  
			int addPosition =0;
			if (SettingHelper.getInstance().isPrfetch()) {
				addPosition = position+9;
			}else {
				addPosition = position+2;
			}
			
			if (list!=null && list.size() < addPosition) {
				Log.i("WaterfallAdapter", "position"+position);
				mOnBottonListener.loadMore();
			}
		}
		
		final ImageBean imageBean = list.get(position);
		
		
		// 得到View
		if (view == null) {
			holder = new Holder();
			LayoutInflater inflater = LayoutInflater.from(mContext);
			view = inflater.inflate(R.layout.image_item, null);
			holder.ivIcon = (ImageView) view.findViewById(R.id.row_icon);
			holder.pbLoad = (ProgressBar) view.findViewById(R.id.pb_load);
			view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}
		
		
		
		
		
		
		

		
		holder.ivIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				ContentFragment fragment = new ContentFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("mode", ContentFragment.MODE_IMAGEDETAILLIST);
				bundle.putString("url", imageBean.getDetailurl());
				fragment.setArguments(bundle);
				
				
				((MainActivity)mContext).addFragment(fragment);
				
				
			}
		});
		
		
		if (!imageBean.getImgurl().equals(holder.url)) {
			imageLoader.displayImage(imageBean.getImgurl(), holder.ivIcon,ImageLoaderHelper.getInstance().getDefaultOptions(), new ImageLoadingListener(){
				
		
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					
		        	// 这儿初先初始化出来image所占的位置的大小，先把瀑布流固定住，这样瀑布流就不会因为图片加载出来后大小变化了
					LayoutParams lp = (LayoutParams) holder.ivIcon.getLayoutParams();
		
					// 屏幕适配缩放比例
					float scale = mScreenWidth/2/Integer.parseInt(imageBean.getWidth());
					lp.width = mScreenWidth/2;
					lp.height = (int) (Integer.parseInt(imageBean.getHeight())*scale);
					holder.ivIcon.setImageDrawable(drawable);
					holder.pbLoad.setVisibility(View.VISIBLE);
					
				}
		
				@Override
				public void onLoadingFailed(String imageUri, View view,
						FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {
					case IO_ERROR:
						message = "Input/Output error";
						break;
					case DECODING_ERROR:
						message = "can not be decoding";
						break;
					case NETWORK_DENIED:
						message = "Downloads are denied";
						break;
					case OUT_OF_MEMORY:
						message = "内存不足";
						Toast.makeText(mContext, message, Toast.LENGTH_SHORT)
								.show();
						break;
					case UNKNOWN:
						message = "Unknown error";
						Toast.makeText(mContext, message, Toast.LENGTH_SHORT)
								.show();
						break;
					}
					holder.pbLoad.setVisibility(View.GONE);
				}
		
				@Override
				public void onLoadingComplete(String imageUri, View view,
						Bitmap loadedImage) {
					if (!imageUri.equals(holder.url)) {
						holder.pbLoad.setVisibility(View.GONE);
						holder.url = imageUri;
					}
					
					
				}
				
				
				
		
				@Override
				public void onLoadingCancelled(String paramString,
						View paramView) {
				}
			});
		}
		
		return view;
	}



}

class Holder {
	public ImageView ivIcon;
	public ProgressBar pbLoad;
	public String url;
}