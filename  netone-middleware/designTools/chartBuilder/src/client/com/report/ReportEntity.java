/**
 * 
 */
package com.report;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 报表对象实体类
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-1-12
 * @history
 */
public class ReportEntity implements Serializable {
	private static final long serialVersionUID = 7523758011123660520L;

	// 汇总关键字
	// 客户隶属关系 客户上级目录
	private String _REPORT_FIELD_DEPT_ID_1;
	private String _REPORT_FIELD_DEPT_ID_2;
	private String _REPORT_FIELD_DEPT_ID_3;
	private String _REPORT_FIELD_DEPT_ID_4;
	private String _REPORT_FIELD_DEPT_ID_5;
	private String _REPORT_FIELD_DEPT_ID_X;// 业务主任
	private String _REPORT_FIELD_DEPT_ID_S1;// 省公司仓库
	private String _REPORT_FIELD_DEPT_ID_S2;// 经销商仓库

	private String _REPORT_FIELD_DEPT_CODE_1;
	private String _REPORT_FIELD_DEPT_CODE_2;
	private String _REPORT_FIELD_DEPT_CODE_3;
	private String _REPORT_FIELD_DEPT_CODE_4;
	private String _REPORT_FIELD_DEPT_CODE_5;
	private String _REPORT_FIELD_DEPT_CODE_X;
	private String _REPORT_FIELD_DEPT_CODE_S1;
	private String _REPORT_FIELD_DEPT_CODE_S2;

	private String _REPORT_FIELD_DEPT_NAME_1;
	private String _REPORT_FIELD_DEPT_NAME_2;
	private String _REPORT_FIELD_DEPT_NAME_3;
	private String _REPORT_FIELD_DEPT_NAME_4;
	private String _REPORT_FIELD_DEPT_NAME_5;
	private String _REPORT_FIELD_DEPT_NAME_X;
	private String _REPORT_FIELD_DEPT_NAME_S1;
	private String _REPORT_FIELD_DEPT_NAME_S2;

	// 组、分类、产品
	// 组
	private String _REPORT_FIELD_PRODUCT_ID_1;
	private String _REPORT_FIELD_PRODUCT_CODE_1;
	private String _REPORT_FIELD_PRODUCT_NAME_1;
	// 分类
	private String _REPORT_FIELD_PRODUCT_ID_2;
	private String _REPORT_FIELD_PRODUCT_CODE_2;
	private String _REPORT_FIELD_PRODUCT_NAME_2;
	// 产品
	private String _REPORT_FIELD_PRODUCT_ID_3;
	private String _REPORT_FIELD_PRODUCT_CODE_3;
	private String _REPORT_FIELD_PRODUCT_NAME_3;

	// 报表字段
	private String _REPORT_FIELD_0;
	private String _REPORT_FIELD_1;
	private String _REPORT_FIELD_2;
	private String _REPORT_FIELD_3;
	private String _REPORT_FIELD_4;
	private String _REPORT_FIELD_5;
	private String _REPORT_FIELD_6;
	private String _REPORT_FIELD_7;
	private String _REPORT_FIELD_8;
	private String _REPORT_FIELD_9;
	private String _REPORT_FIELD_10;
	private String _REPORT_FIELD_11;
	private String _REPORT_FIELD_12;
	private String _REPORT_FIELD_13;
	private String _REPORT_FIELD_14;
	private String _REPORT_FIELD_15;
	private String _REPORT_FIELD_16;
	private String _REPORT_FIELD_17;
	private String _REPORT_FIELD_18;
	private String _REPORT_FIELD_19;
	private String _REPORT_FIELD_20;
	private String _REPORT_FIELD_21;
	private String _REPORT_FIELD_22;
	private String _REPORT_FIELD_23;
	private String _REPORT_FIELD_24;
	private String _REPORT_FIELD_25;
	private String _REPORT_FIELD_26;
	private String _REPORT_FIELD_27;
	private String _REPORT_FIELD_28;
	private String _REPORT_FIELD_29;
	private String _REPORT_FIELD_30;
	private String _REPORT_FIELD_31;

	/** 汇总合并、拆分关键KEY */
	private String _REPORT_FIELD_KEY;

