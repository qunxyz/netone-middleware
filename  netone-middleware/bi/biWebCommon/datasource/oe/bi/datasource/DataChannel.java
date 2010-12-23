package oe.bi.datasource;

import java.util.List;

public interface DataChannel {

	public List[] executeQuery(String targetGroupid, String[] condition,
			String[] targetColumn);

}
