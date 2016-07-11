/*
 * JSONWrk.java: Преобразование строки формата JSON
 * 
 * Функция json_str_pars (JSON String Parse) производит разбор строки. Получает
 * код ответа для проверки и страку ответа с переведенным текстом.
 */

package trans;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONWrk {
	/* json_str_pars(String): Разбор строки ответа в формате JSON */
	public static String json_str_pars(String _str) throws ParseException {
		JSONParser jsonPrs = new JSONParser();
		
		Object     obj     = jsonPrs.parse(_str);
		JSONObject jsonObj = (JSONObject) obj;
		Object     strCode = jsonObj.get("code");
		int        iCode   = Integer.parseInt(strCode.toString());
		Object     objAnsw = "";
		
		/* Обработка кода ответа */
		switch (iCode) {
		case 200:
			JSONArray jsonArr = (JSONArray) jsonObj.get("text");
			objAnsw           = jsonArr.get(0);
			break;
		case 401:
			System.out.println("Неправильный API-ключ");
			break;
		case 402: 
			System.out.println("API-ключ заблокирован"); 
			break;
		case 404:
			System.out.println("Превышено суточное ограничение на объем "
					+ "переведенного текста"); 
			break;
		case 413:
			System.out.println("Превышен максимально допустимый размер "
					+ "текста"); 
			break;
		case 422:
			System.out.println("Текст не может быть переведен"); 
			break;
		case 501: 
			System.out.println("Заданное направление перевода не "
					+ "поддерживается"); 
			break;
		default:  break;
		}
		
		return objAnsw.toString();
	}
}