	/** 特殊业务字段 */
	/** 年 */
	private String _REPORT_FIELD_YEAR;
	/** 月 */
	private String _REPORT_FIELD_MONTH;
	/** 日 */
	private String _REPORT_FIELD_DAY;
	/** 一箱等于多少吨 */
	private Double _REPORT_FIELD_TON;
	/** 一箱等于多少瓶 */
	private Integer _REPORT_FIELD_BOTTLE;
	/** 一托盘等于多少箱 */
	private Integer _REPORT_FIELD_BOX;
	/** 业务类型 */
	private String _REPORT_FIELD_TYPE;
	/** 毛重 */
	private BigDecimal _REPORT_FIELD_GROSSWEIGHT;

	public String get_REPORT_FIELD_0() {
		return _REPORT_FIELD_0;
	}

	public void set_REPORT_FIELD_0(String _report_field_0) {
		_REPORT_FIELD_0 = _report_field_0;
	}

	public String get_REPORT_FIELD_1() {
		return _REPORT_FIELD_1;
	}

	public void set_REPORT_FIELD_1(String _report_field_1) {
		_REPORT_FIELD_1 = _report_field_1;
	}

	public String get_REPORT_FIELD_2() {
		return _REPORT_FIELD_2;
	}

	public void set_REPORT_FIELD_2(String _report_field_2) {
		_REPORT_FIELD_2 = _report_field_2;
	}

	public String get_REPORT_FIELD_3() {
		return _REPORT_FIELD_3;
	}

	public void set_REPORT_FIELD_3(String _report_field_3) {
		_REPORT_FIELD_3 = _report_field_3;
	}

	public String get_REPORT_FIELD_4() {
		return _REPORT_FIELD_4;
	}

	public void set_REPORT_FIELD_4(String _report_field_4) {
		_REPORT_FIELD_4 = _report_field_4;
	}

	public String get_REPORT_FIELD_5() {
		return _REPORT_FIELD_5;
	}

	public void set_REPORT_FIELD_5(String _report_field_5) {
		_REPORT_FIELD_5 = _report_field_5;
	}

	public String get_REPORT_FIELD_6() {
		return _REPORT_FIELD_6;
	}

	public void set_REPORT_FIELD_6(String _report_field_6) {
		_REPORT_FIELD_6 = _report_field_6;
	}

	public String get_REPORT_FIELD_7() {
		return _REPORT_FIELD_7;
	}

	public void set_REPORT_FIELD_7(String _report_field_7) {
		_REPORT_FIELD_7 = _report_field_7;
	}

	public String get_REPORT_FIELD_8() {
		return _REPORT_FIELD_8;
	}

	public void set_REPORT_FIELD_8(String _report_field_8) {
		_REPORT_FIELD_8 = _report_field_8;
	}

	public String get_REPORT_FIELD_9() {
		return _REPORT_FIELD_9;
	}

	public void set_REPORT_FIELD_9(String _report_field_9) {
		_REPORT_FIELD_9 = _report_field_9;
	}

	public String get_REPORT_FIELD_10() {
		return _REPORT_FIELD_10;
	}

	public void set_REPORT_FIELD_10(String _report_field_10) {
		_REPORT_FIELD_10 = _report_field_10;
	}

	public String get_REPORT_FIELD_11() {
		return _REPORT_FIELD_11;
	}

	public void set_REPORT_FIELD_11(String _report_field_11) {
		_REPORT_FIELD_11 = _report_field_11;
	}

	public String get_REPORT_FIELD_12() {
		return _REPORT_FIELD_12;
	}

	public void set_REPORT_FIELD_12(String _report_field_12) {
		_REPORT_FIELD_12 = _report_field_12;
	}

	public String get_REPORT_FIELD_13() {
		return _REPORT_FIELD_13;
	}

	public void set_REPORT_FIELD_13(String _report_field_13) {
		_REPORT_FIELD_13 = _report_field_13;
	}

	public String get_REPORT_FIELD_14() {
		return _REPORT_FIELD_14;
	}

	public void set_REPORT_FIELD_14(String _report_field_14) {
		_REPORT_FIELD_14 = _report_field_14;
	}

	public String get_REPORT_FIELD_15() {
		return _REPORT_FIELD_15;
	}

	public void set_REPORT_FIELD_15(String _report_field_15) {
		_REPORT_FIELD_15 = _report_field_15;
	}

	public String get_REPORT_FIELD_16() {
		return _REPORT_FIELD_16;
	}

	public void set_REPORT_FIELD_16(String _report_field_16) {
		_REPORT_FIELD_16 = _report_field_16;
	}

