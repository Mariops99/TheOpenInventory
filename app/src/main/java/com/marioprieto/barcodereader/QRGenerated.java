package com.marioprieto.barcodereader;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import java.io.IOException;


public class QRGenerated extends AppCompatActivity {

    public ImageView imageView;
    Button save;
    Drawable drawable;
    Uri URI;
    Bitmap bitmap;
    String ImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerated);

        imageView = (ImageView) findViewById(R.id.qr);
        save = (Button) findViewById(R.id.save);

        Bundle bundle2 = this.getIntent().getExtras();
        String texto = bundle2.getString("texto");

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(texto, BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQR();
            }
        });
    }

    @SuppressLint("ResourceType")
    public void saveQR(){
        drawable = ((BitmapDrawable)imageView.getDrawable());

        bitmap = ((BitmapDrawable)drawable).getBitmap();

        ImagePath = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "demo_image",
                "demo_image"
        );

        URI = Uri.parse(ImagePath);

        Toast.makeText(QRGenerated.this, "Imagen guardada correctamente.", Toast.LENGTH_LONG).show();
    }
}