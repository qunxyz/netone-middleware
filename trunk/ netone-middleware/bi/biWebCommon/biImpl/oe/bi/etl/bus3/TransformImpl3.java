package oe.bi.etl.bus3;

import oe.bi.etl.bus.Transform;
import oe.bi.etl.bus.transformcore.GenerateSQL;
import oe.bi.etl.bus.transformcore.GenerateSQL2;
import oe.bi.etl.obj.AimCode;
import oe.bi.etl.obj.MiddleCode;


/**
 * ����Ŀ�����,SQL
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class TransformImpl3 implements Transform {

	public AimCode performTransform(MiddleCode midd) {
		// Ŀ��������
		AimCode aimExpression = new AimCode();

		aimExpression.setDatamodelid(midd.getDatamodelid());

		// �������յ�SQL���ʽ
		String sql[] = GenerateSQL2.makeSQL(midd);
		aimExpression.setSql(sql);

		// ����������Ϣ
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
