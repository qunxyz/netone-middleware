package oe.cav.web.data.dyform.utils;

import com.rongji.webframework.ui.html.Button;
import com.rongji.webframework.ui.html.Table;
import com.rongji.webframework.ui.html.TableRow;

public class DymaticFormButton {
	public static Table addBottonSave(String buttonTitle, String commitAction,
			boolean needcolseButton) {
		Button butCreate = new Button();
		butCreate.setName("but1");
		butCreate.setValue(buttonTitle);

		Table table = new Table();
		TableRow rows = table.insertRow();
		rows.insertCol().setUI(butCreate);
		if (needcolseButton) {
			butCreate.setOnClick("checkAndCommit('" + commitAction + "')");
			Button butBack = new Button();
			butBack.setName("but2");
			butBack.setValue("¹Ø±Õ");
			butBack
					.setOnClick("javascript:window.opener.location.reload();window.close()");
			rows.insertCol().setUI(butBack);
		} else {
			butCreate.setOnClick("checkAndCommit('" + commitAction
					+ "');window.opener.location.reload();window.close();");
		}

		return table;
	}

	public static Table addBottonOpen(String buttonTitle, String commitAction) {
		Button butCreate = new Button();
		butCreate.setName("but1");
		butCreate.setValue(buttonTitle);
		butCreate.setOnClick("commitX('" + commitAction + "')");
		Table table = new Table();
		TableRow rows = table.insertRow();
		rows.insertCol().setUI(butCreate);
		return table;
	}
}
