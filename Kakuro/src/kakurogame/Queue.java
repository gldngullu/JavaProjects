package kakurogame;

public class Queue<T> {
    Element<T> array[];
    int first;
    int last;
    int N;

    public Queue(int N) {
        array = new Element[N];
        this.N = N;
        first = 0;
        last = 0;
    }

    boolean isFull(){
        if(first == (last+1) %N )
            return true;
        else
            return false;
    }

    boolean isEmpty(){
        if(first == last)
            return true;
        else
            return false;
    }

    void enqueue(Element<T> element){
        if(!isFull()){
            array[last] = element;
            last = (last+1) % N;
        }
    }

    Element<T> dequeue(){
        Element<T> result;
        if(!isEmpty()){ //Yada exception throw edersin
            result = array[first];
            first = (first + 1) % N;
            return result;
        }
        return null;
    }
}
