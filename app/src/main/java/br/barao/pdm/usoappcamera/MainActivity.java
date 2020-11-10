package br.barao.pdm.usoappcamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
{
    private static final int REQUEST_CAMERA = 1;
    private ImageView ivFoto;
    private Button btCapturar;
    //
    private File arquivoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializaComponentes();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQUEST_CAMERA:
                if(resultCode == RESULT_OK)
                {
                    exibeImagemCapturada();
                }
                break;
        }
    }

    private void inicializaComponentes()
    {
        ivFoto = findViewById(R.id.ivFoto);
        btCapturar = findViewById(R.id.btCapturar);
        btCapturar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                abreAplicativoCamera();
            }
        });
    }

    private File criaArquivoFoto()
    {
        String nomeArquivo = UUID.randomUUID().toString();
        File diretorioParaSalvar = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        arquivoFoto = new File(diretorioParaSalvar, nomeArquivo + ".jpg");
        return arquivoFoto;
    }

    private void abreAplicativoCamera()
    {
        Intent intentAplicativoCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File arquivoFoto = criaArquivoFoto();
        Uri uriFoto = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", arquivoFoto);
        intentAplicativoCamera.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
        startActivityForResult(intentAplicativoCamera, REQUEST_CAMERA);
    }

    private void exibeImagemCapturada()
    {
        Bitmap bitmap = BitmapFactory.decodeFile(arquivoFoto.getAbsolutePath());
        ivFoto.setImageBitmap(bitmap);
    }

}