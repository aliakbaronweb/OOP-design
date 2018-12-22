package fragileBaseClasses;

public class Stack<T> {

    T[] contents = (T[]) new Object[1000];
    private int stackPointer = 0;

    public void push(T item) {
        assert stackPointer < contents.length : "push to full stack";
        contents[stackPointer++] = item;
    }

    public T pop() {
        assert stackPointer > 0 : "pop from empty stack";
        return contents[--stackPointer];
    }

    public void pushMany(T[] items) {
        assert (stackPointer + items.length) <= contents.length : "too many items";
        System.arraycopy(items, 0, contents, stackPointer, items.length);
        stackPointer += items.length;
    }

    public int size() {
        return contents.length;
    }

    public static void main(String[] args) {
        MonitorableStack<String> monitorableStack = new MonitorableStack<>();
        monitorableStack.pushMany(new String[]{"a", "b"});
        assert monitorableStack.maximumSizeSoFar() == 2
                : "Unexpected maximum size: " + monitorableStack.maximumSizeSoFar();
    }

}

class MonitorableStack<T> extends Stack<T> {
    private int highWatermark = 0;

    public int maximumSizeSoFar() {
        return highWatermark;
    }

    @Override
    public void push(T item) {
        if (size() > highWatermark)
            highWatermark = size();
        super.push(item);
    }
}