package com.example.administrator.mjproject;




        import android.content.Context;
        import android.content.DialogInterface;
        import android.os.Handler;
        import android.os.Message;
        import android.os.PowerManager;
        import android.os.Vibrator;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import java.util.ArrayList;

        import static android.os.PowerManager.FULL_WAKE_LOCK;


public class GymActivity extends AppCompatActivity {

    String mBlueName, mWhiteName;
    int mWhiteScore, mWhiteSet;
    int mBlueScore, mBlueSet;
    ArrayList<Score> arScore = new ArrayList<Score>();
    long mStartTime;
    long mLastTime;
    TextView mBlueTeam, mWhiteTeam;
    Button mBlueBtn, mWhiteBtn;
    TextView mHistory;
    TextView mEllapseTime;
    boolean mSwap;
    Vibrator mVib;
    PowerManager mPm;
    PowerManager.WakeLock mWakeLock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);


        mBlueTeam = (TextView) findViewById(R.id.blueteam);
        mWhiteTeam = (TextView) findViewById(R.id.whiteteam);
        mBlueBtn = (Button) findViewById(R.id.bluescore);
        mWhiteBtn = (Button) findViewById(R.id.whitescore);
        mHistory = (TextView) findViewById(R.id.history);
        mEllapseTime = (TextView) findViewById(R.id.elapsetime);

        mWhiteName = "병사";
        mBlueName = "간부";

        NewGame();
        mHandler.sendEmptyMessageDelayed(0, 1000);
        mVib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mPm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = mPm.newWakeLock(FULL_WAKE_LOCK, "WakeAlways");
    }

    protected void onResume() {
        super.onResume();
        mWakeLock.acquire();
    }

    protected void onPause() {
        super.onPause();
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.bluescore:
                AddScore(true);
                break;
            case R.id.whitescore:
                AddScore(false);
                break;
            case R.id.cancellast:
                if (arScore.size() != 0) {
                    Score s = arScore.get(arScore.size() - 1);
                    if (s.blue) {
                        mBlueScore--;
                    } else {
                        mWhiteScore--;
                    }
                    arScore.remove(arScore.size() - 1);
                    UpdateInfo();
                }
                break;


            case R.id.newset:
                new AlertDialog.Builder(this)
                        .setTitle("질문")
                        .setMessage("현재 세트를 종료할까요?")

                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (mWhiteScore > mBlueScore) {
                                    mWhiteSet++;
                                } else if (mWhiteScore < mBlueScore) {
                                    mBlueSet++;
                                } else {
                                    new AlertDialog.Builder(GymActivity.this)
                                            .setTitle("알림")
                                            .setMessage("비겼으므로 세트 점수에는 영향이 없습니다.")
                                            .setPositiveButton("확인", null)
                                            .show();
                                }

                                mWhiteScore = 0;
                                mBlueScore = 0;
                                arScore.clear();
                                UpdateInfo();
                            }
                        })
                        .setNegativeButton("아니요", null)
                        .show();
                break;
            case R.id.newgame:
                new AlertDialog.Builder(this)
                        .setTitle("질문")
                        .setMessage("현재 경기를 종료할까요?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                NewGame();
                                UpdateInfo();
                            }
                        })
                        .setNegativeButton("아니요", null)
                        .show();
                break;
            case R.id.change:
                mSwap = !mSwap;
                UpdateInfo();
                break;
        }
    }

    void AddScore(boolean blue) {
        mVib.vibrate(100);
        if (mSwap) blue = !blue;
        if (blue) {
            mBlueScore++;
            arScore.add(new Score(System.currentTimeMillis(), true));
        } else {
            mWhiteScore++;
            arScore.add(new Score(System.currentTimeMillis(), false));
        }
        mLastTime = System.currentTimeMillis();
        UpdateInfo();
    }

    void NewGame() {
        mWhiteScore = 0;
        mWhiteSet = 0;
        mBlueScore = 0;
        mBlueSet = 0;
        arScore.clear();
        mStartTime = System.currentTimeMillis();
        mLastTime = -1;
        UpdateInfo();
    }

    void UpdateInfo() {
        if (mSwap) {
            mWhiteTeam.setText(String.format("%s(%d세트)", mBlueName, mBlueSet));
            mBlueTeam.setText(String.format("%s(%d세트)", mWhiteName, mWhiteSet));
            mWhiteBtn.setText("" + mBlueScore);
            mBlueBtn.setText("" + mWhiteScore);
        } else {
            mBlueTeam.setText(String.format("%s(%d세트)", mBlueName, mBlueSet));
            mWhiteTeam.setText(String.format("%s(%d세트)", mWhiteName, mWhiteSet));
            mBlueBtn.setText("" + mBlueScore);
            mWhiteBtn.setText("" + mWhiteScore);
        }
        UpdateTime();
    }

    void UpdateTime() {
        long ellapse;

        StringBuilder history = new StringBuilder();
        for (int i = arScore.size() - 1; i >= 0; i--) {
            Score s = arScore.get(i);
            ellapse = (System.currentTimeMillis() - s.time) / 1000;
            history.append(String.format("%02d:%02d", ellapse / 60, ellapse % 60));
            history.append("초전 ");
            if (s.blue) {
                history.append(mBlueName);
            } else {
                history.append(mWhiteName);
            }
            history.append(" 득점\n");
        }
        mHistory.setText(history.toString());

        ellapse = (System.currentTimeMillis() - mStartTime) / 1000;
        mEllapseTime.setText(String.format("경기 시작 후 경과 시간 = %02d:%02d", ellapse / 60, ellapse % 60));
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            mHandler.sendEmptyMessageDelayed(0, 1000);
            UpdateTime();
        }
    };


    class Score {
        public Score(long atime, boolean ablue) {
            time = atime;
            blue = ablue;

        }

        long time;
        boolean blue;

    }
}
