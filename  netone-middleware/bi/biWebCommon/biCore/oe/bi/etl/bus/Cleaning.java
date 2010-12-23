package oe.bi.etl.bus;
import oe.bi.etl.obj.AimCode;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.exceptions.ErrorDataModelException;


public interface Cleaning {
    /**
     * 清理数据（继续在Transform过的SQL上加清理条件）
     * @param initialSql
     * @param runtimeTargetColumn
     * @param runtimeDimColumn
     * @return
     */
	AimCode performCleaning(ChoiceInfo choice,AimCode aimcode) throws ErrorDataModelException;
}
