package oe.cav.bean.logic.dy.bus;

import oe.frame.orm.QueryInfo;

/**
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class QueryInfoBean implements QueryInfo {

	public QueryInfoBean() {

	}

	public final static String _LARGE = " > ";

	public final static String _LESS = " < ";

	public final static String _LARGE_EQUAL = " >= ";

	public final static String _LESS_EQUAL = " <= ";

	public final static String _LIKE = " like ";

	public final static String _EQUAL = " = ";

	public final static String _UNEQUAL = " <> ";

	public final static String LIKE_VALUE_SYMBOL_LEFT = " %";

	public final static String LIKE_VALUE_SYMBOL_RIGHT = "% ";

	String likecolumn;

	String unequaslcolumn;

	String lesscolumn;

	String lessequalcolumn;

	String largecolumn;

	String largeequalcolumn;

	public String getLargecolumn() {
		return largecolumn;
	}

	public void setLargecolumn(String largecolumn) {
		this.largecolumn = largecolumn;
	}

	public String getLargeequalcolumn() {
		return largeequalcolumn;
	}

	public void setLargeequalcolumn(String largeequalcolumn) {
		this.largeequalcolumn = largeequalcolumn;
	}

	public String getLesscolumn() {
		return lesscolumn;
	}

	public void setLesscolumn(String lesscolumn) {
		this.lesscolumn = lesscolumn;
	}

	public String getLessequalcolumn() {
		return lessequalcolumn;
	}

	public void setLessequalcolumn(String lessequalcolumn) {
		this.lessequalcolumn = lessequalcolumn;
	}

	public String getLikecolumn() {
		return likecolumn;
	}

	public void setLikecolumn(String likecolumn) {
		this.likecolumn = likecolumn;
	}

	public String getUnequaslcolumn() {
		return unequaslcolumn;
	}

	public void setUnequaslcolumn(String unequaslcolumn) {
		this.unequaslcolumn = unequaslcolumn;
	}

}
