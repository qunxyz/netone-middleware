package com.jl.common.dyform;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

public class Sample2 {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String formcode = "674baf6cbc0011e084b38197d5f4b310_";

		// -------------数据操作逻辑--------------

		// 添加数据-1
		DyFormData dydata2 = new DyFormData();
		dydata2.setParticipant("adminx[admin]");
		dydata2.setColumn3("a");
		dydata2.setColumn4("b");
		dydata2.setColumn5("44");
		dydata2.setFatherlsh("1");// 主表单fatherlsh为1，如果是子表单的数据，那么fatherlsh的值是父表单的数据的lsh
		String lsh1 = DyEntry.iv().addData(formcode, dydata2);
		// 添加第二条数据
		String lsh2 = DyEntry.iv().addData(formcode, dydata2);
		
		
		// 装载单个数据
		DyFormData dydatax = DyEntry.iv().loadData(formcode, lsh2);
		// 修改数据
		dydatax.setColumn3("qqqqqq");
		boolean x1=DyEntry.iv().modifyData(dydatax.getFormcode(), dydatax);
		
		dydatax.setColumn3("qqqqqq2222");
		boolean x2=DyEntry.iv().modifyData(dydatax.getFormcode(), dydatax);

		// 查询所有数据(父表单)
		DyFormData dydata = new DyFormData();
		dydata.setFormcode(formcode);
		List list = DyEntry.iv().queryData(dydata, 0, 100, "");
		
		//装载表单
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		DyForm []subform=dyform.getSubform_();
		if(subform.length>0){
			//针对lsh1的数据增加2个子表单数据
			// 添加数据-1
			DyFormData dydata3 = new DyFormData();
			dydata3.setParticipant("adminx[admin]");
			dydata3.setColumn3("a");
			dydata3.setColumn4("b");
			dydata3.setColumn5("c");
			dydata3.setFatherlsh(lsh1);// 主表单fatherlsh为1，如果是子表单的数据，那么fatherlsh的值是父表单的数据的lsh
			String lsh3 = DyEntry.iv().addData(subform[0].getFormcode(), dydata3);
			// 添加第二条数据
			String lsh4 = DyEntry.iv().addData(subform[0].getFormcode(), dydata3);

			// 查询所有子表单的数据
			String lshFather = lsh1; // 某个表单数据

			DyFormData dydataSub = new DyFormData();
			dydataSub.setFormcode(subform[0].getFormcode());// 子表单的formcode
			dydataSub.setFatherlsh(lshFather);
			List listSub = DyEntry.iv().queryData(dydataSub, 0, 4, "");
		}

		// -------------数据操作逻辑--------------

		// -------------表单设计逻辑--------------

		
		// 表单id
		String formcodex = dyform.getFormcode();
		// 表单应用页面中的html扩展展示信息
		String htmlinfo = dyform.getHtmltitleinfo_();
		// 表单中文名
		dyform.getFormname();
		// 表单数据展示的扩展sql脚本参数
		dyform.getOrderinfo();
		// 表单样式
		String style=dyform.getStyleinfo_();
		String styleurl=dyform.getStyleinfourl_();
		// 分页数
		int pagesize = dyform.getEachPageSize_();
		
		//展示表单所有字段
		DyFormColumn _all[] = dyform.getAllColumn_();
		// 展示表单字段-针对管理列表中的查询字段
		DyFormColumn _qc[] = dyform.getQueryColumn_();
		for (int i = 0; i < _qc.length; i++) {
			DyFormColumn _qc1 = _qc[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columncode = _qc1.getColumname();

			// 字段的html类型
			String htmltype = _qc1.getHtmltype();

			// 字段默认值
			String valuelist = _qc1.getValuelist();

		}

		// 展示表单字段-针对表单中的相关字段
		DyFormColumn _formx[] = dyform.getListColumn_();
		for (int i = 0; i < _qc.length; i++) {
			DyFormColumn _qc1 = _qc[i];
			// 字段ID 除了默认字段外，所有的设计字段都为 columnN的模式
			String columnid = _qc1.getColumnid();
			// 字段名（中文）
			String columnname = _qc1.getColumname();
			// 扩展脚本 字段获得焦点的事件时调度的脚本
			String focusScript = _qc1.getFocusScript();

			// 字段的html类型
			String htmltype = _qc1.getHtmltype();
			// 扩展脚本 字段装载时的初始化的脚本
			String initScript = _qc1.getInitScript();
			// 扩展脚本 字段失去焦点的事件时调度的脚本
			String losefocusScript = _qc1.getLoseFocusScript();

			// 字段默认值
			String valuelist = _qc1.getValuelist();
			// 字段展现中的x坐标
			double xoffset = _qc1.getXoffset();
			// 字段展现中的y坐标
			double yoffset = _qc1.getYoffset();

			// 是否隐蔽
			boolean hidden = _qc1.isHidden();
			// 是否非空
			// _qc1.isMusk();
			// 是否只读
			_qc1.isReadonly();
		}

		// 获得子表单
		DyForm _subDy[] = dyform.getSubform_();
		for (int i = 0; i < _subDy.length; i++) {
			DyForm dyformSub = _subDy[i];
		}

		// -------------表单设计逻辑--------------

	}
}
