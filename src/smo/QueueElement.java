package smo;

public class QueueElement<T>
{
    private T element;
    private int priority;

    public QueueElement(T element, int priority)
    {
        this.element = element;
        this.priority = priority;
    }
    public T getElement() {
        return element;
    }
    public void setElement(T element) {
        this.element= element;
    }
    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