	public String get_REPORT_FIELD_17() {
		return _REPORT_FIELD_17;
	}

	public void set_REPORT_FIELD_17(String _report_field_17) {
		_REPORT_FIELD_17 = _report_field_17;
	}

	public String get_REPORT_FIELD_18() {
		return _REPORT_FIELD_18;
	}

	public void set_REPORT_FIELD_18(String _report_field_18) {
		_REPORT_FIELD_18 = _report_field_18;
	}

	public String get_REPORT_FIELD_19() {
		return _REPORT_FIELD_19;
	}

	public void set_REPORT_FIELD_19(String _report_field_19) {
		_REPORT_FIELD_19 = _report_field_19;
	}

	public String get_REPORT_FIELD_20() {
		return _REPORT_FIELD_20;
	}

	public void set_REPORT_FIELD_20(String _report_field_20) {
		_REPORT_FIELD_20 = _report_field_20;
	}

	public String get_REPORT_FIELD_21() {
		return _REPORT_FIELD_21;
	}

	public void set_REPORT_FIELD_21(String _report_field_21) {
		_REPORT_FIELD_21 = _report_field_21;
	}

	public String get_REPORT_FIELD_22() {
		return _REPORT_FIELD_22;
	}

	public void set_REPORT_FIELD_22(String _report_field_22) {
		_REPORT_FIELD_22 = _report_field_22;
	}

	public String get_REPORT_FIELD_23() {
		return _REPORT_FIELD_23;
	}

	public void set_REPORT_FIELD_23(String _report_field_23) {
		_REPORT_FIELD_23 = _report_field_23;
	}

	public String get_REPORT_FIELD_24() {
		return _REPORT_FIELD_24;
	}

	public void set_REPORT_FIELD_24(String _report_field_24) {
		_REPORT_FIELD_24 = _report_field_24;
	}

	public String get_REPORT_FIELD_25() {
		return _REPORT_FIELD_25;
	}

	public void set_REPORT_FIELD_25(String _report_field_25) {
		_REPORT_FIELD_25 = _report_field_25;
	}

	public String get_REPORT_FIELD_26() {
		return _REPORT_FIELD_26;
	}

	public void set_REPORT_FIELD_26(String _report_field_26) {
		_REPORT_FIELD_26 = _report_field_26;
	}

	public String get_REPORT_FIELD_27() {
		return _REPORT_FIELD_27;
	}

	public void set_REPORT_FIELD_27(String _report_field_27) {
		_REPORT_FIELD_27 = _report_field_27;
	}

	public String get_REPORT_FIELD_28() {
		return _REPORT_FIELD_28;
	}

	public void set_REPORT_FIELD_28(String _report_field_28) {
		_REPORT_FIELD_28 = _report_field_28;
	}

	public String get_REPORT_FIELD_29() {
		return _REPORT_FIELD_29;
	}

	public void set_REPORT_FIELD_29(String _report_field_29) {
		_REPORT_FIELD_29 = _report_field_29;
	}

	public String get_REPORT_FIELD_30() {
		return _REPORT_FIELD_30;
	}

	public void set_REPORT_FIELD_30(String _report_field_30) {
		_REPORT_FIELD_30 = _report_field_30;
	}

	public String get_REPORT_FIELD_31() {
		return _REPORT_FIELD_31;
	}

	public void set_REPORT_FIELD_31(String _report_field_31) {
		_REPORT_FIELD_31 = _report_field_31;
	}

	public String get_REPORT_FIELD_KEY() {
		return _REPORT_FIELD_KEY;
	}

	public void set_REPORT_FIELD_KEY(String _report_field_key) {
		_REPORT_FIELD_KEY = _report_field_key;
	}

	public String get_REPORT_FIELD_YEAR() {
		return _REPORT_FIELD_YEAR;
	}

	public void set_REPORT_FIELD_YEAR(String _report_field_year) {
		_REPORT_FIELD_YEAR = _report_field_year;
	}

	public String get_REPORT_FIELD_MONTH() {
		return _REPORT_FIELD_MONTH;
	}

	public void set_REPORT_FIELD_MONTH(String _report_field_month) {
		_REPORT_FIELD_MONTH = _report_field_month;
	}

	public String get_REPORT_FIELD_DAY() {
		return _REPORT_FIELD_DAY;
	}

