public class ArrayDeque<T> {

    private T[] arr;
    private int first;
    private int last;
    private int CAPACITY;
    private int numOfFirst;
    private int numOfLast;

    public ArrayDeque() {
        this.CAPACITY = 8;
        this.first = 0;
        this.last = 1;
        this.numOfFirst = 0;
        this.numOfLast = 0;
        this.arr = (T[]) new Object[CAPACITY];
    }

//    public ArrayDeque(ArrayDeque other) {
//        this.CAPACITY = 8;
//        this.first = 0;
//        this.last = 1;
//        this.numOfFirst = 0;
//        this.numOfLast = 0;
//        this.arr = (T[]) new Object[CAPACITY];
//        for (int i=0; i<other.size(); i++){
//            addLast((T) other.get(i));
//        }
//    }

    /*add item to the first*/
    public void addFirst(T item) {

        if (isFull()) {
            resize(CAPACITY * 2);
        }

        first = (first + 1) % CAPACITY;
        arr[first] = item;
        numOfFirst++;
    }

    /*add item to the last*/
    public void addLast(T item) {
        if (isFull()) {
            resize(CAPACITY * 2);
        }
        last = (last + CAPACITY - 1) % CAPACITY;
        arr[last] = item;
        numOfLast++;
    }

    /*weather the array is full*/
    private boolean isFull() {
        return numOfFirst + numOfLast == CAPACITY;
    }

    /*weather the array is empty*/
    public boolean isEmpty() {
        return numOfFirst + numOfLast == 0;
    }

    /*return the size of the list*/
    public int size() {
        return numOfFirst + numOfLast;
    }

    /*print the list*/
    public void printDeque() {
        int p = first;
        for (int i = 0; i < size(); i++) {
            System.out.print(arr[p] + " ");
            p = (p + CAPACITY - 1) % CAPACITY;
        }
        System.out.println();
    }

    private void resize(int newSize) {
        T[] newArr = (T[]) new Object[newSize];
        int p = first % CAPACITY;
        int newP = first % newSize;
        for (int i = 0; i < size(); i++) {
            newArr[newP] = arr[p];
            p = (p + CAPACITY - 1) % CAPACITY;
            newP = (newP + newSize - 1) % newSize;
        }
        // update
        first = first % newSize;
        last = (newP + 1) % newSize;
        CAPACITY = newSize;
        arr = newArr;
    }

    /*remove the first elem*/
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T item = arr[first];
        arr[first] = null;
        // shift left
        first = (first + CAPACITY - 1) % CAPACITY;
        numOfFirst--;
        if (CAPACITY > 8) {
            if (size()*1.0 / CAPACITY < 0.25) {
                resize(CAPACITY / 2);
            }
        }

        return item;
    }

    /*remove the last elem*/
    public T removeLast() {
        if (isEmpty())
            return null;

        T item = arr[last];
        arr[last] = null;
        // shift right
        last = (last + 1) % CAPACITY;
        numOfLast--;
        if (CAPACITY > 8) {
            if (size()*1.0 / CAPACITY < 0.25) {
                resize(CAPACITY / 2);
            }
        }
        return item;
    }

    /*get the item by index*/
    public T get(int index) {
        if (isEmpty() || index < 0 || index >= size()) {
            return null;
        }
        return arr[(first - index + CAPACITY) % CAPACITY];
    }

}
