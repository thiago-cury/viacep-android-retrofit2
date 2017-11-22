#viacep-android-retrofit2
Exemplo de como consumir o WebService ViaCEP com Android + Retrofit 2 + Gson converter

#ViaCEP

O ViaCEP é um webservice gratuito para consultar Códigos de Endereçamento Postal (CEP) do Brasil. Para saber mais sobre o serviço, acesse: http://viacep.com.br.

#Sobre o projeto

Esse projeto foi desenvolvido utilizando a biblioteca Retrofit 2 + Gson converter.
A versão do AndroidStudio utilizada foi a 3.0. 
Versão mínima requerida: Android API21

Ao abrir o App, você poderá consultar de duas formas, são elas:

- Consulta por CEP;
- Consulta por Estado(UF), cidade e logradouro(rua);

##Setup

Você deve adicionar e sincronizar os seguintes compile no Gradle a nível do App

##Usando o Gradle:

compile 'com.squareup.retrofit2:retrofit:2.3.0'
compile 'com.squareup.retrofit2:converter-gson:2.3.0'

#Licença

Esse projeto é livre!
