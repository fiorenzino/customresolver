package weld.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

@Named
@SessionScoped
public class TestHandler implements Serializable {
	public TestHandler() {
		System.out.println("costruisco");
	}

	private DataModel<String> strings;

	public DataModel<String> getStrings() {
		if (this.strings == null) {
			this.strings = new ListDataModel<String>();
			List<String> str = new ArrayList<String>();
			for (int i = 0; i < 100; i++) {
				str.add("nome" + i);
			}
			this.strings.setWrappedData(str);
		}
		return strings;
	}

	public void setStrings(DataModel<String> strings) {
		this.strings = strings;
	}
}
