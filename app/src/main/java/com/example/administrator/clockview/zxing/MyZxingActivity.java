package com.example.administrator.clockview.zxing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.clockview.R;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Hashtable;

/**
 * 二维码的生成（log）与识别
 */

public class MyZxingActivity extends AppCompatActivity {

    private static final String TAG = MyZxingActivity.class.getSimpleName();
    private static final String PACKAGE_NAME = MyZxingActivity.class.getPackage().getName();

    private  EditText contentET;
    private ImageView imageView;
    private CheckBox addLogoCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_zxing);
        //内容
        contentET = (EditText) findViewById(R.id.create_qr_content);
        //显示二维码图片
        imageView = (ImageView) findViewById(R.id.create_qr_iv);
        //是否添加Logo
        addLogoCB = (CheckBox) findViewById(R.id.create_qr_addLogo);
        Button createQrBtn = (Button) findViewById(R.id.create_qr_btn);

        createQrBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String filePath = getFileRoot(MyZxingActivity.this) + File.separator
                        + "qr_" + System.currentTimeMillis() + ".jpg";

                //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean success = QRCodeUtil.createQRImage(contentET.getText().toString().trim(), 800, 800,
                                addLogoCB.isChecked() ? BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher) : null,
                                filePath);

                        if (success) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
                                }
                            });
                        }
                    }
                }).start();

            }
        });

        findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //解析转换类型UTF-8
                Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
                hints.put(DecodeHintType.CHARACTER_SET, "utf-8");

                Bitmap bitmaps = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                int[] pixels=new int[bitmaps.getWidth() * bitmaps.getHeight()];
                com.example.administrator.clockview.zxing.RGBLuminanceSource source = new com.example.administrator.clockview.zxing.RGBLuminanceSource(bitmaps);
//                  LuminanceSource source = new RGBLuminanceSource(path);
                BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
                QRCodeReader reader2 = new QRCodeReader();
                Result result;
                try {
                    result = reader2.decode(bitmap1, hints);
                    System.out.println("res" + result.getText());
                    Toast.makeText(MyZxingActivity.this,"结果是："+result.getText(),Toast.LENGTH_SHORT).show();
                } catch (NotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ChecksumException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (FormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // System.out.println("res"+result.getText());
            }
        });

    }
    private byte[] bitmapToByte(Bitmap b) {
//        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.test);
        Log.e(TAG, "this mapsssssss is  width=" + b.getWidth() + ",heigh= " + b.getHeight());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        return byteArray;
    }
    //解析二维码图片,返回结果封装在Result对象中
    private com.google.zxing.Result  parseQRcodeBitmap(String bitmapPath) {
        //解析转换类型UTF-8
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        //获取到待解析的图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        //如果我们把inJustDecodeBounds设为true，那么BitmapFactory.decodeFile(String path, Options opt)
        //并不会真的返回一个Bitmap给你，它仅仅会把它的宽，高取回来给你
        options.inJustDecodeBounds = true;
        //此时的bitmap是null，这段代码之后，options.outWidth 和 options.outHeight就是我们想要的宽和高了
        Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        //我们现在想取出来的图片的边长（二维码图片是正方形的）设置为400像素
        //以上这种做法，虽然把bitmap限定到了我们要的大小，但是并没有节约内存，如果要节约内存，我们还需要使用inSimpleSize这个属性
        options.inSampleSize = options.outHeight / 400;
        if (options.inSampleSize <= 0) {
            options.inSampleSize = 1; //防止其值小于或等于0
        }
        /**
         * 辅助节约内存设置
         *
         * options.inPreferredConfig = Bitmap.Config.ARGB_4444;    // 默认是Bitmap.Config.ARGB_8888
         * options.inPurgeable = true;
         * options.inInputShareable = true;
         */
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        //新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
        com.example.administrator.clockview.zxing.RGBLuminanceSource rgbLuminanceSource = new com.example.administrator.clockview.zxing.RGBLuminanceSource(bitmap);
        //将图片转换成二进制图片
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        //初始化解析对象
        QRCodeReader reader = new QRCodeReader();
        //开始解析
        Result result = null;
        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }
    //文件存储根目录
    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }

        return context.getFilesDir().getAbsolutePath();
    }
}
