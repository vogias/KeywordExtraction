package ke.processing;

import org.jdom.Element;

public class Record {

	private String recordName;
	protected Element metadata;

	public Element getMetadata() {
		return metadata;
	}

	public void setMetadata(Element metadata) {
		this.metadata = metadata;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

}
