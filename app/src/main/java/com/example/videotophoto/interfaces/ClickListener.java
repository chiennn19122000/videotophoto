package com.example.videotophoto.interfaces;

import com.example.videotophoto.classUnits.Emojis;
import com.example.videotophoto.classUnits.Folder;
import com.example.videotophoto.classUnits.Images;
import com.example.videotophoto.classUnits.Videos;

public interface ClickListener {
    void OnClickFolder(Folder folder);
    void OnClickVideo(Videos videos);
    void OnClickImage(Images images);
    void OnClickEmojis(Emojis emojis);
}
