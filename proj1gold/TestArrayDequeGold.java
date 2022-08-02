import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testStudentDeque(){
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();

        // store the operator sequence...
        ArrayDequeSolution<String> sequences = new ArrayDequeSolution<>();

        int i = 1;
        while (true) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.25) {
                solution.addFirst(i);
                student.addFirst(i);
                sequences.addLast("addFirst(" + i + ")");
            } else if (numberBetweenZeroAndOne < 0.5) {
                solution.addLast(i);
                student.addLast(i);
                sequences.addLast("addLast(" + i + ")");
            } else if (numberBetweenZeroAndOne < 0.75) {
                if (solution.size() > 0 && student.size() > 0) {
                    Integer r1 = solution.removeFirst();
                    Integer r2 = student.removeFirst();
                    if (r1.equals(r2)) {
                        sequences.addLast("removeFirst(" + i + ")");
                    } else {
                        String message = "\n";
                        for (int j = 0; j < sequences.size(); j++) {
                            message = message + sequences.removeFirst() + "\n";
                        }
                        message += "removeFirst()";
                        assertEquals(message, r1, r2);
                    }
                }

            } else {
                if (solution.size() > 0 && student.size() > 0) {
                    Integer r1 = solution.removeLast();
                    Integer r2 = student.removeLast();
                    if (r1.equals(r2)) {
                        sequences.addLast("removeLast(" + i + ")");
                    } else {
                        String message = "\n";
                        for (int j = 0; j < sequences.size(); j++) {
                            message = message + sequences.removeFirst() + "\n";
                        }
                        message += "removeLast()";
                        assertEquals(message, r1, r2);
                    }
                }
            }
            i++;
        }
    }
}
