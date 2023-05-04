/*
HASSAN SHABIR
MASTER OF COMPUTER SCIENCE AND NETWROKING
2021-2022
*/

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class JobSchedFramework<K, V> {
    
    // The concrete strategy to be used 
    private final FrameworkStrategy<K, V> strategy;
    
    /**
     * Initializes a new job scheduler using the passed concrete strategy
     * 
     * @param strategy The concrete scheduler strategy
     */
    public JobSchedFramework(FrameworkStrategy<K, V> strategy) {
            this.strategy = strategy;
    }
    
    /**
     * Executes the jobs received from emit by invoking execute on
     * them, and returns a single stream of key/value pairs obtained by concatenating the
     * output of the jobs
     * 
     * @param input  The stream obtained from emit() passed as argument to compute() as per requirements
     * @return The computed stream
     */
    public Stream<Pair<K, V>> compute(Stream<AJob<K, V>> input) {
        return input
                .flatMap(x->x.execute());
    }
    
    /**
     * Takes as input the output of compute and groups all the pairs with
     * the same keys in a single pair, having the same key and the list of all values
     * 
     * @param computedStream The computed stream obtained from compute()
     * @return The collected stream
     */
    
 public Stream<Pair<K, List<V>>> collect(Stream<Pair<K, V>> computedStream) {
    return computedStream
            .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())))
            .entrySet().stream()
            .map(entry -> new Pair(entry.getKey(), entry.getValue()));
}
    
    /**
     * Executes the framework's operations
     */
    public void runMain() {
        
        strategy.output(collect(compute(strategy.emit())));
    }
}
