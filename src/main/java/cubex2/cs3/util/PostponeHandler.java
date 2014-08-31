package cubex2.cs3.util;

import com.google.common.collect.Lists;

import java.util.List;

public class PostponeHandler
{
    private List<PostponableTask> postponedTasks = Lists.newArrayList();

    public PostponeHandler()
    {

    }

    /**
     * Executes a task and adds it to the list of postponed tasks if it fails.
     *
     * @param task The task to execute
     *
     * @return true if task has been executed successfully, false otherwise
     */
    public boolean executeTask(PostponableTask task)
    {
        boolean result = task.doWork();

        if (!result)
        {
            postponedTasks.add(task);
        }

        return result;
    }

    public void executePostponedTasks()
    {
        for (PostponableTask task : postponedTasks)
        {
            boolean result = task.doWork();

            if (!result)
            {
                System.err.println("Postponed task failed!");
            }
        }

        postponedTasks.clear();
    }
}
