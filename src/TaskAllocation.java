/**
 * A task allocation queue which holds the tasks in two parallel queues.
 * One according to priority and one according to order of creation.
 * At any given time, a task at the front of either line may be removed from the data structure.
 * 
 * 
 * 
 *
 */
public class TaskAllocation{

	/**
	 * The heap and queue which compose the data structure.
	 * The most important thing to observe is:
	 * 	A TaskElement exists in the queue if and only if it also exists in the heap.
	 */
	TaskHeap heap;
	TaskQueue q;
	
	/**
	 * Creates an empty task allocation queue
	 */
	public TaskAllocation(){
		this.heap = new TaskHeap();
		this.q = new TaskQueue();
	}
	
	
	/**
	 * Creates a task allocation queue from an a array of tasks, ordered according to the creation time 
	 * @param arr a given array of TaskElements. The heapIndex field of the elements in arr might be incorrect
	 */
	public TaskAllocation(TaskElement [] arr){
		this.q = new TaskQueue();
		this.heap = new TaskHeap(arr);
		for (TaskElement task : arr) {
			for(int i = 1; this.heap.heap[i] != null; i++){
				if (task.t.compareTo(this.heap.heap[i].t) == 0){
					task.heapIndex = this.heap.heap[i].heapIndex;
					this.q.enqueue(task);
				}
			}
		}
	}

	/**
	 * Adds a new Task to the data structure.
	 * The Task is entered (wrapped by a  TaskElement) to the back of the queue
	 *  and into the heap, according to its priority.
	 * 
	 * @param c
	 */
	public void addTask(Task c){
		//Your code comes here
		TaskElement cNode = new TaskElement(c);
		this.heap.insert(cNode);
		this.q.enqueue(cNode);
	}
	
	/**
	 * Removes the task with the highest priority from the data structure.
	 * The task must be removed both from the heap and the queue.
	 * 
	 * @return the task with the highest priority.
	 */
	public Task allocatePriorityTask(){
		//Your code comes here
		TaskElement maxTask = this.heap.extractMax();
		TaskElement firstTask = this.q.peek();
		TaskElement currentTask = firstTask;
		firstTask.next = currentTask;
		while (currentTask != null){
			if (currentTask.t.compareTo(maxTask.t) == 0) {
				currentTask.prev.next = currentTask.next;
				currentTask.next.prev = currentTask.prev;
				this.q.first = firstTask;
				return currentTask.t;
			}
			currentTask = currentTask.next;
		}

		return null;
	}
	
	/**
	 * Removes the task which was created first to the data structure.
	 * The task must be removed both from the heap and the queue.
	 * 
	 * @return the task which arrived first to the data structure
	 */
	public Task allocateRegularTask(){
		//Your code comes here
		TaskElement firstTaskQueue = this.q.dequeue();
		this.heap.remove(firstTaskQueue.heapIndex);
		return firstTaskQueue.t;
	}


	/**
	 * A basic test to check your class.
	 * Expected outcome:
	 * task: Solve a problem in production, priority: 100
	 * task: Add a new feature, priority: 10
	 * task: Code Review, priority: 3
	 * task: Analyze performance, priority: 20
	 * task: Move to the new Kafka server, priority: 2
	 */
    public static void main (String[] args){

    	Task a = new Task(10, "Add a new feature");
    	Task b = new Task(3, "Code Review");
    	Task c = new Task(2, "Move to the new Kafka server");
     	Task d = new Task(100, "Solve a problem in production");
    	Task e = new Task(20, "Analyze performance");
    	
    	TaskElement [] arr = {new TaskElement(a), new TaskElement(b), new TaskElement(c)};
    	TaskAllocation q = new TaskAllocation(arr);
   
    	q.addTask(d);
    	System.out.println(q.allocatePriorityTask());
    	System.out.println(q.allocatePriorityTask());
    	q.addTask(e);
    	System.out.println(q.allocateRegularTask());
    	System.out.println(q.allocatePriorityTask());
    	System.out.println(q.allocateRegularTask());
    }
}
