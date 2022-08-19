import java.util.ArrayList;

public class HuffmanDecoder {
    public static void main(String[] args) {
        String input = args[0];
        String output = args[1];

        ObjectReader objectReader = new ObjectReader(input);
        BinaryTrie binaryTrie = (BinaryTrie) objectReader.readObject();
        BitSequence hugeSequence = (BitSequence) objectReader.readObject();

        ArrayList<Character> symbols = new ArrayList<>();

        while (hugeSequence.length() != 0) {
            Match match = binaryTrie.longestPrefixMatch(hugeSequence);
            symbols.add(match.getSymbol());
            hugeSequence = hugeSequence.allButFirstNBits(match.getSequence().length());
        }

        char[] res = new char[symbols.size()];
        for (int i = 0; i < symbols.size(); i++) {
            res[i] = symbols.get(i);
        }

        FileUtils.writeCharArray(output, res);
    }
}
