import edu.princeton.cs.algs4.Queue;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        // padding all strings
        int maxLen = Integer.MIN_VALUE;
        String[] strs = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            strs[i] = asciis[i];
            if (strs[i].length() > maxLen) {
                maxLen = strs[i].length();
            }
        }

        for (int i = 0; i < strs.length; i++) {
            if (strs[i].length() < maxLen) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < maxLen - strs[i].length(); j++) {
                    sb.append('_');
                }
                strs[i] = strs[i].concat(sb.toString());
            }
        }

        // sort
        for (int i = maxLen-1; i >= 0 ; i--) {
            sortHelperLSD(strs, i);
        }

        return strs;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        Queue<String>[] radix = new Queue[256];
        for (int i = 0; i < radix.length; i++) {
            radix[i] = new Queue();
        }

        for (int i = 0; i < asciis.length; i++) {
            int d = (int) (asciis[i].charAt(index));
            radix[d].enqueue(asciis[i]);
        }

        int j = 0;
        for (int i = 0; i < radix.length; i++) {
            while (!radix[i].isEmpty()) {
                asciis[j++] = radix[i].dequeue();
            }
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
