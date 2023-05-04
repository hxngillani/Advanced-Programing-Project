/*
HASSAN SHABIR
MASTER OF COMPUTER SCIENCE AND NETWROKING
2021-2022
*/

import java.util.List;
import java.util.stream.Stream;

public interface FrameworkStrategy<K, V> {
    
    /**
     * Generates a stream of job
     * 
     * @return the generated stream
     */
    public abstract Stream<AJob<K, V>> emit();
    
    /**
     * Prints the result of collect in a convenient way
     * 
     * @param collectedStream A collected stream
     */
    public abstract void output(Stream<Pair<K, List<V>>> collectedStream);
}
