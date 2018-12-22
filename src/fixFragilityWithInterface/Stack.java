package fixFragilityWithInterface;

public interface Stack<T> {
    void push(T item);

    T pop();

    void pushMany(T[] items);

    int size();

    public class Implementation<T> implements Stack<T> {

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
            return stackPointer;
        }

        public static void main(String[] args) {
            MonitorableStack<String> monitorableStack = new MonitorableStack<>();
            monitorableStack.pushMany(new String[]{"a", "b"});
            assert monitorableStack.maximumSizeSoFar() == 2
                    : "Unexpected maximum size: " + monitorableStack.maximumSizeSoFar();
        }

    }
}

    class MonitorableStack<T> implements Stack<T> {
        private int highWatermark = 0;

        private Stack.Implementation<T> stack = new Stack.Implementation<>();

        @Override
        public T pop() {
            return stack.pop();
        }

        @Override
        public int size() {
            return stack.size();
        }

        @Override
        public void pushMany(T[] items) {
            stack.pushMany(items);
            if (stack.size() > highWatermark)
                highWatermark = stack.size();
        }

        public int maximumSizeSoFar() {
            return highWatermark;
        }

        @Override
        public void push(T item) {
            if (size() > highWatermark)
                highWatermark = size();
            stack.push(item);
        }
    }