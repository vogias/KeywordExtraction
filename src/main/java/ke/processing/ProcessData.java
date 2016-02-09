package ke.processing;

import ke.algorithms.KEAlgorithm;
import ke.inputs.KEInput;
import ke.outputs.KEOutput;
import ke.properties.Configuration;

public interface ProcessData {

	public void processData(KEInput input, KEAlgorithm algorithm, KEOutput output, Configuration conf);

}