	public void set_REPORT_FIELD_DAY(String _report_field_day) {
		_REPORT_FIELD_DAY = _report_field_day;
	}

	public Double get_REPORT_FIELD_TON() {
		return _REPORT_FIELD_TON;
	}

	public void set_REPORT_FIELD_TON(Double _report_field_ton) {
		_REPORT_FIELD_TON = _report_field_ton;
	}

	public Integer get_REPORT_FIELD_BOTTLE() {
		return _REPORT_FIELD_BOTTLE;
	}

	public void set_REPORT_FIELD_BOTTLE(Integer _report_field_bottle) {
		_REPORT_FIELD_BOTTLE = _report_field_bottle;
	}

	public Integer get_REPORT_FIELD_BOX() {
		return _REPORT_FIELD_BOX;
	}

	public void set_REPORT_FIELD_BOX(Integer _report_field_box) {
		_REPORT_FIELD_BOX = _report_field_box;
	}

	public BigDecimal get_REPORT_FIELD_GROSSWEIGHT() {
		return _REPORT_FIELD_GROSSWEIGHT;
	}

	public void set_REPORT_FIELD_GROSSWEIGHT(
			BigDecimal _report_field_grossweight) {
		_REPORT_FIELD_GROSSWEIGHT = _report_field_grossweight;
	}

	public String get_REPORT_FIELD_DEPT_CODE_1() {
		return _REPORT_FIELD_DEPT_CODE_1;
	}

	public void set_REPORT_FIELD_DEPT_CODE_1(String _report_field_dept_code_1) {
		_REPORT_FIELD_DEPT_CODE_1 = _report_field_dept_code_1;
	}

	public String get_REPORT_FIELD_DEPT_CODE_2() {
		return _REPORT_FIELD_DEPT_CODE_2;
	}

	public void set_REPORT_FIELD_DEPT_CODE_2(String _report_field_dept_code_2) {
		_REPORT_FIELD_DEPT_CODE_2 = _report_field_dept_code_2;
	}

	public String get_REPORT_FIELD_DEPT_CODE_3() {
		return _REPORT_FIELD_DEPT_CODE_3;
	}

	public void set_REPORT_FIELD_DEPT_CODE_3(String _report_field_dept_code_3) {
		_REPORT_FIELD_DEPT_CODE_3 = _report_field_dept_code_3;
	}

	public String get_REPORT_FIELD_DEPT_CODE_4() {
		return _REPORT_FIELD_DEPT_CODE_4;
	}

	public void set_REPORT_FIELD_DEPT_CODE_4(String _report_field_dept_code_4) {
		_REPORT_FIELD_DEPT_CODE_4 = _report_field_dept_code_4;
	}

	public String get_REPORT_FIELD_DEPT_CODE_5() {
		return _REPORT_FIELD_DEPT_CODE_5;
	}

	public void set_REPORT_FIELD_DEPT_CODE_5(String _report_field_dept_code_5) {
		_REPORT_FIELD_DEPT_CODE_5 = _report_field_dept_code_5;
	}

	public String get_REPORT_FIELD_DEPT_CODE_X() {
		return _REPORT_FIELD_DEPT_CODE_X;
	}

	public void set_REPORT_FIELD_DEPT_CODE_X(String _report_field_dept_code_x) {
		_REPORT_FIELD_DEPT_CODE_X = _report_field_dept_code_x;
	}

	public String get_REPORT_FIELD_DEPT_NAME_1() {
		return _REPORT_FIELD_DEPT_NAME_1;
	}

	public void set_REPORT_FIELD_DEPT_NAME_1(String _report_field_dept_name_1) {
		_REPORT_FIELD_DEPT_NAME_1 = _report_field_dept_name_1;
	}

	public String get_REPORT_FIELD_DEPT_NAME_2() {
		return _REPORT_FIELD_DEPT_NAME_2;
	}

	public void set_REPORT_FIELD_DEPT_NAME_2(String _report_field_dept_name_2) {
		_REPORT_FIELD_DEPT_NAME_2 = _report_field_dept_name_2;
	}

	public String get_REPORT_FIELD_DEPT_NAME_3() {
		return _REPORT_FIELD_DEPT_NAME_3;
	}

	public void set_REPORT_FIELD_DEPT_NAME_3(String _report_field_dept_name_3) {
		_REPORT_FIELD_DEPT_NAME_3 = _report_field_dept_name_3;
	}

