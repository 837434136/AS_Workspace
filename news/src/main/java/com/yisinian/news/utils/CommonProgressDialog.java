package com.yisinian.news.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yisinian.news.R;


public class CommonProgressDialog extends Activity{
	
	private ProgressBar progressBar;
	private TextView tvMessage;
	private Context context;
	private LayoutInflater inflater;
	public static final String FINISH_PROGRESS_DIALOG = "finish_progress_dialog";
//	private FinishProgressReceive finishProgressReceive;
	private static final String TAG = "CommonProgressDialog";
	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.common_progress_dialog_activity);
//		tvMessage = (TextView) findViewById(R.id.common_tv_progress_dialog);
////		progressBar = (progressBar) findViewById(R.id.)
//		String messageString = getIntent().getStringExtra(Constant.PROGRESS_DIALOG_MESSAGE);
//		tvMessage.setText(messageString);
//		finishProgressReceive = new FinishProgressReceive();
//		IntentFilter intentFilter = new IntentFilter(FINISH_PROGRESS_DIALOG);
//		registerReceiver(finishProgressReceive, intentFilter);
//	}
////	public void setMessage(String message){
////		
////	}
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		finish();
//		return true;
//	}
//	
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		unregisterReceiver(finishProgressReceive);
//	}
//	
//	class FinishProgressReceive extends BroadcastReceiver{
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			// TODO Auto-generated method stub
//			Log.w(TAG, "finish_commonprogressDialog");
//			finish();
//		}
//		
//	}
	
	/** 
     * 得到自定义的progressDialog 
     * @param context 
     * @param msg 
	 * @return 
     * @return 
     */  
    public static Dialog createLoadingDialog(Context context, String msg) {  
  
        LayoutInflater inflater = LayoutInflater.from(context);  
        View v = inflater.inflate(R.layout.common_progress_dialog_activity, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.common_ll_progress_view);// 加载布局  
        // main.xml中的ImageView  
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.common_progress_dialog);  
        TextView tipTextView = (TextView) v.findViewById(R.id.common_tv_progress_dialog);// 提示文字  
//        // 加载动画  
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.progress_loading_anim);  
//         使用ImageView显示动画  
        
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);  
        tipTextView.setText(msg);// 设置加载信息  
  
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog  
  
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(  
                LinearLayout.LayoutParams.FILL_PARENT,  
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局  
        return loadingDialog;
  
    }
}
