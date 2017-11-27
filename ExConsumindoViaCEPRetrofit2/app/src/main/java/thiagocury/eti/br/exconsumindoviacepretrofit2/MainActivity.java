package thiagocury.eti.br.exconsumindoviacepretrofit2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //Widgets
    private EditText etCEP;
    private Button btnBuscarPorCEP;
    private ProgressBar progressBar;

    private EditText etRua;
    private EditText etCidade;
    private Spinner spUFs;
    private Button btnBuscarPorRuaCidadeEstado;

    private ArrayList<CEP> arrayCEPs;

    //Tag para o LOG
    private static final String TAG = "logCEP";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Refs.
        etCEP = findViewById(R.id.et_cep);
        btnBuscarPorCEP = findViewById(R.id.btn_buscar_por_cep);
        progressBar = findViewById(R.id.progress_bar);
        etRua = findViewById(R.id.et_rua);
        etCidade = findViewById(R.id.et_cidade);
        spUFs = findViewById(R.id.sp_ufs);
        btnBuscarPorRuaCidadeEstado = findViewById(R.id.btn_buscar_por_rua_cidade_estado);


        Gson g = new GsonBuilder().registerTypeAdapter(CEP.class, new CEPDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(APIRetrofitService.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create(g))
                                .build();

        final APIRetrofitService service = retrofit.create(APIRetrofitService.class);

        /* Buscar por CEP */
        btnBuscarPorCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!etCEP.getText().toString().isEmpty()) {

                    progressBar.setVisibility(View.VISIBLE);

                    Call<CEP> callCEPByCEP = service.getEnderecoByCEP(etCEP.getText().toString());

                    callCEPByCEP.enqueue(new Callback<CEP>() {
                        @Override
                        public void onResponse(Call<CEP> call, Response<CEP> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        getResources().getString(R.string.toast_erro_cep),
                                        Toast.LENGTH_LONG).show();
                            } else {
                                CEP cep = response.body();

                                //Retorno na Toast
                                Toast.makeText(
                                        getApplicationContext(),
                                        getResources().getString(R.string.toast_aviso_retorno)+cep.toString(),
                                        Toast.LENGTH_LONG).show();

                                //Retorno no Log
                                Log.d(TAG, cep.toString());
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<CEP> call, Throwable t) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    getResources().getString(R.string.toast_erro_generico) + t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        /* Busca por Rua Cidade e Estado(UF) */
        btnBuscarPorRuaCidadeEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!etCidade.getText().toString().isEmpty() &&
                   !etRua.getText().toString().isEmpty()) {

                    progressBar.setVisibility(View.VISIBLE);

                    Call<List<CEP>> callCEPByCidadeEstadoEndereco = service.getCEPByCidadeEstadoEndereco(spUFs.getSelectedItem().toString(), etCidade.getText().toString(), etRua.getText().toString());

                    callCEPByCidadeEstadoEndereco.enqueue(new Callback<List<CEP>>() {
                        @Override
                        public void onResponse(Call<List<CEP>> call, Response<List<CEP>> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        getResources().getString(R.string.toast_erro_dados_invalidos),
                                        Toast.LENGTH_LONG).show();
                            } else {
                                List<CEP> CEPAux = response.body();

                                Log.d(TAG, CEPAux.toString());

                                arrayCEPs = new ArrayList<>();

                                for (CEP cep : CEPAux) {
                                    arrayCEPs.add(cep);
                                }

                                //Retorno na Toast
                                Toast.makeText(
                                        getApplicationContext(),
                                        getResources().getString(R.string.toast_aviso_retorno)+arrayCEPs.toString(),
                                        Toast.LENGTH_LONG).show();


                                //Retorno no Log
                                Log.d(TAG, arrayCEPs.toString());
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<List<CEP>> call, Throwable t) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    getResources().getString(R.string.toast_erro_generico) + t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}