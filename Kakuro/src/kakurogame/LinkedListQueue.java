package kakurogame;

public class LinkedListQueue<T> {
    Node<T> first;
    Node<T> last;

    public LinkedListQueue() {
        first = null;
        last = null;
    }

    boolean isEmpty(){
        return first == null;
    }

    void enqueue(Node<T> node){
        if(!isEmpty())
            last.next = node;
        else
            first = node;
        last = node;
    }

    Node<T> dequeue(){
        Node<T> result;
        result = first;
        if(!isEmpty()){
            first = first.next;
            if(first == null)
                last = null;
        }
        return result;
    }
}
