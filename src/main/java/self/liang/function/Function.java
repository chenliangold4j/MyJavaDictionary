package self.liang.function;

public interface Function<T, E> {
    E apply(T x);
}