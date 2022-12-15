/**
 * 
 */
package ma.mang.be.api.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * sub Methodes
 * @author achraf
 *
 */

public class Utils {

	public static final String DD_MM_YYYY_HH_MM_SS_PATTERN_1 = "dd/MM/yyyy hh:mm:ss";
	public static final String DD_MM_YYYY_HH_MM_SS_PATTERN_2 = "yyyy-MM-dd hh:mm:ss";
	public static final String DD_MM_YYYY_PATTERN_1 = "dd/MM/yyyy";
	public static final String DD_MM_YYYY_PATTERN_2 = "ddMMyyyy";


	public static Date stringToSqlDate(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS_PATTERN_1);
		java.util.Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		java.sql.Date sqlDate = new Date(date.getTime());

		return sqlDate;
	}

	public static java.util.Date stringToDate(String strDate, String pattern) {
		if (pattern == null)
			pattern = DD_MM_YYYY_PATTERN_1;
		java.util.Date date = null;
		try {
			date = new SimpleDateFormat(pattern).parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	public static String dateToString(java.util.Date date, String pattern) {
		if (pattern == null)
			pattern = DD_MM_YYYY_PATTERN_1;
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * Checks if a given string matches to a given pattern
	 * @param pattern pattern to apply
	 * @param strToValidate string to check
	 * @return <b>True</b> if the string matches to the pattern, <b>False</b> else.
	 */
	public static boolean isMatch(String pattern, String strToValidate) {
		Pattern p;
		Matcher m;
		// Compilation Pattern to aplly
		p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		// Apply matcher
		m = p.matcher(strToValidate);
		// si le motif est trouvé
		return m.find();
	}
	
	
	/**
	 * Checks if a given row from Excel Sheet is empty or no.
	 * @param row from Excel Sheet
	 * @return true if the row is empty, false else
	 */
	public static boolean checkIfRowIsEmpty(Row row) {
	    if (row == null) {
	        return true;
	    }
	    if (row.getLastCellNum() <= 0) {
	        return true;
	    }
//	    System.out.print("\n"+row.getRowNum() +"\t");
	    for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
	        Cell cell = row.getCell(cellNum);
//	        System.out.print(cellNum + " : " + cell.getCellType()  + " "+ cell.toString() +"| \t");
	        if (cell != null 
	        		&& (cell.getCellType() != Cell.CELL_TYPE_BLANK && cell.getCellType() != Cell.CELL_TYPE_FORMULA &&  !isEmpty(cell.toString()))
	        		&& !row.getZeroHeight()) {
	            return false;
	        }
	    }
	    return true ;
	}
	
	/**
	* check if a string is empty
	*
	* @param s
	* @return
	*/
	public static boolean isEmpty(String s) {
	return (s == null || s.equals("") || s.equals("NaN") || s	.equals("null") || s.trim().length()==0);
	}
	
	
	/**
	* check if a string array is empty
	*
	* @param s
	* @return
	*/
	public static boolean isEmpty(String[] str) {
		int cnt = 1;
		
		if(str==null) return true;
		
		if(str!=null && str.length==0) return true;
		
		for(String s : str) {
			if(isEmpty(s)) cnt++;
		}
		return cnt==str.length;
	}
	
	
	/**
	* check if a string array is empty
	*
	* @param s
	* @return
	*/
	public static boolean isEmpty(String[] str,int[] ids) {
		int cnt = 0;
		
		if(str==null) return true;
		
		if(str!=null && str.length==0) return true;
		
		for(int id : ids) {
			if(isEmpty(str[id])) {
				cnt++;
			}
		}
		return cnt==ids.length;
	}

}
