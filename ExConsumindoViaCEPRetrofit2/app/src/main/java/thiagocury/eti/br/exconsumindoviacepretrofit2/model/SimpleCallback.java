package thiagocury.eti.br.exconsumindoviacepretrofit2.model;

public interface SimpleCallback<T> {
    void onResponse (T response);
    void onError (String error);
}
