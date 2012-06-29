package com.wfp.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wfp.config.PathConfig;
import com.wfp.customcontrols.CameraPreview;
import com.wfp.util.FunctionUtil;

public class CameraActivity extends Activity {
    
	private Camera mCamera; 
    private CameraPreview mPreview; 
    private MediaRecorder mMediaRecorder; 
    private ImageButton captureButton;

    //参数
    private static final int MEDIA_TYPE_IMAGE = 1; 
    private static final int MEDIA_TYPE_VIDEO = 2; 
    private static int CHOOSE_MARKET = 12;
    private static Context context;
    private static String lsh;
    private static String id;
    private static String saveFilePath;
    private static String serialNO;
    private static String marketName;
    private static String marketlsh;
    
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
    	Log.i("onCreate", "onCreate");
        super.onCreate(savedInstanceState); 
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.camera); 
        //界面元素初始化
        captureButton = (ImageButton) findViewById(R.id.button_capture); 
        
        //初始化数据
        Intent intent = getIntent();
        lsh = intent.getStringExtra("lsh");
        id = intent.getStringExtra("id");
        serialNO = intent.getStringExtra("serialNO");
        String marketInfo = intent.getStringExtra("marketInfo");
        if(marketInfo != null){
	        marketName = marketInfo.split(";")[0];
	        marketlsh = marketInfo.split(";")[1];
        }
        
        if(marketlsh != null)
        	saveFilePath = PathConfig.uploadImgPath + PathConfig.formImgPath + "/common";
        else {
	        if(lsh == null)
	        	saveFilePath = PathConfig.uploadImgPath + PathConfig.formImgPath + "/" + id + "/" + serialNO;
	        else
	        	saveFilePath = PathConfig.uploadImgPath + "/" + lsh;
        }

        // 创建Camera实例 
        mCamera = getCameraInstance(); 
        // 创建Preview view并将其设为activity中的内容
        mPreview = new CameraPreview(this, mCamera); 
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview); 
        preview.addView(mPreview); 
        
        
     // 在Capture按钮中加入listener 

            captureButton.setOnClickListener( 
                new View.OnClickListener() { 
                @Override 
                public void onClick(View v) { 
                	Log.i("captureButton", "captureButton");
                	//mCamera.autoFocus(null);//自动对焦
                    // 从摄像头获取图片
                    mCamera.takePicture(null, null, mPicture); 
                    captureButton.setVisibility(View.GONE);
                    

                } 
            } 
        );


    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	// TODO Auto-generated method stub
    	mCamera.startPreview();
    	captureButton.setVisibility(View.VISIBLE);
    	return true;
    	
    }
    
    /** 安全获取Camera对象实例的方法*/ 

    public static Camera getCameraInstance(){ 

        Camera c = null; 
        try { 
            c = Camera.open(); // 试图获取Camera实例
        }catch (Exception e){ 
            // 摄像头不可用（正被占用或不存在）
        } 
        return c; // 不可用则返回null

    }

    private PictureCallback mPicture = new PictureCallback() { 
        @Override 

        public void onPictureTaken(byte[] data, Camera camera) { 
        	
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE); 
            if (pictureFile == null){ 
                Log.d("tag", "创建媒体文件时出错，检查存储权限"); 
                return; 
            } 

            try { 

                FileOutputStream fos = new FileOutputStream(pictureFile); 
                fos.write(data); 
                fos.close(); 
            } catch (FileNotFoundException e) { 

                Log.d("tag", "File not found: " + e.getMessage()); 
            } catch (IOException e) { 

                Log.d("tag", "Error accessing file: " + e.getMessage()); 
            } 
        } 
    };

    /** 为保存图片或视频创建文件Uri */ 

    private static Uri getOutputMediaFileUri(int type){ 

          return Uri.fromFile(getOutputMediaFile(type)); 

    } 

     

    /** 为保存图片或视频创建File */ 

    private static File getOutputMediaFile(int type){ 

        // 安全起见，在使用前应该

        // 用Environment.getExternalStorageState()检查SD卡是否已装入
    	String SDCardPath = null;
    	if(FunctionUtil.getSDPath() == null) {
    		Toast.makeText(context, context.getResources().getString(R.string.noSDCard), Toast.LENGTH_LONG).show();
    		return null;
    	}else	
    		SDCardPath = FunctionUtil.getSDPath();
    	
        File mediaStorageDir = new File(SDCardPath + PathConfig.appSDCardPath, saveFilePath); 

    // 如果期望图片在应用程序卸载后还存在、且能被其它应用程序共享，

    // 则此保存位置最合适
    

        // 如果不存在的话，则创建存储目录

        if (! mediaStorageDir.exists()){ 
            if (! mediaStorageDir.mkdirs()){ 

                Log.d("MyCameraApp", "failed to create directory"); 
                return null; 
            } 

        } 

        // 创建媒体文件名

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); 

        File mediaFile; 

        if (type == MEDIA_TYPE_IMAGE){ 
        	String tempid = "";
//        	if(marketlsh != null)
//        		tempid = marketlsh;
//        	else {
	        	if(lsh == null)
	        		tempid = serialNO;
	        	else
	        		tempid = lsh;
//        	}

            mediaFile = new File(mediaStorageDir.getPath() + File.separator + 
            		
            "IMG&" + tempid + "&" + timeStamp + ".jpg"); 
            //Toast.makeText(context, tempid, Toast.LENGTH_SHORT).show();

        } else if(type == MEDIA_TYPE_VIDEO) { 

            mediaFile = new File(mediaStorageDir.getPath() + File.separator + 

            "VID_" + lsh + "_" + timeStamp + ".mp4"); 

        } else { 

            return null; 
        } 

        return mediaFile; 

    }

    @Override 
    protected void onPause() { 
    	Log.i("onPause", "onPause");
        super.onPause(); 
        releaseMediaRecorder(); // 如果正在使用MediaRecorder，首先需要释放它。
        releaseCamera();         // 在暂停事件中立即释放摄像头
    } 
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	Log.i("onResume", "onResume");
    	super.onResume();
    	if(mCamera == null) {
	    	// 创建Camera实例 
	        mCamera = getCameraInstance(); 
	        // 创建Preview view并将其设为activity中的内容
	        mPreview = new CameraPreview(this, mCamera); 
	        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	        preview.removeAllViews();
	        preview.addView(mPreview); 
    	}
    }
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	Log.i("onStart", "onStart");
    	super.onStart();
    }


 
    private void releaseMediaRecorder(){ 
        if (mMediaRecorder != null) { 
        	
            mMediaRecorder.reset(); // 清除recorder配置
            mMediaRecorder.release(); // 释放recorder对象
            mMediaRecorder = null; 
            mCamera.lock();           // 为后续使用锁定摄像头

        } 

    } 

    private void releaseCamera(){ 

        if (mCamera != null){ 
        	mCamera.setPreviewCallback(null) ;
        	Log.i("releaseCamera", "releaseCamera");
        	mCamera.stopPreview();
        	Log.i("releaseCamera", "releaseCamera");
            mCamera.release();        // 为其它应用释放摄像头
            Log.i("releaseCamera", "releaseCamera");
            mCamera = null; 
        } 

    } 

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if(keyCode == event.KEYCODE_BACK){
    		setResult(RESULT_CANCELED);
			finish();
			return true;
    	}else
    		return false;
    	
    }

}
