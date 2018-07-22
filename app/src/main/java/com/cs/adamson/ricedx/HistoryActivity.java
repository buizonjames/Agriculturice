package com.cs.adamson.ricedx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import java.io.File;
import java.util.ArrayList;

public class HistoryActivity extends NavActivity {

    public class ImageAdapter extends BaseAdapter{
        private Context mContext;
        ArrayList<String> itemList = new ArrayList<String>();

        public ImageAdapter(Context c){
            mContext = c;
        }

        void add(String path){
            itemList.add(path);
        }

        @Override
        public int getCount(){
            return itemList.size();
        }

        @Override
        public Object getItem(int i){
            return i;
        }

        @Override
        public long getItemId(int position){
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            ImageView imageView;

            if(convertView == null){
                imageView = new ImageView(mContext);

                imageView.setLayoutParams(new GridView.LayoutParams(150,150));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(4, 4, 4, 4);
            }
            else
                imageView = (ImageView)convertView;

            Bitmap bmp = decodeFromUri(itemList.get(position), 220, 220);
            imageView.setImageBitmap(bmp);
            return imageView;
        }

        public Bitmap decodeFromUri(String path, int reqWidth, int reqHeight){
            Bitmap bmp = null;
            final BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            options.inSampleSize = calculateSampleSize(options, reqWidth, reqHeight);

            options.inJustDecodeBounds = false;

            bmp = BitmapFactory.decodeFile(path, options);
            return bmp;
        }

        public int calculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if(height > reqHeight || width > reqWidth){
                if(width > height)
                    inSampleSize = Math.round((float)height/(float)reqHeight);
                else
                    inSampleSize = Math.round((float)width/(float)reqWidth);
            }
            return inSampleSize;
        }
    }

    ImageAdapter myImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_history, frameLayout);
        setTitle("History");
        GridView gridView = (GridView)findViewById(R.id.imageGridview);
        myImageAdapter = new ImageAdapter(this);

        gridView.setAdapter(myImageAdapter);

        gridView.setClickable(true);
        String ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath + "/DCIM/Camera";
        Log.v("TAG", ExternalStorageDirectoryPath);
        Log.v("TAG", targetPath);
        File targetDirector = new File(targetPath);
        File[] files = targetDirector.listFiles();
        int i =0;
        for(File file:files){
            myImageAdapter.add(file.getAbsolutePath());
            Log.v("TAG", files[i].getName());
            i++;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        navigationView.getMenu().getItem(0).setChecked(true);
    }
}
