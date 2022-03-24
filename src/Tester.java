/*
public class Tester {

    public static void main(String[] args){

        // create Task
        Task task1 = new Task(1, "a");
        Task task2 = new Task(2, "b");
        Task task3 = new Task(5, "c");
        Task task4 = new Task(8, "d");
        Task task5 = new Task(0, "e");
        Task task6 = new Task(9, "f");
        Task task7 = new Task(3, "g");
        Task task8 = new Task(5, "h");
        Task task9 = new Task(10, "i");
        Task task10 = new Task(7, "j");
        Task task11 = new Task(1, "f");

        // create TaskElement
        TaskElement te1 = new TaskElement(task1);
        TaskElement te2 = new TaskElement(task2);
        TaskElement te3 = new TaskElement(task3);
        TaskElement te4 = new TaskElement(task4);
        TaskElement te5 = new TaskElement(task5);
        TaskElement te6 = new TaskElement(task6);
        TaskElement te7 = new TaskElement(task7);
        TaskElement te8 = new TaskElement(task8);
        TaskElement te9 = new TaskElement(task9);
        TaskElement te10 = new TaskElement(task10);

        // create TaskHeap
        TaskElement[] arr = { te1, te2, te3, te4, te5, te6, te7, te8, te9 };
        TaskHeap hp1 = new TaskHeap(arr);

        // create TaskQueue
        TaskQueue q1 = new TaskQueue();
        q1.enqueue(te1);
        q1.enqueue(te2);

        // test compareTo
        System.out.println("test compareTo:");
        System.out.println(task1.compareTo(task11));  // expected output: -5
        System.out.println();
        System.out.println();

        // test TaskQueue
        System.out.println("test TaskQueue:");
        q1.printQueue();  // expected output: [ (task: a, priority: 1) --> (task: b, priority: 2) --> null ]
        q1.dequeue();
        q1.printQueue();  // expected output: [ (task: b, priority: 2) --> null ]
        q1.dequeue();
        q1.printQueue();  // expected output: [ null ]
        System.out.println();
        System.out.println();

        // test TaskHeap
        System.out.println("test TaskHeap:");
        hp1.printHeap();                          // expected output: [10, 8, 9, 5, 0, 5, 3, 1, 2]
        System.out.println(hp1.extractMax());     // expected output: task: i, priority: 10
        hp1.printHeap();                          // expected output:[9, 8, 5, 5, 0, 2, 3, 1]
        hp1.insert(te10);
        hp1.printHeap();                          // expected output:[9, 8, 5, 7, 0, 2, 3, 1, 5]


        // Note: the remove function can be implemented in many ways.
        //       just to make sure that there is a valid heap in the end.

        hp1.remove(2);
        hp1.printHeap();                          // expected output:[9, 7, 5, 5, 0, 2, 3, 1]
        hp1.remove(4);
        hp1.printHeap();                          // expected output:[9, 7, 5, 1, 0, 2, 3]
        hp1.remove(2);
        hp1.printHeap();                          // expected output:[9, 3, 5, 1, 0, 2]

        */
/* Extra functions for testing:
         *
         * TaskQueue:
         *
         *	public void printQueue(){
         *		System.out.print("[ ");
         *		TaskElement cur = first;
         *		while (cur != null){
         *			System.out.print("(");
         *			System.out.print(cur.toString());
         *			System.out.print(")");
         *			cur = cur.next;
         *			System.out.print(" --> ");
         *		}
         *		System.out.println("null ]");
         *	}
         *
         *
         * TaskHeap:
         *
         *	public void printHeap(){
         *		String str = "[";
         *		for(int i=1; i<=size; i++){
         *			str+= heap[i].t.priority;
         *			if(i!=size) str+= ", ";
         *		}
         *		str+= "]";
         *		System.out.println(str);
         *	}
         *//*

    }
}

*/
