package com.example.android.enghub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class phy1notes extends AppCompatActivity {

    EditText editPDFName;
    Button btn_upload;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phy1notes);

        editPDFName= (EditText) findViewById(R.id.notification);
        btn_upload= (Button)findViewById(R.id.upload);

        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("phy1");

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectPDFFile();
            }
        });
    }

    private void selectPDFFile(){
        Intent intent= new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf File"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){

            uploadPDFFile(data.getData());
        }
    }

    private void uploadPDFFile(Uri data) {
        long harsh= System.currentTimeMillis();
        StorageReference sRef = storageReference.child("uploads/" + harsh + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText (getApplicationContext (),  "File uploaded",  Toast.LENGTH_LONG)  .show ();
                        Task<Uri> uri=  taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                        Uri url = uri.getResult();

                        uploadPDF upload = new uploadPDF(editPDFName.getText().toString(), url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(upload);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        Toast.makeText (getApplicationContext (),  progress+"% Uploading",  Toast.LENGTH_LONG)  .show ();
                    }
                });

    }

    public void btn_action(View view) {
        startActivity(new Intent(getApplicationContext(),view_phy1.class));
    }
}
