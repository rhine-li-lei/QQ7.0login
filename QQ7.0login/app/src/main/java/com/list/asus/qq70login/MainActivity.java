package com.list.asus.qq70login;

import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private SurfaceView sfvBackground;
    private Button btnLogin;
    private EditText edtQQnum;
    private EditText password;
    private TextView tvForgetPassword, tvRegister, tvAgree, tvAgreement, tvListQQnum;
    private ImageButton ibtQQListIndicator, ibtListRemove;
    private CircleImageView civAvatar, civListAvatar;

    private MediaPlayer mediaPlayer;
    private int postion = 0;

    private static boolean isVisible=false;         //ListView是否可见
    private static boolean isIndicatorUp=false;     //指示器的方向

    public static int currentSelectedPosition=-1;
    //用于记录当前选择的ListView中的QQ联系人条目的ID，如果是-1表示没有选择任何QQ账户，注意在向
    //List中添加条目或者删除条目时都要实时更新该currentSelectedPosition

    String[] from={"userPhoto","userQQ","deletButton"};
    int[] to={R.id.login_userPhoto,R.id.login_userQQ,R.id.login_deleteButton};
    ArrayList<HashMap<String,Object>> list=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findId();
        initView();
    }


    //沉浸式状态栏
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT>=19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
}

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.qqNum, R.id.password};
        return ids;
    }


    private void findId() {
        sfvBackground = (SurfaceView) findViewById(R.id.surfaceView_background);
        btnLogin = (Button) findViewById(R.id.login);
        edtQQnum = (EditText) findViewById(R.id.qqNum);
        password = (EditText) findViewById(R.id.password);
        tvForgetPassword = (TextView) findViewById(R.id.forgetPassword);
        tvRegister = (TextView) findViewById(R.id.register);
        tvAgree = (TextView) findViewById(R.id.agree);
        tvAgreement = (TextView) findViewById(R.id.agreement);
        tvListQQnum = (TextView) findViewById(R.id.login_userQQ);
        ibtQQListIndicator = (ImageButton) findViewById(R.id.qqListIndicator);
        ibtListRemove = (ImageButton) findViewById(R.id.login_deleteButton);
        civAvatar = (CircleImageView) findViewById(R.id.avatar);
        civListAvatar = (CircleImageView) findViewById(R.id.login_userPhoto);

    }

    private void initView() {
        mediaPlayer = new MediaPlayer();
        sfvBackground.getHolder().setKeepScreenOn(true);
        sfvBackground.getHolder().addCallback(new SurfaceViewLis());
        btnLogin.setOnClickListener(this);
    }


    private class SurfaceViewLis implements SurfaceHolder.Callback {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (postion == 0) {
                try {
                    play();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }

    }

    public void play() throws IllegalArgumentException, SecurityException,
            IllegalStateException, IOException {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        AssetFileDescriptor fd = this.getAssets().openFd("mox.mp4");
        mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(),
                fd.getLength());
        mediaPlayer.setLooping(true);
        mediaPlayer.setDisplay(sfvBackground.getHolder());
        // 通过异步的方式装载媒体资源
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 装载完毕回调
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                Log.i("TAG", "onClick: 11111111111111111");
                Toast.makeText(getApplicationContext(),"1111111",Toast.LENGTH_SHORT).show();
                break;
            case R.id.agreement:
                Log.i("TAG", "onClick: 22222222222222222");
                break;
            default:
                break;
        }
    }
}
