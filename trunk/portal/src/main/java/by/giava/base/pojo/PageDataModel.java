package by.giava.base.pojo;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import by.giava.base.model.Page;

public class PageDataModel extends ListDataModel<Page> implements
		SelectableDataModel<Page> {

	public PageDataModel() {
	}

	public PageDataModel(List<Page> pageslist) {
		super(pageslist);
	}

	public Page getRowData(String rowKey) {
		List<Page> pages = (List<Page>) getWrappedData();
		for (Page page : pages) {
			if (page.getId().equals(rowKey))
				return page;
		}
		return null;
	}

	public Object getRowKey(Page page) {
		return page.getId();
	}

}
