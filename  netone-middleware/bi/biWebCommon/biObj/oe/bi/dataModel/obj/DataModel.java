package oe.bi.dataModel.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.SequencedHashMap;

/**
 * 数据模型
 * 
 * @version 2006-06-23
 * @author chen.jia.xun (Robanco)
 * 
 */
public class DataModel implements Serializable {

	private String modelid;

	private String modelname;

	private String extendattribute;

	private String description;

	/**
	 * 经营分析1.0中用到，后期的版本中将丢弃
	 */
	private Map dataSets = new SequencedHashMap();

	/**
	 * 经营分析1.0中用到，后期的版本中将丢弃
	 */
	private Map linkers = new SequencedHashMap();

	private Map targetColumns = new SequencedHashMap();

	private Map dimColumns = new SequencedHashMap();

	/**
	 * 经营分析1.0中用到，后期的版本中将丢弃
	 */
	private Map optimizes = new SequencedHashMap();

	public void add(OptimizeTable optimize) {
		optimizes.put(optimize.getId(), optimize);
	}

	public void add(DataSet dataset) {
		dataSets.put(dataset.getId(), dataset);
	}

	public void add(Linker linker) {
		linkers.put(linker.getId(), linker);
	}

	public void add(TargetColumn targetColumn) {
		targetColumns.put(targetColumn.getId(), targetColumn);
	}

	public void add(DimColumn dimColumn) {
		dimColumns.put(dimColumn.getId(), dimColumn);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getModelid() {
		return modelid;
	}

	public void setModelid(String modelid) {
		this.modelid = modelid;
	}

	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	public Map getDataSets() {
		return dataSets;
	}

	public void setDataSets(Map dataSets) {
		this.dataSets = dataSets;
	}

	public Map getDimColumns() {
		return dimColumns;
	}

	public void setDimColumns(Map dimColumns) {
		this.dimColumns = dimColumns;
	}

	public Map getLinkers() {
		return linkers;
	}

	public void setLinkers(Map linkers) {
		this.linkers = linkers;
	}

	public Map getTargetColumns() {
		return targetColumns;
	}

	public void setTargetColumns(Map targetColumns) {
		this.targetColumns = targetColumns;
	}

	public Map getOptimizes() {
		return optimizes;
	}

	public void setOptimizes(Map optimizes) {
		this.optimizes = optimizes;
	}

}
