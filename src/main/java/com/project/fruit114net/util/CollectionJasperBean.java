package com.project.fruit114net.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CollectionJasperBean extends JRAbstractBeanDataSource {
    private Collection<?> data;
    private Iterator<?> iterator;
    private Object currentBean;


    /**
     *
     */

    /**
     *
     */
    public CollectionJasperBean(List<?> collection) {
        super(true);
        this.data = collection;

        if (this.data != null) {
            this.iterator = this.data.iterator();
        }
    }


    @Override
    public boolean next() {
        boolean hasNext = false;

        if (this.iterator != null) {
            hasNext = this.iterator.hasNext();

            if (hasNext) {
                this.currentBean = this.iterator.next();
            }
        }

        return hasNext;
    }


    @Override
    public Object getFieldValue(JRField field) throws JRException {
        return getFieldValue(currentBean, field);
    }


    @Override
    public void moveFirst() {
        if (this.data != null) {
            this.iterator = this.data.iterator();
        }
    }

    /**
     * Returns the underlying bean collection used by this data source.
     *
     * @return the underlying bean collection
     */
    public Collection<?> getData() {
        return data;
    }

    /**
     * Returns the total number of records/beans that this data source
     * contains.
     *
     * @return the total number of records of this data source
     */
    public int getRecordCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * Clones this data source by creating a new instance that reuses the same
     * underlying bean collection.
     *
     * @return a clone of this data source
     */
    public JRBeanCollectionDataSource cloneDataSource() {
        return new JRBeanCollectionDataSource(data);
    }
}
