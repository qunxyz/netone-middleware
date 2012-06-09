package unittest;

import junit.framework.TestCase;

import com.lucaslee.report.CssEngine;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;

public class CssEnginTest extends TestCase {

	private Table getTable() {
		Table t = new Table();
		TableRow row = new TableRow();
		row.addCell(new TableCell("xxx1"));
		row.addCell(new TableCell("xxx2"));
		t.addRow(row);
		return t;
	}

	public void testApplyTableCss1() {
		Table t = getTable();
		t.setType(Report.DATA_TYPE);

		Table newTable = CssEngine.applyCss(t);

		printTable(newTable);
		assertTrue(Report.DATA_TYPE
				.equals(newTable.getCell(0, 0).getCssClass()));
	}

	public void testApplyTableCss2() {
		Table t = getTable();
		t.getRow(0).setType(Report.DATA_TYPE);

		Table newTable = CssEngine.applyCss(t);

		printTable(newTable);
		assertTrue(Report.DATA_TYPE
				.equals(newTable.getCell(0, 0).getCssClass()));
	}
	public void testApplyTableCss3() {
		Table t = getTable();
		t.setType(Report.GROUP_TOTAL_TYPE);
		t.getRow(0).setType(Report.DATA_TYPE);

		Table newTable = CssEngine.applyCss(t);

		printTable(newTable);
		assertTrue(Report.DATA_TYPE
				.equals(newTable.getCell(0, 0).getCssClass()));
	}
	public void testApplyRowCss() {

		TableRow row = new TableRow();
		row.addCell(new TableCell("xxx1"));
		row.addCell(new TableCell("xxx2"));
		row.setType(Report.DATA_TYPE);

		TableRow newRow = CssEngine.applylCss(row);

		printTableRow(newRow);

		assertTrue(Report.DATA_TYPE.equals(newRow.getCell(0).getCssClass()));

	}

	private void printTable(Table t) {
		System.out.println("table type:" + t.getType());
		for (int i = 0; i < t.getRowCount(); i++) {
			TableRow row = t.getRow(i);
			printTableRow(row);
		}
	}

	private void printTableRow(TableRow row) {
		System.out.println("row type:" + row.getType());
		for (int j = 0; j < row.getCellCount(); j++) {
			TableCell cell = row.getCell(j);
			System.out.println("cell cssClass:" + cell.getCssClass());
		}
	}
}
