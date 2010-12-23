package oe.bi.etl.bus;
import oe.bi.etl.obj.AimCode;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.exceptions.ErrorDataModelException;


public interface Cleaning {
    /**
     * �������ݣ�������Transform����SQL�ϼ�����������
     * @param initialSql
     * @param runtimeTargetColumn
     * @param runtimeDimColumn
     * @return
     */
	AimCode performCleaning(ChoiceInfo choice,AimCode aimcode) throws ErrorDataModelException;
}
