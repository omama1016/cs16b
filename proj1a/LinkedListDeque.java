public class LinkedListDeque<T> {
    private int size;
    private Node sentinel;

    private class Node{
        public T item;
        public Node next;
        public Node prev;
        public Node(){}
        public Node(T item){
            this.item = item;
        }
        public Node(T item, Node prev, Node next){
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    public LinkedListDeque(){
        this.sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        this.size = 0;
    }

    public LinkedListDeque(LinkedListDeque other){
        this();
        for (int i=0; i<other.size(); i++){
            addLast((T) other.get(i));
        }
    }

    /*methods*/
    public void addFirst(T item){
        if (isEmpty()){
            Node node = new Node(item, sentinel, sentinel);
            sentinel.next = node;
            sentinel.prev = node;
        }else{
            Node node = new Node(item, sentinel.next.prev, sentinel.next);
            node.next.prev = node;
            node.prev.next = node;
        }
        size++;
    }

    public void addLast(T item){
        if (isEmpty()){
            Node node = new Node(item, sentinel, sentinel);
            sentinel.next = node;
            sentinel.prev = node;
        }else{
            Node node = new Node(item, sentinel.prev, sentinel);
            sentinel.prev.next = node;
            sentinel.prev = node;
        }
        size++;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        Node p = sentinel.next;
        while (p != sentinel){
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    public T removeFirst(){
        if (isEmpty()){
            return null;
        }else {
            T item = sentinel.next.item;
            sentinel.next.next.prev = sentinel.next.prev;
            sentinel.next = sentinel.next.next;
            size--;
            return item;
        }
    }

    public T removeLast(){
        if (isEmpty()){
            return null;
        }else {
            T item = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size--;
            return item;
        }
    }

    public T get(int index){
        if (isEmpty() || index > size-1){
            return null;
        }
        Node p = sentinel;

        for (int i=0; i<=index; i++){
            p = p.next;
        }

        return p.item;
    }

    private T getRecursivehelp(Node first, int index, int start){
        if (start == index){
            return first.item;
        }
        return getRecursivehelp(first.next, index, ++start);
    }

    public T getRecursive(int index){
        if (isEmpty() || index > size-1) {
            return null;
        }
        return getRecursivehelp(sentinel.next, index, 0);
    }
}
