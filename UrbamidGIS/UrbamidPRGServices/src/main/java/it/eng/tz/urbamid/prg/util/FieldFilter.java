package it.eng.tz.urbamid.prg.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FieldFilter<T> {

	private Integer flag;
	private T 		value;

	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}


	protected FieldFilter<String> getFilteredFieldEqual(String value){
		return getValue(value);
	}
	
	protected FieldFilter<Date> getFilteredFieldDate(Date value) {
		return getValueDate(value);
	}
	
	protected FieldFilter<Boolean> getFilteredFieldEqual(Boolean value) {
		return getValue(value);
	}
	
	protected FieldFilter<String> getFilteredFieldDateS(Date value) {
		return getValueDateString(value);
	}
	protected FieldFilter<String> getFilteredFieldTimeS(Date value) {
		return getValueTimeString(value);
	}
	protected FieldFilter<String> getFilteredFieldTimeStampS(Date value) {
		return getValueTimeStampString(value);
	}
	
	protected FieldFilter<Integer> getFilteredFieldEqual(Integer value){
		return getValue(value);
	}
	
	protected FieldFilter<Long> getFilteredFieldEqual(Long value){
		return getValue(value);
	}

	protected FieldFilter<Double> getFilteredFieldEqual(Double value){
		return getValue(value);
	}
	
	protected FieldFilter<Timestamp> getFilteredFieldEqual(Timestamp value){
		return getValue(value);
	}
	
	protected FieldFilter<String> getFilteredFieldLike(String value){
		if (value!=null) {
			value = "%" + value + "%";
		}
	
		return getValue(value);
	}
	
	protected FieldFilter<String> getFilteredFieldLikeL(String value){
		if (value!=null) {
			value = "%" + value;
		}
	
		return getValue(value);
	}
	
	protected FieldFilter<String> getFilteredFieldLikeR(String value){
		if (value!=null) {
			value = value + "%";
		}
	
		return getValue(value);
	}
	
	private FieldFilter<Boolean>  getValue(Boolean value) {
		FieldFilter<Boolean> f = new FieldFilter<>();
		Integer	flgValue= value==null ? 1 : 0;
	
		f.setFlag	(flgValue	);
		f.setValue	(value		);
	
		return f;
	}
	
	private FieldFilter<Integer>  getValue(Integer value) {
		FieldFilter<Integer> f = new FieldFilter<>();
		Integer	flgValue= value==null ? 1 : 0;
	
		f.setFlag	(flgValue	);
		f.setValue	(value		);
	
		return f;
	}
	
	private FieldFilter<Long>  getValue(Long value) {
		FieldFilter<Long> f = new FieldFilter<>();
		Integer	flgValue= value==null ? 1 : 0;
	
		f.setFlag	(flgValue	);
		f.setValue	(value		);
	
		return f;
	}

	private FieldFilter<Double>  getValue(Double value) {
		FieldFilter<Double> f = new FieldFilter<>();
		Integer	flgValue= value==null ? 1 : 0;
	
		f.setFlag	(flgValue	);
		f.setValue	(value		);
	
		return f;
	}
	
	private FieldFilter<Timestamp>  getValue(Timestamp value) {
		FieldFilter<Timestamp> f = new FieldFilter<>();
		Integer	flgValue= value==null ? 1 : 0;
	
		f.setFlag	(flgValue	);
		f.setValue	(value		);
	
		return f;
	}
	
	private FieldFilter<String>  getValue(String value) {
		FieldFilter<String> f = new FieldFilter<>();
		Integer	flgValue= value==null || value.isEmpty() ? 1 : 0;
	
		if (value==null) {
			value = "";
		}
	
		f.setFlag	(flgValue	);
		f.setValue	(value		);
	
		return f;
	}
	
	private FieldFilter<Date> getValueDate(Date value) {
		FieldFilter<Date> f = new FieldFilter<>();
		Integer	flgValue= value==null ? 1 : 0;
		if(value==null) {
			value = new Date();
		}
		f.setFlag	(flgValue	);
		f.setValue	(value		);
	
		return f;
	}
	
	private FieldFilter<String> getValueDateString(Date value) {
		return getValueDateTimeString(value, "yyyy-MM-dd");
	}
	
	private FieldFilter<String> getValueTimeString(Date value) {
		return getValueDateTimeString(value, "HH:mm:ss");
	}
	
	private FieldFilter<String> getValueTimeStampString(Date value) {
		return getValueDateTimeString(value, "yyyy-MM-dd HH:mm:ss");
	}
	
	private FieldFilter<String> getValueDateTimeString(Date value, String format) {
		FieldFilter<String> f = new FieldFilter<>();
		Integer	flgValue= value==null ? 1 : 0;
	
		String valueString = null;
		if (value!=null) {
			SimpleDateFormat sDF = new SimpleDateFormat(format);
			valueString = sDF.format(value);
		}
	
		f.setFlag	(flgValue	);
		f.setValue	(valueString);
	
		return f;
	}
}