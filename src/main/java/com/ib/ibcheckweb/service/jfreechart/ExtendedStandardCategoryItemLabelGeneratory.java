package com.ib.ibcheckweb.service.jfreechart;

import org.jfree.chart.labels.StandardCategoryItemLabelGenerator; 
import org.jfree.data.DataUtilities; 
import org.jfree.data.category.CategoryDataset; 
import org.jfree.util.PublicCloneable; 
import java.io.Serializable; 
import java.text.DateFormat; 
import java.text.MessageFormat; 
import java.text.NumberFormat; 
 

public class ExtendedStandardCategoryItemLabelGeneratory extends StandardCategoryItemLabelGenerator implements PublicCloneable, Cloneable, Serializable { 

 

    private static final long serialVersionUID = -7108591260223293197L; 

    private String labelFormat; 

    private String nullValueString; 

    private NumberFormat numberFormat; 

    private DateFormat dateFormat; 

    private NumberFormat percentFormat; 

    //新增加了参数，柱体series的值是否是总值的百分比，还是仅仅将原来的小数转化为百分数 

    private boolean isPercent = true; 

 

    public ExtendedStandardCategoryItemLabelGeneratory(String labelFormat, NumberFormat formatter, NumberFormat percentFormatter,boolean isPercent){ 

        super(labelFormat, formatter, percentFormatter); 

        if(labelFormat == null) { 

            throw new IllegalArgumentException("Null \'labelFormat\' argument."); 

        } else if(formatter == null) { 

            throw new IllegalArgumentException("Null \'formatter\' argument."); 

        } else if(percentFormatter == null) { 

            throw new IllegalArgumentException("Null \'percentFormatter\' argument."); 

        } else { 

            this.labelFormat = labelFormat; 

            this.numberFormat = formatter; 

            this.percentFormat = percentFormatter; 

            this.dateFormat = null; 

            this.nullValueString = "-"; 

            if (isPercent) 

                this.isPercent = isPercent; 

        } 

 

    } 

 

    @Override 

    protected String generateLabelString(CategoryDataset dataset, int row, int column) { 

        if(dataset == null) { 

            throw new IllegalArgumentException("Null \'dataset\' argument."); 

        } else { 

            String result = null; 

            Object[] items = this.createItemArray(dataset, row, column); 

            result = MessageFormat.format(this.labelFormat, items); 

            return result; 

        } 

    } 

    @Override 

    protected Object[] createItemArray(CategoryDataset dataset, int row, int column) { 

        Object[] result = new Object[]{dataset.getRowKey(row).toString(), dataset.getColumnKey(column).toString(), null, null}; 

        Number value = dataset.getValue(row, column); 

        if(value != null) { 

            if(this.numberFormat != null) { 

                result[2] = this.numberFormat.format(value); 

            } else if(this.dateFormat != null) { 

                result[2] = this.dateFormat.format(value); 

            } 

        } else { 

            result[2] = this.nullValueString; 

        } 

 

        if(value != null) { 

            double total = DataUtilities.calculateColumnTotal(dataset, column); 

            // 

            double percent = 0D;// / total; 

            if (this.isPercent) 

                //StandardCategoryItemLabelGenerator 返回的值是百分比 

                percent = value.doubleValue() / total; 

            else 

                //返回自己原来的值 

                percent = value.doubleValue(); 

            //格式化 

            result[3] = this.percentFormat.format(percent); 

        } 

 

        return result; 

    } 

 

 

} 

 
