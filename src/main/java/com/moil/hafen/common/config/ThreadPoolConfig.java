package com.moil.hafen.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {
    /**
     * 实现多线程的方式
     *  1）继承Thread
     *  2）实现Runnable接口
     *  3）实现Callable接口+FutureTask
     *  4）线程池提交任务
     *      实际开发中。应该蒋所有的多线程异步任务都交给线程池处理。资源控制,性能稳定。
     *
     *  1.如何创建线程池[ExecutorService]
     *      1). Executors.newFixedThreadPool()
     *      2). new ThreadPoolExecutor 七大参数
     *          int corePoolSize: 【10】核心线程数。线程池创建好以后就准备就绪的线程数量。等待来接受异步任务去执行。只要线程池不销毁，核心线程数一直存在。
     *          int maximumPoolSize: 【200】 最大线程数量。控制资源。
     *          long keepAliveTime: 存活时间。如果当前的线程数量大于核心线程数量。释放空闲线程。只要线程空闲大于指定的空闲时间keepAliveTime。释放的线程指的是最大的大小减去核心线程数量。
     *          TimeUnit unit: 存活时间的单位。
     *          BlockingQueue<Runnable> workQueue: 阻塞队列。如果任务有很多（超过了最大线程数），就会将目前多的任务放在队列中。只要有空闲的线程，就会去队列中取出新的任务执行。
     *          ThreadFactory threadFactory: 线程的创建工厂。一般都是默认的。具体是创建线程，例如可以自定义线程的名称等。
     *          RejectedExecutionHandler handler: 如果队列workQueue满了，按照指定的拒绝策略，拒绝执行任务。
     *     3).运行流程
     *          1.线程池创建，准备好core数量的核心线程，准备接收任务。
     *          2.core满了，就将再进来的任务放入阻塞队列中。空闲的core就会自己去阻塞队列获取任务执行。
     *          3.阻塞队列也满了，就会直接开新的线程去执行，最大只能开到max指定的数量。
     *          4.max满了后，就用RejectedExecutionHandler拒绝策略
     *          5.max执行完成后，有很多空闲的线程，在指定的时间keepAliveTime以后，还是空闲的话，就释放max减去core这些线程。
     *  2.案例： 一个线程池core=7,max=20,queue=50,100个并发怎么分配
     *         1).7个任务会立刻执行
     *         1).50个任务会放入到队列中。
     *         1).新开13个线程去执行。
     *         1).剩下的30个使用拒绝策略执行，一般都是丢弃。
     */
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfigProperties threadPoolConfigProperties) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                threadPoolConfigProperties.getCorePoolSize(),
                threadPoolConfigProperties.getMaxPoolSize(),
                threadPoolConfigProperties.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10000),//默认是Integer的最大值，内存肯定会不够。所以需要指定一个数量
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());//新进来的任务，直接丢弃
        return executor;
    }
}