package oe.bi.etl.bus3;

import oe.bi.etl.bus.Transform;
import oe.bi.etl.bus.transformcore.GenerateSQL;
import oe.bi.etl.bus.transformcore.GenerateSQL2;
import oe.bi.etl.obj.AimCode;
import oe.bi.etl.obj.MiddleCode;


/**
 * 创建目标代码,SQL
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class TransformImpl3 implements Transform {

	public AimCode performTransform(MiddleCode midd) {
		// 目标代码对象
		AimCode aimExpression = new AimCode();

		aimExpression.setDatamodelid(midd.getDatamodelid());

		// 创建最终的SQL表达式
		String sql[] = GenerateSQL2.makeSQL(midd);
		aimExpression.setSql(sql);

		// 设置其它信息
		otherSetter(aimExpression, midd);

		return aimExpression;
	}

	public void otherSetter(AimCode aimExpression, MiddleCode midd) {
		aimExpression.setDimensionIds(midd.getDimensionColumnIds());
		aimExpression.setDimensionNames(midd.getDimensionColumnNames());
		aimExpression.setDimensionvalues(midd.getDimensionColumnValues());
		aimExpression.setDimensionColumnValueName(midd
				.getDimensionColumnValueName());
		aimExpression.setTargetColumnIds(midd.getTargetColumnIds());
		aimExpression.setTargetColumnnames(midd.getTargetColumnNames());

		aimExpression.setAlarms(midd.getAlarm());

		aimExpression.setDimensionTypes(midd.getDimensionTypes());
		aimExpression.setDimensionlevel(midd.getDimensionlevel());
	}

}
