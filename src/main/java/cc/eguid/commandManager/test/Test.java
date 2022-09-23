package cc.eguid.commandManager.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cc.eguid.commandManager.CommandManager;
import cc.eguid.commandManager.CommandManagerImpl;
import cc.eguid.commandManager.commandbuidler.CommandBuidlerFactory;
import cc.eguid.commandManager.data.CommandTasker;
/**
 * 测试
 * @author eguid
 * @since jdk1.7
 * @version 2017年10月13日
 */
public class Test {

	public static void main(String[] args) throws InterruptedException {
		test1();
//		test2();
//		test3();
//		test4();
//		testStreamCommandAssmbly();
//		testBroken();
//		testBrokenMuti();
	}

	/**
	 * 命令组装器测试
	 * @throws InterruptedException
	 */
	public static void test1() throws InterruptedException{
		CommandManager manager = new CommandManagerImpl();
		Map<String,String> map = new HashMap<String,String>();
		map.put("appName", "test123");
		map.put("input", "rtsp://admin:zscj123a@192.168.1.64:554/h264/ch1/main/av_stream");
		map.put("output", "rtmp://localhost:1935/live/");
		map.put("codec", "h264");
		map.put("fmt", "flv");
		map.put("fps", "60");
		map.put("rs", "1920x1080");//640x360
		map.put("twoPart", "1");
		// 执行任务，id就是appName，如果执行失败返回为null
		String id = manager.start(map);
		System.out.println(System.currentTimeMillis());
		System.out.println(id);
		// 通过id查询
		CommandTasker info = manager.query(id);
		System.out.println(info);
		// 查询全部
		Collection<CommandTasker> infoList = manager.queryAll();
		System.out.println(infoList);
		Thread.sleep(30000*20);
		// 停止id对应的任务
		 manager.stop(id);
		System.out.println(System.currentTimeMillis());
	}

	public static void testN() throws InterruptedException {
		CommandManager manager = new CommandManagerImpl();
		// -rtsp_transport tcp
		//测试多个任何同时执行和停止情况
		//默认方式发布任务
		manager.start("nzy", "ffmpeg -rtsp_transport tcp -i rtsp://admin:zscj123a@192.168.1.164:554 -vcodec copy -f flv -an rtmp://localhost/live/nzy");

		Thread.sleep(30000*2);
		CommandTasker info = manager.query("nzy");
		System.out.println(info);
		// 停止全部任务
		manager.stopAll();
	}

	/**
	 * 默认方式，rtsp->rtmp转流单个命令测试
	 * @throws InterruptedException
	 */
	public static void test2() throws InterruptedException{
		CommandManager manager = new CommandManagerImpl();
		// -rtsp_transport tcp 
		//测试多个任何同时执行和停止情况
		//默认方式发布任务
		manager.start("tomcat", "ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat");
		
		Thread.sleep(30000);
		// 停止全部任务
		manager.stopAll();
	}

	/**
	 * 完整ffmpeg路径测试
	 * @throws InterruptedException
	 */
	public static void test4() throws InterruptedException{
		CommandManager manager = new CommandManagerImpl();
		// -rtsp_transport tcp 
		//测试多个任何同时执行和停止情况
		//默认方式发布任务
		manager.start("tomcat", "D:/TestWorkspaces/FFmpegCommandHandler/src/cc/eguid/FFmpegCommandManager/ffmpeg/ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat",true);
		
		Thread.sleep(30000);
		// 停止全部任务
		manager.stopAll();
	}
	
