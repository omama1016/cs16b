import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols){
        Map<Character, Integer> table = new HashMap<>();
        for (char c : inputSymbols) {
            if (!table.containsKey(c)) {
                table.put(c, 1);
            } else {
                table.put(c, table.get(c) + 1);
            }
        }
        return table;
    }
    public static void main(String[] args) {
        String filename = args[0];
        char[] inputs = FileUtils.readFile(filename);
        Map<Character, Integer> freqTable = buildFrequencyTable(inputs);
        BinaryTrie binaryTrie = new BinaryTrie(freqTable);
        ObjectWriter objectWriter = new ObjectWriter(filename + ".huf");
        objectWriter.writeObject(binaryTrie);

        Map<Character, BitSequence> lookupTable = binaryTrie.buildLookupTable();
        List<BitSequence> bitSequences = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++) {
            bitSequences.add(lookupTable.get(inputs[i]));
        }
        BitSequence hugeBitSequence = BitSequence.assemble(bitSequences);
        objectWriter.writeObject(hugeBitSequence);
    }
}
