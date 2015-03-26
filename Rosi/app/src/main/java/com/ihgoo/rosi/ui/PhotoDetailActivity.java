package com.ihgoo.rosi.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihgoo.rosi.R;
import com.ihgoo.rosi.adapter.PagerAdapter;
import com.ihgoo.rosi.bean.ImageSimpleBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 图片详情页面
 */
public class PhotoDetailActivity extends Activity implements OnClickListener {
	
	@InjectView(R.id.adLayout)
	RelativeLayout adLayout;
	
	@InjectView(R.id.pager)
	ViewPager mViewPager;
	
	@InjectView(R.id.favorites)
	TextView favorites;
	
	@InjectView(R.id.share)
	TextView share;
	
	@InjectView(R.id.chat)
	TextView chat;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		List<ImageSimpleBean> list = (List<ImageSimpleBean>) getIntent().getSerializableExtra("info");
		String url = getIntent().getStringExtra("url");
		setContentView(R.layout.layout_detail);
		ButterKnife.inject(this);
		mViewPager.setAdapter(new PagerAdapter(getApplicationContext(),list,getLayoutInflater()));
		share.setOnClickListener(this);
		favorites.setOnClickListener(this);
		chat.setOnClickListener(this);
	}
	

//	protected UMSocialService initAcitonBar(Context context,String url
//			) {
//		UMSocialService controller = UMServiceFactory
//				.getUMSocialService(url);

//		if (!controller.getEntity().mInitialized) {
//			controller.setShareContent(url);
//			controller.initEntity(context, new SocializeClientListener() {
//				@Override
//				public void onStart() {
//				}
//
//				@Override
//				public void onComplete(int status, SocializeEntity entity) {
////					matchEntity(mPair, entity);
//					favorites.setText("喜欢：  " + entity.getLikeCount());
//					chat.setText("评论:  " + entity.getCommentCount());
//					share.setText("分享:  " + entity.getShareCount());
//				}
//
//			});
//		} else {
//			
//			favorites.setText("喜欢：  " + controller.getEntity().getLikeCount());
//			chat.setText("评论:  " + controller.getEntity().getCommentCount());
//			share.setText("分享:  " + controller.getEntity().getShareCount());
//		}
//		return controller;
//	}
	
	
	public void onResume() {
		super.onResume();
	}
	
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void onClick(View v) {
//		int id = v.getId();
//	
//		switch (id) {
//		case R.id.favorites:
//			
//
//			mController.likeChange(this,
//					new SocializeClientListener() {
//						@Override
//						public void onStart() {
//						}
//
//						@Override
//						public void onComplete(int status,
//								SocializeEntity entity) {
//							if (entity != null) {
//								LIKESTATUS likestatus  = 
//										 entity.getLikeStatus();
//								
//								if (likestatus == LIKESTATUS.LIKE) {
//									Toast.makeText(getApplicationContext(), "收藏成功", 100).show();
//								}else if(likestatus == LIKESTATUS.UNLIKE){
//									Toast.makeText(getApplicationContext(), "取消收藏", 100).show();
//								}
//								
//								
//								
//							}
//						}
//					});
//
//			
//			break;
//		case R.id.share:
//			mController.openShare(this, false);
//			break;
//			
//			
//		case R.id.chat:
//			mController.openComment(this, false);
//			break;
//		}
//	}
	
}
