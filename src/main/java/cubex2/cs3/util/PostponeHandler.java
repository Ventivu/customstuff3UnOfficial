package cubex2.cs3.util;

import com.google.common.collect.Lists;

import java.util.List;

public class PostponeHandler
{
    private static final PostponeHandler instance = new PostponeHandler();

    private List<PostponableTask> postponedTasks = Lists.newArrayList();

    private PostponeHandler()
    {

    }

    /**
     * Executes a task and adds it to the list of postponed tasks if it fails.
     *
     * @param task The task to execute
     *
     * @return true if task has been executed successfully, false otherwise
     */
    public static boolean executeTask(PostponableTask task)
    {
        boolean result = task.doWork();

        if (!result)
        {
            instance.postponedTasks.add(task);
        }

        return result;
    }

    public static void executePostponedTasks()
    {
        for (PostponableTask task : instance.postponedTasks)
        {
            boolean result = task.doWork();

            if (!result)
            {
                System.err.println("Postponed task failed!");
            }
        }

        instance.postponedTasks.clear();
    }
}
