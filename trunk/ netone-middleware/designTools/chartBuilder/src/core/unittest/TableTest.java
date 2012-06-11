package unittest;

import junit.framework.TestCase;

import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;

public class TableTest extends TestCase {
	public void testGetRotateTable() {
		int colCount = 3;
		int rowCount = 4;
		Table originalTable = new Table();
		for (int i = 0; i < rowCount; i++) {
			TableRow r = new TableRow();
			for (int j = 0; j < colCount; j++) {
				r.addCell(new TableCell(getCellContent(i, j)));
			}
			originalTable.addRow(r);
		}
		Table rotatedTable = originalTable.getRotateTable();
		System.out.println("originalTable:" + originalTable);
		System.out.println("rotatedTable:" + rotatedTable);

		assertEquals(originalTable.getCell(0, 0), rotatedTable.getCell(0, 0));
		assertEquals(originalTable.getCell(rowCount - 1, 0), rotatedTable
				.getCell(0, rowCount - 1));
		assertEquals(originalTable.getCell(colCount - 1, 0), rotatedTable
				.getCell(0, colCount - 1));
	}

	public void testClone() throws CloneNotSupportedException {
		Table originalTable = new Table(2, 2);
		Table clonedTable = (Table) originalTable.clone();
		originalTable.setBorder(Table.ALIGN_CENTER);
		clonedTable.setBorder(Table.ALIGN_LEFT);
		System.out.println(originalTable.getBorder() + ":"
				+ clonedTable.getBorder());
		assertNotSame(originalTable.getBorder(), clonedTable.getBorder());

	}

	public void testCloneAll() {
		Table originalTable = new Table(2, 2);
		Table clonedTable = originalTable.cloneAll();
		System.out.println(originalTable.getRowCount() + ":"
				+ clonedTable.getRowCount());
		clonedTable.deleteRow(clonedTable.getRow(0));
		System.out.println(originalTable.getRowCount() + ":"
				+ clonedTable.getRowCount());
		assertNotSame(clonedTable.getRowCount(), originalTable.getRowCount());
	}

	private String getCellContent(int row, int col) {
		return row + "," + col;
	}
}
