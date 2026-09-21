package com.example.test;

import com.example.model.Task;
import com.example.service.TaskManager;

import java.util.List;
import java.util.Optional;

public class TestRunner {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("       Running TaskManager Tests          ");
        System.out.println("==========================================");

        testAddTask();
        testMarkCompleted();
        testDeleteTask();
        testSearchTasks();
        testInvalidTitle();

        System.out.println("\n------------------------------------------");
        System.out.printf("Test Results: %d Passed, %d Failed%n", passed, failed);
        System.out.println("------------------------------------------");

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void assertEquals(Object expected, Object actual, String testName) {
        if ((expected == null && actual == null) || (expected != null && expected.equals(actual))) {
            System.out.println("[PASS] " + testName);
            passed++;
        } else {
            System.err.printf("[FAIL] %s - Expected: %s, Got: %s%n", testName, expected, actual);
            failed++;
        }
    }

    private static void assertTrue(boolean condition, String testName) {
        if (condition) {
            System.out.println("[PASS] " + testName);
            passed++;
        } else {
            System.err.printf("[FAIL] %s - Expected: true, Got: false%n", testName);
            failed++;
        }
    }

    private static void testAddTask() {
        TaskManager manager = new TaskManager();
        Task t1 = manager.addTask("Buy groceries", "Milk, Bread, Eggs");
        assertEquals(1, t1.getId(), "testAddTask - ID assignment");
        assertEquals("Buy groceries", t1.getTitle(), "testAddTask - Title verification");
        assertEquals(1, manager.getTaskCount(), "testAddTask - Task count");
    }

    private static void testMarkCompleted() {
        TaskManager manager = new TaskManager();
        Task t1 = manager.addTask("Task 1", "Desc 1");
        assertTrue(!t1.isCompleted(), "testMarkCompleted - Initial uncompleted state");
        boolean success = manager.markTaskCompleted(t1.getId());
        assertTrue(success, "testMarkCompleted - Status update success flag");
        assertTrue(t1.isCompleted(), "testMarkCompleted - Completed state flag");
    }

    private static void testDeleteTask() {
        TaskManager manager = new TaskManager();
        Task t1 = manager.addTask("Task to delete", "Desc");
        assertEquals(1, manager.getTaskCount(), "testDeleteTask - Pre-delete count");
        boolean deleted = manager.deleteTask(t1.getId());
        assertTrue(deleted, "testDeleteTask - Delete success flag");
        assertEquals(0, manager.getTaskCount(), "testDeleteTask - Post-delete count");
    }

    private static void testSearchTasks() {
        TaskManager manager = new TaskManager();
        manager.addTask("Learn Java", "Object-oriented programming");
        manager.addTask("Learn Python", "Data analysis");
        List<Task> results = manager.searchTasks("java");
        assertEquals(1, results.size(), "testSearchTasks - Filter count");
        assertEquals("Learn Java", results.get(0).getTitle(), "testSearchTasks - Filter result match");
    }

    private static void testInvalidTitle() {
        TaskManager manager = new TaskManager();
        try {
            manager.addTask("   ", "Empty title task");
            assertTrue(false, "testInvalidTitle - Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(true, "testInvalidTitle - Threw expected exception");
        }
    }
}
