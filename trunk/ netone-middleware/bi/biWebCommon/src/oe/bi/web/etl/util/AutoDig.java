package oe.bi.web.etl.util;

import oe.bi.dao.ui.obj.NodeObj;

public interface AutoDig {
	// 计算出当前节点的下n层的节点总数
	int countCell(NodeObj objnode);

}
