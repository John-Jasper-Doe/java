/*
 * Request.java: Запрос перевода на сервис Яндекса
 * 
 * Функция  rec_to_trans  (Request to Translate)  производит запрос  на  сервис 
 * Яндекса и возвращает строку ответа в формате JSON.
 * Принемаемые параметры:
 * _from - с какого языка перевод
 * _to   - на какой язык перевод
 * _str  - текст для перевода
 */

package trans;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Request {
	/* rec_to_trans(String,String,String): Запрос перевода на сервис */
	public static String rec_to_trans(String _from, String _to, 
			String _str) throws MalformedURLException, IOException {
		
		/* Строка запроса */
		String url  = "https://translate.yandex.net/api/v1.5/tr." +
	              	  "json/translate?key=trnsl.1.1.20160605T214042Z." + 
			          "874648fb11fcd32e.cbca34b612ab63b0fdb7fc69b84b5" +
	                  "29dd437de23"
	                  + "&lang=" + _from + "-" + _to
	                  + "&text=" + _str;
	
		URLConnection urlCon = new URL(url).openConnection();
		InputStream   isAnsw = urlCon.getInputStream();
		
		@SuppressWarnings("resource")
		String jsonAnsw = new java.util.Scanner(isAnsw).nextLine();
		
		return jsonAnsw;
	}
}
