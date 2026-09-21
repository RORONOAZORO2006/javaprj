package com.example;

import com.example.model.Task;
import com.example.service.TaskManager;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(System.in);

        // Pre-populate with sample data for demonstration
        manager.addTask("Set up Java environment", "Install JDK 17 and configure workspace");
        manager.addTask("Write DevOps pipeline script", "Configure CI/CD workflow for automated build");

        System.out.println("==========================================");
        System.out.println("     Welcome to Java Task Manager CLI     ");
        System.out.println("==========================================");

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Select an option (1-6): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    listTasks(manager);
                    break;
                case "2":
                    addNewTask(manager, scanner);
                    break;
                case "3":
                    markCompleted(manager, scanner);
                    break;
                case "4":
                    deleteTask(manager, scanner);
                    break;
                case "5":
                    searchTasks(manager, scanner);
                    break;
                case "6":
                    running = false;
                    System.out.println("Exiting Task Manager. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 6.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. View All Tasks");
        System.out.println("2. Add New Task");
        System.out.println("3. Mark Task as Completed");
        System.out.println("4. Delete Task");
        System.out.println("5. Search Tasks");
        System.out.println("6. Exit");
    }

    private static void listTasks(TaskManager manager) {
        List<Task> tasks = manager.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            System.out.println("\n--- Task List ---");
            tasks.forEach(System.out::println);
        }
    }

    private static void addNewTask(TaskManager manager, Scanner scanner) {
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        try {
            Task task = manager.addTask(title, description);
            System.out.println("Task added successfully: " + task);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void markCompleted(TaskManager manager, Scanner scanner) {
        System.out.print("Enter task ID to mark as completed: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (manager.markTaskCompleted(id)) {
                System.out.println("Task ID " + id + " marked as completed.");
            } else {
                System.out.println("Task ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a valid number.");
        }
    }

    private static void deleteTask(TaskManager manager, Scanner scanner) {
        System.out.print("Enter task ID to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (manager.deleteTask(id)) {
                System.out.println("Task ID " + id + " deleted successfully.");
            } else {
                System.out.println("Task ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a valid number.");
        }
    }

    private static void searchTasks(TaskManager manager, Scanner scanner) {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        List<Task> results = manager.searchTasks(keyword);
        if (results.isEmpty()) {
            System.out.println("No tasks matching '" + keyword + "' found.");
        } else {
            System.out.println("\n--- Search Results ---");
            results.forEach(System.out::println);
        }
    }
}
