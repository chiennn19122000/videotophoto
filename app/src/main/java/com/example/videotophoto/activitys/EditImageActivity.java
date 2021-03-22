package com.example.videotophoto.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.videotophoto.BaseActivity;
import com.example.videotophoto.R;
import com.example.videotophoto.adapters.EmojisAdapter;
import com.example.videotophoto.classUnits.Emojis;
import com.example.videotophoto.classUnits.Folder;
import com.example.videotophoto.classUnits.Images;
import com.example.videotophoto.classUnits.Videos;
import com.example.videotophoto.interfaces.ClickListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.SaveSettings;
import ja.burhanrashid52.photoeditor.ViewType;
import yuku.ambilwarna.AmbilWarnaDialog;

import static com.example.videotophoto.Constants.REQUEST_URI_IMAGE;
import static com.example.videotophoto.Constants.RESULTCODE;
import static com.example.videotophoto.Constants.RETURNDATA;

public class EditImageActivity extends BaseActivity implements OnPhotoEditorListener {


    @BindView(R.id.photo_editor)
    PhotoEditorView mPhotoEditorView;

    @BindView(R.id.undo)
    ImageView undo;

    @BindView(R.id.redo)
    ImageView redo;

    @BindView(R.id.close)
    ImageView close;

    @BindView(R.id.frame_edit)
    RelativeLayout layout_edit;

    @BindView(R.id.back_and_next)
    RelativeLayout back_and_next;

    @BindView(R.id.cut)
    LinearLayout cut;

    @BindView(R.id.text)
    LinearLayout textbtn;

    @BindView(R.id.sticker)
    LinearLayout sticker;

    @BindView(R.id.paint)
    LinearLayout paint;

    @BindView(R.id.main_img)
    RelativeLayout main_img;

    @BindView(R.id.share)
    LinearLayout share;

    @BindView(R.id.edit)
    LinearLayout edit;

    @BindView(R.id.delete)
    LinearLayout delete;

    @BindView(R.id.paint_img)
    RelativeLayout layout_paint;

    @BindView(R.id.seekbar)
    SeekBar seekBar;

    @BindView(R.id.color)
    ImageView color;

    @BindView(R.id.close_paint)
    ImageView close_paint;

    @BindView(R.id.erase)
    ImageView erase;

    @BindView(R.id.layout_edit_text)
    RelativeLayout layout_text;

    @BindView(R.id.eidt_text)
    EditText editText;

    @BindView(R.id.color_text)
    ImageView colortext;

    @BindView(R.id.tick_text)
    ImageView ticktext;

    @BindView(R.id.close_text)
    ImageView closetext;

    @BindView(R.id.layout_sticker)
    RelativeLayout layout_sticker;

    @BindView(R.id.layout_cut)
    RelativeLayout layout_cut;

    @BindView(R.id.cut11)
    LinearLayout cut11;

    @BindView(R.id.cut23)
    LinearLayout cut23;

    @BindView(R.id.cut31)
    LinearLayout cut31;

    @BindView(R.id.cut34)
    LinearLayout cut34;

    @BindView(R.id.cut43)
    LinearLayout cut43;

    @BindView(R.id.cut46)
    LinearLayout cut46;

    @BindView(R.id.close_cut)
    LinearLayout close_cut;

    @BindView(R.id.crop_image)
    CropImageView cropImageView;
    private List<Emojis> emojisList;
    private RecyclerView recyclerView;
    private EmojisAdapter emojisAdapter;
    private Boolean cutorno = true;

