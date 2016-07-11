/*
 * main.java : Главный модуль программы 
 * 
 * Программа посылает запрос на сервис яндекса (переводчик).   Полученный ответ
 * заносит в таблицу.  Запрос производится по одному слову из переданной строки
 * в качестве параметра.
 * В качестве таблици используется СУБД - HyperSQLDB.
 * 
 * Описание входных параметров:
 * 1 - с какого языка необходим перевод;
 * 2 - не какой язык необходим перевод;
 * 3 - строка с текстом заключенная в кавычки.
 * 
 * Первый  и  второй параметры записываются абревиатурой (ru -русский, en - ан-
 * глийский и т.д.)
 */

package trans;

import java.io.IOException;
import java.util.regex.Pattern;
import org.json.simple.parser.*;


public class main {
	public static void main(String[] _args) throws IOException, 
												   ParseException {
		/* Проверка входных параметров */
		if (_args.length < 3) return;
		
		String fromLang     = _args[0];		/* с какого языка     */
		String toLang       = _args[1];		/* на какой язык      */
		String txtToTrans   = _args[2];		/* строка с текстом   */
		String txtFromTrans = "";			/* переведенный текст */

		/* Производим разбор строки на слова */
		Pattern  pat     = Pattern.compile(" ");
		String[] strArr  = pat.split(txtToTrans);
		int      nStrArr = strArr.length;
		
		/* Инициализация нашей таблици */
		table.load_driver();
		table.db_connect();
		table.tbl_create();
				
		/* Запросы на сервис для перевода списка слов */
		for (int i = 0; i < nStrArr; i++) {
			txtFromTrans = JSONWrk.json_str_pars(
					Request.rec_to_trans(fromLang, toLang,strArr[i]));
			
			table.tbl_add(fromLang, toLang, strArr[i], txtFromTrans);
		}
		
		/* Вывод получившейся таблици и закрытие ее */
		table.tbl_print();
		table.db_disconnect();
	}
}