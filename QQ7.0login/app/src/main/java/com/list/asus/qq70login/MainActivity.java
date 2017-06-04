package com.list.asus.qq70login;

import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.list.asus.qq70login.bean.UserInfo;

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
    private ImageButton ibtQQListIndicator, ibtDelete, ibtListDelete;
    private CircleImageView civAvatar, civListAvatar;
    RecyclerView recLogin;

    ArrayList<UserInfo> loginQQList = null;

    private MediaPlayer mediaPlayer;
    private int position = 0;

    private static boolean isVisible = false;         //ListView是否可见
    private static boolean isIndicatorUp = false;     //指示器的方向

    public static int currentSelectedPosition = -1;
    //用于记录当前选择的ListView中的QQ联系人条目的ID，如果是-1表示没有选择任何QQ账户，注意在向
    //List中添加条目或者删除条目时都要实时更新该currentSelectedPosition

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

    //软键盘
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
        tvAgreement = (TextView) findViewById(R.id.agreement);
        tvListQQnum = (TextView) findViewById(R.id.login_userQQ);
        tvAgree = (TextView) findViewById(R.id.agree);
        ibtQQListIndicator = (ImageButton) findViewById(R.id.qqListIndicator);
        ibtListDelete = (ImageButton) findViewById(R.id.login_deleteButton);
        ibtDelete = (ImageButton) findViewById(R.id.delete);
        civAvatar = (CircleImageView) findViewById(R.id.avatar);
        civListAvatar = (CircleImageView) findViewById(R.id.login_userPhoto);
        recLogin=(RecyclerView) findViewById(R.id.rec_loginQQ);
    }

    private void initView() {
        mediaPlayer = new MediaPlayer();
        sfvBackground.getHolder().setKeepScreenOn(true);
        sfvBackground.getHolder().addCallback(new SurfaceViewLis());

        ibtQQListIndicator.setBackgroundResource(R.drawable.down);

        btnLogin.setOnClickListener(this);
        ibtDelete.setOnClickListener(this);
        ibtQQListIndicator.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);
        edtQQnum.setOnClickListener(this);

        loginQQList= new ArrayList<>();
        UserInfo user1=new UserInfo();
        user1.setAvatar(R.drawable.contact_0);
        user1.setQqNum("123456");
        UserInfo user2=new UserInfo();
        user2.setAvatar(R.drawable.contact_1);
        user2.setQqNum("3153135");
        loginQQList.add(user1);
        loginQQList.add(user2);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recLogin.setLayoutManager(layoutManager);
        MyLoginListAdapter adapter = new MyLoginListAdapter(loginQQList);
        recLogin.setAdapter(adapter);


        //设置当前显示的被选中的账户的头像
        if(currentSelectedPosition==-1){
            edtQQnum.setText("");
        }
        else{
            civAvatar.setImageResource(loginQQList.get(currentSelectedPosition).getAvatar());
            edtQQnum.setText(loginQQList.get(currentSelectedPosition).getQqNum());
        }

    }

    private class SurfaceViewLis implements SurfaceHolder.Callback {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (position == 0) {
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
            //登陆按钮
            case R.id.login:
                if (!edtQQnum.getText().toString().isEmpty() && !password.getText().toString().isEmpty() ) {
                    Toast.makeText(getApplicationContext(),"我是不会让你登陆",Toast.LENGTH_SHORT).show();
                }else if (edtQQnum.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"请输入qq号",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"请输入密码",Toast.LENGTH_SHORT).show();
                }
                break;
            //服务条款
            case R.id.agreement:
                Toast.makeText(getApplicationContext(),"好像不能给你看",Toast.LENGTH_SHORT).show();
                break;
            //输入qq号的EditText
            case R.id.qqNum:
                if(!edtQQnum.getText().toString().equals("")){
                    ibtDelete.setVisibility(View.VISIBLE);
                }
                break;
            //忘记密码
            case R.id.forgetPassword:
                Toast.makeText(getApplicationContext(),"忘记密码那就没法了",Toast.LENGTH_SHORT).show();
                break;
            //新用户注册
            case R.id.register:
                Toast.makeText(getApplicationContext(),"对不起，本企鹅拒绝你",Toast.LENGTH_SHORT).show();
                break;
            //qq号的EditText的删除
            case R.id.delete:
                edtQQnum.setText("");
                currentSelectedPosition=-1;
                ibtDelete.setVisibility(View.GONE);
                break;
            //登陆过的list列表的点开图标
            case R.id.qqListIndicator:
                if(isIndicatorUp){
                    isIndicatorUp = false;
                    isVisible = false;
                    ibtQQListIndicator.setBackgroundResource(R.drawable.down);
                    recLogin.setVisibility(View.GONE);   //让ListView列表消失
                    password.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    tvForgetPassword.setVisibility(View.VISIBLE);
                    tvRegister.setVisibility(View.VISIBLE);
                    tvAgree.setVisibility(View.VISIBLE);
                } else {
                    isIndicatorUp=true;
                    isVisible=true;
                    ibtQQListIndicator.setBackgroundResource(R.drawable.up);
                    recLogin.setVisibility(View.VISIBLE);
                    password.setVisibility(View.GONE);
                    btnLogin.setVisibility(View.GONE);
                    tvForgetPassword.setVisibility(View.GONE);
                    tvRegister.setVisibility(View.GONE);
                    tvAgree.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    //onTouchEvent方法，用于实现点击控件之外的部分使控件消失的功能
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if(event.getAction()==MotionEvent.ACTION_DOWN && isVisible){
            int[] location=new int[2];
            //调用getLocationInWindow方法获得某一控件在窗口中左上角的横纵坐标
            recLogin.getLocationInWindow(location);
            //获得在屏幕上点击的点的坐标
            int x=(int)event.getX();
            int y=(int)event.getY();
            if(x<location[0]|| x>location[0]+recLogin.getWidth() ||
                    y<location[1]||y>location[1]+recLogin.getHeight()) {
                isIndicatorUp = false;
                isVisible = false;
                ibtQQListIndicator.setBackgroundResource(R.drawable.down);
                recLogin.setVisibility(View.GONE);   //让ListView列表消失，并且让游标向下指！
                password.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.VISIBLE);
                tvForgetPassword.setVisibility(View.VISIBLE);
                tvRegister.setVisibility(View.VISIBLE);
                tvAgree.setVisibility(View.VISIBLE);
            }
        }
        return super.onTouchEvent(event);
    }
}