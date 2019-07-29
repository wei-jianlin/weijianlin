package weijianlin.concurrentProgrammingPractice.five;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author wei
 * 高效地,可扩展地,缓存之前的计算结果
 * @param <A>
 * @param <V>
 */
public class Memorize<A,V> implements Computable<A,V>{

	private final ConcurrentMap<A,Future<V>> cache = new ConcurrentHashMap<A,Future<V>>();
	
	private final Computable<A,V> c;
	
	public Memorize(Computable<A,V> c){
		this.c = c;
	}
	
	@Override
	public V compute(final A arg) throws InterruptedException {
		while(true){
			Future<V> f = cache.get(arg);
			if(f == null){
				Callable<V> eval = new Callable<V>(){
					@Override
					public V call() throws Exception {
						return c.compute(arg);
					}
				};
				FutureTask<V> ft = new FutureTask<V>(eval);
				//线程安全的检查在运行
				f = cache.putIfAbsent(arg, ft);
				if(f == null){
					f = ft;
					ft.run();
				}
			}
			try {
				//在线程返回之前一直阻塞
				return f.get();
			} catch (CancellationException e) {
				cache.remove(arg);
			} catch (ExecutionException e) {
				throw new InterruptedException(e.getMessage());
			}
		}
	}
	
}
