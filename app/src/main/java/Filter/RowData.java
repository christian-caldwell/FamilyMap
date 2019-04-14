package Filter;

import java.util.Date;

/**
 * Created by seanwade on 3/26/16.
 */
public class RowData {

    private String filterText;

    public RowData(String filterText) {
        this.filterText = filterText;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }
}
