package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class GSResFeatureTypeDB implements Serializable {

	private static final long serialVersionUID = 7413529617284971432L;

	public static final String PROJ_POLICY_REPROJECT_NATIVE_TO_DECLARED = "REPROJECT_TO_DECLARED";
	public static final String PROJ_POLICY_REPROJECT_FORCE_DECLARED = "FORCE_DECLARED";
	public static final String PROJ_POLICY_KEEP_NATIVE = "NONE";

	public static final String PROJ_SRID = "EPSG:4326";
	
	private String name;
	
	private String nativeName;
	
	private GSNameSpace namespace;
	
	private String title;
	
	@SerializedName(value = "abstract")
	private String abs;
	
	private GSKeywords keywords;
	
	private String srs;
	
	private GSBoundingBox nativeBoundingBox;
	
	private GSBoundingBox latLonBoundingBox;
	
	private String projectionPolicy;
	
	private boolean enabled;
	
	private boolean advertised;
	
	private GSMetadata metadata;
	
	private GSStore store;
	
	private boolean serviceConfiguration;
	
	private int maxFeatures;
	
	private int numDecimals;
	
	private boolean padWithZeros;
	
	private boolean forcedDecimal;
	
	private boolean overridingServiceSRS;
	
	private boolean skipNumberMatched;
	
	private boolean circularArcPresent;
	
	private boolean encodeMeasures;
	
	private GSAttributes attributes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNativeName() {
		return nativeName;
	}

	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}

	public GSNameSpace getNamespace() {
		return namespace;
	}

	public void setNamespace(GSNameSpace namespace) {
		this.namespace = namespace;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public GSKeywords getKeywords() {
		return keywords;
	}

	public void setKeywords(GSKeywords keywords) {
		this.keywords = keywords;
	}

	public String getSrs() {
		return srs;
	}

	public void setSrs(String srs) {
		this.srs = srs;
	}

	public GSBoundingBox getNativeBoundingBox() {
		return nativeBoundingBox;
	}

	public void setNativeBoundingBox(GSBoundingBox nativeBoundingBox) {
		this.nativeBoundingBox = nativeBoundingBox;
	}

	public GSBoundingBox getLatLonBoundingBox() {
		return latLonBoundingBox;
	}

	public void setLatLonBoundingBox(GSBoundingBox latLonBoundingBox) {
		this.latLonBoundingBox = latLonBoundingBox;
	}

	public String getProjectionPolicy() {
		return projectionPolicy;
	}

	public void setProjectionPolicy(String projectionPolicy) {
		this.projectionPolicy = projectionPolicy;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAdvertised() {
		return advertised;
	}

	public void setAdvertised(boolean advertised) {
		this.advertised = advertised;
	}

	public GSMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(GSMetadata metadata) {
		this.metadata = metadata;
	}

	public GSStore getStore() {
		return store;
	}

	public void setStore(GSStore store) {
		this.store = store;
	}

	public boolean isServiceConfiguration() {
		return serviceConfiguration;
	}

	public void setServiceConfiguration(boolean serviceConfiguration) {
		this.serviceConfiguration = serviceConfiguration;
	}

	public int getMaxFeatures() {
		return maxFeatures;
	}

	public void setMaxFeatures(int maxFeatures) {
		this.maxFeatures = maxFeatures;
	}

	public int getNumDecimals() {
		return numDecimals;
	}

	public void setNumDecimals(int numDecimals) {
		this.numDecimals = numDecimals;
	}

	public boolean isPadWithZeros() {
		return padWithZeros;
	}

	public void setPadWithZeros(boolean padWithZeros) {
		this.padWithZeros = padWithZeros;
	}

	public boolean isForcedDecimal() {
		return forcedDecimal;
	}

	public void setForcedDecimal(boolean forcedDecimal) {
		this.forcedDecimal = forcedDecimal;
	}

	public boolean isOverridingServiceSRS() {
		return overridingServiceSRS;
	}

	public void setOverridingServiceSRS(boolean overridingServiceSRS) {
		this.overridingServiceSRS = overridingServiceSRS;
	}

	public boolean isSkipNumberMatched() {
		return skipNumberMatched;
	}

	public void setSkipNumberMatched(boolean skipNumberMatched) {
		this.skipNumberMatched = skipNumberMatched;
	}

	public boolean isCircularArcPresent() {
		return circularArcPresent;
	}

	public void setCircularArcPresent(boolean circularArcPresent) {
		this.circularArcPresent = circularArcPresent;
	}

	public boolean isEncodeMeasures() {
		return encodeMeasures;
	}

	public void setEncodeMeasures(boolean encodeMeasures) {
		this.encodeMeasures = encodeMeasures;
	}

	public GSAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(GSAttributes attributes) {
		this.attributes = attributes;
	}
	
}
