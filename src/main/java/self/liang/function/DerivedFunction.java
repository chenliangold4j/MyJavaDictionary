package self.liang.function;

public class DerivedFunction implements Function<Double, Double> {
    private static final double DELTA_X = 0.000001;
    private Function<Double, Double> function;

    public DerivedFunction(Function<Double, Double> function) {
        this.function = function;
    }

    @Override
    public Double apply(Double x) {
        return (function.apply(x + DELTA_X) - function.apply(x)) / DELTA_X;
    }

    public static void main(String[] args) {
        System.out.println(new DerivedFunction(new DerivedFunction(x -> 3 * x * x * x + 2 * x * x + x + 1)).apply(2.0));
    }
}