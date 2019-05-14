package thiagocury.eti.br.exconsumindoviacepretrofit2.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import thiagocury.eti.br.exconsumindoviacepretrofit2.R;
import thiagocury.eti.br.exconsumindoviacepretrofit2.helpers.CEPDeserializer;
import thiagocury.eti.br.exconsumindoviacepretrofit2.model.CEP;
import thiagocury.eti.br.exconsumindoviacepretrofit2.model.SimpleCallback;

public class CEPService {

    private Context context;
    private APIRetrofitService service;

    public CEPService(Context context) {
        this.context = context;

        initialize();
    }

    private void initialize(){
        Gson g = new GsonBuilder().registerTypeAdapter (CEP.class, new CEPDeserializer()).create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel (HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder ().connectTimeout (1, TimeUnit.MINUTES)
                .readTimeout (30, TimeUnit.SECONDS)
                .writeTimeout (15, TimeUnit.SECONDS)
                .addInterceptor (interceptor)
                .build ();

        Retrofit retrofit = new Retrofit.Builder ()
                .baseUrl (APIRetrofitService.BASE_URL)
                .client (okHttpClient)
                .addConverterFactory (GsonConverterFactory.create (g))
                .build ();

        service = retrofit.create (APIRetrofitService.class);

        final APIRetrofitService service = retrofit.create(APIRetrofitService.class);
    }

    public void getCEP(String CEP, final SimpleCallback<CEP> callback){
        service.getEnderecoByCEP(CEP).enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if (response.isSuccessful () && response.body () != null) {
                    callback.onResponse (response.body());
                } else {
                    if (response.body () != null) {
                        callback.onError("erro");
                    } else {
                        callback.onError("erro");
                    }
                }
            }
            @Override
            public void onFailure(Call<CEP> call, Throwable t) {
                t.printStackTrace ();
                callback.onError (t.getMessage());
            }
        });
    }

    public void getCEPUFCidadeRua(String UF, String Cidade, String rua, final SimpleCallback<List<CEP>> callback){
        service.getCEPByCidadeEstadoEndereco(UF, Cidade, rua).enqueue(new Callback<List<CEP>>() {
            @Override
            public void onResponse(Call<List<CEP>> call, Response<List<CEP>> response) {
                if (response.isSuccessful () && response.body () != null) {
                    callback.onResponse (response.body());
                } else {
                    if (response.body () != null) {
                        callback.onError("erro");
                    } else {
                        callback.onError("erro");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CEP>> call, Throwable t) {
                t.printStackTrace ();
                callback.onError (t.getMessage());
            }
        });
    }
}