package com.example.videotophoto.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.videotophoto.BaseActivity;
import com.example.videotophoto.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import wseemann.media.FFmpegMediaMetadataRetriever;

import static com.example.videotophoto.Constants.REQUEST_URI_VIDEO;

public class CaptureVideoActivity extends BaseActivity {
    @BindView(R.id.select_Video)
    VideoView videoView;

    @BindView(R.id.play_pause)
    ImageView playPause;

    @BindView(R.id.name_video)
    TextView nameVideo;

    @BindView(R.id.quick_capture)
    CheckedTextView quickCapture;

    @BindView(R.id.time_capture)
    CheckedTextView timeCapture;

    @BindView(R.id.seekbar)
    SeekBar seekbar;

    @BindView(R.id.image_capture)
    ImageView imageCapture;

    @BindView(R.id.view)
    LinearLayout view;

    @BindView(R.id.view1)
    LinearLayout view1;

    @BindView(R.id.view2)
    LinearLayout view2;

    @BindView(R.id.time)
    EditText time;

    @BindView(R.id.sec)
    TextView sec;

    private String s,m,valueS,valueM;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    public static Boolean oneTimeOnly = false;
    private static boolean checkQuickOrTime = true;
    Uri uri;

    Bitmap bitmap;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_capture_video;
    }

    @Override
    protected void setupListener() {
        playAndPause();
        captureFinalTime();
        listenQuickCapture();
        listenTimeCapture();




    }
    @Override
    protected void populateData() {
        setData();
        setTime();
        callback();
    }

    private void captureFinalTime() {
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(CaptureVideoActivity.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_in_seconds);
                EditText finaltimesec = (EditText) dialog.findViewById(R.id.final_time);
                TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
                TextView ok = (TextView) dialog.findViewById(R.id.ok);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time.setText(finaltimesec.getText());
                        dialog.cancel();

                    }
                });
                dialog.show();
            }
        });
    }

    private void setData()
    {
        Bundle bundle = getIntent().getExtras();
        String path = bundle.getString(REQUEST_URI_VIDEO);
        File video = new File(path);
        uri = Uri.parse(path);
        videoView.setVideoURI(uri);
        nameVideo.setText(video.getName());
        seekbar.setProgress(0);
        quickCapture.setChecked(true);
        if (quickCapture.isChecked())
        {
            view.setVisibility(View.INVISIBLE);
            view1.setBackgroundColor(Color.GREEN);
        }


    }

    private void listenQuickCapture()
    {
        quickCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickCapture.setChecked(true);
                timeCapture.setChecked(false);
                view1.setBackgroundColor(Color.GREEN);
                view2.setBackgroundColor(Color.WHITE);
                view.setVisibility(View.INVISIBLE);
                checkQuickOrTime = true;
            }
        });
    }
    private void listenTimeCapture()
    {
        timeCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickCapture.setChecked(false);
                timeCapture.setChecked(true);
                view2.setBackgroundColor(Color.GREEN);
                view1.setBackgroundColor(Color.WHITE);
                view.setVisibility(View.VISIBLE);
                time.setFocusable(false);
                checkQuickOrTime = false;
            }
        });
    }

    private void playAndPause()
    {
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying())
                {
                    videoView.pause();
                    playPause.setImageResource(R.drawable.play);
                }
                else {
                    videoView.start();
                    playPause.setImageResource(R.drawable.pause);

                    startTime = videoView.getCurrentPosition();
                    finalTime = videoView.getDuration();

                    if (oneTimeOnly == false) {
                        seekbar.setMax((int) finalTime);
                        oneTimeOnly = true;
                    }

                    seekbar.setProgress((int)startTime);
                    myHandler.postDelayed(UpdateSongTime,100);
                }
            }
        });
    }

    private void updateTime(int up)
    {


        s = String.format("%d", TimeUnit.MILLISECONDS.toSeconds((long) up) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) up)));
        if(Integer.parseInt(s)<=9)
        {
            s = "0" + s;
        }
        m = String.format("%d",
                TimeUnit.MILLISECONDS.toMinutes((long) up)
        );

        valueS = String.format("%d",TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)));

        if (Integer.parseInt(valueS) <=9)
        {
            valueS = "0" + valueS;
        }
        valueM = String.format("%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime));


        Drawable drawable = new TextDrawable.Builder()
                .setWidth(220)
                .setHeight(40)
                .setColor(Color.GREEN)
                .setShape(TextDrawable.SHAPE_ROUND_RECT)
                .setRadius(100)
                .setText(m + ":" + s + "/" + valueM + ":" + valueS)
                .setFontSize(40 )
                .build();
        seekbar.setThumb(drawable);

    }

    private void setTime()
    {

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int time = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                time = progress;
                updateTime(time);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                videoView.seekTo(time);
            }
        });
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = videoView.getCurrentPosition();
            finalTime = videoView.getDuration();

            updateTime((int) startTime);
            seekbar.setProgress((int)startTime);
            seekbar.setMax((int) finalTime);
            myHandler.postDelayed(this, 100);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.item_toolbar,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.capture :

                CaptureImage();
                break;

            case R.id.done :
                MyCaptute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void MyCaptute() {
        startActivity(new Intent(this,GalleryActivity.class));
    }

    private void CaptureImage() {

        MediaMetadataRetriever fm = new MediaMetadataRetriever();
        fm.setDataSource(CaptureVideoActivity.this,uri);
        File f = new File(Environment.getExternalStorageDirectory() + "/VideoToPhto/Images");
        f.mkdirs();
        if (checkQuickOrTime)
        {
            bitmap = fm.getFrameAtTime((long)videoView.getCurrentPosition()*1000);
            String imagename = System.currentTimeMillis() + ".jpg";

            File image = new File(f,imagename);

            try {
                image.createNewFile();
                FileOutputStream out = new FileOutputStream(image);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                imageCapture.setImageBitmap(bitmap);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            double a =Double.parseDouble(time.getText().toString());
            a = a*1000;
            int n = (int) ((int)videoView.getDuration()/a);



            for (int i=0;i<n ; i++)
            {
                bitmap = fm.getFrameAtTime((long) (i*a*1000));



                String imagename = System.currentTimeMillis() + ".jpg";

                File image = new File(f,imagename);
                Toast.makeText(CaptureVideoActivity.this,imagename,Toast.LENGTH_SHORT).show();


                try {
                    image.createNewFile();
                    FileOutputStream out = new FileOutputStream(image);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    imageCapture.setImageBitmap(bitmap);
                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


}