	/**
	 * rtsp-rtmp转流多任务测试
	 * @throws InterruptedException
	 */
	public static void test3() throws InterruptedException{
		CommandManager manager = new CommandManagerImpl();
		// -rtsp_transport tcp 
		//测试多个任何同时执行和停止情况
		//false表示使用配置文件中的ffmpeg路径，true表示本条命令已经包含ffmpeg所在的完整路径
		manager.start("nzy", "ffmpeg -rtsp_transport tcp -i rtsp://admin:zscj123a@192.168.1.164:554 -vcodec copy -f flv -an rtmp://localhost/live/nzy",false);
		manager.start("nzy1", "ffmpeg -rtsp_transport tcp -i rtsp://admin:zscj123a@192.168.1.164:554 -vcodec copy -f flv -an rtmp://localhost/live/nzy1",false);
		manager.start("nzy2", "ffmpeg -rtsp_transport tcp -i rtsp://admin:zscj123a@192.168.1.164:554 -vcodec copy -f flv -an rtmp://localhost/live/nzy2",false);
		manager.start("nzy3", "ffmpeg -rtsp_transport tcp -i rtsp://admin:zscj123a@192.168.1.164:554 -vcodec copy -f flv -an rtmp://localhost/live/nzy3",false);
		manager.start("nzy4", "ffmpeg -rtsp_transport tcp -i rtsp://admin:zscj123a@192.168.1.164:554 -vcodec copy -f flv -an rtmp://localhost/live/nzy4",false);
		manager.start("nzy5", "ffmpeg -rtsp_transport tcp -i rtsp://admin:zscj123a@192.168.1.164:554 -vcodec copy -f flv -an rtmp://localhost/live/nzy5",false);
		manager.start("nzy6", "ffmpeg -rtsp_transport tcp -i rtsp://admin:zscj123a@192.168.1.164:554 -vcodec copy -f flv -an rtmp://localhost/live/nzy6",false);
		manager.start("nzy7", "ffmpeg -rtsp_transport tcp -i rtsp://admin:zscj123a@192.168.1.164:554 -vcodec copy -f flv -an rtmp://localhost/live/nzy7",false);
		manager.start("nzy8", "ffmpeg -rtsp_transport tcp -i rtsp://admin:zscj123a@192.168.1.164:554 -vcodec copy -f flv -an rtmp://localhost/live/nzy8",false);
		manager.start("nzy9", "ffmpeg -rtsp_transport tcp -i rtsp://admin:zscj123a@192.168.1.164:554 -vcodec copy -f flv -an rtmp://localhost/live/nzy9",false);
		manager.start("nzy10", "ffmpeg -rtsp_transport tcp -i rtsp://admin:zscj123a@192.168.1.164:554 -vcodec copy -f flv -an rtmp://localhost/live/nzy10",false);
		
		Thread.sleep(30000*8);
		// 停止全部任务
		manager.stopAll();
	}
	
	/**
	 * 测试流式命令行构建器
	 * @throws InterruptedException
	 */
	public static void testStreamCommandAssmbly() throws InterruptedException {
		CommandManager manager = new CommandManagerImpl();
		manager.start("test1", CommandBuidlerFactory.createBuidler()
				.add("ffmpeg").add("-i","rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov")
				.add("-rtsp_transport","tcp")
				.add("-vcodec","copy")
				.add("-acodec","copy")
				.add("-f","flv")
				.add("-y").add("rtmp://106.14.182.20:1935/rtmp/test1"));
		Thread.sleep(30000);
		// 停止全部任务
		manager.stopAll();
	}
	/**
	 * 测试任务中断自动重启任务
	 * @throws InterruptedException 
	 */
	public static void testBroken() throws InterruptedException {
		CommandManager manager = new CommandManagerImpl();
		manager.start("test1", CommandBuidlerFactory.createBuidler()
				.add("ffmpeg").add("-i","rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov")
				.add("-rtsp_transport","tcp")
				.add("-vcodec","copy")
				.add("-acodec","copy")
				.add("-f","flv")
				.add("-y").add("rtmp://106.14.182.20:1935/rtmp/test1"));
		Thread.sleep(30000);
		// 停止全部任务
		manager.stopAll();
		manager.destory();
	}
	/**
	 * 批量测试任务中断自动重启任务
	 * @throws InterruptedException 
	 */
	public static void testBrokenMuti() throws InterruptedException {
		CommandManager manager = new CommandManagerImpl();
		manager.start("test1", CommandBuidlerFactory.createBuidler()
				.add("ffmpeg").add("-i","rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov")
				.add("-rtsp_transport","tcp")
				.add("-vcodec","copy")
				.add("-acodec","copy")
				.add("-f","flv")
				.add("-y").add("rtmp://106.14.182.20:1935/rtmp/test1"));
		manager.start("test2", CommandBuidlerFactory.createBuidler()
				.add("ffmpeg").add("-i","rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov")
				.add("-rtsp_transport","tcp")
				.add("-vcodec","copy")
				.add("-acodec","copy")
				.add("-f","flv")
				.add("-y").add("rtmp://106.14.182.20:1935/rtmp/test2"));
		manager.start("test3", CommandBuidlerFactory.createBuidler()
				.add("ffmpeg").add("-i","rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov")
				.add("-rtsp_transport","tcp")
				.add("-vcodec","copy")
				.add("-acodec","copy")
				.add("-f","flv")
				.add("-y").add("rtmp://106.14.182.20:1935/rtmp/test3"));
		manager.start("test4", CommandBuidlerFactory.createBuidler()
				.add("ffmpeg").add("-i","rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov")
				.add("-rtsp_transport","tcp")
				.add("-vcodec","copy")
				.add("-acodec","copy")
				.add("-f","flv")
				.add("-y").add("rtmp://106.14.182.20:1935/rtmp/test4"));
		manager.start("test5", CommandBuidlerFactory.createBuidler()
				.add("ffmpeg").add("-i","rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov")
//				.add("-rtsp_transport","tcp")
				.add("-vcodec","copy")
				.add("-acodec","copy")
				.add("-f","flv")
				.add("-y").add("rtmp://106.14.182.20:1935/rtmp/test5"));
		Thread.sleep(30000);
		// 停止全部任务
		manager.stopAll();
		manager.destory();
	}
}
