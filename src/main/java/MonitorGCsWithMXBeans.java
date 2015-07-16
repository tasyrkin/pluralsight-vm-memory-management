import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class MonitorGCsWithMXBeans {
    public static void main(String[] args) {
        final List<GarbageCollectorMXBean> mxBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean mxBean : mxBeans) {
            System.out.println("name:" + mxBean.getName());
            System.out.println("collections count:" + mxBean.getCollectionCount());
            System.out.println("collections time:" + mxBean.getCollectionTime() + "ms");
            System.out.println("Pool names:");
            for (String poolName : mxBean.getMemoryPoolNames()) {
                System.out.println("\t" + poolName);
            }

            System.out.println();

        }


    }
}
