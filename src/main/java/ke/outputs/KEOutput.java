package ke.outputs;

import java.util.Map;

import ke.processing.Record;

public interface KEOutput {

	public void storeKERecord(Record metadata, Map<String, String> parameters);

}
