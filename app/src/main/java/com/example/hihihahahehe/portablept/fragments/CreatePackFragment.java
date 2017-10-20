package com.example.hihihahahehe.portablept.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.databases.RealmHandleAccount;
import com.example.hihihahahehe.portablept.models.FaceBookModel;
import com.example.hihihahahehe.portablept.models.HotSportsModel;
import com.example.hihihahahehe.portablept.models.JSONModel.PackJSONModel;
import com.example.hihihahahehe.portablept.networks.RetrofitFactory;
import com.example.hihihahahehe.portablept.networks.services.AddPack;
import com.example.hihihahahehe.portablept.utils.ScreenManager;

import org.greenrobot.eventbus.Subscribe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePackFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = CreatePackFragment.class.toString();
    private static final int RESULT_LOAD_IMAGE = 1;
    private FaceBookModel faceBookModel;
    private String duration;
    private Uri imageUrl;
    private Cloudinary cloudinary;
    private InputStream inputStream;
    private String packName;
    private String type;
    @BindView(R.id.edt_pack_name)
    EditText edtPackName;
    @BindView(R.id.edt_pack_price)
    EditText edtPackPrice;
    @BindView(R.id.bt_cancel)
    TextView btCancel;
    @BindView(R.id.bt_create)
    TextView btCreate;
    @BindView(R.id.tv_set_week)
    TextView tvSetWeek;
    @BindView(R.id.tv_set_type)
    TextView tvSetType;
    @BindView(R.id.iv_add_image)
    ImageView ivAddImage;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.cb_T2)
    CheckBox checkBoxT2;
    @BindView(R.id.cb_T3)
    CheckBox checkBoxT3;
    @BindView(R.id.cb_T4)
    CheckBox checkBoxT4;
    @BindView(R.id.cb_T6)
    CheckBox checkBoxT6;
    @BindView(R.id.cb_T7)
    CheckBox checkBoxT7;
    @BindView(R.id.cb_T5)
    CheckBox checkBoxT5;
    @BindView(R.id.edt_content)
    TextView edt_content;




    public CreatePackFragment() {
        // Required empty public constructor
    }
    int checkData = 0;
    private ArrayAdapter<String> arrayAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_pack_1, container, false);
        setupUI(view);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);

        setOnClickItem();
    }

    private void setOnClickItem() {
        btCancel.setOnClickListener(this);
        tvSetWeek.setOnClickListener(this);
        btCreate.setOnClickListener(this);
        ivAddImage.setOnClickListener(this);
        tvSetType.setOnClickListener(this);
    }

    private void showChooseTypeDialog() {
        AlertDialog.Builder chooseType = new AlertDialog.Builder(getContext());

        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        arrayAdapter.add("Fitness");
        chooseType.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvSetType.setText(arrayAdapter.getItem(i) + " ▾");
                type = arrayAdapter.getItem(i);
            }
        });
        chooseType.show();
    }

    private void showChooseWeekDialog() {
        AlertDialog.Builder chooseWeek = new AlertDialog.Builder(getContext());

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        arrayAdapter.add("1 tuần");
        arrayAdapter.add("2 tuần");
        arrayAdapter.add("3 tuần");
        arrayAdapter.add("4 tuần");

        chooseWeek.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvSetWeek.setText(arrayAdapter.getItem(i) + " ▾");
                duration = arrayAdapter.getItem(i);
            }
        });
        chooseWeek.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE) {
            imageUrl = data.getData();
            ivAddImage.setImageURI(imageUrl);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_cancel:
                ScreenManager.onBackPressed(getActivity().getSupportFragmentManager());
                break;
            case R.id.tv_set_week:
                showChooseWeekDialog();
                break;
            case R.id.bt_create:
                new UploadService().execute();
                final AddPack addPack = RetrofitFactory.getInstance().create(AddPack.class);



                packName = String.valueOf(edtPackName.getText());
                String coach = RealmHandleAccount.getAccount().getData().getId();
                String price = edtPackPrice.getText().toString() + " VND";
                String address = edtAddress.getText().toString();
                String imageUrl = "https://res.cloudinary.com/dekbhfa6g/image/upload/" + edtPackName.getText().toString() + ".jpg";
                String calendar ="" ;
                if (checkBoxT2.isChecked()){
                    calendar = calendar + " T2 ";
                }
                if (checkBoxT3.isChecked()){
                    calendar = calendar + " T3 ";
                }
                if (checkBoxT4.isChecked()){
                    calendar = calendar + " T4 ";
                }
                if (checkBoxT5.isChecked()){
                    calendar = calendar + " T5 ";
                }
                if (checkBoxT6.isChecked()){
                    calendar = calendar + " T6 ";
                }
                if (checkBoxT7.isChecked()){
                    calendar = calendar + " T7 ";
                }
                String content= edt_content.getText().toString();

                PackJSONModel model = new PackJSONModel(type, packName, coach, price, duration, imageUrl, address,calendar,content);

                addPack.addPack(model).enqueue(new Callback<PackJSONModel>() {
                    @Override
                    public void onResponse(Call<PackJSONModel> call, Response<PackJSONModel> response) {
                        Toast.makeText(getContext(), "Đã thêm gói tập mới !", Toast.LENGTH_SHORT).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack();
                    }

                    @Override
                    public void onFailure(Call<PackJSONModel> call, Throwable t) {
                        Toast.makeText(getContext(), "Không thể tạo, vui lòng kiểm tra lại kết nối !", Toast.LENGTH_SHORT).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack();
                    }
                });
                break;
            case R.id.iv_add_image:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, RESULT_LOAD_IMAGE);
                break;
            case R.id.tv_set_type:
                showChooseTypeDialog();
                break;
        }
    }

    public class UploadService extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            uploadImage();
            return null;
        }
    }

    private void uploadImage() {
        cloudinary = new Cloudinary("cloudinary://764643933774622:eO4wWI6IeBnROiOvsR3auqbyDQg@dekbhfa6g");
        String imageName = com.example.hihihahahehe.portablept.utils.Utils.removeAccent(edtPackName.getText().toString());
        cloudinary.url().generate(imageName + ".jpg");
        try {
            inputStream = getActivity().getContentResolver().openInputStream(imageUrl);
            cloudinary.uploader().upload(inputStream, ObjectUtils.asMap("public_id", imageName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //get Pack Type
    @Subscribe(sticky = true)
    public  void onReceivedPack(List<HotSportsModel>  hotSportsModelList){
        if (checkData == 0){
            arrayAdapter.clear();
            for(int i = 0; i < hotSportsModelList.size(); i++){
                    arrayAdapter.add(hotSportsModelList.get(i).getName());
            }
            checkData = 1;
        }


    }
}