	public String get_REPORT_FIELD_DEPT_NAME_4() {
		return _REPORT_FIELD_DEPT_NAME_4;
	}

	public void set_REPORT_FIELD_DEPT_NAME_4(String _report_field_dept_name_4) {
		_REPORT_FIELD_DEPT_NAME_4 = _report_field_dept_name_4;
	}

	public String get_REPORT_FIELD_DEPT_NAME_5() {
		return _REPORT_FIELD_DEPT_NAME_5;
	}

	public void set_REPORT_FIELD_DEPT_NAME_5(String _report_field_dept_name_5) {
		_REPORT_FIELD_DEPT_NAME_5 = _report_field_dept_name_5;
	}

	public String get_REPORT_FIELD_DEPT_NAME_X() {
		return _REPORT_FIELD_DEPT_NAME_X;
	}

	public void set_REPORT_FIELD_DEPT_NAME_X(String _report_field_dept_name_x) {
		_REPORT_FIELD_DEPT_NAME_X = _report_field_dept_name_x;
	}

	public String get_REPORT_FIELD_DEPT_ID_1() {
		return _REPORT_FIELD_DEPT_ID_1;
	}

	public void set_REPORT_FIELD_DEPT_ID_1(String _report_field_dept_id_1) {
		_REPORT_FIELD_DEPT_ID_1 = _report_field_dept_id_1;
	}

	public String get_REPORT_FIELD_DEPT_ID_2() {
		return _REPORT_FIELD_DEPT_ID_2;
	}

	public void set_REPORT_FIELD_DEPT_ID_2(String _report_field_dept_id_2) {
		_REPORT_FIELD_DEPT_ID_2 = _report_field_dept_id_2;
	}

	public String get_REPORT_FIELD_DEPT_ID_3() {
		return _REPORT_FIELD_DEPT_ID_3;
	}

	public void set_REPORT_FIELD_DEPT_ID_3(String _report_field_dept_id_3) {
		_REPORT_FIELD_DEPT_ID_3 = _report_field_dept_id_3;
	}

	public String get_REPORT_FIELD_DEPT_ID_4() {
		return _REPORT_FIELD_DEPT_ID_4;
	}

	public void set_REPORT_FIELD_DEPT_ID_4(String _report_field_dept_id_4) {
		_REPORT_FIELD_DEPT_ID_4 = _report_field_dept_id_4;
	}

	public String get_REPORT_FIELD_DEPT_ID_5() {
		return _REPORT_FIELD_DEPT_ID_5;
	}

	public void set_REPORT_FIELD_DEPT_ID_5(String _report_field_dept_id_5) {
		_REPORT_FIELD_DEPT_ID_5 = _report_field_dept_id_5;
	}

	public String get_REPORT_FIELD_DEPT_ID_X() {
		return _REPORT_FIELD_DEPT_ID_X;
	}

	public void set_REPORT_FIELD_DEPT_ID_X(String _report_field_dept_id_x) {
		_REPORT_FIELD_DEPT_ID_X = _report_field_dept_id_x;
	}

	public String get_REPORT_FIELD_PRODUCT_ID_1() {
		return _REPORT_FIELD_PRODUCT_ID_1;
	}

	public void set_REPORT_FIELD_PRODUCT_ID_1(String _report_field_product_id_1) {
		_REPORT_FIELD_PRODUCT_ID_1 = _report_field_product_id_1;
	}

	public String get_REPORT_FIELD_PRODUCT_ID_2() {
		return _REPORT_FIELD_PRODUCT_ID_2;
	}

	public void set_REPORT_FIELD_PRODUCT_ID_2(String _report_field_product_id_2) {
		_REPORT_FIELD_PRODUCT_ID_2 = _report_field_product_id_2;
	}

	public String get_REPORT_FIELD_PRODUCT_ID_3() {
		return _REPORT_FIELD_PRODUCT_ID_3;
	}

	public void set_REPORT_FIELD_PRODUCT_ID_3(String _report_field_product_id_3) {
		_REPORT_FIELD_PRODUCT_ID_3 = _report_field_product_id_3;
	}

	public String get_REPORT_FIELD_PRODUCT_CODE_1() {
		return _REPORT_FIELD_PRODUCT_CODE_1;
	}

