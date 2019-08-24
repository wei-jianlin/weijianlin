package weijianlin.concurrentProgrammingPractice.six;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author wei
 * 每需要下载一个图像,就创建一个独立的任务,只要任何一个图像下载完成,就立刻呈现.
 */
public class Renderer {

	//扩展了Executor接口,添加了一些用于生命周期管理的方法,同时要有一些用于任务提交的便利方法
	private final ExecutorService executor;
	
	public Renderer(ExecutorService executor){
		this.executor = executor;
	}
	
	void renderPager(CharSequence source) throws ExecutionException{
		final List<ImageInfo> info = new ArrayList<ImageInfo>();
		//CompletionService整合了Executor和BlockingQueue的功能,你可以将Callable任务提交给他执行,
		//然后使用类似于队列中的take和poll方法,在结果完整可用时获得这个结果,像一个打包的Future.
		CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executor);
		for(final ImageInfo imageInfo : info){
			completionService.submit(new Callable<ImageData>(){
				@Override
				public ImageData call() throws Exception {
					return imageInfo.downloadImage();
				}
			});
		}
		renderText(source);
		try {
			for(int i = 0; i < info.size(); i++){
				Future<ImageData> future = completionService.take();
				ImageData imageData = future.get();
				renderImage(imageData);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	void renderText(CharSequence source){
		//渲染文本
	}
	
	void renderImage(ImageData imageData){
		//渲染图片
	}
}

class ImageInfo{
	
	public ImageData downloadImage(){
		return new ImageData();
	}
}

class ImageData{
	
}