    Uri uriImage;
    private static int COLOR = Color.rgb(0,0,0);
    PhotoEditor mPhotoEditor;
    public static final String FILE_PROVIDER_AUTHORITY = "com.example.videotophoto.fileprovider";

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_edit_image;
    }

    @Override
    protected void setupListener() {
        Edit();
        Close();
        Undo();
        Redo();
        Paint();
        Sticker();
        Text();
        Cut();
        Sharing();

        Delete();

    }



    @Override
    protected void populateData() {
        callback();
        setData();
    }

    private void setData() {
        back_and_next.setVisibility(View.INVISIBLE);
        layout_edit.setVisibility(View.INVISIBLE);
        layout_paint.setVisibility(View.INVISIBLE);
        layout_text.setVisibility(View.INVISIBLE);
        layout_sticker.setVisibility(View.INVISIBLE);
        layout_cut.setVisibility(View.INVISIBLE);
        cropImageView.setVisibility(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();
        uriImage = Uri.parse(bundle.getString(REQUEST_URI_IMAGE));
        loadBitmap();
        mPhotoEditorView.getSource().setImageURI(uriImage);

        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(true) // set flag to make text scalable when pinch
                //.setDefaultTextTypeface(mTextRobotoTf)
                //.setDefaultEmojiTypeface(mEmojiTypeFace)
                .build(); // build photo editor sdk

        mPhotoEditor.setOnPhotoEditorListener(this);


        // recycler view emojis duoc khoi tao

        recyclerView = (RecyclerView) findViewById(R.id.recycler_stiker);
        emojisList = new ArrayList<>();
        for (int i=0;i<PhotoEditor.getEmojis(this).size();i++)
        {
            emojisList.add(new Emojis(PhotoEditor.getEmojis(this).get(i)));
        }
        emojisAdapter = new EmojisAdapter(EditImageActivity.this,emojisList);
        recyclerView.setAdapter(emojisAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(EditImageActivity.this,6));
    }

    private void Sharing() {
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, buildFileProviderUri(Uri.fromFile(new File(uriImage.toString()))));
                startActivity(Intent.createChooser(intent, getString(R.string.msg_share_image)));
            }
        });
    }

    private Uri buildFileProviderUri(@NonNull Uri uri) {
        return FileProvider.getUriForFile(this,
                FILE_PROVIDER_AUTHORITY,
                new File(uri.getPath()));
    }

    private void Edit() {
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_and_next.setVisibility(View.VISIBLE);
                main_img.setVisibility(View.INVISIBLE);
                layout_edit.setVisibility(View.VISIBLE);
            }
        });
    }

    private void Close() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_and_next.setVisibility(View.INVISIBLE);
                layout_edit.setVisibility(View.INVISIBLE);
                main_img.setVisibility(View.VISIBLE);

            }
        });
    }

    private void Delete() {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(uriImage.toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(EditImageActivity.this);
                builder.setTitle("Delete");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        file.delete();
                        Intent intent = new Intent();
                        intent.putExtra(RETURNDATA, uriImage);
                        setResult(RESULTCODE, intent);
                        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_save, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.save:
                saveImage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingPermission")
    private void saveImage() {
        if (requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) && cutorno) {
            showLoading("Saving...");
            File file = new File(Environment.getExternalStorageDirectory()+ "/VideoToPhto/Images" , System.currentTimeMillis() + ".jpg");
            try {
                file.createNewFile();

                SaveSettings saveSettings = new SaveSettings.Builder()
                        .setClearViewsEnabled(true)
                        .setTransparencyEnabled(true)
                        .build();
                mPhotoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                        hideLoading();
                        uriImage = Uri.fromFile(new File(imagePath));
                        mPhotoEditorView.getSource().setImageURI(uriImage);
                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        hideLoading();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                hideLoading();
            }
        }
        else {
            showLoading("Saving...");

            File file = new File(Environment.getExternalStorageDirectory()+ "/VideoToPhto/Images" , System.currentTimeMillis() + ".jpg");
            try {
                FileOutputStream out = new FileOutputStream(file);
                cropImageView.getCroppedImage().compress(Bitmap.CompressFormat.PNG, 100, out);

                out.flush();
                out.close();
                hideLoading();
            } catch(Exception e) {
                hideLoading();
            }
        }
        Toast.makeText(EditImageActivity.this,"Đã lưu ảnh",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onEditTextChangeListener(View rootView, String text, int colorCode) {
        layout_text.setVisibility(View.VISIBLE);
        layout_edit.setVisibility(View.INVISIBLE);
        close.setVisibility(View.INVISIBLE);
        layout_paint.setVisibility(View.INVISIBLE);
        showKeyboard();
        editText.setText(text);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPhotoEditor.editText(rootView,s.toString(),COLOR);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {
    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {

    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {
    }

    private void Paint()
    {
        paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close.setVisibility(View.INVISIBLE);
                layout_edit.setVisibility(View.INVISIBLE);
                layout_paint.setVisibility(View.VISIBLE);
                seekBar.setMax(40);
                seekBar.setProgress(3);
                mPhotoEditor.setBrushSize(8);
                mPhotoEditor.setBrushDrawingMode(true);
            }
        });
        setSeekBar();
        SelectColor();
        Erase();
        ClosePaint();
    }

    private void ClosePaint() {
        close_paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_paint.setVisibility(View.INVISIBLE);
                layout_edit.setVisibility(View.VISIBLE);
                close.setVisibility(View.VISIBLE);

            }
        });
    }

    private void setSeekBar() {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int n;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                n=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPhotoEditor.setBrushSize(n+5);
            }
        });
    }

    private void SelectColor() {
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSelectColor();
                mPhotoEditor.setBrushColor(COLOR);

            }
        });
    }

    private void Erase() {
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoEditor.brushEraser();

            }
        });
    }

    private void OpenSelectColor()
    {
        AmbilWarnaDialog pickColor = new AmbilWarnaDialog(this, Color.rgb(0,0,0), new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                COLOR = color;

                editText.setText(editText.getText());
            }
        });

        pickColor.show();
    }

    private void Text()
    {
        textbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_text.setVisibility(View.VISIBLE);
                layout_edit.setVisibility(View.INVISIBLE);
                close.setVisibility(View.INVISIBLE);
                showKeyboard();

            }
        });
        colortext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               OpenSelectColor();
            }
        });
        AddText();
        CloseText();
    }

    private void CloseText() {
        closetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_text.setVisibility(View.INVISIBLE);
                layout_edit.setVisibility(View.VISIBLE);
                close.setVisibility(View.VISIBLE);
                closeKeyboard(v);
            }
        });
    }

    private void AddText() {


        ticktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoEditor.addText(editText.getText().toString(),COLOR);
               closeKeyboard(v);
            }
        });

    }
    private void showKeyboard(){
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void closeKeyboard(View view){
        editText.setText("");
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void Undo()
    {
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoEditor.undo();
            }
        });
    }
    private void Redo()
    {
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoEditor.redo();
            }
        });
    }

    private void Sticker()
    {
        sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_sticker.setVisibility(View.VISIBLE);
                SelectEmojis();
            }
        });

    }

    private void SelectEmojis() {
        emojisAdapter.EmojisOnClick(new ClickListener() {
            @Override
            public void OnClickFolder(Folder folder) {

            }

            @Override
            public void OnClickVideo(Videos videos) {

            }

            @Override
            public void OnClickImage(Images images) {

            }

            @Override
            public void OnClickEmojis(Emojis emojis) {
                mPhotoEditor.addEmoji(emojis.getName());
                layout_sticker.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void Cut()
    {
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_cut.setVisibility(View.VISIBLE);
                layout_edit.setVisibility(View.INVISIBLE);
                back_and_next.setVisibility(View.INVISIBLE);
                mPhotoEditorView.setVisibility(View.INVISIBLE);
                cropImageView.setVisibility(View.VISIBLE);
                cutorno = false;

                CloseCut();
                cut11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartCut(uriImage,1,1);
                        Toast.makeText(EditImageActivity.this,uriImage+"",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void StartCut(Uri uriImage, int i, int i1) {
        CropImage.activity(uriImage)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
//                .setAspectRatio(i,i1)
                .start(this);
    }

    private void CloseCut() {
        close_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_and_next.setVisibility(View.VISIBLE);
                layout_edit.setVisibility(View.VISIBLE);
                mPhotoEditorView.setVisibility(View.VISIBLE);
                layout_cut.setVisibility(View.INVISIBLE);
                cropImageView.setVisibility(View.INVISIBLE);
                cutorno = true;
            }
        });
    }

    public Bitmap loadBitmap()
    {
        Bitmap bitmap= null;
       File f = new File(uriImage.toString());
       BitmapFactory.Options options = new BitmapFactory.Options();
       options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
             bitmap = BitmapFactory.decodeStream(new FileInputStream(f),null,options);
            cropImageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}