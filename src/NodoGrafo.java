public class NodoGrafo<T> {

    private T dado;

    public NodoGrafo(T dado) {
        this.dado = dado;
    }

    public T getDado() {
        return dado;
    }

    @Override
    public String toString() {
        return dado.toString();
    }
}