	public void set_REPORT_FIELD_PRODUCT_CODE_1(
			String _report_field_product_code_1) {
		_REPORT_FIELD_PRODUCT_CODE_1 = _report_field_product_code_1;
	}

	public String get_REPORT_FIELD_PRODUCT_CODE_2() {
		return _REPORT_FIELD_PRODUCT_CODE_2;
	}

	public void set_REPORT_FIELD_PRODUCT_CODE_2(
			String _report_field_product_code_2) {
		_REPORT_FIELD_PRODUCT_CODE_2 = _report_field_product_code_2;
	}

	public String get_REPORT_FIELD_PRODUCT_CODE_3() {
		return _REPORT_FIELD_PRODUCT_CODE_3;
	}

	public void set_REPORT_FIELD_PRODUCT_CODE_3(
			String _report_field_product_code_3) {
		_REPORT_FIELD_PRODUCT_CODE_3 = _report_field_product_code_3;
	}

	public String get_REPORT_FIELD_PRODUCT_NAME_1() {
		return _REPORT_FIELD_PRODUCT_NAME_1;
	}

	public void set_REPORT_FIELD_PRODUCT_NAME_1(
			String _report_field_product_name_1) {
		_REPORT_FIELD_PRODUCT_NAME_1 = _report_field_product_name_1;
	}

	public String get_REPORT_FIELD_PRODUCT_NAME_2() {
		return _REPORT_FIELD_PRODUCT_NAME_2;
	}

	public void set_REPORT_FIELD_PRODUCT_NAME_2(
			String _report_field_product_name_2) {
		_REPORT_FIELD_PRODUCT_NAME_2 = _report_field_product_name_2;
	}

	public String get_REPORT_FIELD_PRODUCT_NAME_3() {
		return _REPORT_FIELD_PRODUCT_NAME_3;
	}

	public void set_REPORT_FIELD_PRODUCT_NAME_3(
			String _report_field_product_name_3) {
		_REPORT_FIELD_PRODUCT_NAME_3 = _report_field_product_name_3;
	}

	public String get_REPORT_FIELD_TYPE() {
		return _REPORT_FIELD_TYPE;
	}

	public void set_REPORT_FIELD_TYPE(String _report_field_type) {
		_REPORT_FIELD_TYPE = _report_field_type;
	}

	public String get_REPORT_FIELD_DEPT_ID_S1() {
		return _REPORT_FIELD_DEPT_ID_S1;
	}

	public void set_REPORT_FIELD_DEPT_ID_S1(String _report_field_dept_id_s1) {
		_REPORT_FIELD_DEPT_ID_S1 = _report_field_dept_id_s1;
	}

	public String get_REPORT_FIELD_DEPT_ID_S2() {
		return _REPORT_FIELD_DEPT_ID_S2;
	}

	public void set_REPORT_FIELD_DEPT_ID_S2(String _report_field_dept_id_s2) {
		_REPORT_FIELD_DEPT_ID_S2 = _report_field_dept_id_s2;
	}

	public String get_REPORT_FIELD_DEPT_CODE_S1() {
		return _REPORT_FIELD_DEPT_CODE_S1;
	}

	public void set_REPORT_FIELD_DEPT_CODE_S1(String _report_field_dept_code_s1) {
		_REPORT_FIELD_DEPT_CODE_S1 = _report_field_dept_code_s1;
	}

	public String get_REPORT_FIELD_DEPT_CODE_S2() {
		return _REPORT_FIELD_DEPT_CODE_S2;
	}

	public void set_REPORT_FIELD_DEPT_CODE_S2(String _report_field_dept_code_s2) {
		_REPORT_FIELD_DEPT_CODE_S2 = _report_field_dept_code_s2;
	}

	public String get_REPORT_FIELD_DEPT_NAME_S1() {
		return _REPORT_FIELD_DEPT_NAME_S1;
	}

	public void set_REPORT_FIELD_DEPT_NAME_S1(String _report_field_dept_name_s1) {
		_REPORT_FIELD_DEPT_NAME_S1 = _report_field_dept_name_s1;
	}

	public String get_REPORT_FIELD_DEPT_NAME_S2() {
		return _REPORT_FIELD_DEPT_NAME_S2;
	}

	public void set_REPORT_FIELD_DEPT_NAME_S2(String _report_field_dept_name_s2) {
		_REPORT_FIELD_DEPT_NAME_S2 = _report_field_dept_name_s2;
	}

}
